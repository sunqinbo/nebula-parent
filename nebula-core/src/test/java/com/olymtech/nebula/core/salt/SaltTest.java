/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.salt;

import com.olymtech.nebula.core.salt.core.SaltClientFactory;
import com.suse.saltstack.netapi.client.SaltStackClient;
import com.suse.saltstack.netapi.datatypes.target.Glob;
import com.suse.saltstack.netapi.exception.SaltStackException;
import com.suse.saltstack.netapi.results.ResultInfo;
import com.suse.saltstack.netapi.results.ResultInfoSet;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * @author taoshanchang 15/10/30
 */
public class SaltTest {
    private SaltStackServiceImpl service;

    @Before
    public void init() throws Exception {
        service = new SaltStackServiceImpl();
    }

    @Test
    public void getClientTest() throws Exception {
        SaltStackClient saltClient = SaltClientFactory.getSaltClient();

        Map<String, Map<String, Object>> minions = saltClient.getMinions();

        for (Map.Entry<String, Map<String, Object>> entry : minions.entrySet()) {
            System.out.println(entry.getKey() + "--->" + entry.getValue());

            for (Map.Entry<String, Object> entry2 : entry.getValue().entrySet()) {
                System.out.println(entry2.getKey() + "--->" + entry2.getValue());
            }
        }
    }

    @Test
    public void cpFileTest() throws SaltStackException {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("/home/saas/*.war", "/root");
        map.put("/home/saas/b", "/root/b");

        ResultInfoSet resultInfos = service.cpFile(new Glob(), map);
        List<ResultInfo> infoList = resultInfos.getInfoList();
        for (ResultInfo info : infoList) {
            System.out.println(info.getResults());
            System.out.println(info.getMinions());
            System.out.println(info.getStartTime());
        }
    }

    @Test
    public void cpDirTest() throws SaltStackException {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("/home/saas/test", "/root/test");
        map.put(" /home/saas/test2", "/root/test");

        ResultInfoSet resultInfos = service.cpDir(new Glob(), map);

        int size = resultInfos.getInfoList().size();

        System.out.println(size);

        for (ResultInfo info : resultInfos) {
            System.out.println(info.getResults());
            System.out.println(info.getMinions());
            System.out.println(info.getStartTime());
        }
    }

    @Test
    public void cmdTest() throws SaltStackException {
        List<Object> args1 = new ArrayList<>();
        args1.add("rm -rf /home/saas/tomcat/public_wars/a /home/saas/tomcat/public_wars/b");

        Map<String, Object> kwargs = new LinkedHashMap<>();

        ResultInfoSet resultInfos = service.cmdRun(new Glob(), args1, kwargs);
        List<ResultInfo> infoList = resultInfos.getInfoList();
        for (ResultInfo info : infoList) {
            System.out.println(info.getResults());
            System.out.println(info.getMinions());
            System.out.println(info.getStartTime());
        }
    }

    @Test
    public void cmdMakeDir() throws SaltStackException {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("/home/saas/tomcat/public_etcs/test4");

        ResultInfoSet b = service.mkDir(new Glob(), strings, false);

        System.out.print(b);

    }

    @Test
    public void startTomcatTest() throws SaltStackException {
        List<Object> args1 = new ArrayList<>();
        args1.add("sh /home/saas/tomcat/bin/start_tomcat.sh");

        Map<String, Object> kwargs = new LinkedHashMap<>();

        ResultInfoSet resultInfos = service.cmdRun(new Glob(), args1, kwargs);
        List<ResultInfo> infoList = resultInfos.getInfoList();
        for (ResultInfo info : infoList) {
            System.out.println(info.getResults());
            System.out.println(info.getMinions());
            System.out.println(info.getStartTime());
        }
    }

    @Test
    public void lnTest() throws SaltStackException {
        List<Object> args1 = new ArrayList<>();
        args1.add("ln -s /home/saas/tomcat/public_wars/a /home/saas/tomcat/webapps");

        Map<String, Object> kwargs = new LinkedHashMap<>();

        ResultInfoSet resultInfos = service.cmdRun(new Glob(), args1, kwargs);
        List<ResultInfo> infoList = resultInfos.getInfoList();
        for (ResultInfo info : infoList) {
            System.out.println(info.getResults());
            System.out.println(info.getMinions());
            System.out.println(info.getStartTime());
        }
    }

    @Test
    public void cpFileRemote() throws SaltStackException {
        ResultInfoSet resultInfos = service.cpFileRemote(new Glob(), "a.war", "/home/saas/webapps/a.war");
        List<ResultInfo> infoList = resultInfos.getInfoList();
        for (ResultInfo info : infoList) {
            System.out.println(info.getResults());
            System.out.println(info.getMinions());
            System.out.println(info.getStartTime());
        }
    }
}
