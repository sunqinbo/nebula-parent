package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.NebulaPublishSequence;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;

import java.util.List;

/**
 * Created by liwenji on 2015/11/9.
 */
public interface IPublishSequenceService {
    public NebulaPublishSequence selectByActionName(PublishAction actionName);

    public List<NebulaPublishSequence> selectByActionGroup(PublishActionGroup publishActionGroup);
}
