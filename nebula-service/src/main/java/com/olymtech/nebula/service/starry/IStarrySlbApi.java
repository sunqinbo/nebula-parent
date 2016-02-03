/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.starry;

import com.aliyuncs.slb.model.v20140515.DescribeHealthStatusResponse;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerAttributeResponse;
import com.olymtech.nebula.entity.NebulaPublishSlb;

/**
 * Created by Gavin on 2016-02-03 13:03.
 */
public interface IStarrySlbApi {
    DescribeLoadBalancerAttributeResponse describeLoadBalancerAttribute(NebulaPublishSlb publishSlb);

    DescribeHealthStatusResponse describeHealthStatusTasks(NebulaPublishSlb publishSlb);
}
