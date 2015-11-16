package com.olymtech.nebula.file.analyze.impl;

import com.olymtech.nebula.file.analyze.IFileAnalyzeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liwenji on 2015/10/30.
 */
@Service
public class FileAnalyzeServiceImpl implements IFileAnalyzeService {

    private Logger logger       = LoggerFactory.getLogger(this.getClass());

    public List<String> getFileListByDirPath(String dirPath) {
        List<String> fileList = new ArrayList<String>();
        try{
            File file = new File(dirPath);
            File[] files = file.listFiles();
            for (File f:files) {
                if (!f.isDirectory()){
                    fileList.add(f.getName());
                }
            }
        }catch (Exception e){
            logger.error("getFileListByDirPath error,dirPath:"+dirPath,e);
        }
        return fileList;
    }
    @Override
    public List<String> getFileListByRecursionDirPath(String dirPath) {
        List<String> fileList = new ArrayList<String>();
        try{
            File file = new File(dirPath);
            reverseRecursionDirPath(file,fileList);
        }catch (Exception e){
            logger.error("getFileListByRecursionDirPath error,dirPath:"+dirPath,e);
        }
        return fileList;
    }

    public Map<String,Boolean> getDirMapByDirPath(String dirPath) {
        Map<String,Boolean> fileList = new HashMap<>();
        try{
            File file = new File(dirPath);
            File[] files = file.listFiles();
            for (File f:files) {
                if (!f.isDirectory()){
                    fileList.put(f.getName(), false);
                }
                else {
                    fileList.put(f.getName(), true);
                }
            }
        }catch(Exception e){
            logger.error("getDirMapByDirPath error,dirPath:"+dirPath,e);
        }
        return fileList;
    }

    public void reverseRecursionDirPath(File file,List<String> fileList){
        File[] files = file.listFiles();
        for (File f:files) {
            if (!f.isDirectory()) {
                fileList.add(f.getName());
            }
            else {
                File newfile = new File(f.getAbsolutePath());
                reverseRecursionDirPath(newfile,fileList);
            }
        }
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
}
