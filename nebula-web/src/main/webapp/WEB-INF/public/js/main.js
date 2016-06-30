/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
var nebula = {};
//var BASE_SITE_URL = "http://127.0.0.1:8080";


nebula.main = function(){
    $(document).ready(function(){

    });
};

nebula.common={};

nebula.common.timestapToDate=function(timestap){
    var date = new Date(timestap);
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = date.getDate() + ' ';
    var h = date.getHours() + ':';
    var m = date.getMinutes() + ':';
    var s = date.getSeconds();
    return Y+M+D+h+m+s;
};

nebula.common.transform = {};

nebula.common.transform.publishEnv = function(publishEnv){
    var array = {
        "test1":"测试1",
        "test2":"测试2",
        "test3":"测试3",
        "stage":"准生产",
        "product":"生产",
        "":"无"
    };
    return array[publishEnv];
};

nebula.common.transform.isSuccessPublish = function(isSuccessPublish){
    var result = "";
    if(isSuccessPublish == null){
        return result;
    }
    isSuccessPublish?(result="成功"):(result="失败");
    return result;
};

nebula.common.transform.publishStatus = function(publishStatus){
    var array = {
        "PENDING_APPROVE":"待审批",
        "PENDING_PRE":"待准备",
        "PENDING_PUBLISH":"待发布",
        "PUBLISHING":"发布中",
        "PUBLISHED":"已发布",
        "ROLLBACK":"已回滚",
        "CANCEL":"已取消",
        "":"无"
    };
    return array[publishStatus];
};

nebula.common.transform.publishAction = function(publishAction){
    var array = {
        "CREATE_PUBLISH_EVENT":"创建发布事件",
        "GET_PUBLISH_SVN":"获取发布WAR包",
        "ANALYZE_PROJECT":"分析发布工程",
        "GET_SRC_SVN":"获取源SVN文件",
        "UPDATE_ETC":"更新配置",
        "ETC_APPROVE":"配置审核",
        "CREATE_PUBLISH_DIR":"创建发布目录",
        "COPY_PUBLISH_OLD_ETC":"拷贝原etc文件",
        "COPY_PUBLISH_OLD_WAR":"拷贝原war文件",
        "PUBLISH_NEW_ETC":"发布新etc文件",
        "PUBLISH_NEW_WAR":"发布新war文件",
        "START_TOMCAT":"启动Tomcat",
        "CHANGE_LN":"更改文件指向",
        "STOP_TOMCAT":"停止Tomcat",
        "CHECK_HEALTH":"健康检查",
        "CLEAN_HISTORY_DIR":"清除历史发布目录",
        "UPDATE_SRC_SVN":"更新到源SVN",
        "CLEAN_FAIL_DIR":"清除失败发布目录",
        "CLEAN_PUBLISH_DIR":"清除临时发布目录",
        "":"无"
    };
    return array[publishAction];
};

nebula.common.transform.hostPublishStatus = function(hostPublishStatus){
    var array = {
        "PENDING_PUBLISH":"<span class='label label-default'>待上线</span>",
        "PUBLISHING":"<span class='label label-warning'>上线中</span>",
        "PUBLISHED":"<span class='label label-success'>已上线</span>",
        "FAILED":"<span class='label label-danger'>失败</span>",
        "":"无"
    };
    return array[hostPublishStatus];
};

nebula.common.transform.batchTag = function(batchTag){
    var array = {
        "1":"<span class='label label-info'>a</span>",
        "2":"<span class='label label-info'>b</span>",
        "3":"<span class='label label-info'>c</span>",
        "4":"<span class='label label-info'>d</span>",
        "5":"<span class='label label-info'>e</span>",
        "6":"<span class='label label-info'>f</span>",
        "7":"<span class='label label-info'>g</span>",
        "8":"<span class='label label-info'>h</span>",
        "9":"<span class='label label-info'>i</span>",
        "10":"<span class='label label-info'>j</span>",
        "11":"<span class='label label-info'>k</span>",
        "12":"<span class='label label-info'>l</span>",
        "13":"<span class='label label-info'>m</span>",
        "14":"<span class='label label-info'>n</span>",
        "15":"<span class='label label-info'>o</span>",
    };
    return array[batchTag];
};

nebula.common.alert = {};

nebula.common.alert.info= function (msg,second) {
    $.notify({
        icon: '',
        message: msg
    },{
        type: 'info',
        timer: second
    });
};

nebula.common.alert.danger= function (msg,second) {
    $.notify({
        icon: '',
        message: msg
    },{
        type: 'danger',
        timer: second
    });
};

nebula.common.alert.success= function (msg,second) {
    $.notify({
        icon: '',
        message: msg
    },{
        type: 'success',
        timer: second
    });
};

nebula.common.alert.warning= function (msg,second) {
    $.notify({
        icon: '',
        message: msg
    },{
        type: 'warning',
        timer: second
    });
};

nebula.publish = {};

nebula.publish.event = {};

nebula.publish.event.main = function(){
    $(document).ready(function(){
        $("#navbar-header-name").html("发布申请");
        $("#eventTipModal").appendTo("body");

        $("#select-bu").change(function(){
            var pid = $("#select-bu").val();
            $("#select-product").html("<option value=''>请选择</option>");
            $.ajax({
                url:"/publish/productTreeList/pid",
                type:"post",
                data:{"pid":pid},
                success:function(jsonData){
                    if(jsonData.callbackMsg.match(/Success/)){
                        var productTrees = jsonData.responseContext;
                        for(var i=0;i<productTrees.length;i++){
                            var productTree = productTrees[i];
                            $("#select-product").append("<option value-publishSvn='"+productTree.publishSvn+"' value-svn='"+productTree.srcSvn+"' value-hidden='"+productTree.nodeName+"' value='"+productTree.id+"'>"+productTree.nodeCname+"</option>");
                        }
                    }
                }
            });
        });

        $("#select-product").change(function(){
            var publishSvn= $("#select-product").find("option:selected").attr("value-publishSvn");
            if(publishSvn=="null"||publishSvn==""){
                $("#publish-svn-tip").text("请先配置基础信息库发布SVN地址");
                $("#publish-svn").val("");
            }
            else {
                $("#publish-svn-tip").text("");
                $("#publish-svn").val(publishSvn);
            }
        });

        $("#submitConfirm_btn").click(function(){
            nebula.publish.event.submitPublishEvent();
        });
    });
};
//事件确认
nebula.publish.event.createPublishEvent = function(){
    if($("#publish-svn-tip").text()!=""){
        return;
    }
    var publishSubject = $("#publish-subject").val();
    //var publishBuName = $("#select-bu").find("option:selected").attr("value-hidden");
    var publishBuCname = $("#select-bu").find("option:selected").text();
    //var publishProductName = $("#select-product").find("option:selected").attr("value-hidden");
    var publishProductCname = $("#select-product").find("option:selected").text();
    //var productSrcSvn = $("#select-product").find("option:selected").attr("value-svn");
    var publishEnv = $("#select-publich-env").val();
    var publishSvn = $("#publich-svn").val().trim();
    var publishRemark=$("#publishRemark").val();
    if(publishSvn==""){
        nebula.common.alert.danger("SVN地址不能为空",1000);
        return false;
    }
    if(isNaN(publishSvn)){
        nebula.common.alert.danger("SVN地址必须为数字",1000);
        return false;
    }
    publishSvn=$("#select-product").find("option:selected").attr("value-publishSvn")+publishSvn;
    if(publishSubject==""||publishBuCname=="请选择"||publishProductCname=="请选择"||publishEnv==""||publishSvn==""||publishRemark==""){
        nebula.common.alert.danger("请确认选择的所有字段",1000);
        return false;
    }
    $("#publishSubject_lb").text(publishSubject)
    $("#publishBuCname_lb").text(publishBuCname)
    $("#publishProductCname_lb").text(publishProductCname)
    $("#publishEnv_lb").text($("#select-publich-env").find("option:selected").text())
    $("#publishSvn_lb").text(publishSvn)
    $("#publishRemark_lb").text($("#publishRemark").val());
    $('#eventTipModal').modal('show');
};
//事件提交
nebula.publish.event.submitPublishEvent = function(){
    var publishSubject = $("#publish-subject").val();
    var publishBuName = $("#select-bu").find("option:selected").attr("value-hidden");
    var publishBuCname = $("#select-bu").find("option:selected").text();
    var publishProductName = $("#select-product").find("option:selected").attr("value-hidden");
    var publishProductCname = $("#select-product").find("option:selected").text();
    var productSrcSvn = $("#select-product").find("option:selected").attr("value-svn");
    var publishEnv = $("#select-publich-env").val();
    var publishSvn = $("#select-product").find("option:selected").attr("value-publishSvn")+$("#publich-svn").val();
    var publishRemark=$("#publishRemark").val()
    $.ajax({
        url:"/publish/add",
        type:"post",
        async: false,
        data:(function(){
            var data={};
            data["publishSubject"]=publishSubject;
            data["publishBuName"]=publishBuName;
            data["publishBuCname"]=publishBuCname;
            data["publishProductName"]=publishProductName;
            data["publishProductCname"]=publishProductCname;
            data["publishEnv"]=publishEnv;
            data["publishSvn"]=publishSvn;
            data["productSrcSvn"]=productSrcSvn;
            data["publishRemark"]=publishRemark;
            return data;
        })(),
        success:function(jsonData){
            if(jsonData.callbackMsg.match(/Success/)){
                window.location.href="/publish/process.htm?id="+jsonData.responseContext;
            }else{
                nebula.common.alert.danger(jsonData.responseContext,1000);
            }
        }
    });
}

nebula.publish.process={};

nebula.publish.process.main= function () {
    $(document).ready(function(){

    });
};

nebula.publish.process.preMasterPublish = function(){
    var id = $("#eventId").val();
    $.ajax({
        url:"/publish/preMasterPublish",
        type:"post",
        data:{"id":id},
        success:function(jsonData){
            if(!jsonData.callbackMsg){
                jsonData=JSON.parse(jsonData);
            }
            if(jsonData.callbackMsg=="Error") {
                nebula.common.alert.danger(jsonData.responseContext, 1000);
                return;
            }
            if(jsonData.callbackMsg.match(/Success/)){
                nebula.common.alert.info("准备发布执行完成",2000);
            }
        }
    });
};

nebula.publish.process.preMinionPublish = function(){
    var id = $("#eventId").val();
    $.ajax({
        url:"/publish/preMinionPublish",
        type:"post",
        data:{"id":id},
        success:function(jsonData){
            if(!jsonData.callbackMsg){
                jsonData=JSON.parse(jsonData);
            }
            if(jsonData.callbackMsg=="Error") {
                nebula.common.alert.danger(jsonData.responseContext, 1000);
                return;
            }
            if(jsonData.callbackMsg.match(/Success/)){
                nebula.common.alert.info("预发布执行完成",2000);
            }
        }
    });
};


nebula.publish.process.publishReal = function(){
    var id = $("#eventId").val();
    $.ajax({
        url:"/publish/publishReal",
        type:"post",
        data:{"id":id,"totpCode": $("#code_momdal").val()},
        success:function(jsonData){
            if(!jsonData.callbackMsg){
                jsonData=JSON.parse(jsonData);
            }
            if(jsonData.callbackMsg=="Error") {
                nebula.common.alert.danger(jsonData.responseContext, 1000);
                return;
            }
            if(jsonData.callbackMsg.match(/Success/)){
                nebula.common.alert.info("正式发布执行完成",2000);
            }
        }
    });
};

nebula.publish.process.publishContinue = function(){
    var id = $("#eventId").val();
    $("#false_btn").hide();
    $.ajax({
        url:"/publish/publishContinue",
        type:"post",
        data:{"id":id},
        success:function(jsonData){
            if(!jsonData.callbackMsg){
                jsonData=JSON.parse(jsonData);
            }
            if(jsonData.callbackMsg=="Error") {
                nebula.common.alert.danger(jsonData.responseContext, 1000);
                return;
            }
            if(jsonData.callbackMsg.match(/Success/)){
                nebula.common.alert.info("继续发布执行完成",2000);
            }
        }
    });
};

nebula.publish.process.publishSuccessEnd = function(){
    var id = $("#eventId").val();
    $.ajax({
        url:"/publish/publishSuccessEnd",
        type:"post",
        data:{"id":id},
        success:function(jsonData){
            if(!jsonData.callbackMsg){
                jsonData=JSON.parse(jsonData);
            }
            if(jsonData.callbackMsg=="Error") {
                nebula.common.alert.danger(jsonData.responseContext, 1000);
                return;
            }
            if(jsonData.callbackMsg.match(/Success/)){
                $("#restartPublish").hide();
                nebula.common.alert.info("确认发布执行完成",2000);
            }
        }
    });
};

nebula.publish.process.publishFailEnd = function(){
    var id = $("#eventId").val();
    $.ajax({
        url:"/publish/publishFailEnd",
        type:"post",
        data:{"id":id},
        success:function(jsonData){
            if(!jsonData.callbackMsg){
                jsonData=JSON.parse(jsonData);
            }
            if(jsonData.callbackMsg=="Error") {
                nebula.common.alert.danger(jsonData.responseContext, 1000);
                return;
            }
            if(jsonData.callbackMsg.match(/Success/)){
                $("#restartPublish").hide();
                nebula.common.alert.info("回滚执行完成",2000);
            }
        }
    });
};

nebula.publish.process.retryPublishRollback = function(){
    var id = $("#eventId").val();
    $.ajax({
        url:"/publish/retryPublishRollback",
        type:"post",
        data:{"id":id},
        success:function(jsonData){
            if(!jsonData.callbackMsg){
                jsonData=JSON.parse(jsonData);
            }
            if(jsonData.callbackMsg.match(/Success/)){
                $("#restartPublish").hide();
                window.location.href="/publish/process.htm?id="+id;
                nebula.common.alert.info("正式发布执行完成",2000);
            }
            if(jsonData.callbackMsg=="Error") {
                nebula.common.alert.danger(jsonData.responseContext, 1000);
                return;
            }
        }
    });
};

nebula.publish.process.restartTomcat = function(){
    var id = $("#eventId").val();
    $.ajax({
        url:"/publish/restartTomcat",
        type:"post",
        data:{"id":id},
        success:function(jsonData){
            if(!jsonData.callbackMsg){
                jsonData=JSON.parse(jsonData);
            }
            if(jsonData.callbackMsg.match(/Success/)){
                $("#restartPublish").hide();
                nebula.common.alert.info("重启tomcat执行完成",2000);
            }
            if(jsonData.callbackMsg=="Error") {
                nebula.common.alert.danger(jsonData.responseContext, 1000);
                return;
            }
        }
    });
};

nebula.publish.process.cancelPublish = function () {
    var id = $("#eventId").val();
    $.ajax({
        url:"/publish/cancelPublish",
        type:"post",
        data:{"id":id},
        success:function(jsonData){
            if(!jsonData.callbackMsg){
                jsonData=JSON.parse(jsonData);
            }
            if(jsonData.callbackMsg.match(/Success/)){
                $("#restartPublish").hide();
                nebula.common.alert.info("取消发布执行完成",2000);
            }
            if(jsonData.callbackMsg=="Error") {
                nebula.common.alert.danger(jsonData.responseContext, 1000);
                return;
            }
        }
    });
};

nebula.tools = {};

nebula.tools.qrCode = {};

nebula.tools.qrCode.generateQRCodesByOtpauthUrl= function (otpauthUrl,divId) {
    var qrcodeDiv = new QRCode( divId);
    if (!otpauthUrl) {
        console.log("otpauthUrl is null");
        otpauthUrl.focus();
        return;
    }
    qrcodeDiv.makeCode(otpauthUrl);
    //qrcodeDiv.makeCode(otpauthUrl.value);
};

nebula.tools.qrCode.generateQRCodesGetUrl = function(label,bu,key) {
    return "otpauth://totp/" + label + ":" + bu + "?secret=" + key + "&issuer=" + label;
};


