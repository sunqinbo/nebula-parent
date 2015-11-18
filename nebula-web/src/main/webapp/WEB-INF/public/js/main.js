/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
var nebula = {};
var BASE_SITE_URL = "http://127.0.0.1:8080";


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

nebula.publish = {};

nebula.publish.event = {};

nebula.publish.event.main = function(){
    $(document).ready(function(){
        $("#select-bu").change(function(){
            var pid = $("#select-bu").val();
            $("#select-product").html("<option value=''>请选择</option>");
            $.ajax({
                url:"/publish_event/getProductTreeListByPid.htm",
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

    if(publishSubject==null||publishBuCname==null||publishProductCname==null||productSrcSvn==null||publishEnv==null||publishSvn==null){
        $.notify({
            icon: '',
            message: "请确认选择的所有字段"
        },{
            type: 'error',
            timer: 1000
        });
    }
    $.ajax({
        url:"/publish_event/createPublishEvent.htm",
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
                window.location.href="/publishProcess.htm?id="+jsonData.responseContext;
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
        url:"/publish_event/preMasterPublish.htm",
        type:"post",
        data:{"id":id},
        success:function(jsonData){
            if(jsonData.callbackMsg.match(/Success/)){
                $.notify({
                    icon: '',
                    message: "准备发布成功"
                },{
                    type: 'info',
                    timer: 2000
                });
            }
        }
    });
};

nebula.publish.process.preMinionPublish = function(){
    var id = $("#eventId").val();
    $.ajax({
        url:"/publish_event/preMinionPublish.htm",
        type:"post",
        data:{"id":id},
        success:function(jsonData){
            if(jsonData.callbackMsg.match(/Success/)){
                $.notify({
                    icon: '',
                    message: "预发布成功"
                },{
                    type: 'info',
                    timer: 2000
                });
            }
        }
    });
};


nebula.publish.process.publishReal = function(){
    var id = $("#eventId").val();
    $.ajax({
        url:"/publish_event/publishReal.htm",
        type:"post",
        data:{"id":id},
        success:function(jsonData){
            if(jsonData.callbackMsg.match(/Success/)){
                $.notify({
                    icon: '',
                    message: "正式发布成功"
                },{
                    type: 'info',
                    timer: 2000
                });
            }
        }
    });
};

nebula.publish.process.publishContinue = function(){
    var id = $("#eventId").val();
    $.ajax({
        url:"/publish_event/publishContinue.htm",
        type:"post",
        data:{"id":id},
        success:function(jsonData){
            if(jsonData.callbackMsg.match(/Success/)){
                $.notify({
                    icon: '',
                    message: "正式发布成功"
                },{
                    type: 'info',
                    timer: 2000
                });
            }
        }
    });
};

nebula.publish.process.publishSuccessEnd = function(){
    var id = $("#eventId").val();
    $.ajax({
        url:"/publish_event/publishSuccessEnd.htm",
        type:"post",
        data:{"id":id},
        success:function(jsonData){
            if(jsonData.callbackMsg.match(/Success/)){
                $("#restartPublish").hide();
                $.notify({
                    icon: '',
                    message: "确认发布成功"
                },{
                    type: 'info',
                    timer: 2000
                });
            }
        }
    });
};

nebula.publish.process.publishFailEnd = function(){
    var id = $("#eventId").val();
    $.ajax({
        url:"/publish_event/publishFailEnd.htm",
        type:"post",
        data:{"id":id},
        success:function(jsonData){
            if(jsonData.callbackMsg.match(/Success/)){
                $("#restartPublish").hide();
                $.notify({
                    icon: '',
                    message: "回滚成功"
                },{
                    type: 'info',
                    timer: 2000
                });
            }
        }
    });
};

nebula.publish.process.retryPublishRollback = function(){
    var id = $("#eventId").val();
    $.ajax({
        url:"/publish_event/retryPublishRollback.htm",
        type:"post",
        data:{"id":id},
        success:function(jsonData){
            if(jsonData.callbackMsg.match(/Success/)){
                $("#restartPublish").hide();
                window.location.href="/publishProcess.htm?id="+id;
                $.notify({
                    icon: '',
                    message: "正式发布成功"
                },{
                    type: 'info',
                    timer: 2000
                });
            }
        }
    });
};


