/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao.impl;

import com.github.pagehelper.Page;
import com.olymtech.nebula.dao.IBaseDao;
import com.olymtech.nebula.dao.mybatis.SqlSessionDaoSupport;
import com.olymtech.nebula.entity.BaseDO;

import java.awt.print.Pageable;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Gavin on 2015-10-23 14:17.
 */
public class BaseDaoImpl<T extends BaseDO, ID extends Serializable> extends SqlSessionDaoSupport implements IBaseDao<T, ID>  {

    public String   CLASS_NAME = ("Nebula-" + this.getClass().getSimpleName().replace("Impl", ""));

    @Override
    public int insert(T baseDO) {
        getSqlSession().insert(CLASS_NAME + "-Insert", baseDO);
        return baseDO.getId();
    }

    @Override
    public int insertSelective(T baseDO) {
        getSqlSession().insert(CLASS_NAME + "-Insert-Selective", baseDO);
        return baseDO.getId();
    }

    @Override
    public T selectById(ID id) {
        return getSqlSession().selectOne(CLASS_NAME + "-Select-By-Id", id);
    }

    @Override
    public void update(T baseDO){
        getSqlSession().update(CLASS_NAME + "-Update-By-Id", baseDO);
    }

    @Override
    public void updateByIdSelective(T baseDO){
        getSqlSession().update(CLASS_NAME + "-Update-By-Id-Selective", baseDO);
    }

    @Override
    public void deleteById(ID id){
        getSqlSession().delete(CLASS_NAME + "-Delete-By-Id", id);
    }

    @Override
    public List<T> selectAllPaging(T t) {
//        t.getPage().setSumPage(this.selectCount(t));
        return getSqlSession().selectList(CLASS_NAME + "-Select-All-Paging-Where", t);
    }

    @Override
    public Page<T> selectAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<T> selectAll() {
        return getSqlSession().selectList(CLASS_NAME + "-Select-All");
    }


    @Override
    public int selectCount(){
        return (Integer) getSqlSession().selectOne(CLASS_NAME + "-Select-Count");
    }

    @Override
    public int selectCount(T t){
        return (Integer) getSqlSession().selectOne(CLASS_NAME + "-Select-Count-Where", t);
    }
}
