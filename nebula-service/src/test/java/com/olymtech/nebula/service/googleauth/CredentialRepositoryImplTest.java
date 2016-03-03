package com.olymtech.nebula.service.googleauth;

import com.olymtech.nebula.core.googleauth.GoogleAuthFactory;
import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gavin on 2016-02-25 17:14.
 */
public class CredentialRepositoryImplTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetSecretKey() throws Exception {

    }

    @Test
    public void testSaveUserCredentials() throws Exception {

    }

    @Test
    public void testCreateCredentialsForUser() throws Exception {
        GoogleAuthFactory.createCredentialsForUser("zongxinh");
    }

    @Test
    public void testAuthoriseUser() throws Exception {
        GoogleAuthFactory.authoriseUser("zongxinh", 464693);
    }

    @Test
    public void testAAA() throws  Exception{
        List<Integer> scratchCodes = new ArrayList<>();
        scratchCodes.add(123);
        scratchCodes.add(234);
        scratchCodes.add(345);
        String scratchCodesString = StringUtils.join(scratchCodes.toArray(), ",");
        System.out.println(scratchCodesString);

        String a = "tRuE";
        Boolean b = Boolean.valueOf(a);
        if(b){
            System.out.println("true...");
        }else{
            System.out.println("false...");
        }

    }

    @Test
    public void testWar() throws Exception {
        List<String> appNameList = new ArrayList<>();
        appNameList.add("jdflsdio&&66.war");
        appNameList.add("12qwreetwar.txt");
        appNameList.add("yuyuyuy5454.war$");
        appNameList.add("yuyuyuy5454.war$");
        appNameList.add("yuyuyuy5454.tar");
        appNameList.add("yuyuyuy5454.wazp.war");
        appNameList.add("yuyuyuy5454.war.exel");
        String appNames = "";
        String regex = ".*\\.war$";
        List<String> appNameListWithoutWar = new ArrayList<>();
        for (int i = 0; i < appNameList.size() ; i++) {
            if (appNameList.get(i).matches(regex)) {
                String appname = appNameList.get(i).replace(".war", "");
                appNameListWithoutWar.add(appname);
            }
        }
        appNames = StringUtils.join(appNameListWithoutWar.toArray(), ",");
    }

}