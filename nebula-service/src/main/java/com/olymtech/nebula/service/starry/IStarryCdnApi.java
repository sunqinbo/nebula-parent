/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.starry;

import com.aliyuncs.cdn.model.v20141111.DescribeRefreshTasksResponse;
import com.aliyuncs.cdn.model.v20141111.RefreshObjectCachesResponse;

/**
 * Created by Gavin on 2016-01-06 18:01.
 */
public interface IStarryCdnApi {
    DescribeRefreshTasksResponse describeRefreshTasks(String aliyunAcount, String regionId);

    RefreshObjectCachesResponse refreshObjectCaches(String aliyunAcount, String regionId, String objectPath, String objectType);
}
