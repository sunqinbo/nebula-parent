/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.salt.test;

import com.olymtech.nebula.salt.core.SaltClientFactory;
import com.suse.saltstack.netapi.client.SaltStackClient;

import java.util.Map;

/**
 * @author taoshanchang 15/10/30
 */
public class Test {
    public static void main(String[] args) throws Exception {
        SaltStackClient saltClient = SaltClientFactory.getSaltClient();

        Map<String, Map<String, Object>> minions = saltClient.getMinions();

        for(Map.Entry<String, Map<String, Object>> entry:minions.entrySet()){
            System.out.println(entry.getKey()+"--->"+entry.getValue());

            for(Map.Entry<String, Object> entry2:entry.getValue().entrySet()){
                System.out.println(entry2.getKey()+"--->"+entry2.getValue());
            }
        }

        saltClient.getMinions();

    }
}
