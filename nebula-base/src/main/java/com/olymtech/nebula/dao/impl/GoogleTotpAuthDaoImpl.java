package com.olymtech.nebula.dao.impl;

import com.olymtech.nebula.dao.IGoogleTotpAuthDao;
import com.olymtech.nebula.entity.GoogleTotpAuth;
import org.springframework.stereotype.Repository;

/**
 * Created by wangyiqiang on 16/2/26.
 */
@Repository
public class GoogleTotpAuthDaoImpl extends BaseDaoImpl<GoogleTotpAuth, Integer> implements IGoogleTotpAuthDao {

    @Override
    public GoogleTotpAuth selectByEmpId(Integer empId) {
        return getSqlSession().selectOne(CLASS_NAME + "-Select-By-Emp-Id", empId);
    }
}
