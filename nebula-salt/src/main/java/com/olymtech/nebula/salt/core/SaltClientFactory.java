/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.salt.core;

import com.suse.saltstack.netapi.AuthModule;
import com.suse.saltstack.netapi.client.SaltStackClient;
import com.suse.saltstack.netapi.datatypes.Token;

import java.io.InputStreamReader;
import java.net.URI;
import java.util.MissingResourceException;
import java.util.Properties;

import static com.suse.saltstack.netapi.AuthModule.PAM;

/**
 * @author taoshanchang 15/10/30
 */
public class SaltClientFactory {

    // CONSTANT
    private static final String RESOURCE_SALT_PROPERTIES = "/salt.properties";

    protected static final String SALT_URI = "uri";
    protected static final String TRUST_STOR = "trust_stor";
    protected static final String PORT = "port";
    protected static final String USERNAME = "username";
    protected static final String PASSWORD = "password";

    private static SaltStackClient client = null;
    private static Token token;

    // CONFIG
    protected static Properties conf;

    // SINGLETON
    public static synchronized SaltStackClient getSaltClient() throws Exception {

        if (client == null) {

            try {
                InputStreamReader ip = new InputStreamReader(SaltClientFactory.class.getResourceAsStream(RESOURCE_SALT_PROPERTIES), "utf-8");
                conf = new Properties();
                conf.load(ip);
            } catch (Exception ex) {
                MissingResourceException mrex = new MissingResourceException("Failed to load conf resource.", SaltClientFactory.class.getName(), RESOURCE_SALT_PROPERTIES);
                mrex.initCause(ex);
                throw (mrex);
            }

            if(conf.get(SALT_URI)==null){
                throw new NullPointerException("in th salt.properties file does't have the propertiy ->>>> uri");
            }
            if(conf.get(TRUST_STOR)==null){
                throw new NullPointerException("in th salt.properties file does't have the propertiy ->>>> trust_stor");
            }
            if(conf.get(PORT)==null){
                throw new NullPointerException("in th salt.properties file does't have the propertiy ->>>> port");
            }
            if(conf.get(USERNAME)==null){
                throw new NullPointerException("in th salt.properties file does't have the propertiy ->>>> username");
            }
            if(conf.get(PASSWORD)==null){
                throw new NullPointerException("in th salt.properties file does't have the propertiy ->>>> password");
            }

            System.setProperty("javax.net.ssl.trustStore", conf.getProperty(TRUST_STOR));
            URI uri = URI.create(conf.get(SALT_URI) + ":" + conf.getProperty(PORT));
            client = new SaltStackClient(uri);

            token = client.login(conf.get(USERNAME).toString(), conf.get(PASSWORD).toString(), PAM);

            return client;
        }

        if (token.getExpire().getTime()<=System.currentTimeMillis()){
            token = client.login(conf.get(USERNAME).toString(), conf.get(PASSWORD).toString(), PAM);
        }

        return client;

    }

}
