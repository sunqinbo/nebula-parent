package com.olymtech.nebula;

import org.springframework.core.io.FileSystemResource;
import org.springframework.util.FileCopyUtils;

import java.io.*;

/**
 * Created by wangyiqiang on 16/3/9.
 */
public class MainTest {
    public static void main(String[] args) {

        String dirPath = "/Users/wangyiqiang/Desktop";
        String destPath = "/Users/wangyiqiang/test";

//        Boolean success = createDir(destPath);
//        System.out.println(success);

        File src = new File(dirPath);
        File dest = new File(destPath);
        try {
            Boolean success = copyFolder(src, dest);
            System.out.println(success);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
