package com.olymtech.nebula.dao;

import com.olymtech.nebula.entity.GoogleTotpAuth;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;

/**
 * Created by wangyiqiang on 16/2/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class GoogleTotpAuthDaoTest {

    @Autowired
    private IGoogleTotpAuthDao googleTotpAuthDao;

    @Test
    public void insert() throws Exception{
        GoogleTotpAuth googleTotpAuth = new GoogleTotpAuth();
        googleTotpAuth.setEmpId(888);
        googleTotpAuth.setgOptAuthUrl("hahhaha");
        googleTotpAuth.setgScratchCodes("bbbbbbbbbbbb");
        googleTotpAuth.setgSecret("cccccccc");
        googleTotpAuthDao.insert(googleTotpAuth);
    }

}