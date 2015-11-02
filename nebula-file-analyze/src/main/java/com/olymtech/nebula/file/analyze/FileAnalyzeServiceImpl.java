package com.olymtech.nebula.file.analyze;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liwenji on 2015/10/30.
 */
public class FileAnalyzeServiceImpl implements IFileAnalyzeService{
    public List<String> getFileListByDirPath(String dirPath) {
        List<String> fileList = new ArrayList<String>();
        File file = new File(dirPath);
        File[] files = file.listFiles();
        for (File f:files) {
            if (!f.isDirectory()){
                fileList.add(f.getName());
            }
        }
        return fileList;
    }

    public List<String> getFileListByRecursionDirPath(String dirPath) {
        List<String> fileList = new ArrayList<String>();
        File file = new File(dirPath);
        reverseRecursionDirPath(file,fileList);
        return fileList;
    }

    public Map<String,Boolean> getDirMapByDirPath(String dirPath) {
        Map<String,Boolean> fileList = new HashMap<>();
        File file = new File(dirPath);
        reverseRecursionMapByDirPath(file,fileList);
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

    public void reverseRecursionMapByDirPath(File file,Map<String,Boolean> fileList){
        File[] files = file.listFiles();
        for (File f:files) {
            if (!f.isDirectory()){
                fileList.put(f.getName(), false);
            }
            else {
                fileList.put(f.getName(), true);
                File newfile = new File(f.getAbsolutePath());
                reverseRecursionMapByDirPath(newfile,fileList);
            }
        }
    }


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
