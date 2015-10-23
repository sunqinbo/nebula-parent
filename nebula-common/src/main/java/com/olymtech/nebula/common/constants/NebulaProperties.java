/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.common.constants;

/**
 * Created by Gavin on 2015-10-23 15:05.
 */
public class NebulaProperties {
    public static String filename = null;

    /**
     * 构造方法，初始化需要读取的filename
     * @param file
     */
    public NebulaProperties(String file){
        filename = file;
    }

    /**
     *
     * @param idName
     * @return
     */
    public static String getPropertyByIdName(String idName) {
        if(filename == null){
            return null;
        }
        return (String) PropertiesReader.getProperty(idName,filename);
    }
}
