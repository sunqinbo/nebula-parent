package com.olymtech.nebula.service;

import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.wc.SVNClientManager;

/**
 * Created by wangyiqiang on 16/6/14.
 */
public interface ISvnService {

    SVNClientManager getWarSvnClientManager();

    ISVNAuthenticationManager getWarAuthManager();
}
