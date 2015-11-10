$(document).ready(function(){
    //初始化隐藏所有进度条的DIV及设置按钮不可点
    $("#step0").hide();
    $("#step1").hide();
    $("#step2").hide();
    $("#step3").hide();
    $("#publishPrepare").attr('disabled',true);
    $("#startPublish").attr('disabled',true);
    $("#startRepublish").attr('disabled',true);
    $("#publishBack").attr('disabled',true);
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

    //根据当前动作所在组，当前动作及动作的完成情况判断按钮是否可点
    function btncontrol(){
        var i=$("#btncontrol").val();
        var action=$("#action").val();
        var actionState=$("#actionState").val();
        if(actionState=="true") {
            switch (i) {
                case "0":
                    if (action == "CREATE_PUBLISH_EVENT") {
                        $("#publishPrepare").attr('disabled', false);
                    } else actionStep();
                    break;
                case "1":
                    if (action == "UPDATE_ETC")
                        $("#startPublish").attr('disabled', false);
                    else $("#startPublish").trigger("click");
                    break;
                case "2":
                    if (action == "PUBLISH_NEW_FILES")
                        $("#startRepublish").attr('disabled', false);
                    else $("#startRepublish").trigger("click");
                    break;
                case "3":
                    if (action == "START_TOMCAT")
                        $("#publishBack").attr('disabled', false);
                    else $("#publishBack").trigger("click");
                    break;
            }
        }
    }
    btncontrol();
    //页面加载时判断进行到了哪一步
    //function whichAction(){
    //    $.ajax({
    //        data: {
    //            eventId: $("#eventId").val()
    //        },
    //        url: "/whichStep.htm",
    //        datetype: "json",
    //        success: function (data) {
    //            var stepwhere = data.responseContext;
    //            switch (stepwhere) {
    //                case "GET_PUBLISH_SVN":
    //                    $("#processbar0").setStep(1);
    //                    break;
    //                case "ANALYZE_PROJECT":
    //                    $("#processbar0").setStep(2);
    //                    break;
    //                case "GET_SRC_SVN":
    //                    $("#processbar0").setStep(3);
    //                    break;
    //                case "UPDATE_ETC":
    //                    $("#processbar0").setStep(4);
    //                    break;
    //            }
    //        },
    //        error: function (XMLHttpRequest, textStatus, errorThrown) {
    //            alert("很抱歉，获取进度失败，原因：" + errorThrown);
    //        }
    //    })
    //}
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
    //var timer;//声明一个定时器
    //timer = window.setInterval("add()",500);
    ////$(".ystep4").setStep(1);

    //根据事件ID查询当前动作，并设置进度条进度
    function actionStep(){
        $.ajax({
            data: {
                eventId:$("#eventId").val()
            },
            url:"/publishProcessStep.htm",
            datetype:"json",
            success:function(data){
                var stepwhere=data.responseContext;
                var actionState=$("#actionState").val();
                switch (stepwhere){
                    case "GET_PUBLISH_SVN":$("#processbar0").setStep(1);break;
                    case "ANALYZE_PROJECT":$("#processbar0").setStep(2);break;
                    case "GET_SRC_SVN":$("#processbar0").setStep(3);break;
                    case "UPDATE_ETC":if(actionState=="false"){$("#processbar0").setStep(4);
                        var etc_btn="<div id='etc_btns'><input type='button' id='etc_btn' class='btn btn-info' value='编辑etc'/>"+
                            "<input type='button' id='edit_success' style='margin-left: 30px' class='btn btn-info' value='编辑完成'/></div>";
                        $("#etc_edit").append(etc_btn);
                        $("#etc_btn").click(function(){
                            window.open('/fileEdit.html');
                        })
                        $("#edit_success").click(function(){
                            var ms=confirm("确认完成编辑么（确定后将无法再编辑）？");
                            if(ms==true){
                                $("#etc_btns").empty();
                            }
                        });}if(actionState=="true"){$("#processbar0").setStep(5);$("#startPublish").attr('disabled',false);}
                        break;
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("很抱歉，获取进度失败，原因：" + errorThrown);
            }
        });
        $("#publishPrepare").attr('disabled',true);
        $("#step0").show();
        $("#step1").hide();
        $("#step2").hide();
        $("#step3").hide();
    }
})