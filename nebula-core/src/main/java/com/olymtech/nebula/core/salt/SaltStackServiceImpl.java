/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.salt;

import com.olymtech.nebula.core.salt.core.SaltClientFactory;
import com.suse.saltstack.netapi.client.SaltStackClient;
import com.suse.saltstack.netapi.datatypes.ScheduledJob;
import com.suse.saltstack.netapi.datatypes.target.Target;
import com.suse.saltstack.netapi.exception.SaltStackException;
import com.suse.saltstack.netapi.results.ResultInfoSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public <T> ResultInfoSet cpFileRemote(Target<T> target, String from, String to) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        args.add(BaseDirPrefix + from);
        args.add(to);

        ScheduledJob job = saltClient.startCommand(target, CommandCpFile, args, null);

        ResultInfoSet jobResult = saltClient.getJobResult(job.getJid());

        return jobResult;
    }

    @Override
    public <T> ResultInfoSet cpDirRemote(Target<T> target, String from, String to) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        args.add(BaseDirPrefix + from);
        args.add(to);

        ScheduledJob job = saltClient.startCommand(target, CommandCpDir, args, null);

        ResultInfoSet jobResult = saltClient.getJobResult(job.getJid());
        return jobResult;
    }

    @Override
    public <T> ResultInfoSet cpFile(Target<T> target, HashMap<String, String> keyValue) throws SaltStackException {
        return this.cp(target, keyValue, false);
    }

    @Override
    public <T> ResultInfoSet cpDir(Target<T> target, HashMap<String, String> keyValue) throws SaltStackException {
        return this.cp(target, keyValue, true);
    }

    private <T> ResultInfoSet cp(Target<T> target, HashMap<String, String> keyValue, boolean isDir) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        int i = 0;
        if (isDir) {
            for (Map.Entry<String, String> entry : keyValue.entrySet()) {
                if (i == 0) {
                    buffer.append("cp -R " + entry.getKey() + " " + entry.getValue());
                    i = 1;
                } else {
                    buffer.append("&& cp -R " + entry.getKey() + " " + entry.getValue());
                }
            }
        } else {
            for (Map.Entry<String, String> entry : keyValue.entrySet()) {
                if (i == 0) {
                    buffer.append("cp " + entry.getKey() + " " + entry.getValue());
                    i = 1;
                } else {
                    buffer.append("&& cp " + entry.getKey() + " " + entry.getValue());
                }
            }
        }
        args.add(buffer.toString());

        System.out.println(buffer.toString());

        ScheduledJob job = saltClient.startCommand(target, CommandCmdRun, args, null);

        ResultInfoSet jobResult = saltClient.getJobResult(job.getJid());

        return jobResult;
    }

    @Override
    public <T> ResultInfoSet mkDir(Target<T> target, List<String> pathList, boolean parents) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        StringBuffer paths = new StringBuffer();
        for (String path : pathList) {
            paths.append(" " + path);
        }
        if (parents) {
            args.add("mkdir -p " + paths);
        } else {
            args.add("mkdir " + paths);
        }

        ScheduledJob job = saltClient.startCommand(target, CommandCmdRun, args, null);

        ResultInfoSet jobResult = saltClient.getJobResult(job.getJid());
        return jobResult;
    }

    @Override
    public <T> ResultInfoSet mkDir(Target<T> target, List<String> pathList) throws SaltStackException {
        return this.mkDir(target, pathList, false);
    }

    @Override
    public <T> ResultInfoSet mkDirWithParents(Target<T> target, List<String> pathList) throws SaltStackException {
        return this.mkDir(target, pathList, true);
    }

    @Override
    public <T> ResultInfoSet cmdRun(Target<T> target, List<Object> args, Map<String, Object> kwargs) throws SaltStackException {
        ScheduledJob job = saltClient.startCommand(target, CommandCmdRun, args, kwargs);

        ResultInfoSet jobResult = saltClient.getJobResult(job.getJid());

        return jobResult;
    }

    @Override
    public <T> ResultInfoSet doCommand(Target<T> target, List<String> pathList) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        StringBuffer paths = new StringBuffer();
        for (String path : pathList) {
            paths.append(" " + path);
        }

        args.add("sh " + paths);

        ScheduledJob job = saltClient.startCommand(target, CommandCmdRun, args, null);

        ResultInfoSet jobResult = saltClient.getJobResult(job.getJid());
        return jobResult;
    }


    @Override
    public <T> ResultInfoSet deleteFile(Target<T> target, List<String> pathList, boolean recursion) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        StringBuffer paths = new StringBuffer();
        for (String path : pathList) {
            paths.append(" " + path);
        }
        if (recursion) {
            args.add("rf -rf " + paths);
        } else {
            args.add("rf " + paths);
        }

        ScheduledJob job = saltClient.startCommand(target, CommandCmdRun, args, null);

        ResultInfoSet jobResult = saltClient.getJobResult(job.getJid());

        return jobResult;
    }

    @Override
    public <T> ResultInfoSet makeLn(Target<T> target, HashMap<String, String> keyValue) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        int i = 0;
        for (Map.Entry<String, String> entry : keyValue.entrySet()) {
            if (i == 0) {
                buffer.append("rm -rf " + entry.getValue());
                i = 1;
            } else {
                buffer.append("&& rm -rf " + entry.getValue());
            }
            buffer.append(" && ln -s " + entry.getKey() + " " + entry.getValue());

        }

        args.add(buffer.toString());

        System.out.println(buffer.toString());

        ScheduledJob job = saltClient.startCommand(target, CommandCmdRun, args, null);

        ResultInfoSet jobResult = saltClient.getJobResult(job.getJid());

        return jobResult;
    }

}
