package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.ProductTree;
import org.springframework.stereotype.Service;

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

    /**
     * 根据产品名，应用名获取树节点
     * @param productName 产品名
     * @param appNames 发布的应用名列表
     * @return
     */
    public List<ProductTree> getSimpleHostListByProductAndModule(String productName,String appNames,String publishEnv);
}
