/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.salt;

import com.sun.jna.platform.win32.WinDef;
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
     * 创建文件夹并选择自动建立好那些尚不存在的目录
     * @param target
     * @param path
     * @param <T>
     * @return
     * @throws SaltStackException
     */
    public <T> boolean mkDir(final Target<T> target,String path,boolean parents) throws SaltStackException;

    /**
     * 创建文件夹
     * @param target
     * @param path
     * @param <T>
     * @return
     */
    public <T> boolean mkDir(final Target<T> target,String path) throws SaltStackException;

    /**
     * 创建文件夹并自动建立好那些尚不存在的目录
     * @param target
     * @param path
     * @param <T>
     * @return
     */
    public <T> boolean mkDirWithParents(final Target<T> target,String path) throws SaltStackException;

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
