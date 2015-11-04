/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.salt;

import com.olymtech.nebula.core.salt.core.SaltClientFactory;
import com.suse.saltstack.netapi.client.SaltStackClient;
import com.suse.saltstack.netapi.datatypes.ScheduledJob;
import com.suse.saltstack.netapi.datatypes.target.Glob;
import com.suse.saltstack.netapi.datatypes.target.Target;
import com.suse.saltstack.netapi.exception.SaltStackException;
import com.suse.saltstack.netapi.results.ResultInfoSet;

import java.util.*;

/**
 * @author taoshanchang 15/10/30
 */

public class SaltStackServiceImpl implements ISaltStackService {

    SaltStackClient saltClient = SaltClientFactory.getSaltClient();

    public static final String BaseDirPrefix = "salt://";
    public static final String CommandCpFile = "cp.get_file";
    public static final String CommandCpDir = "cp.get_dir";
    public static final String CommandCmdRun = "cmd.run";

    @Override
    public <T> ResultInfoSet cpFile(Target<T> target, String from, String to) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        args.add(BaseDirPrefix+from);
        args.add(to);

        ScheduledJob job = saltClient.startCommand(new Glob(), CommandCpFile, args, null);

        ResultInfoSet jobResult = saltClient.getJobResult(job.getJid());
        return jobResult;
    }

    @Override
    public <T> ResultInfoSet cpDir(Target<T> target, String from, String to) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        args.add(BaseDirPrefix+from);
        args.add(to);

        ScheduledJob job = saltClient.startCommand(new Glob(), CommandCpDir, args, null);

        ResultInfoSet jobResult = saltClient.getJobResult(job.getJid());
        return jobResult;
    }

    @Override
    public <T> ResultInfoSet cmdRun(Target<T> target, List<Object> args, Map<String, Object> kwargs) throws SaltStackException {
        ScheduledJob job = saltClient.startCommand(new Glob(), CommandCmdRun, args, kwargs);

        ResultInfoSet jobResult = saltClient.getJobResult(job.getJid());

        return jobResult;
    }
}
