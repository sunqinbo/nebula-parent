/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao;

import com.olymtech.nebula.entity.BaseDO;

import java.util.List;

/**
 * Created by Gavin on 2015-10-23 14:16.
 */
public interface IBaseDao {

    public int insert(BaseDO baseDO);

    public BaseDO selectById(Integer id);

    public void update(BaseDO baseDO);

    public void updateByIdSelective(BaseDO baseDO);

    public void deleteById(Integer id);

    public <T extends BaseDO> List<T> selectAllPaging(T t);

    public <T extends BaseDO> List<T> selectAll();

    public int selectCount();

    public <T extends BaseDO> int selectCount(T t);

    public int insertSelective(BaseDO baseDO);
}
