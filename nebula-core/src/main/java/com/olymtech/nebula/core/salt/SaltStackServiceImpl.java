/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.salt;

import com.olymtech.nebula.core.salt.core.SaltClientFactory;
import com.olymtech.nebula.core.salt.core.SaltTarget;
import com.suse.saltstack.netapi.datatypes.ScheduledJob;
import com.suse.saltstack.netapi.exception.SaltStackException;
import com.suse.saltstack.netapi.results.ResultInfoSet;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${script_check_files_md5}")
    private String scriptCheckFilesMd5;


    @Override
    public <T> ResultInfoSet cpFileRemote(List<String> targets, String from, String to) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        args.add(BaseDirPrefix + from);
        args.add(to);

        ScheduledJob job = SaltClientFactory.getSaltClient().startCommand(new SaltTarget(targets), CommandCpFile, args, null);

        ResultInfoSet jobResult = SaltClientFactory.getResult(job.getJid(),targets.size());

        return jobResult;
    }

    @Override
    public <T> ResultInfoSet cpDirRemote(List<String> targets, String from, String to) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        args.add(BaseDirPrefix + from);
        args.add(to);

        ScheduledJob job = SaltClientFactory.getSaltClient().startCommand(new SaltTarget(targets), CommandCpDir, args, null);

        ResultInfoSet jobResult = SaltClientFactory.getResult(job.getJid(),targets.size());

        return jobResult;
    }

    @Override
    public <T> ResultInfoSet cpFileAndDir(List<String> targets, Map<String, String> fileKeyValue, Map<String, String> dirKeyValue) throws SaltStackException {
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

        ScheduledJob job = SaltClientFactory.getSaltClient().startCommand(new SaltTarget(targets), CommandCmdRun, args, null);

        ResultInfoSet jobResult = SaltClientFactory.getResult(job.getJid(),targets.size());

        return jobResult;
    }


    @Override
    public <T> ResultInfoSet mkDir(List<String> targets, List<String> pathList, boolean parents) throws SaltStackException {
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
        ScheduledJob job = SaltClientFactory.getSaltClient().startCommand(new SaltTarget(targets), CommandCmdRun, args, null);

        ResultInfoSet jobResult = SaltClientFactory.getResult(job.getJid(),targets.size());
        return jobResult;
    }

    @Override
    public <T> ResultInfoSet checkFilesMd5ByDir(List<String> targets,String dir,String suffix) throws SaltStackException{
        List<Object> args = new ArrayList<>();
        String command = "";
        if (StringUtils.isNotEmpty(suffix)) {
            command = scriptCheckFilesMd5 + " -d " + dir + " -s " +suffix;
        } else {
            command = scriptCheckFilesMd5 + " -d " + dir;
        }
        args.add(command);

        logger.info("当前执行的命令:" + command);
        ScheduledJob job = SaltClientFactory.getSaltClient().startCommand(new SaltTarget(targets), CommandCmdRun, args, null);

        ResultInfoSet jobResult = SaltClientFactory.getResult(job.getJid(),targets.size());
        return jobResult;
    }

    @Override
    public <T> ResultInfoSet mkDir(List<String> targets, List<String> pathList) throws SaltStackException {
        return this.mkDir(targets, pathList, false);
    }

    @Override
    public <T> ResultInfoSet mkDirWithParents(List<String> targets, List<String> pathList) throws SaltStackException {
        return this.mkDir(targets, pathList, true);
    }

    @Override
    public <T> ResultInfoSet cmdRun(List<String> targets, List<Object> args, Map<String, Object> kwargs) throws SaltStackException {
        ScheduledJob job = SaltClientFactory.getSaltClient().startCommand(new SaltTarget(targets), CommandCmdRun, args, kwargs);
        ResultInfoSet jobResult = SaltClientFactory.getResult(job.getJid(),targets.size());
        return jobResult;
    }

    @Override
    public <T> ResultInfoSet doCommand(List<String> targets, List<String> pathList) throws SaltStackException {
        List<Object> args = new ArrayList<>();
        StringBuffer paths = new StringBuffer();
        for (String path : pathList) {
            paths.append(path + ";");
        }

        logger.info("当前执行的命令:" + paths.toString());
        args.add(paths);

        ScheduledJob job = SaltClientFactory.getSaltClient().startCommand(new SaltTarget(targets), CommandCmdRun, args, null);

        ResultInfoSet jobResult = SaltClientFactory.getResult(job.getJid(),targets.size());

        return jobResult;
    }


    @Override
    public <T> ResultInfoSet deleteFile(List<String> targets, List<String> pathList, boolean recursion) throws SaltStackException {
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


        ScheduledJob job = SaltClientFactory.getSaltClient().startCommand(new SaltTarget(targets), CommandCmdRun, args, null);

        ResultInfoSet jobResult = SaltClientFactory.getResult(job.getJid(),targets.size());

        return jobResult;
    }

    @Override
    public <T> ResultInfoSet makeLn(List<String> targets, HashMap<String, String> keyValue) throws SaltStackException {
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

        ScheduledJob job = SaltClientFactory.getSaltClient().startCommand(new SaltTarget(targets), CommandCmdRun, args, null);

        ResultInfoSet jobResult = SaltClientFactory.getResult(job.getJid(),targets.size());

        return jobResult;
    }

}
