/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.olymtech.nebula.entity.NginxServer;
import com.olymtech.nebula.service.INginxService;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gavin on 2016-06-23 16:41.
 */
@Service
public class NginxServiceImpl implements INginxService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${master_nginx_url}")
    private String masterNginxUrl;
    @Value("${master_nginx_username}")
    private String masterNginxUsername;
    @Value("${master_nginx_password}")
    private String masterNginxPassword;

    @Override
    public List<NginxServer> getMasterNginxServers(){
        return getNginxServers(masterNginxUrl,masterNginxUsername,masterNginxPassword);
    }

    private List<NginxServer> getNginxServers(String nginxUrl,String username,String password){
        List<NginxServer> nginxServers = new ArrayList<>();
        try {
            String jsonDataString = connectToUrlUsingBasicAuthentication(nginxUrl, username, password);
            JSONObject jsonObject = JSONObject.parseObject(jsonDataString);
            if (null == jsonObject) {
                return null;
            }

            JSONObject jsonServers = jsonObject.getJSONObject("servers");

            if(jsonServers == null){
                return null;
            }

            JSONArray jsonServerArray = jsonServers.getJSONArray("server");

            if(jsonServerArray == null){
                return null;
            }

            for(int i = 0;i<jsonServerArray.size();i++){
                Object serverObject = jsonServerArray.get(i);
                NginxServer nginxServer = JSON.parseObject(serverObject.toString(), NginxServer.class);
                nginxServers.add(nginxServer);
            }
            return nginxServers;
        } catch (Exception e) {
            return null;
        }
    }

    private String connectToUrlUsingBasicAuthentication(String urlString,String username,String password){
        try {
            String authString = username + ":" + password;
            byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
            String authStringEnc = new String(authEncBytes);

            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            logger.error("connectToUrlUsingBasicAuthentication error MalformedURLException",e);
        } catch (IOException e) {
            logger.error("connectToUrlUsingBasicAuthentication error IOException", e);
        }
        return null;
    }

}
