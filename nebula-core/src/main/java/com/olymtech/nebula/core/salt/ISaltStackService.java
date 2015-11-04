/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.salt;

import com.suse.saltstack.netapi.datatypes.target.Target;
import com.suse.saltstack.netapi.exception.SaltStackException;
import com.suse.saltstack.netapi.results.ResultInfoSet;

import java.util.List;
import java.util.Map;

/**
 * Created by taoshanchang on 15/10/30.
 */
public interface ISaltStackService {


    /**
     * 文件拷贝接口
     * @param target 目标机
     * @param from master指定文件
     * @param to minion指定文件
     * @return
     * @throws SaltStackException
     */
    public <T> ResultInfoSet cpFile(final Target<T> target, String from, String to ) throws SaltStackException;

    /**
     *
     * @param target 目标机
     * @param from master指定目录
     * @param to minion指定目录
     * @return
     * @throws SaltStackException
     */
    public <T> ResultInfoSet cpDir(final Target<T> target, String from, String to ) throws SaltStackException;

    /**
     *
     * @param target 目标机
     * @param args 命令集
     * @param kwargs 命令参数
     * @return
     * @throws SaltStackException
     */
    public <T> ResultInfoSet cmdRun(final Target<T> target, List<Object> args, Map<String, Object> kwargs) throws SaltStackException;


}
