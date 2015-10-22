/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.svn;

import com.olymtech.nebula.common.utils.DateUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.*;

import java.io.File;
import java.util.Date;

/**
 * Created by Gavin on 2015-10-21 16:05.
 */
public class SvnClient {

    private static Logger logger = LoggerFactory.getLogger(SvnClient.class);

    private static SVNClientManager svnClientManager;

    public SvnClient(){
        PropertyConfigurator.configure(SvnClient.class.getClassLoader().getResource("log4j.properties"));
    }

    public static Boolean getFilesBySvnUrl(String svnUrl,String svnLocalPath) {

        SVNRepositoryFactoryImpl.setup();

        SVNURL repositoryURL = null;
        try {
            repositoryURL = SVNURL.parseURIEncoded(svnUrl);
        } catch (SVNException e) {
            logger.error("parseURIEncoded error:", e);
        }
        //版本库的用户名
        String name = "gavin";
        //版本库的用户名密码
        String password = "";

        //驱动选项
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);

        svnClientManager = SVNClientManager.newInstance((DefaultSVNOptions) options, name, password);

        File orderDir = new File(svnLocalPath);
        if (orderDir.exists()) {
            logger.error("the destination directory '"
                    + orderDir.getAbsolutePath() + "' already exists!");
        }
        orderDir.mkdirs();

        try {
            /*
             * 递归的把工作副本从repositoryURL check out 到 wcDir目录。
             * SVNRevision.HEAD 意味着把最新的版本checked out出来。
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

    public static void main(String[] args) throws Exception {
        String svnUrl = "svn://192.168.1.234/test/";
        //工作副本目录
        String svnLocalPath = "/Users/Gavin/svnlocal/" + DateUtils.longDate(new Date());
        Boolean reuslt = getFilesBySvnUrl(svnUrl,svnLocalPath);
        System.out.println(reuslt);

    }


}