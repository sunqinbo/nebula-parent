package com.olymtech.nebula;

import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaUserInfo;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.FileCopyUtils;

import java.io.*;

/**
 * Created by wangyiqiang on 16/3/9.
 */
public class MainTest {
    public static void main(String[] args) {

//        String dirPath = "/Users/wangyiqiang/Desktop";
//        String destPath = "/Users/wangyiqiang/test";
//
////        Boolean success = createDir(destPath);
////        System.out.println(success);
//
//        File src = new File(dirPath);
//        File dest = new File(destPath);
//        try {
//            Boolean success = copyFolder(src, dest);
//            System.out.println(success);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        Integer a = 1;
//        Integer b = 1;
//        if(a == b){
//            System.out.println("a == b");
//        }else{
//            System.out.println("a != b");
//        }
//
//        NebulaUserInfo userA = new NebulaUserInfo();
//        userA.setEmpId(1000);
//        NebulaUserInfo userB = new NebulaUserInfo();
//        userB.setEmpId(1000);
//
//        if(userA.getEmpId().equals(userB.getEmpId())){
//            System.out.println("userA == userB");
//        }else{
//            System.out.println("userA != userB");
//        }

//        Boolean a  = null;
//        if(a != true){
//            System.out.println("xxx");
//        }
        File file = new File("/tmp/aaaaaaaaaa");
        if(file.exists()){
            System.out.println(deleteDir(file));
            System.out.println("have");
        }else{
            System.out.println(deleteDir(file));
            System.out.println("no");
        }

    }

    private static Boolean deleteDir(File dir){
        if(dir.isDirectory()){
            String[] child = dir.list();
            for(int i = 0;i<child.length;i++){
                Boolean success = deleteDir(new File(dir,child[i]));
                if(!success){
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /*copy目录及其子文件*/
    public static Boolean copyFolder(File src, File dest) throws IOException {
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
            System.out.println("copyFolder error:" + e);
            return false;
        }
        return true;
    }

    /*新建目录*/
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }

    public static Boolean copyFile(String srcFilePath, String destFilePath) {
        Boolean result = false;
        try {
            FileOutputStream dest = new FileOutputStream(new File(destFilePath));
            FileSystemResource src = new FileSystemResource(srcFilePath);
            FileCopyUtils.copy(src.getInputStream(), dest);
            result = true;
        } catch (Exception e) {
            System.out.println("copyFile error:" + e);
            result = false;
        }
        return result;
    }


}
