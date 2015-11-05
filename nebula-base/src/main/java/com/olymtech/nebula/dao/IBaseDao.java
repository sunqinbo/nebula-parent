/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao;

import com.github.pagehelper.Page;
import com.olymtech.nebula.entity.BaseDO;

import java.awt.print.Pageable;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Gavin on 2015-10-23 14:16.
 */
public interface IBaseDao<T extends BaseDO, ID extends Serializable> {

    public int insert(T baseDO);

    public T selectById(ID id);

    public void update(T baseDO);

    public void updateByIdSelective(T baseDO);

    public void deleteById(ID id);

    public List<T> selectAllPaging(T t);

    public List<T> selectAll();

    public Page<T> selectAll(Pageable pageable);

    public int selectCount();

    public int selectCount(T t);

    public int insertSelective(T baseDO);
}
