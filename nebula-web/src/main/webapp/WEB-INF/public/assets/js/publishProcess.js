$(document).ready(function(){

    setInterval("btncontrol()",2000);
    //初始化隐藏所有进度条的DIV及设置按钮不可点
    $("#step0").hide();
    $("#step1").hide();
    $("#step2").hide();
    $("#step3").hide();
    //根据jQuery选择器找到需要加载ystep的容器
    //loadStep 方法可以初始化ystep
    //加载动态进度条
    $("#processbar0").loadStep({
        size: "large",
        color: "blue",
        steps: [{
            title: "获取发布war包",
        },{
            title: "分析工程",
        },{
            title: "获取源war/etc",
        },{
            title: "编辑etc",
        },{
            title: "准备完成",
        }]
    });
    $("#processbar1").loadStep({
        size: "large",
        color: "blue",
        steps: [{
            title: "创建发布目录",
        },{
            title: "复制原始文件",
        },{
            title: "发布war/etc",
        },{
            title: "预发布完成",
        }]
    });
    $("#processbar2").loadStep({
        size: "large",
        color: "blue",
        steps: [{
            title: "停止tomcat",
        },{
            title: "删除ln",
        },{
            title: "创建ln",
        },{
            title: "启动tomcat",
        },{
            title: "正式发布完成",
        }]
    });
    $("#processbar3").loadStep({
        size: "large",
        color: "blue",
        steps: [{
            title: "停止tomcat",
        },{
            title: "删除ln",
        },{
            title: "创建ln",
        },{
            title: "启动tomcat",
        },{
            title: "回滚完成",
        }]
    });
    //var btnwhich=${whichstep};
    btncontrol();
    //按钮点击事件
    $("#publishPrepare").click(function(){
        $("#publishPrepare").attr('disabled',true);
        $("#step0").show();
        $("#step1").hide();
        $("#step2").hide();
        $("#step3").hide();
    });
    $("#startPublish").click(function(){
        $("#step0").hide();
        $("#step1").show();
        $("#step2").hide();
        $("#step3").hide();
    });
    $("#startRepublish").click(function(){
        $("#step0").hide();
        $("#step1").hide();
        $("#step2").show();
        $("#step3").hide();
    });
    $("#publishBack").click(function(){
        $("#step0").hide();
        $("#step1").hide();
        $("#step2").hide();
        $("#step3").show();
    })

})


/**
 * 查询当前阶段，调整进度条
 * @param step 阶段号（0，1，2，3）
 */
function actionStep(step){
    var case0,case1,case2,case3,case4;
    var actionName=$("#action").val();
    var actionState=$("#actionState").val();
    //设置case的动作名
    if (actionName=="CREATE_PUBLISH_EVENT"&&actionState=="true"||actionName == "UPDATE_ETC"&&actionState=="true"||actionName == "PUBLISH_NEW_FILES"&&actionState=="true"||actionName == "START_TOMCAT"&&actionState=="true"){;}
    else if(actionName=="CREATE_PUBLISH_EVENT"&&actionState=="false");
    else
    switch (step)
    {
        case 0:case0="GET_PUBLISH_SVN";case1="ANALYZE_PROJECT";case2="GET_SRC_SVN";case3="UPDATE_ETC";case4="999"; $("#step0").show();$("#step1").hide();$("#step2").hide();$("#step3").hide();break;
        case 1:case0="CREATE_PUBLISH_DIR";case1="COPY_PUBLISH_OLD_FILES";case2="PUBLISH_NEW_FILES";case3="9999";case4="999"; $("#step1").show();$("#step0").hide();$("#step2").hide();$("#step3").hide();break;
        case 2:case0="STOP_TOMCAT";case1="DELETE_LN";case2="CREATE_LN";case3="START_TOMCAT";case4="999"; $("#step2").show();$("#step0").hide();$("#step1").hide();$("#step3").hide();break;
        case 3:case0="FDELETE_LN";case1="FSTOP_TOMCAT";case2="FCREATE_LN";case3="FSTART_TOMCAT";case4="FCLEAR_PUBLISH_DIR"; $("#step3").show();$("#step1").hide();$("#step2").hide();$("#step0").hide();break;
    }
            var whichstep=$("#btncontrol").val();
            var stepwhere = $("#action").val();
            var actionState=$("#actionState").val();
            if (step == 0) {
                switch (stepwhere) {
                    case case0:
                        $("#processbar" + step).setStep(1);
                        break;
                    case case1:
                        $("#processbar" + step).setStep(2);
                        break;
                    case case2:
                        $("#processbar" + step).setStep(3);
                        break;
                    case case3:
                        $("#processbar"+step).setStep(4);
                        if (actionState == "null") {
                            var etc_btn = "<input type='button' id='etc_btn' class='btn btn-info' value='编辑etc'/>" +
                                "<input type='button' id='edit_success' style='margin-left: 30px' class='btn btn-info' value='编辑完成'/>";
                            $("#etc_btns").html(etc_btn);
                            $("#etc_btn").click(function () {
                                window.open('/fileEdit.html');
                            })
                            $("#edit_success").click(function () {
                                var ms = confirm("确认完成编辑么（确定后将无法再编辑）？");
                                if (ms == true) {
                                    $("#etc_btns").empty();
                                }
                            });
                        }
                        if (actionState == "true") {
                            $("#processbar"+step).setStep(5);
                            $("#step"+step).hide();
                            $("#startPublish").attr('disabled', false);
                            actionStep(step-0+1);
                        }
                        break;
                }
            }
            else{
                switch (stepwhere) {
                    case case0:
                        $("#processbar" + step).setStep(1);
                        break;
                    case case1:
                        $("#processbar" + step).setStep(2);
                        break;
                    case case2:
                        $("#processbar" + step).setStep(3);
                        break;
                    case case3:
                        $("#processbar" + step).setStep(4);
                        break;
                    case case4:
                        if (actionState == "true") {
                            $("#step"+step).hide();
                            actionStep(step + 1);
                        }
                        break;
                }
            }
    //$("#step0").show();$("#step1").hide();$("#step2").hide();$("#step3").hide();
}

//根据当前动作所在组，当前动作及动作的完成情况判断按钮是否可点
function btncontrol(){
    $("#publishPrepare").attr('disabled',true);
    $("#startPublish").attr('disabled',true);
    $("#startRepublish").attr('disabled',true);
    $("#publishBack").attr('disabled',true);
    //发送请求得到当前动作，完成情况
    $.ajax({
        data: {
            eventId: $("#eventId").val()
        },
        url: "/publishProcessStep.htm",
        datetype: "json",
        success: function (data) {
            var stepwhere = data.responseContext.actionName;
            var actionState = data.responseContext.actionState;
            var whichstep = data.responseContext.whichstep;
            $("#btncontrol").val(whichstep);
            $("#action").val(stepwhere);
            $("#actionState").val(actionState);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("很抱歉，获取进度失败，原因：" + errorThrown);
        }
    });
    var i=$("#btncontrol").val();
    var actionName=$("#action").val();
    var actionState=$("#actionState").val();
    if (actionName == "UPDATE_ETC"&&actionState=="true"||actionName == "PUBLISH_NEW_FILES"&&actionState=="true"||actionName == "START_TOMCAT"&&actionState=="true")
        i=i-0+1+"";
    switch (i) {
        case "0":
            if (actionName == "CREATE_PUBLISH_EVENT"&&actionState=="true") {
                $("#publishPrepare").attr('disabled', false);
            } else actionStep(0);
            break;
        case "1":
            if (actionName == "UPDATE_ETC"&&actionState=="true") {
                $("#startPublish").attr('disabled', false);$("#step0").hide();
            }
            else actionStep(1);
            break;
        case "2":
            if (actionName == "PUBLISH_NEW_FILES"&&actionState=="true"){
                $("#startRepublish").attr('disabled', false);$("#step1").hide();
            }
            else actionStep(2);
            break;
        case "3":
            if (actionName == "START_TOMCAT"&&actionState=="true") {
                $("#publishBack").attr('disabled', false);$("#step2").hide();
            }
            else actionStep(3);
            break;
    }
}

//function GetStepPolling(){
//    //var timer;//声明一个定时器
//    //timer = window.setInterval("add()",500);
//    alert("查询查询");
//}