/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.core.googleauth;

import org.apache.http.client.utils.URIBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Gavin on 2016-02-29 09:54.
 * 原生处理的是 GoogleAuthenticatorKey credentials
 * 这里我们替换成 String key
 * 详见：com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator
 */
public class MyGoogleAuthenticatorQRGenerator {

    private static final String TOTP_URI_FORMAT =
            "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=%s";

    private static String internalURLEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 encoding is not supported by URLEncoder.", e);
        }
    }

    private static String formatLabel(String issuer, String accountName) {
        if (accountName == null || accountName.trim().length() == 0) {
            throw new IllegalArgumentException("Account name must not be empty.");
        }

        StringBuilder sb = new StringBuilder();
        if (issuer != null) {
            if (issuer.contains(":")) {
                throw new IllegalArgumentException("Issuer cannot contain the \':\' character.");
            }

            sb.append(issuer);
            sb.append(":");
        }

        sb.append(accountName);

        return sb.toString();
    }

    public static String getOtpAuthURL(String issuer,
                                       String accountName,
                                       String secret) {
        return String.format(
                TOTP_URI_FORMAT,
                internalURLEncode(getOtpAuthTotpURL(issuer, accountName, secret)));
    }

    public static String getOtpAuthTotpURL(String issuer,
                                           String accountName,
                                           String secret) {

        URIBuilder uri = new URIBuilder()
                .setScheme("otpauth")
                .setHost("totp")
                .setPath("/" + formatLabel(issuer, accountName))
                .setParameter("secret", secret);


        if (issuer != null) {
            if (issuer.contains(":")) {
                throw new IllegalArgumentException("Issuer cannot contain the \':\' character.");
            }

            uri.setParameter("issuer", issuer);
        }

        /*
            The following parameters aren't needed since they are all defaults.
            We can exclude them to make the URI shorter.
         */
        // uri.setParameter("algorithm", "SHA1");
        // uri.setParameter("digits", "6");
        // uri.setParameter("period", "30");

        return uri.toString();

    }

}
