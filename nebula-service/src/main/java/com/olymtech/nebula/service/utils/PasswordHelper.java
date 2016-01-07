package com.olymtech.nebula.service.utils;

import com.olymtech.nebula.dao.INebulaUserInfoDao;
import com.olymtech.nebula.entity.NebulaUserInfo;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Service
public class PasswordHelper {

    @Resource
    private INebulaUserInfoDao iNebulaUserInfoDao;

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Value("${password.algorithmName}")
    private String algorithmName = "md5";
    @Value("${password.hashIterations}")
    private int hashIterations = 2;

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public void encryptPassword(NebulaUserInfo user) {

        user.setSecurityKey(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();

        user.setPassword(newPassword);
    }

        /**
     * 验证密码
     * */
    public Boolean verifyAccount(String userName, String password){
        Boolean result = false;
        NebulaUserInfo userInfo = iNebulaUserInfoDao.selectByUsername(userName);

        String salt = userName + userInfo.getSecurityKey();
        String submitPassword = new SimpleHash(
                algorithmName,
                password,
                ByteSource.Util.bytes(salt),
                hashIterations).toHex();

        if(userInfo.getPassword().equals(submitPassword)){
            result = true;
        }

        return result;
    }
}
