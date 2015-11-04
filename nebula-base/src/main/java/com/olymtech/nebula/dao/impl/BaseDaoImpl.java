/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao.impl;

import com.olymtech.nebula.dao.IBaseDao;
import com.olymtech.nebula.entity.BaseDO;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * Created by Gavin on 2015-10-23 14:17.
 */
public class BaseDaoImpl extends SqlSessionDaoSupport implements IBaseDao {

    public String   CLASS_NAME = ("Nebula-" + this.getClass().getSimpleName().replace("Impl", ""));

    @Override
    public int insert(BaseDO baseDO) {
        getSqlSession().insert(CLASS_NAME + "-Insert", baseDO);
        return baseDO.getId();
    }

    @Override
    public int insertSelective(BaseDO baseDO) {
        getSqlSession().insert(CLASS_NAME + "-Insert-Selective", baseDO);
        return baseDO.getId();
    }

    @Override
    public BaseDO selectById(Integer id) {
        return (BaseDO) getSqlSession().selectOne(CLASS_NAME + "-Select-By-Id", id);
    }

    @Override
    public void update(BaseDO baseDO){
        getSqlSession().update(CLASS_NAME + "-Update", baseDO);
    }

    @Override
    public void updateByIdSelective(BaseDO baseDO){
        getSqlSession().update(CLASS_NAME + "-Update-By-Id-Selective", baseDO);
    }

    @Override
    public void deleteById(Integer id){
        getSqlSession().delete(CLASS_NAME + "-Delete-By-Id", id);
    }

    @Override
    public <T extends BaseDO> List<T> selectAllPaging(T t) {
//        t.getPage().setSumPage(this.selectCount(t));
        return getSqlSession().selectList(CLASS_NAME + "-Select-All-Paging-Where", t);
    }

    @Override
    public <T extends BaseDO> List<T> selectAll() {
        return getSqlSession().selectList(CLASS_NAME + "-Select-All");
    }


    @Override
    public int selectCount(){
        return (Integer) getSqlSession().selectOne(CLASS_NAME + "-Select-Count");
    }

    @Override
    public <T extends BaseDO> int selectCount(T t){
        return (Integer) getSqlSession().selectOne(CLASS_NAME + "-Select-Count-Where", t);
    }
}
