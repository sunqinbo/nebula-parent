package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.dao.IGoogleTotpAuthDao;
import com.olymtech.nebula.entity.GoogleTotpAuth;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class GoogleTotpAuthServiceImplTest {
    @Resource
    private IGoogleTotpAuthDao googleTotpAuthDao;

    @Test
    public void testInsertGoogleTotpAuth() throws Exception {
        GoogleTotpAuth googleTotpAuth = new GoogleTotpAuth();
        googleTotpAuth.setEmpId(888);
        googleTotpAuth.setgSecret("aaaa");
        googleTotpAuth.setgScratchCodes("bbbb");
        googleTotpAuth.setgOptAuthUrl("cccc");
        googleTotpAuthDao.insert(googleTotpAuth);
    }

    @Test
    public void testDeleteGoogleTotpAuthById() throws Exception {

    }

    @Test
    public void testUpdateGoogleTotpAuth() throws Exception {

    }

    @Test
    public void testSelectById() throws Exception {

    }
}