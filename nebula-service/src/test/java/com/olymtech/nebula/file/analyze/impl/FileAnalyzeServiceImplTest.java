package com.olymtech.nebula.file.analyze.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;

import static org.junit.Assert.*;

/**
 * Created by Gavin on 2016-03-03 17:49.
 */
public class FileAnalyzeServiceImplTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCopyFile() throws Exception {
        String fileName = "arsenal-api.war";

        String srcFilePath = "/home/saas/deploy_tmp/product.ywpt.20160303.165728/publish_war/" + fileName;
        String destFilePath = "/home/saas/deploy_tmp/product.ywpt.20160303.165728/src_svn/publish/" + fileName;
        Boolean copyResult = copyFile(srcFilePath, destFilePath);
        System.out.println(copyResult);
    }


    public Boolean copyFile(String srcFilePath, String destFilePath){
        Boolean result = false;
        try{
            FileOutputStream dest = new FileOutputStream(new File(destFilePath));
            FileSystemResource src = new FileSystemResource(srcFilePath);
            FileCopyUtils.copy(src.getInputStream(), dest);
            result = true;
        }catch (Exception e){
            System.out.println(e);
            result = false;
        }
        return result;
    }

}