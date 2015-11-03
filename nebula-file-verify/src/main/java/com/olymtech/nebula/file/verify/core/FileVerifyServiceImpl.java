package com.olymtech.nebula.file.verify.core;



import com.olymtech.nebula.file.verify.IFileVerifyService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;

import java.util.Map;

/**
 * Created by WYQ on 2015/10/30.
 */

/*
*获取文件md5值
*/

public class FileVerifyServiceImpl implements IFileVerifyService {

    /**
     * 根据文件路径计算文件的MD5
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String getFileMD5ByFilePath(String filePath) throws IOException {
        File f = new File(filePath);
        return getFileMD5String(f);
    }

    /**
     * 根据文件对象计算文件的MD5
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
                file.length());
        messageDigest.update(byteBuffer);
        return bufferToHex(messageDigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    protected static MessageDigest messageDigest = null;
    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /* 根据文件路径，获取文件修改时间*/
    public static String getFileModifyTimeByFilePath(String filePath) {
        Calendar cal = Calendar.getInstance();
        File f = new File(filePath);
        long time = f.lastModified();
        cal.setTimeInMillis(time);
        return cal.getTime().toLocaleString();
    }

    /*给定一个目录路径，计算该目录下每个文件的md5值*/

    public static Map<String,String> getFilesMd5MapByDirPath(String dirPath) throws IOException{
        Map<String,String> fileList = new HashMap<String, String>();
        File file = new File(dirPath);
        reverseRecursionMapByDirPath(file,fileList);
        return fileList;
    }

    public static void reverseRecursionMapByDirPath(File file, Map<String, String> fileList) throws IOException{
        File[] files = file.listFiles();
        for (File f:files) {
            if (!f.isDirectory()){
                fileList.put(f.getName(), getFileMD5ByFilePath(f.getAbsolutePath()));
            }
            else {
                fileList.put(f.getName(),null );
                File newFile = new File(f.getAbsolutePath());
                reverseRecursionMapByDirPath(newFile,fileList);
            }
        }
    }

    /**
     * 测试方法
     *
     * @param args
     * @throws IOException
     */
//    public static void main(String[] args) throws IOException {
//        String filePath = "C:\\Users\\WYQ\\Desktop\\index.txt"; // 更改文件名不会导致文件md5值变化
//        String dirPath = "C:\\Users\\WYQ\\Desktop\\任务"; // 更改文件名不会导致文件md5值变化
//        System.out.println("输出MD5的值为："+getFileMD5ByFilePath(filePath));
//        System.out.println("文件最后修改时间为："+getFileModifyTimeByFilePath(filePath));
//        System.out.println("获取目录文件子路径："+getFilesMd5MapByDirPath(dirPath));
//    }


}
