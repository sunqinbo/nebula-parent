/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.ElkSearchData;

/**
 * Created by Gavin on 2016-02-02 12:37.
 */
public interface IElkLogService {
    PageInfo search(ElkSearchData elkSearchData);

    Integer count(ElkSearchData elkSearchData);
}
