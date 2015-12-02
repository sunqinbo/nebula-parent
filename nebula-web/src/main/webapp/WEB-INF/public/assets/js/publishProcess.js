$(document).ready(function(){

    //审批按钮
    $("#approval_btn").click(function(){
        approvalBtn();
        window.location.reload();
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
                url: "/publish/updateEtcEnd.htm",
                type: "post",
                data: {"id": $("#eventId").val()},
                success: function (jsonData) {
                    if (jsonData.callbackMsg.match(/Success/)) {
                        $.notify({
                            icon: '',
                            message: "保存成功"

                        }, {
                            type: 'info',
                            timer: 1000
                        });
                    }
                }
            });
            $("#etc_btns").hide();
            //$("#etc_btns").empty();
        }
    });

    //初始化隐藏所有进度条的DIV及设置按钮不可点
    $("#step1").hide();
    $("#step2").hide();
    $("#step3").hide();
    $("#step4").hide();
    $("#step5").hide();
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
    //页面初次加载进度条控制
    Initialization();
    setInterval("Initialization()", 5000);
    //按钮点击事件
    $("#btn1").click(function () {
        $("#loading-status").show();
        $("#btn1").attr('disabled', true);
        $("#btn1").removeClass("btn-info");
        $("#step1").show();
    });
    $("#btn2").click(function () {
        $("#loading-status").show();
        $("#btn2").attr('disabled', true);
        $("#btn2").removeClass("btn-info");
        $("#step2").show();
    });
    $("#btn3").click(function () {
        $("#loading-status").show();
        $("#btn3").attr('disabled', true);
        $("#btn3").removeClass("btn-info");
        $("#step3").show();
        $("#restartPublish").hide();
    });
    $("#btn4").click(function () {
        $("#loading-status").show();
        $("#btn4").attr('disabled', true);
        $("#btn4").removeClass("btn-info");
        $("#btn_ConfirmResult").attr('disabled', true);
        $("#btn_ConfirmResult").removeClass("btn-info");
        $("#step4").show();
    })
    $("#btn_ConfirmResult").click(function () {
        $("#loading-status").show();
        $("#btn_ConfirmResult").attr('disabled', true);
        $("#btn_ConfirmResult").removeClass("btn-info");
        $("#btn4").attr('disabled', true);
        $("#btn4").removeClass("btn-info");
        $("#step5").show();
    })

})

//页面加载控制进度条
function Initialization() {
    var whichStep, actionGroup, actionState;
    //获取动作组，动作进度点，动作状态
    $.ajax({
        data: {
            eventId: $("#eventId").val()
        },
        url: "/publish/publishProcessStep",
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
                if (HostList[i]["passPublishHostName"] != null)
                    passPublishHostName = HostList[i]["passPublishHostName"];
                if (HostList[i]["passPublishHostName"] != null)
                    passPublishHostIp = HostList[i]["passPublishHostIp"];
                if (HostList[i]["actionName"] != null)
                    actionName = HostList[i]["actionName"];
                if (HostList[i]["isSuccessAction"] != null)
                    isSuccessAction = HostList[i]["isSuccessAction"];
                if (HostList[i]["actionResult"] != null)
                    actionResult = HostList[i]["actionResult"];
                tbString = tbString + "<tr><td>" + passPublishHostName + "</td><td>" + passPublishHostIp + "</td><td>" +
                    actionName + "</td><td>" + isSuccessAction + "</td><td>" + actionResult + "</td></tr>";
            }
            $("#hostInfo").html(tbString);
            //进度条相关
            whichStep = data.responseContext.whichStep;
            actionGroup = data.responseContext.actionGroup;
            actionState = data.responseContext.actionState + "";
            var lastGroup=data.responseContext.lastGroup;
            btnUnclick();
            //发布完成，不管成功或失败
            //if(actionGroup>=5){
            //    //if(actionGroup==5) {
            //    //    var btn_text;
            //    //    if ($("#publishEnv").html() == "test") {
            //    //        btn_text = "准生产";
            //    //    }
            //    //    if ($("#publishEnv").html() == "stage") {
            //    //        btn_text = "生产";
            //    //    }
            //    //    $("#nextPublish").text("进入" + btn_text).show();
            //    //    $("#nextPublish").click(function () {
            //    //        nextPublish(btn_text);
            //    //    });
            //    //}
            //    //轮询过慢
            //    if(lastGroup==4) {
            //        $("#processbar4").setStep(4);
            //        $("#step4").show();
            //    }
            //    if(lastGroup==5) {
            //        $("#processbar5").setStep(3);
            //        $("#step5").show();
            //    }
            //    //return;
            //}
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
                    break;
                case 2:
                    lastStep = 5;
                    break;
                case 3:
                    lastStep = 3;
                    break;
                case 4:
                    lastStep = 3;
                    break;
                case 5:
                    lastStep = 2;
                    break;
                case 6:
                    lastStep = 1;
                    break;
            }

            //动作成功执行 隐藏重试按钮,失败显示重试按钮并显示错误信息
            if (actionState == "false") {
                //var false_btn = "<Button type='button' class='btn btn-info' onclick='nebula.publish.process.publishContinue()'>重试</Button>"
                //$("#false_btn").html(false_btn);
                $("#false_btn").show();
                $("#errorMsgDiv").html(data.responseContext.errorMsg);
                $("#errorMsgDiv").show();
            }
            else {
                $("#false_btn").html("");
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
                        $("#restartPublish").hide();
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
                        return;
                    case 7: if(lastGroup==5) {
                        var btn_text;
                        if ($("#publishEnv").html() == "test") {
                            btn_text = "准生产";
                        }
                        if ($("#publishEnv").html() == "stage") {
                            btn_text = "生产";
                        }
                        $("#nextPublish").text("进入" + btn_text).show();
                        $("#nextPublish").click(function () {
                            nextPublish(btn_text);
                        });
                        if(lastGroup==5) {
                            $("#processbar5").setStep(3);
                            $("#step5").show();
                        }
                    }
                        if(lastGroup==4){
                            $("#processbar4").setStep(4);
                            $("#step4").show();
                        }
                        return;
                    default :
                        if (actionGroup < 5) {
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
                        $("#btn5").removeClass("btn-info");
                        $("#processbar" + whichshow).setStep(whichStep);
                        return;
                }
                //if(actionGroup>=4){
                //    whichStep=whichStep+1;
                //}
                //else {
                //    actionGroup = actionGroup - 1 + 2;
                //    if(actionGroup==3){
                //        $("#restartPublish").hide();
                //    }
                //    else if (actionGroup == 4) {
                //        $("#btn_ConfirmResult").attr('disabled', false);
                //        $("#btn_ConfirmResult").addClass("btn-info");
                //        $("#btn4").attr('disabled', false);
                //        $("#btn4").addClass("btn-info");
                //        $("#step" + (3)).hide();
                //    }
                //    else {
                //        for (var i = 1; i < 4; i++) {
                //            if (i == actionGroup) {
                //                $("#btn" + i).attr('disabled', false);
                //                $("#btn" + i).addClass("btn-info");
                //                $("#step" + (i - 1)).hide();
                //            }
                //            else {
                //                $("#btn" + i).attr('disabled', true);
                //                $("#btn4").removeClass("btn-info");
                //            }
                //        }
                //    }
                //    return;
                //}
            }
            //动作为ect开始时
            if (actionGroup == 1 && whichStep == 4 && (actionState == "" || actionState == "null")) {
                //添加编辑按钮
                $("#restartPublish").show();

                //var etc_btn = "<input type='button' id='etc_btn' class='btn btn-info' value='编辑etc'/>" +
                //    "<input type='button' id='edit_success' style='margin-left: 30px' class='btn btn-info' value='编辑完成'/>";
                //$("#etc_btns").html(etc_btn);
                $("#etc_btns").show();
            }
            else {
                $("#restartPublish").hide()
            }
            //btnUnclick();
            //控制进度条显示
            for (var i = 1; i <= 5; i++) {
                if (i == actionGroup) {
                    $("#step" + i).show();
                }
                else
                    $("#step" + i).hide();
            }
            //设置进度条进度
            $("#processbar" + actionGroup).setStep(whichStep);
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


//按钮设为不可点
function btnUnclick() {
    $("#btn1").attr('disabled', true);
    $("#btn2").attr('disabled', true);
    $("#btn3").attr('disabled', true);
    $("#btn4").attr('disabled', true);
    $("#btn_ConfirmResult").attr('disabled', true);
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
            location.href = "/publish/publishProcess.htm?id=" + data.responseContext;
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
            $.notify({
                icon: '',
                message: "审批完成"
            }, {
                type: 'info',
                timer: 1000
            });
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