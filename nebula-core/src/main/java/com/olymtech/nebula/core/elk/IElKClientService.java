/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.core.elk;

import com.olymtech.nebula.entity.ElkSearchData;
import org.elasticsearch.action.search.SearchResponse;

/**
 * Created by Gavin on 2016-02-14 14:48.
 */
public interface IElKClientService {

    SearchResponse search(ElkSearchData elkSearchData,String publishEnv);

    Long count(ElkSearchData elkSearchData,String publishEnv);
}
