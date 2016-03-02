/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.googleauth;

import com.olymtech.nebula.core.googleauth.MyGoogleAuthenticatorQRGenerator;
import com.olymtech.nebula.core.utils.SpringUtils;
import com.olymtech.nebula.dao.impl.NebulaUserInfoDaoImpl;
import com.olymtech.nebula.entity.GoogleTotpAuth;
import com.olymtech.nebula.entity.NebulaUserInfo;
import com.olymtech.nebula.service.IGoogleTotpAuthService;
import com.olymtech.nebula.service.IUserService;
import com.olymtech.nebula.service.impl.GoogleTotpAuthServiceImpl;
import com.olymtech.nebula.service.impl.UserServiceImpl;
import com.warrenstrange.googleauth.ICredentialRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Gavin on 2016-02-25 16:39.
 */
@Service
public class CredentialRepositoryImpl implements ICredentialRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private IUserService userService = SpringUtils.getBean(UserServiceImpl.class);

    private IGoogleTotpAuthService googleTotpAuthService = SpringUtils.getBean(GoogleTotpAuthServiceImpl.class);

    /**
     * 验证code，获取key
     * 调用 GoogleAuthFactory.authoriseUser时触发
     * @param userName
     * @return
     */
    @Override
    public String getSecretKey(String userName) {
//        String key = "YJOQQPWFHXZAMM2A";

//        System.out.println(
//                String.format(
//                        "getSecretKey invoked with user name %s returning %s.",
//                        userName,
//                        key));
        NebulaUserInfo userInfo = userService.findByUsername(userName);
        GoogleTotpAuth googleTotpAuth = googleTotpAuthService.selectByEmpId(userInfo.getEmpId());
        return googleTotpAuth.getgSecret();
    }

    /**
     * 新增账户，存储key
     * 调用 GoogleAuthFactory.createCredentialsForUser时触发
     * @param userName
     * @param secretKey
     * @param validationCode
     * @param scratchCodes
     */
    @Override
    public void saveUserCredentials(String userName,
                                    String secretKey,
                                    int validationCode,
                                    List<Integer> scratchCodes) {

//        System.out.println("saveUserCredentials invoked with user name " + userName);
        NebulaUserInfo userInfo = userService.findByUsername(userName);

        String label = "Nebula";
        String bu = userName+"@olymtech.com";
        String otpAuthURL = MyGoogleAuthenticatorQRGenerator.getOtpAuthURL(label, bu, secretKey);

        String scratchCodesString = StringUtils.join(scratchCodes.toArray(), ",");
        GoogleTotpAuth googleTotpAuth = new GoogleTotpAuth(userInfo.getEmpId(), secretKey, otpAuthURL,scratchCodesString);
        googleTotpAuthService.insertGoogleTotpAuth(googleTotpAuth);
    }

}
