package com.olymtech.nebula.file.analyze.impl;

import com.olymtech.nebula.common.utils.DataConvert;
import com.olymtech.nebula.file.analyze.IFileAnalyzeService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liwenji on 2015/10/30.
 */
@Service
public class FileAnalyzeServiceImpl implements IFileAnalyzeService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${master_deploy_dir}")
    private String masterDeployDir;

    @Value("${script_get_dir_diff_list}")
    private static String scriptGetDirDiffList;

    @Override
    public List<String> getFileListByDirPath(String dirPath) {
        List<String> fileList = new ArrayList<String>();
        try {
            File file = new File(dirPath);
            File[] files = file.listFiles();
            for (File f : files) {
                if (!f.isDirectory()) {
                    fileList.add(f.getName());
                }
            }
        } catch (Exception e) {
            logger.error("getFileListByDirPath error,dirPath:" + dirPath, e);
        }
        return fileList;
    }

    @Override
    public List<String> getFileListByRecursionDirPath(String dirPath) {
        List<String> fileList = new ArrayList<String>();
        try {
            File file = new File(dirPath);
            reverseRecursionDirPath(file, fileList);
        } catch (Exception e) {
            logger.error("getFileListByRecursionDirPath error,dirPath:" + dirPath, e);
        }
        return fileList;
    }

    @Override
    public Map<String, Boolean> getDirMapByDirPath(String dirPath) {
        Map<String, Boolean> fileList = new HashMap<>();
        try {
            File file = new File(dirPath);
            File[] files = file.listFiles();
            for (File f : files) {
                if (!f.isDirectory()) {
                    fileList.put(f.getName(), false);
                } else {
                    fileList.put(f.getName(), true);
                }
            }
        } catch (Exception e) {
            logger.error("getDirMapByDirPath error,dirPath:" + dirPath, e);
        }
        return fileList;
    }

    public void reverseRecursionDirPath(File file, List<String> fileList) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (!f.isDirectory()) {
                fileList.add(f.getName());
            } else {
                File newfile = new File(f.getAbsolutePath());
                reverseRecursionDirPath(newfile, fileList);
            }
        }
    }

    @Override
    public Boolean copyFile(String srcFilePath, String destFilePath) {
        Boolean result = false;
        try {
            FileOutputStream dest = new FileOutputStream(new File(destFilePath));
            FileSystemResource src = new FileSystemResource(srcFilePath);
            FileCopyUtils.copy(src.getInputStream(), dest);
            result = true;
        } catch (Exception e) {
            logger.error("copyFile error:", e);
            result = false;
        }
        return result;
    }

//    public void reverseRecursionMapByDirPath(File file,Map<String,Boolean> fileList){
//        File[] files = file.listFiles();
//        for (File f:files) {
//            if (!f.isDirectory()){
//                fileList.put(f.getName(), false);
//            }
//            else {
//                fileList.put(f.getName(), true);
//                File newfile = new File(f.getAbsolutePath());
//                reverseRecursionMapByDirPath(newfile,fileList);
//            }
//        }
//    }


//    public static void main(String[] args) throws Exception {
//        String dirPath="F:\\121";
//        List<String> list=getFileListByDirPath(dirPath);
//        for (int i=0; i <list.size();i++)
//            System.out.println(list.get(i));
//        System.out.println("-----------------------------------");
//        List<String> list2=getFileListByRecursionDirPath(dirPath);
//        for (int i=0; i <list2.size();i++)
//            System.out.println(list2.get(i));
//        Map<String,Boolean> fileList=getDirMapByDirPath(dirPath);
//    }

    @Override
    public Boolean rmFile(String key, String filename) {
        Boolean result = false;

        /** 删除的目录 不能为空*/
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(filename)) {
            return false;
        }

        try {
            String filePath = masterDeployDir + key + "/" + filename;
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                file.delete();
                return true;
            } else {
                logger.error("rmFile " + filename + " failed: not is file, file not exists.");
            }
        } catch (Exception e) {
            logger.error("rmFile " + filename + " error:", e);
        }
        return result;
    }

    /**
     * 复制一个目录及其子目录、文件到另外一个目录
     * @param src
     * @param dest
     * @throws IOException
     */
    @Override
    public Boolean copyFolder(File src, File dest) throws IOException {
        try {
            if (src.isDirectory()) {
                if (!dest.exists()) {
                    dest.mkdir();
                }
                String files[] = src.list();
                for (String file : files) {
                    File srcFile = new File(src, file);
                    File destFile = new File(dest, file);
                    // 递归复制
                    copyFolder(srcFile, destFile);
                }
            } else {
                copyFile(src.getAbsolutePath(), dest.getAbsolutePath());
            }
        } catch (IOException e) {
            logger.error("copyFolder error:",e);
            return false;
        }
        return true;
    }

    /**
     *
     *
     *

     {
         "msg": "\u83b7\u53d6\u6587\u4ef6\u5dee\u5f02\u6210\u529f",
         "responseContext": {
            "/a.properties": {
                 "change": "+dddd:3333\n",
                 "filename": "/a.properties",
                 "time": "2016-03-09 09:30:18.000000000"
             },
             "/arsenal.properties": {
                 "change": " ssss=3333\n-eeee=3333\n+cccc=4444\n",
                 "filename": "/arsenal.properties",
                 "time": "2016-03-09 09:29:27.000000000"
             },
             "/baidu/b.txt": {
                 "change": "-ddddddddddd\n",
                 "filename": "/baidu/b.txt",
                 "time": "1970-01-01 08:00:00.000000000"
             }
         },
         "status": "success"
     }

     * 返回 responseContext 的 string
     * 返回 null 均为错误
     * @param srcDir
     * @param destDir
     * @return
     */
    @Override
    public String getDirDiffList(String srcDir,String destDir){
        String responseContext = "";
        try {
            String command = scriptGetDirDiffList + " -s " + srcDir + " -d " + destDir;

            Process ps = Runtime.getRuntime().exec(command);
            ps.waitFor();

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String result = sb.toString();
            Map<String,Object> map = DataConvert.jsonStringToMap(result);

            if(map==null){
                return null;
            }
            if(map.get("status") == null){
                return null;
            }

            String status = map.get("status").toString();
            if (status.equals("error")){
                return null;
            }
            responseContext = map.get("responseContext").toString();
        } catch (Exception e) {
            logger.error("getDirDiffList error:",e);
        }
        return responseContext;
    }

}
