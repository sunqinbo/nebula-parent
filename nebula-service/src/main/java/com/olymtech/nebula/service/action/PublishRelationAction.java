package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.dao.INebulaPublishAppDao;
import com.olymtech.nebula.dao.INebulaPublishHostDao;
import com.olymtech.nebula.dao.INebulaPublishModuleDao;
import com.olymtech.nebula.entity.*;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.file.analyze.IFileAnalyzeService;
import com.olymtech.nebula.service.IAnalyzeArsenalApiService;
import com.olymtech.nebula.service.IPublishScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.olymtech.nebula.common.utils.DateUtils.getKeyDate;

/**
 * Created by liwenji on 2015/11/4.
 */
@Service
public class PublishRelationAction extends AbstractAction {

    @Autowired
    IAnalyzeArsenalApiService analyzeArsenalApiService;

    @Resource
    IFileAnalyzeService fileAnalyzeService;

    @Resource
    INebulaPublishModuleDao nebulaPublishModuleDao;

    @Resource
    INebulaPublishHostDao nebulaPublishHostDao;

    @Resource
    INebulaPublishAppDao nebulaPublishAppDaoImpl;

    @Autowired
    private IPublishScheduleService publishScheduleService;

    public PublishRelationAction() {

    }

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.ANALYZE_PROJECT, PublishActionGroup.PRE_MASTER, null, "");
        String publicWarDirPath = "/Users/taoshanchang/Desktop/test";
        List<String> appNameList = fileAnalyzeService.getFileListByDirPath(publicWarDirPath);
        String appNames = "";
        int appNameNum = appNameList.size();
        for (int i = 0; i < appNameNum - 1; i++) {
            String appname = appNameList.get(i).replace(".war", "");
            appNames += appname + ",";
        }
        String appname = appNameList.get(appNameNum - 1).replace(".war", "");
        appNames += appname;
        try {
            List<NebulaPublishModule> modules = new ArrayList<>();
            List<ProductTree> productTrees = analyzeArsenalApiService.getSimpleHostListByProductAndModule(event.getPublishProductName(), appNames);
            for (ProductTree productTree : productTrees) {
                NebulaPublishModule nebulaPublishModule = new NebulaPublishModule();
                nebulaPublishModule.setId(productTree.getId());
                nebulaPublishModule.setPublishEventId(event.getId());
                nebulaPublishModule.setModuleSrcSvn(productTree.getSrcSvn());
                nebulaPublishModule.setPublishModuleName(productTree.getNodeName());
                nebulaPublishModule.setPublishProductName(event.getPublishProductName());
                Date now = event.getSubmitDatetime();
                String date = getKeyDate(now);
                String key = event.getPublishEnv() + "." + event.getPublishProductName() + "." + nebulaPublishModule.getPublishModuleName() + "." + date;
                nebulaPublishModule.setPublishModuleKey(key);
                nebulaPublishModuleDao.insert(nebulaPublishModule);
                List<NebulaPublishHost> hosts = new ArrayList<>();
                for (SimpleHost simpleHost : productTree.getHosts()) {
                    NebulaPublishHost nebulaPublishHost = new NebulaPublishHost();
                    nebulaPublishHost.setPassPublishHostName(simpleHost.getHostName());
                    nebulaPublishHost.setPassPublishHostIp(simpleHost.getHostIp());
                    nebulaPublishHostDao.insert(nebulaPublishHost);
                    hosts.add(nebulaPublishHost);
                }
                List<NebulaPublishApp> apps = new ArrayList<>();
                int n = productTree.getApps().size();
                for (int i = 0; i < n; i++) {
                    NebulaPublishApp nebulaPublishApp = new NebulaPublishApp();
                    nebulaPublishApp.setPublishAppName(productTree.getApps().get(i));
                    nebulaPublishApp.setPublishEventId(event.getId());
                    nebulaPublishApp.setPublishModuleId(nebulaPublishModule.getId());
                    nebulaPublishAppDaoImpl.insert(nebulaPublishApp);
                    apps.add(nebulaPublishApp);
                }
                nebulaPublishModule.setPublishHosts(hosts);
                nebulaPublishModule.setPublishApps(apps);
                modules.add(nebulaPublishModule);
            }
            event.setPublishModules(modules);
            publishScheduleService.logScheduleByAction(event.getId(), PublishAction.ANALYZE_PROJECT, PublishActionGroup.PRE_MASTER, true, "");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.ANALYZE_PROJECT, PublishActionGroup.PRE_MASTER, false, "error message");
        return false;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }
}
