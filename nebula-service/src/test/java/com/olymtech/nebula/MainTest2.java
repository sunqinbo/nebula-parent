package com.olymtech.nebula;

import java.io.File;

/**
 * Created by wangyiqiang on 16/7/13.
 */
public class MainTest2 {
    public  static  void main(String[] args) {
        String svnLocalPath = "/Users/wangyiqiang/Downloads/dcas/";
        File file = new File(svnLocalPath);
        File[] tempList = file.listFiles();
        System.out.println(tempList.length);
        if (tempList.length == 0) {
            System.out.println(false);
        } else {
            System.out.println(true);
        }
    }
}
