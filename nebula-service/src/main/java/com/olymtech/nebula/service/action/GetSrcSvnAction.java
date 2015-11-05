/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.core.svn.SvnUtils;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import org.tmatesoft.svn.core.wc.SVNClientManager;

/**
 * Created by Gavin on 2015-11-04 20:12.
 */
public class GetSrcSvnAction extends AbstractAction {

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        String svnUrl = event.getPublishSvn()+"/"+event.getPublishEnv();
        SVNClientManager svnClientManager = SvnUtils.createSvnClientManager(svnUrl, "gavin", "hellomonitor");
        try{
            SvnUtils.checkout(svnClientManager,svnUrl,"/Users/saas/deploy_tmp/" + event.getPublishProductKey() + "/src_svn/");
            return true;
        }catch (Exception e){
//            logger.error("GetPublishSvnAction error:",e);
        }
        return false;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }
}
