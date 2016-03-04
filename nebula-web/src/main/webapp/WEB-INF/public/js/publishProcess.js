$(document).ready(function(){

    $("#detail_btn").click(function(){
        if($("#detail_btn").text()=="展开") {
            $("#detail_btn").text("收缩");
        }
        else{
            $("#detail_btn").text("展开");
        }
        $("#detail").slideToggle("swing");
    })

    //审批按钮
    $("#approval_btn").click(function(){
        approvalBtn();
        //$("#approval_btn").attr('disabled', true).removeClass("btn-info");
    });

    //编辑etc
    $("#etc_btn").click(function () {
        window.open('/etc_edit/fileEdit.htm?id='+$("#eventId").val());
    })
    //编辑etc完成
    $("#edit_success").click(function () {
        var ms = confirm("确认完成编辑么（确定后将无法再编辑）？");
        if (ms == true) {
            $.ajax({
                url: "/publish/updateEtcEnd",
                type: "post",
                data: {"id": $("#eventId").val()},
                success: function (jsonData) {
                    if(!data.callbackMsg){
                        data=JSON.parse(data);
                    }
                    if(data.callbackMsg=="Error") {
                        nebula.common.alert.danger(data.responseContext, 1000);
                        return;
                    }
                    if (jsonData.callbackMsg.match(/Success/)) {
                        nebula.common.alert.success(data.responseContext, 1000);
                    }
                },
                error: function (errorThrown) {
                    nebula.common.alert.danger( "完成编辑失败，原因" + errorThrown, 1000);
                }
            });
            $("#etc_btns").hide();
            //$("#etc_btns").empty();
        }
    });

    //发布事件阶段跳转按钮文字控制
    checkPublishBtnSet();

    $("#modalCheck_btn").click(function(){
        $.ajax({
            type:"POST",
            url: "/publish/list/describeRefreshTasks",
            dataType:"json",
            success: function (data) {
                if(!data.callbackMsg){
                    data=JSON.parse(data);
                }
                if(data.callbackMsg=="Success") {
                    var modal_tbString = ""
                    for (var i = 0, modallen = data.responseContext.tasks.length; i < modallen; i++) {
                        var task = data.responseContext.tasks[i];
                        modal_tbString += "<tr><td>" + task.taskId + "</td>" +
                            "<td>" + task.objectPath + "</td>" +
                            "<td>" + task.status + "</td>" +
                            "<td>" + task.creationTime + "</td>" +
                            "</tr>"
                    }
                    $("#modal_tb").html(modal_tbString);
                }
                else{
                    nebula.common.alert.danger(data.responseContext, 1000);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                nebula.common.alert.danger("请求CDN列表失败，原因" + errorThrown, 1000);
            }
        })
    });

    $("#modalSubmit_btn").click(function(){
        $.ajax({
            type:"POST",
            url: "/publish/add/refreshObjectCaches",
            data:{
                objectPath:$("#modalUrl_select").find("option:selected").text(),
                objectType:"Directory",
            },
            dataType:"json",
            success: function (data) {
                if(!data.callbackMsg){
                    data=JSON.parse(data);
                }
                if(data.callbackMsg=="Error") {
                    nebula.common.alert.danger(data.responseContext, 1000);
                    return;
                }
                nebula.common.alert.success("刷新CDN成功", 1000);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.notify({
                    icon: '',
                    message: "刷新CDN失败，原因" + errorThrown
                }, {
                    type: 'danger',
                    timer: 1000
                });
            }
        })
    });

    /** 解决弹窗的问题，先将modal移到body层 */
    $('#mymodal').appendTo("body");
    $('#checkmodal').appendTo("body");
    $("#logModal").appendTo("body");
    $("#codeModal").appendTo("body");
    $("#refreshCDN").click(function(){
        $('#mymodal').modal('show');
    });
    $("#modalCheck_btn").click(function(){
        $('#checkmodal').modal('show');
    });


    //初始化隐藏所有进度条的DIV及设置按钮不可点
    $("#step1").hide();
    $("#step2").hide();
    $("#step3").hide();
    $("#step4").hide();
    $("#step5").hide();
    $("#step6").hide();
    btnUnclick();
    //根据jQuery选择器找到需要加载ystep的容器
    //loadStep 方法可以初始化ystep
    //加载动态进度条
    $("#processbar1").loadStep({
        size: "large",
        color: "blue",
        steps: [{
            title: "获取发布war包",
        }, {
            title: "分析工程",
        }, {
            title: "获取源war/etc",
        }, {
            title: "编辑etc",
        }, {
            title: "准备完成",
        }]
    });
    $("#processbar2").loadStep({
        size: "large",
        color: "blue",
        steps: [{
            title: "创建发布目录",
        }, {
            title: "拷贝原etc文件",
        }, {
            title: "拷贝原war文件",
        }, {
            title: "发布新etc文件",
        }, {
            title: "发布新war文件",
        }, {
            title: "预发布完成",
        }]
    });
    $("#processbar3").loadStep({
        size: "large",
        color: "blue",
        steps: [{
            title: "停止tomcat",
        }, {
            title: "更改文件指向",
        }, {
            title: "启动tomcat",
        }, {
            title: "正式发布完成",
        }]
    });
    $("#processbar4").loadStep({
        size: "large",
        color: "blue",
        steps: [{
            title: "停止tomcat",
        }, {
            title: "更改文件指向",
        }, {
            title: "启动tomcat",
        }, {
            title: "清楚失败目录",
        }, {
            title: "回滚完成",
        }]
    });
    $("#processbar5").loadStep({
        size: "large",
        color: "blue",
        steps: [{
            title: "清除发布目录",
        }, {
            title: "更新源SVN",
        }, {
            title: "确认完成",
        }]
    });
    $("#processbar6").loadStep({
        size: "large",
        color: "blue",
        steps: [{
            title: "停止Tomcat",
        }, {
            title: "启动tomcat",
        }, {
            title: "重启完成",
        }]
    });
    //页面初次加载进度条控制
    Initialization();
    setInterval("Initialization()", 5000);
    getSlbInfo();
    setInterval("getSlbInfo()", 30000);
    //按钮点击事件
    $("#btn1").click(function () {
        $("#loading-status").show();
        $("#btn1").attr('disabled', true);
        $("#btn1").removeClass("btn-info");
        $("#step1").show();
    });
    $("#btn2").click(function () {
        $.ajax({
            async: false,
            data: {
                "publishBuName": $("#publishBuName").val(),
                "publishProductName": $("#publishProductName").val()
            },
            type: "post",
            url: "/publish/get/noPublish",
            datetype: "json",
            success: function (data) {
                if(data.length>0){
                    //var msg=""
                    //for(var i= 0,len=data.length;i<len;i++){
                    //    msg+=data[i]["id"]+",";
                    //}
                    var msg=data[0]["id"];
                    msg="很抱歉，该产品已有发布中的事件，请先"+"<a target='_blank' href='/publish/process.htm?id="+msg+"'>前往</a>结束该事件";
                    //msg="很抱歉，该产品正在发布中,发布id为:"+msg+"请稍后再试";
                    nebula.common.alert.warning(msg,1000);
                    return;
                }
                else {
                    $("#loading-status").show();
                    $("#btn2").attr('disabled', true);
                    $("#btn2").removeClass("btn-info");
                    $("#step2").show();
                    nebula.publish.process.preMinionPublish();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                nebula.common.alert.danger("很抱歉，获取发布事件信息失败，原因" + errorThrown,1000);
                return;
            }
        });
    });
    $("#btn3").click(function () {
        if($("#publishEnv").text()=='product') {
            $('#codeModal').modal('show');
        }
        else {
            $("#loading-status").show();
            $("#btn3").attr('disabled', true);
            $("#btn3").removeClass("btn-info");
            $("#step3").show();
            $("#cancelPublish").hide();
            $("#restartPublish").hide();
            nebula.publish.process.publishReal();
        }
    });
    $("#btn4").click(function () {
        $("#loading-status").show();
        $("#btn4").attr('disabled', true);
        $("#btn4").removeClass("btn-info");
        $("#btn_ConfirmResult").attr('disabled', true);
        $("#btn_ConfirmResult").removeClass("btn-info");
        $("#refreshCDN").hide();
        $("#restartTomcat_btn").hide();
        $("#restartPublish").hide();
        $("#cancelPublish").hide();
        $("#step4").show();
        $("#step6").hide();
    });
    $("#btn_ConfirmResult").click(function () {
        $("#loading-status").show();
        $("#btn_ConfirmResult").attr('disabled', true);
        $("#btn_ConfirmResult").removeClass("btn-info");
        $("#btn4").attr('disabled', true);
        $("#btn4").removeClass("btn-info");
        $("#restartTomcat_btn").hide();
        $("#refreshCDN").hide();
        $("#restartPublish").hide();
        $("#cancelPublish").hide();
        $("#step5").show();
        $("#step6").hide();
    });
    $("#restartTomcat_btn").click(function () {
        $("#loading-status").show();
        $("#restartTomcat_btn").attr('disabled', true);
        $("#restartTomcat_btn").removeClass("btn-danger");
        $("#step6").show();
        $("#btn_ConfirmResult").attr('disabled', true);
        $("#btn_ConfirmResult").removeClass("btn-info");
        $("#btn4").attr('disabled', true);
        $("#btn4").removeClass("btn-info");
    });
    $("#backPublish").click(function(){
        $("#loading-status").show();
        $("#restartTomcat_btn").hide();
        $("#restartPublish").hide();
        $("#cancelPublish").hide();
        $("#step4").show();
        $("#step6").hide();
        $("#nextPublish").hide();
    });
    //进入下一阶段的发布（禁用）
    $("#nextPublish").click(function () {
        if ($("#publishEnv").html() == "test1"||$("#publishEnv").html() == "test2"||$("#publishEnv").html() == "test3") {
            nextPublish("stage");
        }
        if ($("#publishEnv").html() == "stage") {
            nextPublish("product");
        }
    });

    $("#freshControl_switch").find("label").css("width","0px");

    //自动刷新按钮点击事件
    $("#freshControl_switch").click(function () {
        if($('#freshControl_checkbox').prop("checked")){
            $('#freshControl_switch').bootstrapSwitch('setState', true);
            $("#pageSort").hide();
        } else{
            $('#freshControl_switch').bootstrapSwitch('setState', false);
            $("#pageSort").show();
        }
        logFrenshControl(1);
    });

    //动态验证码验证
    $("#code_btn_modal").click(function () {
        verificationCodeBtn();
    });

    //删除非.war文件
    $("#deleteNoWar_btn").click(function(){
        $.ajax({
            async: false,
            data: {
                eventId: $("#eventId").val()
            },
            type: "post",
            url: "/publish/deleteErrorFiles",
            datetype: "json",
            success: function (data) {
                if(!data.callbackMsg){
                    data=JSON.parse(data);
                }
                if(data.callbackMsg=="Error") {
                    nebula.common.alert.danger(data.responseContext, 1000);
                    return;
                }
                $("#deleteNoWar_btn").hide();
                nebula.common.alert.success(data.responseContext, 1000);
            },
            error: function (errorThrown) {
                nebula.common.alert.danger("很抱歉，获取发布事件信息失败，原因" + errorThrown,1000);
            }
        });
    });

});

//页面加载控制进度条
function Initialization() {
    var whichStep, actionGroup, actionState;
    //获取动作组，动作进度点，动作状态
    $.ajax({
        data: {
            eventId: $("#eventId").val()
        },
        url: "/publish/publishProcessStep",
        type: "post",
        datetype: "json",
        success: function (data) {
            //机器信息列表相关
            var HostList = data.responseContext.HostInfos;
            var tbString = "";
            for (var i = 0; i < HostList.length; i++) {
                var passPublishHostName = "";
                var actionName = ""
                var isSuccessAction = ""
                var actionResult = ""
                var passPublishHostIp = "";
                var logNumShow="";
                if (HostList[i]["passPublishHostName"] != null)
                    passPublishHostName =""+ HostList[i]["passPublishHostName"];
                if (HostList[i]["passPublishHostName"] != null)
                    passPublishHostIp = HostList[i]["passPublishHostIp"];
                if (HostList[i]["actionName"] != null)
                    actionName = HostList[i]["actionName"];
                if (HostList[i]["isSuccessAction"] != null)
                    isSuccessAction = HostList[i]["isSuccessAction"];
                if (HostList[i]["actionResult"] != null)
                    actionResult = HostList[i]["actionResult"];
                if(data.responseContext.eventStatus!="PUBLISHED"&&data.responseContext.eventStatus!="ROLLBACK"&&data.responseContext.eventStatus!="CANCEL")
                {
                    logNumShow+="<a onclick='errorNumClick("+"&quot;"+passPublishHostName+"&quot;"+",&quot;"+"ERROR"+"&quot;"+")' href='#'><span class='label label-danger'>error:"+
                        HostList[i]["logNumber"]+"</span></a><br/><br/>"+"<a onclick='errorNumClick("+"&quot;"+passPublishHostName+"&quot;"+",&quot;"+"EXCEPTION"+"&quot;"+")' href='#'><span class='label label-danger'>exc:"+
                        HostList[i]["excNumber"]+"</span></a>";
                }
                tbString = tbString + "<tr><td>" + passPublishHostName + "</td><td>" + passPublishHostIp + "</td><td>" +
                    actionName + "</td><td>" + isSuccessAction + "</td><td>" + actionResult +
                    "</td><td>"+logNumShow+"</td></tr>";
            }
            $("#hostInfo").html(tbString);

            //进度条相关
            whichStep = data.responseContext.whichStep;
            actionGroup = data.responseContext.actionGroup;
            actionState = data.responseContext.actionState + "";

            //按钮显示控制
            if(actionGroup==6) {
                btnControl(data.responseContext.eventStatus);
            }
            var lastGroup=data.responseContext.lastGroup;
            btnUnclick();
            //动作不为编辑ETC 且正在执行，显示等待条
            if ((actionState == "null" || actionState == "") && !(actionGroup == 1 && whichStep == 4)) {
                $("#loading-status").show();
            } else {
                $("#loading-status").hide();
            }

            //设置进度条长度
            var lastStep;
            switch (actionGroup) {
                case 1:
                    lastStep = 4;
                    if(whichStep>2&&$("#moduleAndApps tr").length==0){
                        $.ajax({
                            data:{eventId: $("#eventId").val()},
                            url:"/publish/list/moduleAndApps",
                            datatype:"json",
                            type: "post",
                            success:function(data){
                                var moduletbString=""
                                for(var i= 0,len=data.length;i<len;i++){
                                    var module=data[i];
                                    moduletbString+="<tr><td>"+module.publishModuleName+"</td>"
                                    +"<td>"+module.publishModuleKey+"</td><td>";
                                    for(var j= 0,len1=module.publishApps.length;j<len1;j++){
                                        var app=module.publishApps[j];
                                        moduletbString+=app.publishAppName+";";
                                    }
                                    moduletbString+="</td></tr>"
                                }
                                $("#moduleAndApps").html("");
                                $("#moduleAndApps").html(moduletbString);
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                var msg="很抱歉，获取发布模块信息失败，原因" + errorThrown
                                nebula.common.alert.danger(msg,1000);
                            }
                        })
                    }
                    break;
                case 2:
                    lastStep = 5;
                    break;
                case 3:
                    lastStep = 3;
                    break;
                case 4:
                    lastStep = 4;
                    break;
                case 5:
                    lastStep = 2;
                    break;
                case 6:
                    lastStep = 1;
                    break;
                case 7:
                    lastStep = 2;
                    break;
            }

            //动作成功执行 隐藏重试按钮,失败显示重试按钮并显示错误信息
            if (actionState == "false") {
                //var false_btn = "<Button type='button' class='btn btn-info' onclick='nebula.publish.process.publishContinue()'>重试</Button>"
                //$("#false_btn").html(false_btn);
                $("#false_btn").show();
                if(data.responseContext.errorType==1){
                    $("#deleteNoWar_btn").show();
                }
                $("#errorMsgDiv").html(data.responseContext.errorMsg);
                $("#errorMsgDiv").show();
            }
            else {
                $("#false_btn").hide();
                //$("#false_btn").html("");
                $("#errorMsgDiv").hide();
            }
            //当动作为创建发布事件且成功时，发布准备可点
            if (whichStep == 0) {
                if (actionState == "true") {
                    $("#btn1").attr('disabled', false);
                    $("#btn1").addClass("btn-info");
                }
                return;
            }
            //动作为此阶段最后一步完成 下一步按钮可点   (否则调用初始化 按钮不可点样式移除)
            if (whichStep == lastStep && actionState == "true") {
                actionGroup = actionGroup - 1 + 2;
                switch (actionGroup) {
                    case 3:
                        //$("#restartPublish").hide();
                        $("#restartPublish").show();
                        $("#cancelPublish").show();
                        for (var i = 1; i < 4; i++) {
                            if (i == actionGroup) {
                                $("#btn" + i).attr('disabled', false);
                                $("#btn" + i).addClass("btn-info");
                                $("#step" + (i - 1)).hide();
                            }
                            else {
                                $("#btn" + i).attr('disabled', true);
                                $("#btn"+i).removeClass("btn-info");
                            }
                        }
                        return;
                    //阶段三完成，确认发布，和回滚可点
                    case 4:
                        $("#btn_ConfirmResult").attr('disabled', false);
                        $("#btn_ConfirmResult").addClass("btn-info");
                        $("#btn4").attr('disabled', false);
                        $("#btn4").addClass("btn-info");
                        $("#step" + (3)).hide();
                        $("#restartTomcat_btn").show();
                        $("#restartTomcat_btn").attr('disabled', false);
                        $("#refreshCDN").show();
                        $("#refreshCDN").attr('disabled', false);
                        $("#refreshCDN").addClass("btn-danger");
                        return;
                    case 7:
                        //$("#restartTomcat_btn").attr('disabled', false);
                        $("#restartTomcat_btn").hide();
                        $("#backPublish").attr('disabled', false);
                        $("#nextPublish").attr('disabled', false);
                        return;
                    //case 7:$("#restartTomcat_btn").attr('disabled', false).show();
                    //    if(lastGroup==5) {
                    //    var btn_text;
                    //    if ($("#publishEnv").html() == "test") {
                    //        btn_text = "准生产";
                    //    }
                    //    if ($("#publishEnv").html() == "stage") {
                    //        btn_text = "生产";
                    //    }
                    //    $("#backPublish").show();
                    //    $("#nextPublish").text("进入" + btn_text).show();
                    //    $("#processbar5").setStep(3);
                    //    $("#step5").show();
                    //}
                    //    if(lastGroup==4){
                    //        $("#processbar4").setStep(4);
                    //        $("#step4").show();
                    //    }
                    //    return;
                    case 8: $("#step6").show();
                        $("#restartTomcat_btn").show();
                        $("#restartTomcat_btn").attr('disabled', false);
                        $("#restartTomcat_btn").addClass("btn-danger");
                        $("#processbar6").setStep(3);
                        $("#btn_ConfirmResult").attr('disabled', false);
                        $("#btn_ConfirmResult").addClass("btn-info");
                        $("#backPublish").addClass("btn-danger");
                        $("#backPublish").attr('disabled', false);
                        $("#nextPublish").addClass("btn-info");
                        $("#nextPublish").attr('disabled', false);
                        $("#btn4").attr('disabled', false);
                        $("#btn4").addClass("btn-info");
                        $("#step4").hide();
                        $("#step5").hide();
                        $("#refreshCDN").show();
                        $("#refreshCDN").attr('disabled', false);
                        $("#refreshCDN").addClass("btn-danger");
                        return;
                    default :
                        if (actionGroup < 5) {
                            $("#restartPublish").show();
                            $("#cancelPublish").show();
                            for (var i = 1; i < 4; i++) {
                                if (i == actionGroup) {
                                    $("#btn" + i).attr('disabled', false);
                                    $("#btn" + i).addClass("btn-info");
                                    $("#step" + (i - 1)).hide();
                                }
                                else {
                                    $("#btn" + i).attr('disabled', true);
                                    $("#btn4").removeClass("btn-info");
                                }
                            }
                            return;
                        }
                        whichStep = whichStep + 1;
                        var whichshow = actionGroup - 1 + "";
                        $("#step" + whichshow).show();
                        $("#btn4").removeClass("btn-info");
                        $("#btn_ConfirmResult").removeClass("btn-info");
                        //$("#restartTomcat_btn").hide();
                        $("#restartTomcat_btn").attr('disabled', false).show();
                        $("#processbar" + whichshow).setStep(whichStep);
                        return;
                }
            }
            //动作为ect开始时
            if (actionGroup == 1 && whichStep == 4 && (actionState == "" || actionState == "null")) {
                //添加编辑按钮
                $("#restartPublish").show();
                $("#cancelPublish").show();

                $("#etc_btns").show();
            }
            else {
                $("#restartPublish").hide();
                $("#cancelPublish").hide();
            }
            //btnUnclick();
            //控制进度条显示
            for (var i = 1; i <= 5; i++) {
                if (i == actionGroup) {
                    $("#step" + i).show();
                }
                else {
                    $("#step" + i).hide();
                }
            }
            if(actionGroup>5){
                if(actionGroup!=6) {
                    $("#restartTomcat_btn").show();
                }
                $("#refreshCDN").show();
            }
            if(actionGroup==7) {
                //$("#restartTomcat_btn").show();
                $("#restartTomcat_btn").removeClass("btn-danger");
                $("#backPublish").removeClass("btn-danger");
                $("#nextPublish").removeClass("btn-info");
                $("#step6").show();
                $("#step4").hide();
                $("#step5").hide();
                $("#processbar6").setStep(whichStep);
            }
            else {
                //设置进度条进度
                $("#processbar" + actionGroup).setStep(whichStep);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.notify({
                icon: '',
                message: "很抱歉，获取进度失败，原因" + errorThrown

            }, {
                type: 'info',
                timer: 1000
            });
        }
    });
}

//按钮显示控制
function btnControl(publishStatus){
    switch (publishStatus){
        case "PUBLISHED":var btn_text;
            $("#restartTomcat_btn").show();
            if ($("#publishEnv").html() == "test1"||$("#publishEnv").html() == "test2"||$("#publishEnv").html() == "test3") {
                btn_text = "准生产";
                $("#nextPublish").text("进入" + btn_text).show();
            }
            if ($("#publishEnv").html() == "stage") {
                btn_text = "生产";
                $("#nextPublish").text("进入" + btn_text).show();
            }
            $("#backPublish").show();
            $("#processbar5").setStep(3);
            $("#step5").show();
            break;
        case "ROLLBACK":$("#restartTomcat_btn").show();
            $("#processbar4").setStep(5);
            $("#step4").show();
    }
}

//按钮设为不可点
function btnUnclick() {
    $("#btn1").attr('disabled', true);
    $("#btn2").attr('disabled', true);
    $("#btn3").attr('disabled', true);
    $("#btn4").attr('disabled', true);
    $("#btn4").removeClass("btn-info");
    $("#btn_ConfirmResult").attr('disabled', true);
    $("#btn_ConfirmResult").removeClass("btn-info");
    $("#restartTomcat_btn").attr('disabled',true);
    $("#backPublish").attr('disabled', true);
    $("#nextPublish").attr('disabled', true);
    //$("#refreshCDN").attr('disabled',true);
    //$("#refreshCDN").removeClass("btn-danger");
}

//下一阶段的发布事件的点击事件
function nextPublish(nowPublish) {
    $.ajax({
        async: false,
        type: "post",
        data: {
            "nowPublish": nowPublish,
            "eventId": $("#eventId").val()
        },
        url: "/publish/add/nextpublish",
        datatype: "json",
        success: function (data) {
            location.href = "/publish/process.htm?id=" + data.responseContext;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.notify({
                icon: '',
                message: "很抱歉插入发布事件单失败，原因" + errorThrown
            }, {
                type: 'info',
                timer: 1000
            });
        }
    })
}

//审批功能
function approvalBtn(){
    $.ajax({
        async: false,
        type: "post",
        data: {
            "eventId": $("#eventId").val()
        },
        url: "/publish/update/approval",
        datatype: "json",
        success: function (data) {
            if(!data.callbackMsg){
                data=JSON.parse(data);
            }
            if(data.callbackMsg=="Error") {
                nebula.common.alert.danger(data.responseContext, 1000);
                return;
            }else{
                nebula.common.alert.success(data.responseContext, 1000);
                window.location.reload();
            }
            //$.notify({
            //    icon: '',
            //    message: "审批完成"
            //}, {
            //    type: 'info',
            //    timer: 1000
            //});
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.notify({
                icon: '',
                message: "很抱歉插入发布事件单失败，原因" + errorThrown
            }, {
                type: 'danger',
                timer: 1000
            });
        }
    })
}

//错误数点击事件
function errorNumClick(hostName,type){
    $("#hostName_modal").val(hostName);
    $("#publishDatetime_modal").val($("#publishDatetime").text());
    $("#logEndTime_modal").val( new Date().Format("yyyy-MM-dd hh:mm:ss"));
    //$("#keyWord_modal").val("ERROR");
    $("#keyWord_modal").val(type);
    //设置开关为开启状态
    $('#freshControl_switch').bootstrapSwitch('setState', true);
    //模态框状态设置（开启）
    $("#isclosed_modal").val("0");
    //轮询事件
    //setInterval("endTimeControl()", 2000);
    //setTimeout(logFrenshControl,2000);
    logFrenshControl(1);
    //$("#keyWord_modal").change(logFrenshControl);
    //过滤框内容变更事件
    $("#keyWord_modal").change(function(){
        logFrenshControl(1)
    })
    //模态框关闭事件
    $("#close_logModal_pan").click(function(){
        $("#isclosed_modal").val("1");
    })
    $("#close_logModal_btn").click(function(){
        $("#isclosed_modal").val("1");
    });
    //点击空白或ESC不能取消模态框
    $('#logModal').modal({backdrop: 'static', keyboard: false});
    //$('#logModal').modal('show');
}
//自动刷新日志文件
function logFrenshControl(pageNum,currentPage){
    if( $("#isclosed_modal").val()==1){
        return;
    }
    if($('#freshControl_checkbox').prop("checked")){
        $('#logEndTime_modal').val(new Date().Format('yyyy-MM-dd hh:mm:ss'));
        setTimeout(logFrenshControl,2000);
    }
    //else{
    //    //clearTimeout(global_setTimeout);
    //}
    $.ajax({
        type: "POST",
        url: "/publish/log/getPublishLogByHost",
        data: {
            host:$("#hostName_modal").val(),
            eventId:$("#eventId").val(),
            keyWord:$("#keyWord_modal").val(),
            toDateString:$("#logEndTime_modal").val(),
            pageNum:pageNum,
            pageSize:10,
        },
        //async: true,
        success: function (data) {
            if(!data.callbackMsg){
                data=JSON.parse(data);
            }
            if(data.callbackMsg=="Error") {
                nebula.common.alert.danger(data.responseContext, 1000);
                return;
            }
            var tbLogString="";
            for(var i= 0,len=data.responseContext["list"].length;i<len;i++){
                var logInfo=data.responseContext["list"][i];
                var elkUrl="http://elk.stage.900jit.com/#/doc/logstash-*/"+logInfo.index+"/tomcat?id="+logInfo.id+"&_g=()"
                tbLogString+="<tr>"+
                    "<td style='WORD-WRAP: break-word'><div class='doc-viewer'>"+logInfo.message+"</div></td>"+
                    "<td class='tdTopControl'><a href='"+elkUrl+"'target=_blank >详情</a></td>"
                "</tr>"
            }
            $("#logInfoTb_modal").html(tbLogString);
            //停止自动刷新时
            if(!$('#freshControl_checkbox').prop("checked")) {
                var totalPage = data.responseContext["pages"];
                //$('#pagination').twbsPagination({
                //    totalPages: totalPage,
                //    visiblePages: 6,
                //    onPageClick: function (event, page) {
                //        $("#logInfoTb_modal").html("");
                //        logAjax(page);
                //    }
                //});
                (function() {
                    $('#pageSort').pagination({
                        pages: totalPage,
                        styleClass: ['pagination-large'],
                        showCtrl: true,
                        displayPage: 6,
                        currentPage:currentPage,
                        onSelect: function (num) {
                            $("#logInfoTb_modal").html("");
                            logFrenshControl(num,num);  //分页点击
                        }
                    });
                    $('#pageSort').pagination('updatePages',totalPage);
                })();

            }
        },
        error: function (errorThrown) {
            $.notify({
                icon: '',
                message: "获取日志失败，原因：" + errorThrown

            }, {
                type: 'danger',
                timer: 1000
            });
        }
    })

}

//日志请求
//function logAjax(page){
//    $.ajax({
//        type: "POST",
//        url: "/publish/log/getPublishLogByHost",
//        data: {
//            host:$("#hostName_modal").val(),
//            eventId:$("#eventId").val(),
//            keyWord:$("#keyWord_modal").val(),
//            toDateString:$("#logEndTime_modal").val(),
//            pageNum:page,
//            pageSize:10,
//        },
//        //async: true,
//        success: function (data) {
//            var tbLogString="";
//            for(var i= 0,len=data.responseContext["list"].length;i<len;i++){
//                var logInfo=data.responseContext["list"][i];
//                var elkUrl="http://elk.stage.900jit.com/#/doc/logstash-*/"+logInfo.index+"/tomcat?id="+logInfo.id+"&_g=()"
//                tbLogString+="<tr>"+
//                    "<td style='WORD-WRAP: break-word'><div class='doc-viewer'>"+logInfo.message+"</div></td>"+
//                    "<td><a href='"+elkUrl+"'target=_blank >详情</a></td>"
//                "</tr>"
//            }
//            $("#logInfoTb_modal").html(tbLogString);
//        },
//        error: function (errorThrown) {
//            $.notify({
//                icon: '',
//                message: "获取日志失败，原因：" + errorThrown
//
//            }, {
//                type: 'danger',
//                timer: 1000
//            });
//        }
//    })
//}

//结束时间失焦事件
function endTimeOnblur(){
    $("#endTimecheck_modal").val($("#logEndTime_modal").val());
}
//结束时间获取焦点事件
function endTimeOnfocus(){
    if($("#endTimecheck_modal").val()==0){
        return;
    }
    var oldTime = (new Date($("#endTimecheck_modal").val())).getTime();
    var newTime = (new Date($("#logEndTime_modal").val())).getTime();
    if(Math.abs((newTime-oldTime)/60000)>=1){
        $('#freshControl_switch').bootstrapSwitch('setState', false);
        logFrenshControl(1);
    }
}

//获取slb信息
function getSlbInfo(){
    $.ajax({
        type:"POST",
        url:"/publish/list/describeLoadBalancerAttributes",
        data:{eventId:$("#eventId").val()},
        success: function (data) {
            var slbTbString="";
            for(var i= 0,len=data.responseContext.length;i<len;i++){
                var slbInfo=data.responseContext[i];
                var slbHostInfo="";
                if(data.responseContext[i].describeHealthStatusResponse!=null){
                    slbHostInfo=data.responseContext[i].describeHealthStatusResponse.backendServers;
                }
                var hostInfoString="";
                var loadBalancerStatus="";
                for(var j= 0,leng=slbHostInfo.length;j<leng;j++){
                    var serverHealthStatus="";
                    switch (slbHostInfo[j].serverHealthStatus){
                        case "normal":serverHealthStatus+="<span class='label label-success'>"+slbHostInfo[j].serverHealthStatus+"</span>";break;
                        case "abnormal":serverHealthStatus+="<span class='label label-danger'>"+slbHostInfo[j].serverHealthStatus+"</span>";break;
                        default:serverHealthStatus+="<span class='label label-default'>"+slbHostInfo[j].serverHealthStatus+"</span>";
                    }
                    if(j!=0){
                        hostInfoString+="<br/>";
                    }
                    hostInfoString+="<div class='col-md-5'>"+slbHostInfo[j].serverId+"</div>"+"&nbsp;&nbsp;: &nbsp;&nbsp;"+serverHealthStatus;
                }
                //hostInfoString=hostInfoString+"<br/>"+"sdsd"+":"+"bbb";
                switch (slbInfo.loadBalancerStatus){
                    case "inactive":loadBalancerStatus+="<span class='label label-danger'>"+slbInfo.loadBalancerStatus+"</span>";break;
                    case "active":loadBalancerStatus+="<span class='label label-success'>"+slbInfo.loadBalancerStatus+"</span>";break;
                    default:loadBalancerStatus+="<span class='label label-default'>"+slbInfo.loadBalancerStatus+"</span>";
                }
                slbTbString+="<tr>"+
                    "<td>"+slbInfo.loadBalancerName+"</td>"+
                    "<td>"+slbInfo.loadBalancerAddress+"</td>"+
                    "<td>"+slbInfo.loadBalancerId+"</td>"+
                    "<td>"+loadBalancerStatus+"</td>"+
                    "<td>"+hostInfoString+"</td>";
                "</tr>"
            }
            $("#slbInfo_tb").html(slbTbString);
        },
        error: function (errorThrown) {
            $.notify({
                icon: '',
                message: "获取SLB信息失败，原因：" + errorThrown

            }, {
                type: 'danger',
                timer: 1000
            });
        }
    })
}

//查看下一发布阶段
function checkNextPublish(eventId){
    location.href = "/publish/process.htm?id=" + eventId;
}

//查看上一发布阶段
function checkLastPublish(){
    $.ajax({
        type:"POST",
        url:"/publish/getLastPublishId",
        data:{
            eventId:$("#eventId").val()
        },
        success:function(data){
            location.href = "/publish/process.htm?id=" + data.responseContext;
        },
        error:function(errorThrown){
            nebula.common.alert.danger("获取事件Id失败，原因："+errorThrown, 1000);
        }
    });
}
//查看发布阶段按钮名称控制
function checkPublishBtnSet(){
    switch ($("#publishEnv").text()){
        case "test1":
        case "test2":
        case "test3":$("#checkNext_btn").text("查看准生产环境");break;
        case "stage":$("#checkNext_btn").text("查看生产环境");$("#checkLast_btn").text("查看测试环境");break;
        default:$("#checkLast_btn").text("查看准生产环境");
    }
}

//验证动态验证码
function verificationCodeBtn(){
    $.ajax({
        async: false,
        type: "post",
        data: {
            "code": $("#code_momdal").val()
        },
        url: "/publish/isTotpCodeValid",
        datatype: "json",
        success: function (data) {
            if(!data.callbackMsg){
                data=JSON.parse(data);
            }
            if(data.callbackMsg=="Error") {
                nebula.common.alert.danger(data.responseContext, 1000);
                return;
            }
            nebula.common.alert.success(data.responseContext, 1000);
            $('#codeModal').modal('hide');
            $("#loading-status").show();
            $("#btn3").attr('disabled', true);
            $("#btn3").removeClass("btn-info");
            $("#cancelPublish").hide();
            $("#step3").show();
            $("#restartPublish").hide();
            nebula.publish.process.publishReal()
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.notify({
                icon: '',
                message: "很抱歉动态验证码验证失败，原因" + errorThrown
            }, {
                type: 'danger',
                timer: 1000
            });
        }
    })
}

//日期格式化
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}