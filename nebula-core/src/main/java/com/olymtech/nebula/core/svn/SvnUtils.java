/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.core.svn;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.*;

import java.io.File;

/**
 * Created by Gavin on 2015-10-21 16:05.
 */
public class SvnUtils {

    private static Logger logger = LoggerFactory.getLogger(SvnUtils.class);

    public SvnUtils(){
        /** 默认加载log4j */
        PropertyConfigurator.configure(SvnUtils.class.getClassLoader().getResource("log4j.properties"));

    }

    /**
     * 初始化svn
     */
    public static void initSvnLibrary(){
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
    }

    /** 创建 SvnClientManager */
    public static SVNClientManager createSvnClientManager(String svnUrl, String username,
                                                          String password){
        initSvnLibrary();

        SVNRepository repository = null;
        try {
            repository = SVNRepositoryFactory.create(SVNURL
                    .parseURIEncoded(svnUrl));
        } catch (SVNException e) {
            logger.error("createSvnClientManager SVNRepositoryFactory create error", e);
            return null;
        }

        /** 身份验证 */
        ISVNAuthenticationManager authManager = SVNWCUtil
                .createDefaultAuthenticationManager(username, password);

        /** 身份验证管理期 */
        repository.setAuthenticationManager(authManager);

        DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
        SVNClientManager svnClientManager = SVNClientManager.newInstance(options,
                authManager);
        return svnClientManager;

    }

    /**
     * 确定一个URL在SVN上是否存在
     * @param svnDirPath
     * @param authManager
     * @return
     */
    public static boolean isURLExist(String svnDirPath,ISVNAuthenticationManager authManager){
        try {
            SVNURL url = SVNURL.parseURIEncoded(svnDirPath);
            SVNRepository svnRepository = SVNRepositoryFactory.create(url);
            svnRepository.setAuthenticationManager(authManager);
            SVNNodeKind nodeKind = svnRepository.checkPath("", -1);
            return nodeKind == SVNNodeKind.NONE ? false : true;
        } catch (SVNException e) {
            logger.error("svn isURLExist error:",e);
        }
        return false;
    }

    /**  svn checkout */
    public static Boolean checkout(SVNClientManager svnClientManager,String svnUrl,String svnLocalPath) {

        SVNURL repositoryURL = null;
        try {
            repositoryURL = SVNURL.parseURIEncoded(svnUrl);
        } catch (SVNException e) {
            logger.error("checkout parseURIEncoded error:", e);
            return false;
        }

        File orderDir = new File(svnLocalPath);
        if (orderDir.exists()) {
            logger.error("the destination directory '"
                    + orderDir.getAbsolutePath() + "' already exists!");
        }
        orderDir.mkdirs();

        try {
            /*
             * 递归的方式 从repositoryURL check out 到 orderDir 目录。
             * SVNRevision.HEAD checked out 到最新的版本
             */
            SVNUpdateClient updateClient = svnClientManager.getUpdateClient();
            updateClient.setIgnoreExternals(false);
            updateClient.doCheckout(repositoryURL, orderDir, SVNRevision.HEAD, SVNRevision.HEAD, true);
            return true;
        } catch (SVNException svne) {
            logger.error("checkout svn error:",svne);
        }
        return false;
    }

    /**
     * 提交commit add /delete 文件、目录处理
     *
     * */
    public static SVNCommitInfo commit(final SVNClientManager svnClientManager,
                                       String svnLocalPath, boolean keepLocks, String commitMessage) {
        try {
            File localPath = new File(svnLocalPath);
            if (!localPath.exists()) {
                logger.error("the destination directory '"
                        + localPath.getAbsolutePath() + "' is not exists!");
                return null;
            }
            /** 递归添加文件、文件夹 */
            checkVersiondDirectory(svnClientManager, localPath);
            /** add/delete 文件、文件夹 */
            svnClientManager.getStatusClient().doStatus(localPath, SVNRevision.HEAD, SVNDepth.INFINITY, false, false, false, false, new ISVNStatusHandler() {
                @Override
                public void handleStatus(SVNStatus status) throws SVNException {
                    if (SVNStatusType.STATUS_UNVERSIONED.equals(status.getNodeStatus())) {
                        svnClientManager.getWCClient().doAdd(status.getFile(), true, false, false, SVNDepth.EMPTY, false, false);
                    } else if (SVNStatusType.STATUS_MISSING.equals(status.getNodeStatus())) {
                        svnClientManager.getWCClient().doDelete(status.getFile(), true, false, false);
                    }
                }
            }, null);
            /** 提交 commit */
            return svnClientManager.getCommitClient().doCommit(new File[] { localPath }, false, "<commit> " + commitMessage, null, null, false, true, SVNDepth.INFINITY);
        } catch (SVNException e) {
            logger.error("svn commit error:", e);
        }
        return null;
    }

    /**
     * 递归检查不在版本控制的文件，并add到svn
     * @param clientManager
     * @param wc
     */
    private static void checkVersiondDirectory(SVNClientManager clientManager,File wc){
        if(!SVNWCUtil.isVersionedDirectory(wc)){
            addEntry(clientManager, wc);
        }
        if(wc.isDirectory()){
            for(File sub:wc.listFiles()){
                if(sub.isDirectory() && sub.getName().equals(".svn")){
                    continue;
                }
                checkVersiondDirectory(clientManager,sub);
            }
        }
    }

    /**
     * 增加到 svn本地库
     * */
    public static void addEntry(SVNClientManager svnClientManager, File wcPath) {
        try {
            svnClientManager.getWCClient().doAdd(new File[] { wcPath }, true,
                    false, false, SVNDepth.INFINITY, false, false, true);
        } catch (SVNException e) {
            logger.error("addEntry error:", e);
        }
    }

    /** 递归清理工作副本，删除未完成的工作副本锁定，并恢复未完成的操作 */
    public static void wCClientDoCleanup(SVNClientManager svnClientManager, File localPath){
        try{
            svnClientManager.getWCClient().doCleanup(localPath);
        }catch (Exception e){
            logger.error("wCClientDoCleanup error", e);
        }
    }

    /** string svn地址 转 SVNURL */
    public static SVNURL stringToSVNURL(String svnUrlString){
        SVNURL svnUrl = null;
        try {
            svnUrl = SVNURL.parseURIEncoded(svnUrlString);
            return svnUrl;
        } catch (SVNException e) {
            logger.error("checkout parseURIEncoded error:", e);
        }
        return null;
    }

}