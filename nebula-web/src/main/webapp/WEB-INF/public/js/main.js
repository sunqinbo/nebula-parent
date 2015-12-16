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
    var result = "";
    switch (publishEnv){
        case "test":
            result = "测试";
            break;
        case "stage":
            result = "准生产";
            break;
        case "product":
            result = "生产";
            break;
        default:
            result = "";
    }
    return result;
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
    var result = "";
    switch (publishStatus){
        case "PENDING_APPROVE":
            result = "待审批";
            break;
        case "PENDING_PRE":
            result = "待准备";
            break;
        case "PENDING_PUBLISH":
            result = "待发布";
            break;
        case "PUBLISHING":
            result = "发布中";
            break;
        case "PUBLISHED":
            result = "已发布";
            break;
        case "ROLLBACK":
            result = "已回滚";
            break;
        case "CANCEL":
            result = "已取消";
            break;
        default:
            result = "";
    }
    return result;
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
                            $("#select-product").append("<option value-svn='"+productTree.srcSvn+"' value-hidden='"+productTree.nodeName+"' value='"+productTree.id+"'>"+productTree.nodeCname+"</option>");
                        }
                    }
                }
            });
        });


    });
};

nebula.publish.event.createPublishEvent = function(){
    var publishSubject = $("#publish-subject").val();
    var publishBuName = $("#select-bu").find("option:selected").attr("value-hidden");
    var publishBuCname = $("#select-bu").find("option:selected").text();
    var publishProductName = $("#select-product").find("option:selected").attr("value-hidden");
    var publishProductCname = $("#select-product").find("option:selected").text();
    var productSrcSvn = $("#select-product").find("option:selected").attr("value-svn");
    var publishEnv = $("#select-publich-env").val();
    var publishSvn = $("#publich-svn").val();

    if(publishSubject==""||publishBuCname=="请选择"||publishProductCname=="请选择"||publishEnv==""||publishSvn==""){
        nebula.common.alert.danger("请确认选择的所有字段",1000);
        return false;
    }
    $.ajax({
        url:"/publish/add",
        type:"post",
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
};

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
            if(jsonData.callbackMsg.match(/Success/)){
                nebula.common.alert.info("准备发布成功",2000);
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
            if(jsonData.callbackMsg.match(/Success/)){
                nebula.common.alert.info("预发布成功",2000);
            }
        }
    });
};


nebula.publish.process.publishReal = function(){
    var id = $("#eventId").val();
    $.ajax({
        url:"/publish/publishReal",
        type:"post",
        data:{"id":id},
        success:function(jsonData){
            if(jsonData.callbackMsg.match(/Success/)){
                nebula.common.alert.info("正式发布成功",2000);
            }
        }
    });
};

nebula.publish.process.publishContinue = function(){
    var id = $("#eventId").val();
    $.ajax({
        url:"/publish/publishContinue",
        type:"post",
        data:{"id":id},
        success:function(jsonData){
            if(jsonData.callbackMsg.match(/Success/)){
                nebula.common.alert.info("正式发布成功",2000);
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
            if(jsonData.callbackMsg.match(/Success/)){
                $("#restartPublish").hide();
                nebula.common.alert.info("确认发布成功",2000);
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
            if(jsonData.callbackMsg.match(/Success/)){
                $("#restartPublish").hide();
                nebula.common.alert.info("回滚成功",2000);
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
            if(jsonData.callbackMsg.match(/Success/)){
                $("#restartPublish").hide();
                window.location.href="/publishProcess.htm?id="+id;
                nebula.common.alert.info("正式发布成功",2000);
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
            if(jsonData.callbackMsg.match(/Success/)){
                $("#restartPublish").hide();
                nebula.common.alert.info("重启tomcat成功",2000);
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
            if(jsonData.callbackMsg.match(/Success/)){
                $("#restartPublish").hide();
                nebula.common.alert.info("取消发布成功",2000);
            }
        }
    });
};



