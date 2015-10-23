/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by Gavin on 2015-10-23 15:03.
 */
public class MD5Utils {

    /**
     * 对字符串进行MD5加密
     *
     * @param text 明文
     *
     * @return 密文
     */
    public static String encode(String text) {
        MessageDigest msgDigest = null;

        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }

        msgDigest.update(text.getBytes());

        byte[] bytes = msgDigest.digest();

        byte tb;
        char low;
        char high;
        char tmpChar;

//        String md5Str = new String();
        StringBuffer md5StrBuffer = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            tb = bytes[i];

            tmpChar = (char) ((tb >>> 4) & 0x000f);

            if (tmpChar >= 10) {
                high = (char) (('a' + tmpChar) - 10);
            } else {
                high = (char) ('0' + tmpChar);
            }

//            md5Str += high;
            md5StrBuffer.append(high);
            tmpChar = (char) (tb & 0x000f);

            if (tmpChar >= 10) {
                low = (char) (('a' + tmpChar) - 10);
            } else {
                low = (char) ('0' + tmpChar);
            }

//            md5Str += low;
            md5StrBuffer.append(low);
        }

        return md5StrBuffer.toString();
    }

    public static String monEncode(String text) throws NoSuchAlgorithmException,UnsupportedEncodingException {
        // 对该字符串进行MD5加密
//        String str = "对该字符串进行MD5加密";
        // 进行MD5加密时，使用该字符集解码
        String charset = "GBK";

        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(text.getBytes(charset));
        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

//        System.out.println(md5StrBuff.toString().toLowerCase());
        return md5StrBuff.toString().toLowerCase();
    }

}
