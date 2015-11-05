/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.salt;

import com.olymtech.nebula.core.salt.core.SaltClientFactory;
import com.suse.saltstack.netapi.client.SaltStackClient;
import com.suse.saltstack.netapi.datatypes.ScheduledJob;
import com.suse.saltstack.netapi.datatypes.target.Glob;
import com.suse.saltstack.netapi.datatypes.target.MinionList;
import com.suse.saltstack.netapi.datatypes.target.Target;
import com.suse.saltstack.netapi.exception.SaltStackException;
import com.suse.saltstack.netapi.results.ResultInfo;
import com.suse.saltstack.netapi.results.ResultInfoSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by taoshanchang on 15/10/30.
 */
@Service
public class SaltStackServiceImpl implements ISaltStackService {

    private static final Logger logger = LoggerFactory.getLogger(SaltStackServiceImpl.class);

    SaltStackClient saltClient = SaltClientFactory.getSaltClient();

    public static final String BaseDirPrefix = "salt://";
    public static final String CommandCpFile = "cp.get_file";
    public static final String CommandCpDir = "cp.get_dir";
    public static final String CommandCmdRun = "cmd.run";

    @Override
    public <T> ResultInfoSet cpFile(Target<T> target, String from, String to) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        args.add(BaseDirPrefix + from);
        args.add(to);

        ScheduledJob job = saltClient.startCommand(new Glob(), CommandCpFile, args, null);

        ResultInfoSet jobResult = saltClient.getJobResult(job.getJid());
        return jobResult;
    }

    @Override
    public <T> ResultInfoSet cpDir(Target<T> target, String from, String to) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        args.add(BaseDirPrefix + from);
        args.add(to);

        ScheduledJob job = saltClient.startCommand(new Glob(), CommandCpDir, args, null);

        ResultInfoSet jobResult = saltClient.getJobResult(job.getJid());
        return jobResult;
    }

    @Override
    public <T> boolean mkDir(Target<T> target, String path, boolean parents) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        if (parents) {
            args.add("mkdir -p " + path);
        } else {
            args.add("mkdir " + path);
        }

        int succeedCount = 0;

        ScheduledJob job = saltClient.startCommand(target, CommandCmdRun, args, null);

        ResultInfoSet jobResult = saltClient.getJobResult(job.getJid());

        if (jobResult.getInfoList().size() == 1) {
            ResultInfo resultInfo = jobResult.get(0);
            Map<String, Object> results = resultInfo.getResults();
            for (Map.Entry<String, Object> entry : results.entrySet()) {

                if (entry.getValue().equals("")) {
                    succeedCount++;
                } else {
                    throw new SaltStackException(entry.getValue().toString());
                }
            }

        } else {
            return false;
        }

        logger.debug("成功执行" + succeedCount + "台机器");

        return true;
    }

    @Override
    public <T> boolean mkDir(Target<T> target, String path) throws SaltStackException {
        return this.mkDir(target, path, false);
    }

    @Override
    public <T> boolean mkDirWithParents(Target<T> target, String path) throws SaltStackException {
        return this.mkDir(target, path, true);
    }

    @Override
    public <T> ResultInfoSet cmdRun(Target<T> target, List<Object> args, Map<String, Object> kwargs) throws SaltStackException {
        ScheduledJob job = saltClient.startCommand(new Glob(), CommandCmdRun, args, kwargs);

        ResultInfoSet jobResult = saltClient.getJobResult(job.getJid());

        return jobResult;
    }
}
