package com.olymtech.nebula.service.googleauth;

import com.olymtech.nebula.core.googleauth.GoogleAuthFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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

}