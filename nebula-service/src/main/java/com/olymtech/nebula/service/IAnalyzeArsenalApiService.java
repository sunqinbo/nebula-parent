package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.ProductTree;

import java.util.List;

/**
 * Created by liwenji on 2015/11/2.
 */
public interface IAnalyzeArsenalApiService {
    /**
     *根据父id获取产品树结点
     * @param pid 父id
     * @return
     */
    public List<ProductTree> getProductTreeListByPid(Integer pid);
}
