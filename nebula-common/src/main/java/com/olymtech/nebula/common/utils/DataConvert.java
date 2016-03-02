/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.common.utils;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gavin on 2016-02-24 15:34.
 */
public class DataConvert {

    private static Logger logger = LoggerFactory.getLogger(DataConvert.class);

    /**
     * 数据格式:
     * {
         "/Users/Gavin/workpython/nebula_script.bak/a/b.txt": "d41d8cd98f00b204e9800998ecf8427e",
         "/Users/Gavin/workpython/nebula_script.bak/change_ln.py": "9019f84862749b4b4e294e22a7105a59",
         "/Users/Gavin/workpython/nebula_script.bak/check_files_md5.py": "9fb175d7f51f4c95ae237ae1c712373b",
         "/Users/Gavin/workpython/nebula_script.bak/clean_fail_dir.py": "a59fd83e038819dd19c0de1d8469d73c",
         "/Users/Gavin/workpython/nebula_script.bak/clean_history_dir.py": "365f61e79c4a5c18c6d2fdb857b7fc3c",
         "/Users/Gavin/workpython/nebula_script.bak/copy_publish_old_etc.py": "60541ec1c85acb332dbcb107d93c7e75",
         "/Users/Gavin/workpython/nebula_script.bak/copy_publish_old_war.py": "c8c25d7a8343d5a30518991a24c6a333",
         "/Users/Gavin/workpython/nebula_script.bak/create_publish_dir.py": "473238a9b4c19a928cf1bda6991fd71a",
         "/Users/Gavin/workpython/nebula_script.bak/nebula_common.py": "c47326b1082354aafb7c73926c24f9a1",
         "/Users/Gavin/workpython/nebula_script.bak/nebula_common.pyc": "c7c5013e23566da93da4e78c35a592e2",
         "/Users/Gavin/workpython/nebula_script.bak/start_tomcat.py": "859c9b3ff7ca690f36760b0a324f0602",
         "/Users/Gavin/workpython/nebula_script.bak/stop_tomcat.py": "6b3cfd69535f6b562def08db915e9f30"
       }
     *
     * {"Port8080": false, "JavaCount": 0, "Ip": "172.16.137.130"}
     *
     * @param fileJsonString
     * @return
     */
    public static Map<String,String> jsonStringToList(String fileJsonString){
        Map<String,String> maps = new HashMap<>();
        try{
            JSONObject jsonObject = JSONObject.parseObject(fileJsonString);
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
//                System.out.println(entry.getKey() + ":" + entry.getValue());
                maps.put(entry.getKey(),String.valueOf(entry.getValue()));
            }
        }catch (Exception e){
            logger.error("fileJsonStringToList error:"+fileJsonString);
        }
        return maps;
    }

    /**
     * 去除filemap中 key的不同
     * 路径 模块key 是不同的，需要去除
     * eg key: /home/saas/tomcat/public_wars/test.ywpt.publish.20160225.113432
     *         /home/saas/tomcat/public_wars/test.ywpt.publish.20160206.111113
     * @param fileMap
     * @param key
     * @return
     */
    public static Map<String,String> fileMapWithoutModuleKey(Map<String,String> fileMap,String key){
        Map<String,String> resultMap = new HashMap<>();
        for (Map.Entry<String, String> entry : fileMap.entrySet()) {
            String entryKey = entry.getKey();
            entryKey = entryKey.replace(key,"");
            resultMap.put(entryKey,entry.getValue());
        }
        return resultMap;
    }


}
