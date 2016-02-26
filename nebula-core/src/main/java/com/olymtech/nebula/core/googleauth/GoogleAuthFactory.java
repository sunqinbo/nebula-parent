/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.core.googleauth;

import com.olymtech.nebula.entity.GoogleAuth;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Gavin on 2016-02-25 16:43.
 */
public class GoogleAuthFactory {

    /**
     * Number of digits of a scratch code represented as a decimal integer.
     */
    private static final int SCRATCH_CODE_LENGTH = 8;

    /**
     * Modulus used to truncate the scratch code.
     */
    public static final int SCRATCH_CODE_MODULUS = (int) Math.pow(10, SCRATCH_CODE_LENGTH);


    public static GoogleAuth createCredentialsForUser(String userName) {
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();

        final GoogleAuthenticatorKey key =
                googleAuthenticator.createCredentials(userName);
        final String secret = key.getKey();
        final List<Integer> scratchCodes = key.getScratchCodes();

        String label = "Nebula";
        String bu = "ops@olymtech.com";

        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthURL(label, bu, key);

        GoogleAuth googleAuth = new GoogleAuth(userName,secret,otpAuthURL,scratchCodes,label,bu);

        System.out.println("Please register (otpauth uri): " + otpAuthURL);
        System.out.println("Secret key is " + secret);

        for (Integer i : scratchCodes) {
            if (!validateScratchCode(i)) {
                throw new IllegalArgumentException("An invalid code has been " +
                        "generated: this is an application bug.");
            }
            System.out.println("Scratch code: " + i);
        }
        return googleAuth;
    }

    public static Boolean authoriseUser(String userName, int validationCode) {
        GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder gacb =
                new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder()
                        .setTimeStepSizeInMillis(TimeUnit.SECONDS.toMillis(30))
                        .setWindowSize(5)
                        .setCodeDigits(6);
        GoogleAuthenticator ga = new GoogleAuthenticator(gacb.build());
        Boolean isCodeValid = ga.authorizeUser(userName, validationCode);

        System.out.println("Check VALIDATION_CODE = " + isCodeValid);

        return isCodeValid;
    }

    private static boolean validateScratchCode(int scratchCode) {
        return (scratchCode >= SCRATCH_CODE_MODULUS / 10);
    }

}
