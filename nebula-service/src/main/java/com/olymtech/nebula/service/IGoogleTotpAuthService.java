package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.GoogleTotpAuth;

/**
 * Created by wangyiqiang on 16/2/26.
 */
public interface IGoogleTotpAuthService {
    int insertGoogleTotpAuth(GoogleTotpAuth googleTotpAuth);

    void deleteGoogleTotpAuthById(Integer id);

    void updateGoogleTotpAuth(GoogleTotpAuth googleTotpAuth);


    GoogleTotpAuth selectByEmpId(Integer id);
}
