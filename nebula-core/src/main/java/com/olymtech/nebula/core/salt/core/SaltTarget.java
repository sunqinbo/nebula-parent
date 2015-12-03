/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.salt.core;

import com.suse.saltstack.netapi.datatypes.target.Target;

import java.util.List;

/**
 * @author taoshanchang 15/11/12
 */
public class SaltTarget implements Target<String> {

    private final List<String> targets;

    public SaltTarget(List<String> targets) {
        this.targets = targets;
    }

    @Override
    public String getTarget() {

        StringBuffer buffer = new StringBuffer();

        int falg = 0;
        for (String str : targets) {
            if (falg == 0) {
                buffer.append(str);
                falg = 1;
            } else {
                buffer.append("," + str);
            }
        }

        return buffer.toString();
    }

    @Override
    public String getType() {
        return "list";
    }

    public List<String> getTargets() {
        return targets;
    }
}
