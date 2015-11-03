/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.common.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by Gavin on 2015-10-23 15:01.
 */
public class HttpUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    //    @SuppressWarnings("deprecation")
    public static String getResponesByURL(String url, String... method) throws HttpException,
            IOException {
        HttpClient httpClient = new HttpClient();
        logger.info("HTTP REQUEST:" + url);
        HttpMethod getMethod = null;
        // 如果url不是以“http//”开头的话加上 http：//
        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }
        // 默认为get方法
        if (method.length > 0 && method[0].equals("post")) {
            getMethod = new PostMethod(url);
        } else {
            getMethod = new GetMethod(url);
        }
        httpClient.setTimeout(30000);
        httpClient.executeMethod(getMethod);
        return inputStream2String(getMethod.getResponseBodyAsStream());
    };

    /**
     * @Title: HttpClient
     * @Description: GET URL请求，返回InputStream
     * @param url
     * @param method
     * @return InputStream
     * @throws HttpException
     * @throws IOException
     */
    @SuppressWarnings("deprecation")
    public static InputStream getResponesInputStreamByURL(String url, String... method) throws HttpException,
            IOException {
        org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
        logger.info("HTTP REQUEST:" + url);
        HttpMethod getMethod = null;
        // 如果url不是以“http//”开头的话加上 http：//
        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }
        // 默认为get方法
        if (method.length > 0 && method[0].equals("post")) {
            getMethod = new PostMethod(url);
        } else {
            getMethod = new GetMethod(url);
        }
        httpClient.setTimeout(30000);
        httpClient.executeMethod(getMethod);
        return getMethod.getResponseBodyAsStream();
    };

    /**
     *
     *
     * @return
     */
    public static String postRequestWithPostBody(String url, Map<String, String> requestMap,
                                                 String... encode) {
        org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
        //http://ap-sms.alipay.com/noti-queue-api/alarms.htm
        PostMethod postMethod = new PostMethod(url);
        NameValuePair[] nameValuePairs = new NameValuePair[requestMap.size()];
        int count = 0;
        for (Map.Entry<String, String> entry : requestMap.entrySet()) {
            nameValuePairs[count++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        postMethod.setRequestBody(nameValuePairs);
        if (encode.length > 0) {
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encode[0]);
        }
        try {
            httpClient.executeMethod(postMethod);
            return postMethod.getResponseBodyAsString();
        } catch (Exception e) {
            logger.error(url + " requst ERROR:", e);
            return null;
        }
    }

    /**
     * 流转化
     *
     * @param inputStream
     * @return
     */
    public static String inputStream2String(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String readLine = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            while ((readLine = reader.readLine()) != null) {
                stringBuffer.append(readLine);
            }
        } catch (IOException e) {
            logger.error("inputStream2String", e);
        }
        return stringBuffer.toString();
    }

    /**
     * <KEY,VALUE>转成：key=value&key=value
     *
     * @param paramters
     * @return
     */
    public static String mapParametersToString(Map<String, String> paramters) {
        StringBuffer sbf = new StringBuffer();
        for (String key : paramters.keySet()) {
            sbf.append(key + "=" + paramters.get(key));
            sbf.append("&");
        }
        return sbf.toString();
    }

    public static String getResponesEncodeUTF8ByURL(String url, String... method) throws HttpException,
            IOException {
        HttpClient httpClient = new HttpClient();
        logger.info("HTTP REQUEST:" + url);
        HttpMethod getMethod = null;
        // 如果url不是以“http//”开头的话加上 http：//
        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }
        // 默认为get方法
        if (method.length > 0 && method[0].equals("post")) {
            getMethod = new PostMethod(url);
        } else {
            getMethod = new GetMethod(url);
        }
        httpClient.setTimeout(30000);
        httpClient.executeMethod(getMethod);
        return inputStream2StringEncodeUTF8(getMethod.getResponseBodyAsStream());
    }

    public static String inputStream2StringEncodeUTF8(InputStream inputStream){
    	/*转码*/
        try {
            byte bytes[] = new byte[4096];
            StringBuffer sb = new StringBuffer();
            int size = 0;
            while ((size = inputStream.read(bytes)) > 0) {
                String str = new String(bytes, 0, size, "UTF-8");
                sb.append(str);
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error("inputStream2StringEncodeUTF8 error:",e);
        }
        return null;
    }

}
