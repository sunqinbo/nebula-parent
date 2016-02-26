package com.olymtech.nebula.dao;

import com.olymtech.nebula.entity.GoogleTotpAuth;

public interface IGoogleTotpAuthDao extends IBaseDao<GoogleTotpAuth, Integer>{

    GoogleTotpAuth selectByEmpId(Integer id);
}