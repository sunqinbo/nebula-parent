package com.olymtech.nebula.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.olymtech.nebula.common.utils.HttpUtils;
import com.olymtech.nebula.entity.ProductTree;
import com.olymtech.nebula.entity.SimpleHost;
import com.olymtech.nebula.service.IAnalyzeArsenalApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwenji on 2015/11/2.
 */
@Service
public class AnalyzeArsenalApiServiceImpl implements IAnalyzeArsenalApiService {

    private Logger logger       = LoggerFactory.getLogger(this.getClass());
    private String serverHost = "localhost:8080";
    private String DESCRIBE_INSTANCES_API = "http://"+serverHost;
    @Override
    public  List<ProductTree> getProductTreeListByPid(Integer pid) {
        List<ProductTree> productTrees=new ArrayList<>();
        try {
            String url = DESCRIBE_INSTANCES_API+"/productpid/"+pid;
            String jsonDataString = HttpUtils.getResponesEncodeUTF8ByURL(url);
            JSONObject jsonObject = JSONObject.parseObject(jsonDataString);
            if (null == jsonObject) {
                return null;
            }
            if(!jsonObject.get("callbackMsg").toString().equals("Success")){
                return null;
            }
            JSONArray jsonObjectData = jsonObject.getJSONArray("responseContext");
            if(jsonObjectData == null){
                return null;
            }
            productTrees=instancesJsonArrayToProductTreeList(jsonObjectData);
            return productTrees;
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ProductTree> getSimpleHostListByProductAndModule(String productName, String appNames) {
        List<ProductTree> productTrees=new ArrayList<>();
        try {
            String url = DESCRIBE_INSTANCES_API+"/productName/"+productName+"/appNames/"+appNames;
            String jsonDataString = HttpUtils.getResponesEncodeUTF8ByURL(url);
            JSONObject jsonObject = JSONObject.parseObject(jsonDataString);
            if (null == jsonObject) {
                return null;
            }
            if(!jsonObject.get("callbackMsg").toString().equals("Success")){
                return null;
            }
            JSONArray jsonObjectData = jsonObject.getJSONArray("responseContext");
            if(jsonObjectData == null){
                return null;
            }
            productTrees=instancesJsonArrayToModularTreeList(jsonObjectData);
            return productTrees;
        }catch (Exception e) {
            return null;
        }
    }


    public List<ProductTree> instancesJsonArrayToProductTreeList(JSONArray JsonArray){
        List<ProductTree> productTrees=new ArrayList<>();
        try {
            int n=JsonArray.size();
            for (int i=0;i<n;i++)
            {
                JSONObject instanceJsonObject = (JSONObject) JsonArray.get(i);
                ProductTree productTree=new ProductTree();
                productTree.setId(Integer.parseInt(instanceJsonObject.getString("id")));
                productTree.setNodeCname(instanceJsonObject.getString("nodeCname"));
                productTree.setNodeName(instanceJsonObject.getString("nodeName"));
                productTree.setSrcSvn(instanceJsonObject.getString("srcSvn"));
                productTree.setTreeLevel(Integer.parseInt(instanceJsonObject.getString("treeLevel")));
                productTree.setPid(Integer.parseInt(instanceJsonObject.getString("pid")));
                productTrees.add(productTree);
            }
        }catch (Exception e){
            logger.error("instancesJsonArrayToProductTreeList:",e);
        }
        return productTrees;
    }

    private List<ProductTree> instancesJsonArrayToModularTreeList(JSONArray JsonArray) {
        List<ProductTree> productTrees=new ArrayList<>();
        try {
            int n=JsonArray.size();
            for (int i=0;i<n;i++)
            {
                JSONObject instanceJsonObject = (JSONObject) JsonArray.get(i);
                ProductTree productTree=new ProductTree();
                productTree.setId(Integer.parseInt(instanceJsonObject.getString("id")));
                productTree.setNodeCname(instanceJsonObject.getString("nodeCname"));
                productTree.setNodeName(instanceJsonObject.getString("nodeName"));
                productTree.setSrcSvn(instanceJsonObject.getString("srcSvn"));
                productTree.setTreeLevel(Integer.parseInt(instanceJsonObject.getString("treeLevel")));
                productTree.setPid(Integer.parseInt(instanceJsonObject.getString("pid")));
                JSONArray apps=instanceJsonObject.getJSONArray("apps");
                List<String> appNames=new ArrayList<>();
                for (int j = 0; j <apps.size() ; j++) {
                    JSONObject jsonObject = (JSONObject) apps.get(j);
                    appNames.add(jsonObject.getString("appName"));
                }
                productTree.setApps(appNames);
                JSONArray jsonHosts=instanceJsonObject.getJSONArray("hosts");
                List<SimpleHost> hosts=new ArrayList<>();
                for (int j = 0; j <jsonHosts.size() ; j++) {
                    JSONObject jsonObject = (JSONObject) jsonHosts.get(j);
                    SimpleHost simpleHost=new SimpleHost();
                    simpleHost.setHostName(jsonObject.getString("hostName"));
                    simpleHost.setHostIp(jsonObject.getString("innerIpAddress"));
                    hosts.add(simpleHost);
                }
                productTree.setHosts(hosts);
                productTrees.add(productTree);
            }
        }catch (Exception e){
            logger.error("instancesJsonArrayToModularTreeList:",e);
        }
        return productTrees;
    }

}
