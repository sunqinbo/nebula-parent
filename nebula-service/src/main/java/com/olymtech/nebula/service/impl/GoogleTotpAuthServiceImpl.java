package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.dao.IGoogleTotpAuthDao;
import com.olymtech.nebula.entity.GoogleTotpAuth;
import com.olymtech.nebula.service.IGoogleTotpAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangyiqiang on 16/2/26.
 */
@Service
public class GoogleTotpAuthServiceImpl implements IGoogleTotpAuthService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IGoogleTotpAuthDao googleTotpAuthDao;

    /**
     * 无数据insert，有数据update
     * @param googleTotpAuth
     * @return
     */
    @Override
    public int insertGoogleTotpAuth(GoogleTotpAuth googleTotpAuth) {
        GoogleTotpAuth googleTotpAuthInDB = googleTotpAuthDao.selectByEmpId(googleTotpAuth.getEmpId());

        if(googleTotpAuthInDB == null){
            return googleTotpAuthDao.insert(googleTotpAuth);
        }else {
            googleTotpAuthInDB.setgSecret(googleTotpAuth.getgSecret());
            googleTotpAuthInDB.setgOptAuthUrl(googleTotpAuth.getgOptAuthUrl());
            googleTotpAuthInDB.setgScratchCodes(googleTotpAuth.getgScratchCodes());
            googleTotpAuthDao.update(googleTotpAuthInDB);
            return googleTotpAuthInDB.getId();
        }
    }

    @Override
    public void deleteGoogleTotpAuthById(Integer id) {
        googleTotpAuthDao.deleteById(id);
    }

    @Override
    public void updateGoogleTotpAuth(GoogleTotpAuth googleTotpAuth) {
        googleTotpAuthDao.update(googleTotpAuth);
    }

    @Override
    public GoogleTotpAuth selectByEmpId(Integer id) {
        return googleTotpAuthDao.selectByEmpId(id);
    }
}
