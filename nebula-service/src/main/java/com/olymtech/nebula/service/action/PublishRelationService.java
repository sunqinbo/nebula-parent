package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.dao.INebulaPublishHostDao;
import com.olymtech.nebula.dao.INebulaPublishModuleDao;
import com.olymtech.nebula.entity.*;
import com.olymtech.nebula.file.analyze.IFileAnalyzeService;
import com.olymtech.nebula.service.IAnalyzeArsenalApiService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.olymtech.nebula.common.utils.DateUtils.getKeyDate;

/**
 * Created by liwenji on 2015/11/4.
 */
public class PublishRelationService extends AbstractAction {

    @Resource
    IAnalyzeArsenalApiService analyzeArsenalApiService;

    @Resource
    IFileAnalyzeService fileAnalyzeService;

    @Resource
    INebulaPublishModuleDao nebulaPublishModuleDao;

    @Resource
    INebulaPublishHostDao nebulaPublishHostDao;

    public PublishRelationService(String actionName) {
        super();
    }

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        String publicWarDirPath="F:\\olym\\test";
        List<String> appNameList=fileAnalyzeService.getFileListByDirPath(publicWarDirPath);
        String appNames="";
        int appNameNum=appNameList.size();
        for (int i=0;i<appNameNum-1;i++)
        {
            appNameList.get(i).replace(".war","");
            appNames+=appNameList.get(i)+",";
        }
        appNameList.get(appNameNum-1).replace(".war","");
        appNames+=appNameList.get(appNameNum-1);
        try {
            List<ProductTree> productTrees = analyzeArsenalApiService.getSimpleHostListByProductAndModule(event.getPublishProductName(), appNames);
            for (ProductTree productTree : productTrees) {
                NebulaPublishModule nebulaPublishModule = new NebulaPublishModule();
                nebulaPublishModule.setId(productTree.getId());
                nebulaPublishModule.setModuleSrcSvn(productTree.getSrcSvn());
                nebulaPublishModule.setPublishModuleName(productTree.getNodeName());
                nebulaPublishModule.setPublishProductName(event.getPublishProductName());
                Date now = event.getSubmitDatetime();
                String date = getKeyDate(now);
                String key = event.getPublishEnv() + "." + event.getPublishProductName() + "." + nebulaPublishModule.getPublishModuleName() + "." + date;
                nebulaPublishModule.setPublishModuleKey(key);
                nebulaPublishModuleDao.insert(nebulaPublishModule);
                for (SimpleHost simpleHost : productTree.getHosts()) {
                    NebulaPublishHost nebulaPublishHost = new NebulaPublishHost();
                    nebulaPublishHost.setPassPublishHostName(simpleHost.getHostName());
                    nebulaPublishHost.setPassPublishHostIp(simpleHost.getHostIp());
                    nebulaPublishHostDao.insert(nebulaPublishHost);
                }
                int n = productTree.getApps().size();
                for (int i = 0; i < n; i++) {
                    NebulaPublishApp nebulaPublishApp = new NebulaPublishApp();
                    nebulaPublishApp.setPublishAppName(productTree.getApps().get(i));
                    nebulaPublishApp.setPublishEventId(event.getId());
                    nebulaPublishApp.setPublishModuleId(nebulaPublishModule.getId());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }
}
