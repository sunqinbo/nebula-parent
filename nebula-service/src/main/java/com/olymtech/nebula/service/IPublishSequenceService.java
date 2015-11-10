package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.NebulaPublishSequence;

/**
 * Created by liwenji on 2015/11/9.
 */
public interface IPublishSequenceService {
    public NebulaPublishSequence selectByActionName(String actionname);
}
