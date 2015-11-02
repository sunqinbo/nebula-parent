package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.ProductTree;

import java.util.List;

/**
 * Created by liwenji on 2015/11/2.
 */
public interface IAnalyzeArsenalApi {
    public List<ProductTree> getProductTreeListByPid(Integer pid);
}
