package com.olymtech.nebula.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.olymtech.nebula.common.utils.HttpUtils;
import com.olymtech.nebula.entity.ProductTree;
import com.olymtech.nebula.entity.SimpleHost;
import com.olymtech.nebula.entity.SimpleSlb;
import com.olymtech.nebula.service.IAnalyzeArsenalApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liwenji on 2015/11/2.
 */
@Service
public class AnalyzeArsenalApiServiceImpl implements IAnalyzeArsenalApiService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${arsenal.api.url}")
    private String ARSENAL_SERVER;
//    private String ARSENAL_SERVER="http://localhost:8090/arsenal-web";

    @Override
    public List<ProductTree> getProductTreeListByPid(Integer pid) {
        List<ProductTree> productTrees = new ArrayList<>();
        try {
            String url = ARSENAL_SERVER + "/arsenal-api/productpid/" + pid;
            String jsonDataString = HttpUtils.getResponesEncodeUTF8ByURL(url);
            JSONObject jsonObject = JSONObject.parseObject(jsonDataString);
            if (null == jsonObject) {
                return null;
            }
            if (!jsonObject.get("callbackMsg").toString().equals("Success")) {
                return null;
            }
            JSONArray jsonObjectData = jsonObject.getJSONArray("responseContext");
            if (jsonObjectData == null) {
                return null;
            }
            productTrees = instancesJsonArrayToProductTreeList(jsonObjectData);
            return productTrees;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * @param productName 产品名
     * @param appNames    发布的应用名列表
     * @param publishEnv
     * @return 会返回三种情况
     * 1.捕捉异常返回为空
     * 2.返回正常（没有Msg,有result）
     * 3.没有返回结果，有msg
     */
    @Override
    public Map<String, Object> getSimpleHostListByProductAndModule(String productName, String appNames, String publishEnv) {
        List<ProductTree> productTrees = new ArrayList<>();
        Map<String, Object> objectMap = new HashMap<>();
        try {
            String url = ARSENAL_SERVER + "/arsenal-api/productName/" + productName + "/appNames/" + appNames + "/publishEnv/" + publishEnv;
            String jsonDataString = HttpUtils.getResponesEncodeUTF8ByURL(url);
            JSONObject jsonObject = JSONObject.parseObject(jsonDataString);
            if (null == jsonObject) {
                return null;
            }
            if (jsonObject.get("callbackMsg").toString().equals("Success_No_App")) {
                objectMap.put("msg", jsonObject.get("responseContext").toString());
                objectMap.put("result", productTrees);
                return objectMap;
            }
            if (!jsonObject.get("callbackMsg").toString().equals("Success")) {
                return null;
            }
            JSONArray jsonObjectData = jsonObject.getJSONArray("responseContext");
            if (jsonObjectData == null) {
                return null;
            }
            productTrees = instancesJsonArrayToModularTreeList(jsonObjectData);
            objectMap.put("msg", "");
            objectMap.put("result", productTrees);
            return objectMap;
        } catch (Exception e) {
            return null;
        }
    }


    public List<ProductTree> instancesJsonArrayToProductTreeList(JSONArray JsonArray) {
        List<ProductTree> productTrees = new ArrayList<>();
        try {
            int n = JsonArray.size();
            for (int i = 0; i < n; i++) {
                JSONObject instanceJsonObject = (JSONObject) JsonArray.get(i);
                ProductTree productTree = new ProductTree();
                productTree.setId(Integer.parseInt(instanceJsonObject.getString("id")));
                productTree.setNodeCname(instanceJsonObject.getString("nodeCname"));
                productTree.setNodeName(instanceJsonObject.getString("nodeName"));
                productTree.setSrcSvn(instanceJsonObject.getString("srcSvn"));
                productTree.setTreeLevel(Integer.parseInt(instanceJsonObject.getString("treeLevel")));
                productTree.setPid(Integer.parseInt(instanceJsonObject.getString("pid")));
                productTrees.add(productTree);
            }
        } catch (Exception e) {
            logger.error("instancesJsonArrayToProductTreeList:", e);
        }
        return productTrees;
    }

    private List<ProductTree> instancesJsonArrayToModularTreeList(JSONArray JsonArray) {
        List<ProductTree> productTrees = new ArrayList<>();
        try {
            int n = JsonArray.size();
            for (int i = 0; i < n; i++) {
                JSONObject instanceJsonObject = (JSONObject) JsonArray.get(i);
                ProductTree productTree = new ProductTree();
                productTree.setId(Integer.parseInt(instanceJsonObject.getString("id")));
                productTree.setNodeCname(instanceJsonObject.getString("nodeCname"));
                productTree.setNodeName(instanceJsonObject.getString("nodeName"));
                productTree.setSrcSvn(instanceJsonObject.getString("srcSvn"));
                productTree.setTreeLevel(Integer.parseInt(instanceJsonObject.getString("treeLevel")));
                productTree.setPid(Integer.parseInt(instanceJsonObject.getString("pid")));

                JSONArray apps = instanceJsonObject.getJSONArray("apps");
                List<String> appNames = new ArrayList<>();
                for (int j = 0; j < apps.size(); j++) {
                    JSONObject jsonObject = (JSONObject) apps.get(j);
                    appNames.add(jsonObject.getString("appName"));
                }
                productTree.setApps(appNames);

                JSONArray jsonHosts = instanceJsonObject.getJSONArray("hosts");
                List<SimpleHost> hosts = new ArrayList<>();
                for (int j = 0; j < jsonHosts.size(); j++) {
                    JSONObject jsonObject = (JSONObject) jsonHosts.get(j);
                    SimpleHost simpleHost = new SimpleHost();
                    simpleHost.setHostName(jsonObject.getString("instanceName"));
                    JSONObject ipJson = jsonObject.getJSONObject("innerIpAddress");
                    simpleHost.setHostIp(ipJson.getString("ipAddress"));
                    hosts.add(simpleHost);
                }
                productTree.setHosts(hosts);

                JSONArray jsonSlbs = instanceJsonObject.getJSONArray("slbs");
                List<SimpleSlb> simpleSlbs = new ArrayList<>();
                for (int j = 0; j < jsonSlbs.size(); j++) {
                    JSONObject jsonObject = (JSONObject) jsonSlbs.get(j);
                    SimpleSlb simpleSlb = new SimpleSlb();
                    simpleSlb.setId(jsonObject.getInteger("id"));
                    simpleSlb.setLoadBalancerAddress(jsonObject.getString("address"));
                    simpleSlb.setLoadBalancerName(jsonObject.getString("loadBalancerName"));
                    simpleSlb.setLoadBalancerStatus(jsonObject.getString("loadBalancerStatus"));
                    simpleSlb.setRegionId(jsonObject.getString("regionId"));
                    simpleSlb.setAliyunAccount(jsonObject.getString("aliyunAccountMail"));
                    simpleSlbs.add(simpleSlb);
                }
                productTree.setSlbs(simpleSlbs);

                productTrees.add(productTree);
            }
        } catch (Exception e) {
            logger.error("instancesJsonArrayToModularTreeList:", e);
        }
        return productTrees;
    }

}
