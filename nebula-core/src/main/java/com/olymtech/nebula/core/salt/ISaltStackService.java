/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.salt;

import com.suse.saltstack.netapi.datatypes.target.Target;
import com.suse.saltstack.netapi.exception.SaltStackException;
import com.suse.saltstack.netapi.results.ResultInfoSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taoshanchang on 15/10/30.
 */
public interface ISaltStackService {


    /**
     * 远程文件拷贝接口
     *
     * @param target 目标机
     * @param from   master指定文件
     * @param to     minion指定文件
     * @return
     * @throws SaltStackException
     */
    public <T> ResultInfoSet cpFileRemote(final Target<T> target, String from, String to) throws SaltStackException;

    /**
     * 远程拷贝文件夹
     *
     * @param target 目标机
     * @param from   master指定目录
     * @param to     minion指定目录
     * @return
     * @throws SaltStackException
     */
    public <T> ResultInfoSet cpDirRemote(final Target<T> target, String from, String to) throws SaltStackException;

    /**
     * 文件拷贝接口
     * @param target 目标机器
     * @param fileKeyValue 拷贝文件列表
     * @param dirKeyValue 拷贝文件夹列表
     * @param <T>
     * @return
     * @throws SaltStackException
     */
    public <T> ResultInfoSet cpFileAndDir(Target<T> target, Map<String, String> fileKeyValue, Map<String, String> dirKeyValue) throws SaltStackException;

    /**
     * 创建文件夹并选择自动建立好那些尚不存在的目录
     *
     * @param target 目标机
     * @param pathList
     * @param <T>
     * @return
     * @throws SaltStackException
     */
    public <T> ResultInfoSet mkDir(final Target<T> target, List<String> pathList, boolean parents) throws SaltStackException;

    /**
     * 创建文件夹
     *
     * @param target 目标机
     * @param pathList
     * @param <T>
     * @return
     */
    public <T> ResultInfoSet mkDir(final Target<T> target, List<String> pathList) throws SaltStackException;

    /**
     * 创建文件夹并自动建立好那些尚不存在的目录
     *
     * @param target 目标机
     * @param pathList
     * @param <T>
     * @return
     */
    public <T> ResultInfoSet mkDirWithParents(final Target<T> target, List<String> pathList) throws SaltStackException;

    /**
     * 远程运行cmd命令
     *
     * @param target 目标机
     * @param args   命令集
     * @param kwargs 命令参数
     * @return
     * @throws SaltStackException
     */
    public <T> ResultInfoSet cmdRun(final Target<T> target, List<Object> args, Map<String, Object> kwargs) throws SaltStackException;

    /**
     * 远程运行cmd命令
     * @param target
     * @param pathList 命令路径列表
     * @param <T>
     * @return
     * @throws SaltStackException
     */
    public <T> ResultInfoSet doCommand(final Target<T> target, List<String> pathList) throws SaltStackException;

    /**
     * 删除文件或文件
     * @param target 目标机
     * @param pathList 文件目录或者文件集合
     * @param recursion 是否递归
     * @param <T>
     * @return
     * @throws SaltStackException
     */
    public <T> ResultInfoSet deleteFile(final Target<T> target,List<String> pathList, boolean recursion) throws SaltStackException;

    /**
     * 创建软连接
     * @param target 目标机
     * @param keyValue 被连接的文件夹,创建的软连接
     * @param <T>
     * @return
     * @throws SaltStackException
     */
    public <T> ResultInfoSet makeLn(final Target<T> target,HashMap<String, String> keyValue) throws SaltStackException;


}
