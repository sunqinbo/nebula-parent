/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.salt.test;

import com.olymtech.nebula.salt.SaltStackServiceImpl;
import com.olymtech.nebula.salt.core.SaltClientFactory;
import com.suse.saltstack.netapi.client.SaltStackClient;
import com.suse.saltstack.netapi.datatypes.target.Glob;
import com.suse.saltstack.netapi.exception.SaltStackException;
import com.suse.saltstack.netapi.results.ResultInfo;
import com.suse.saltstack.netapi.results.ResultInfoSet;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author taoshanchang 15/10/30
 */
public class ServiceTest {
    private SaltStackServiceImpl service;

    @Before
    public void init() throws Exception {
        service = new SaltStackServiceImpl();
    }

    @Test
    public void getClientTest() throws Exception {
        SaltStackClient saltClient = SaltClientFactory.getSaltClient();

        Map<String, Map<String, Object>> minions = saltClient.getMinions();

        for(Map.Entry<String, Map<String, Object>> entry:minions.entrySet()){
            System.out.println(entry.getKey()+"--->"+entry.getValue());

            for(Map.Entry<String, Object> entry2:entry.getValue().entrySet()){
                System.out.println(entry2.getKey()+"--->"+entry2.getValue());
            }
        }
    }

    @Test
    public void cpFileTest() throws SaltStackException {
        ResultInfoSet resultInfos = service.cpFile(new Glob(), "backup/111", "/root/111");
        List<ResultInfo> infoList = resultInfos.getInfoList();
        for (ResultInfo info : infoList) {

            System.out.println(info.getResults());
            System.out.println(info.getMinions());
            System.out.println(info.getStartTime());

        }

    }

    @Test
    public void cpDirTest() throws SaltStackException {
        service = new SaltStackServiceImpl();
        ResultInfoSet resultInfos = service.cpDir(new Glob(), "backup/dir", "/root/dir");
        List<ResultInfo> infoList = resultInfos.getInfoList();
        for (ResultInfo info : infoList) {
            System.out.println(info.getResults());
            System.out.println(info.getMinions());
            System.out.println(info.getStartTime());
        }
    }

    @Test
    public void cmdTest() throws SaltStackException {

        SaltStackServiceImpl service = new SaltStackServiceImpl();

        List<Object> args1 = new ArrayList<>();
        args1.add("ls");

        Map<String, Object> kwargs = new LinkedHashMap<>();

        ResultInfoSet resultInfos = service.cmdRun(new Glob(), args1, kwargs);
        List<ResultInfo> infoList = resultInfos.getInfoList();
        for (ResultInfo info : infoList) {
            System.out.println(info.getResults());
            System.out.println(info.getMinions());
            System.out.println(info.getStartTime());
        }
    }

}
