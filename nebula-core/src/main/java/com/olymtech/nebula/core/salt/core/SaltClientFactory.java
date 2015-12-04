/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.salt.core;

import com.olymtech.nebula.core.salt.SaltStackServiceImpl;
import com.suse.saltstack.netapi.client.SaltStackClient;
import com.suse.saltstack.netapi.datatypes.Token;
import com.suse.saltstack.netapi.exception.SaltStackException;
import com.suse.saltstack.netapi.results.ResultInfo;
import com.suse.saltstack.netapi.results.ResultInfoSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;

import static com.suse.saltstack.netapi.AuthModule.PAM;

/**
 * @author taoshanchang 15/10/30
 */
public class SaltClientFactory {

    private static final Logger logger = LoggerFactory.getLogger(SaltStackServiceImpl.class);

    // CONSTANT
    private static final String RESOURCE_SALT_PROPERTIES = "/nebula.properties";

    protected static final String SALT_URI = "uri";
    protected static final String TRUST_STOR_NAME = "trust_stor_name";
    protected static final String PORT = "port";
    protected static final String USERNAME = "username";
    protected static final String PASSWORD = "password";

    protected static final String TIMEOUT = "timeout";

    private static SaltStackClient client = null;
    private static Token token = null;

    // CONFIG
    protected static Properties conf;

    // SINGLETON
    public static synchronized SaltStackClient getSaltClient() throws SaltStackException {

        if (client == null) {
            InputStream ip = null;
            try {
                String resource = "classpath:"+RESOURCE_SALT_PROPERTIES;
                ip = new FileInputStream(new PathMatchingResourcePatternResolver().getResource(resource).getFile());
                conf = new Properties();
                conf.load(ip);
                ip.close();
            } catch (Exception ex) {
                MissingResourceException mrex = new MissingResourceException("Failed to load conf resource.", SaltClientFactory.class.getName(), RESOURCE_SALT_PROPERTIES);
                mrex.initCause(ex);
                throw (mrex);
            }

            if(conf.get(SALT_URI)==null){
                throw new NullPointerException("in the salt.properties file does't have the propertiy ->>>> uri");
            }
            if(conf.get(TRUST_STOR_NAME)==null){
                throw new NullPointerException("in the salt.properties file does't have the propertiy ->>>> trust_stor");
            }
            if(conf.get(PORT)==null){
                throw new NullPointerException("in the salt.properties file does't have the propertiy ->>>> port");
            }
            if(conf.get(USERNAME)==null){
                throw new NullPointerException("in the salt.properties file does't have the propertiy ->>>> username");
            }
            if(conf.get(PASSWORD)==null){
                throw new NullPointerException("in the salt.properties file does't have the propertiy ->>>> password");
            }

            System.setProperty("javax.net.ssl.trustStore", SaltClientFactory.class.getClassLoader().getResource(conf.getProperty(TRUST_STOR_NAME)).getPath());
            URI uri = URI.create(conf.get(SALT_URI) + ":" + conf.getProperty(PORT));
            client = new SaltStackClient(uri);

            try {
                token = client.login(conf.get(USERNAME).toString(), conf.get(PASSWORD).toString(), PAM);
            } catch (SaltStackException e) {
                e.printStackTrace();
                throw e;
            }

            return client;
        }

        if (token==null){
            try {
                token = client.login(conf.get(USERNAME).toString(), conf.get(PASSWORD).toString(), PAM);
            } catch (SaltStackException e) {
                e.printStackTrace();
                throw e;
            }
        }

        if (token.getExpire().getTime()<=System.currentTimeMillis()){
            try {
                token = client.login(conf.get(USERNAME).toString(), conf.get(PASSWORD).toString(), PAM);
            } catch (SaltStackException e) {
                e.printStackTrace();
            }
        }

        return client;

    }

    public static ResultInfoSet getResult(String jobId,int targetsCount) throws SaltStackException {
        Map<String, Object> results = null;
        ResultInfoSet jobResult = null;

        int time = 30;
        if (conf.get(TIMEOUT)!=null){
            String timeStr = conf.get(TIMEOUT).toString();
            try {
                time = Integer.getInteger(timeStr);
            }catch (Exception e){
                //do nothing but the default timeout is 30s
            }
        }

        long startTime = System.currentTimeMillis();   //获取开始时间

        do {
            jobResult = SaltClientFactory.getSaltClient().getJobResult(jobId);
            ResultInfo resultInfo = jobResult.get(0);
            results = resultInfo.getResults();
            long endTime = System.currentTimeMillis(); //获取结束时间
            if ((endTime - startTime) / 1000 > time) {
                logger.error("cpFileAndDir is waiting timeout");
                break;
            }

        } while (results.size() != targetsCount);

        return jobResult;
    }

}
