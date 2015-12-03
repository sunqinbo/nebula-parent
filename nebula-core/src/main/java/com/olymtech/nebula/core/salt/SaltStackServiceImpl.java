/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.salt;

import com.olymtech.nebula.core.salt.core.SaltClientFactory;
import com.olymtech.nebula.core.salt.core.SaltTarget;
import com.suse.saltstack.netapi.datatypes.ScheduledJob;
import com.suse.saltstack.netapi.datatypes.target.Target;
import com.suse.saltstack.netapi.exception.SaltStackException;
import com.suse.saltstack.netapi.results.ResultInfo;
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

    public static final String BaseDirPrefix = "salt://";
    public static final String CommandCpFile = "cp.get_file";
    public static final String CommandCpDir = "cp.get_dir";
    public static final String CommandCmdRun = "cmd.run";


    @Override
    public <T> ResultInfoSet cpFileRemote(Target<T> target, String from, String to) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        args.add(BaseDirPrefix + from);
        args.add(to);

        ScheduledJob job = SaltClientFactory.getSaltClient().startCommand(target, CommandCpFile, args, null);

        Map<String, Object> results = null;
        ResultInfoSet jobResult = null;

        long startTime = System.currentTimeMillis();   //获取开始时间

        int targetCount = 0;
        if (target instanceof SaltTarget) {
            SaltTarget t = (SaltTarget) target;
            targetCount = t.getTargets().size();
        }

        do {
            jobResult = SaltClientFactory.getSaltClient().getJobResult(job.getJid());
            ResultInfo resultInfo = jobResult.get(0);
            results = resultInfo.getResults();
            long endTime = System.currentTimeMillis(); //获取结束时间
            if ((endTime - startTime) / 1000 / 60 > 30) {
                logger.error("cpFileRemote is waiting timeout");
                break;

            }

        } while (results.size() != targetCount);

        return jobResult;
    }

    @Override
    public <T> ResultInfoSet cpDirRemote(Target<T> target, String from, String to) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        args.add(BaseDirPrefix + from);
        args.add(to);

        ScheduledJob job = SaltClientFactory.getSaltClient().startCommand(target, CommandCpDir, args, null);

        Map<String, Object> results = null;
        ResultInfoSet jobResult = null;

        long startTime = System.currentTimeMillis();   //获取开始时间
        int targetCount = 0;
        if (target instanceof SaltTarget) {
            SaltTarget t = (SaltTarget) target;
            targetCount = t.getTargets().size();
        }

        do {
            jobResult = SaltClientFactory.getSaltClient().getJobResult(job.getJid());
            ResultInfo resultInfo = jobResult.get(0);
            results = resultInfo.getResults();
            long endTime = System.currentTimeMillis(); //获取结束时间
            if ((endTime - startTime) / 1000 / 60 > 30) {
                logger.error("cpDirRemote is waiting timeout");
                break;
            }

        } while (results.size() != targetCount);

        return jobResult;
    }

    @Override
    public <T> ResultInfoSet cpFileAndDir(Target<T> target, Map<String, String> fileKeyValue, Map<String, String> dirKeyValue) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        int i = 0;
        if (dirKeyValue != null && dirKeyValue.size() != 0) {
            for (Map.Entry<String, String> entry : dirKeyValue.entrySet()) {
                if (i == 0) {
                    buffer.append("cp -R " + entry.getKey() + " " + entry.getValue());
                    i = 1;
                } else {
                    buffer.append("&& cp -R " + entry.getKey() + " " + entry.getValue());
                }
            }
            buffer.append(";");
        }
        i = 0;
        if (fileKeyValue != null && fileKeyValue.size() != 0) {

            for (Map.Entry<String, String> entry : fileKeyValue.entrySet()) {
                if (i == 0) {
                    buffer.append("cp " + entry.getKey() + " " + entry.getValue());
                    i = 1;
                } else {
                    buffer.append("&& cp " + entry.getKey() + " " + entry.getValue());
                }
            }
        }
        logger.info("当前执行的命令:" + buffer.toString());
        args.add(buffer.toString());

        ScheduledJob job = SaltClientFactory.getSaltClient().startCommand(target, CommandCmdRun, args, null);

        Map<String, Object> results = null;
        ResultInfoSet jobResult = null;

        long startTime = System.currentTimeMillis();   //获取开始时间
        int targetCount = 0;
        if (target instanceof SaltTarget) {
            SaltTarget t = (SaltTarget) target;
            targetCount = t.getTargets().size();
        }

        do {
            jobResult = SaltClientFactory.getSaltClient().getJobResult(job.getJid());
            ResultInfo resultInfo = jobResult.get(0);
            results = resultInfo.getResults();
            long endTime = System.currentTimeMillis(); //获取结束时间
            if ((endTime - startTime) / 1000 / 60 > 30) {
                logger.error("cpFileAndDir is waiting timeout");
                break;
            }

        } while (results.size() != targetCount);

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

        logger.info("当前执行的命令:" + paths.toString());
        ScheduledJob job = SaltClientFactory.getSaltClient().startCommand(target, CommandCmdRun, args, null);

        ResultInfoSet jobResult = SaltClientFactory.getSaltClient().getJobResult(job.getJid());
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
        ScheduledJob job = SaltClientFactory.getSaltClient().startCommand(target, CommandCmdRun, args, kwargs);
        ResultInfoSet jobResult = SaltClientFactory.getSaltClient().getJobResult(job.getJid());
        return jobResult;
    }

    @Override
    public <T> ResultInfoSet doCommand(Target<T> target, List<String> pathList) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        StringBuffer paths = new StringBuffer();
        for (String path : pathList) {
            paths.append(path + ";");
        }

        logger.info("当前执行的命令:" + paths.toString());
        args.add(paths);

        ScheduledJob job = SaltClientFactory.getSaltClient().startCommand(target, CommandCmdRun, args, null);

        ResultInfoSet jobResult = SaltClientFactory.getSaltClient().getJobResult(job.getJid());
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
            args.add("rm -rf " + paths);
        } else {
            args.add("rm " + paths);
        }
        logger.info("当前执行的命令:" + paths.toString());


        ScheduledJob job = SaltClientFactory.getSaltClient().startCommand(target, CommandCmdRun, args, null);

        ResultInfoSet jobResult = SaltClientFactory.getSaltClient().getJobResult(job.getJid());

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
        logger.info("当前执行的命令:" + buffer.toString());

        args.add(buffer.toString());

        ScheduledJob job = SaltClientFactory.getSaltClient().startCommand(target, CommandCmdRun, args, null);

        ResultInfoSet jobResult = SaltClientFactory.getSaltClient().getJobResult(job.getJid());

        return jobResult;
    }

}
