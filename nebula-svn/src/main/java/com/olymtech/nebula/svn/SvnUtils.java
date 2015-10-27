/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.svn;

import com.olymtech.nebula.common.utils.DateUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.*;

import java.io.File;
import java.util.Date;

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

    public static SVNCommitInfo commit(final SVNClientManager svnClientManager,
                                       File localPath, boolean keepLocks, String commitMessage) {
        try {
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
            return svnClientManager.getCommitClient().doCommit(new File[] { localPath }, false, "<commit> " + commitMessage, null, null, false, true, SVNDepth.INFINITY);
        } catch (SVNException e) {
            logger.error("svn commit error:", e);
        }
        return null;
    }

    public static void addEntry(SVNClientManager svnClientManager, File wcPath) {
        try {
            svnClientManager.getWCClient().doAdd(new File[] { wcPath }, true,
                    false, false, SVNDepth.INFINITY, false, false, true);
        } catch (SVNException e) {
            logger.error("addEntry error:", e);
        }
    }

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


//    public static void main(String[] args) throws Exception {
//        String svnUrl = "svn://172.16.137.150/";
//        String svnLoaclBase = "/Users/Gavin/svnlocal/";
//        String svnLocalPath = svnLoaclBase + DateUtils.longDate(new Date());
//        SVNClientManager svnClientManager = createSvnClientManager(svnUrl,"gavin","hellomonitor");
//
//        /** checkout */
//        Boolean reuslt_checkout = checkout(svnClientManager, svnUrl, svnLocalPath);
//        System.out.println(reuslt_checkout);
//
//        /** commit */
//        String commitPath = svnLoaclBase+"20151027105751/";
//        System.out.println(commitPath);
//
//        File orderDir = new File(commitPath);
//        if (!orderDir.exists()) {
//            logger.error("the destination directory '"
//                    + orderDir.getAbsolutePath() + "' is not exists!");
//        }
//        SVNCommitInfo commitInfo = commit(svnClientManager,orderDir,false,"test add");
//
//        /**
//         * r11 by 'gavin' at Tue Oct 27 12:17:05 CST 2015
//         * r15 by 'gavin' at Tue Oct 27 14:53:50 CST 2015
//         * EMPTY COMMIT
//         */
//        if(commitInfo.toString().equals("EMPTY COMMIT")){
//            System.out.println(commitInfo+":nothing");
//        }else{
//            System.out.println(commitInfo);
//        }
//    }


}