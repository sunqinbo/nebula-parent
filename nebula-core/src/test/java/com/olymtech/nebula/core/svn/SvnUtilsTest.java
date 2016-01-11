package com.olymtech.nebula.core.svn;

import com.olymtech.nebula.common.utils.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.wc.SVNClientManager;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Gavin on 2016-01-08 00:43.
 */
public class SvnUtilsTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCheckout() throws Exception {
        String svnUrl = "svn://172.16.137.150/";
        String svnLoaclBase = "/Users/Gavin/svnlocal/";
        String svnLocalPath = svnLoaclBase + DateUtils.longDate(new Date());
        SVNClientManager svnClientManager = SvnUtils.createSvnClientManager(svnUrl, "gavin", "hellomonitor");

        /** checkout */
        Boolean reuslt_checkout = SvnUtils.checkout(svnClientManager, svnUrl, svnLocalPath);
        System.out.println(reuslt_checkout);

    }

    @Test
    public void testCommit() throws Exception {
        String svnUrl = "svn://172.16.137.150/";
        String svnLoaclBase = "/Users/Gavin/svnlocal/";
        SVNClientManager svnClientManager = SvnUtils.createSvnClientManager(svnUrl, "gavin", "hellomonitor");

        /** commit */
        String commitPath = svnLoaclBase+"20160108004655/";
        System.out.println(commitPath);

        String orderDir = commitPath;
        SVNCommitInfo commitInfo = SvnUtils.commit(svnClientManager, orderDir, false, "test add");

        /**
         * r11 by 'gavin' at Tue Oct 27 12:17:05 CST 2015
         * r15 by 'gavin' at Tue Oct 27 14:53:50 CST 2015
         * EMPTY COMMIT
         */
        if(commitInfo.toString().equals("EMPTY COMMIT")){
            System.out.println(commitInfo+":nothing");
        }else{
            System.out.println(commitInfo);
        }
    }

}