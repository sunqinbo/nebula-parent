/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.common.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Created by Gavin on 2015-10-23 15:05.
 */
public class PropertiesReader {
    private static Logger logger = LoggerFactory.getLogger(PropertiesReader.class);


    /**
     * 读取filename配置文件，通过配置文件的id，获取value
     * @param id
     * @param filename
     * @return value
     */
    public static Object getProperty(String id, String filename) {
        InputStream propertiesFile = null;
        String resource = "classpath:"+filename;
        try {
            propertiesFile = new FileInputStream(new PathMatchingResourcePatternResolver()
                    .getResource(resource).getFile());
            Properties p = new Properties();
            p.load(propertiesFile);
            return p.get(id);
        } catch (IOException e) {
            logger.error(resource+"无法找到", e);
        }
        return null;
    }
}
