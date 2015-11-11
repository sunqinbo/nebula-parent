$(document).ready(function(){

    //初始化隐藏所有进度条的DIV及设置按钮不可点
    $("#step1").hide();
    $("#step2").hide();
    $("#step3").hide();
    $("#step4").hide();
    btnUnclick();
    //根据jQuery选择器找到需要加载ystep的容器
    //loadStep 方法可以初始化ystep
    //加载动态进度条
    $("#processbar1").loadStep({
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
    $("#processbar2").loadStep({
        size: "large",
        color: "blue",
        steps: [{
            title: "创建发布目录",
        },{
            title: "拷贝原etc文件",
        },{
            title: "拷贝原war文件",
        },{
            title: "发布新etc文件",
        },{
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
        },{
            title: "更改文件指向",
        },{
            title: "启动tomcat",
        },{
            title: "正式发布完成",
        }]
    });
    $("#processbar4").loadStep({
        size: "large",
        color: "blue",
        steps: [{
            title: "停止tomcat",
        },{
            title: "删除Tomcat原始链",
        },{
            title: "创建Tomcat新链接",
        },{
            title: "启动tomcat",
        },{
            title: "清除临时发布目录",
        },{
            title: "回滚完成",
        }]
    });
    //页面初次加载进度条控制
    Initialization();
    setInterval("Initialization()",2000);
    //var btnwhich=${whichstep};
    //btncontrol();
    //按钮点击事件
    $("#btn1").click(function(){
        $("#btn1").attr('disabled',true);
        $("#step1").show();
        $("#step2").hide();
        $("#step3").hide();
        $("#step4").hide();
    });
    $("#btn2").click(function(){
        $("#btn2").attr('disabled',true);
        $("#step1").hide();
        $("#step2").show();
        $("#step3").hide();
        $("#step4").hide();
    });
    $("#btn3").click(function(){
        $("#btn3").attr('disabled',true);
        $("#step1").hide();
        $("#step2").hide();
        $("#step3").show();
        $("#step4").hide();
    });
    $("#btn4").click(function(){
        $("#btn4").attr('disabled',true);
        $("#step1").hide();
        $("#step2").hide();
        $("#step3").hide();
        $("#step4").show();
    })

})

//页面加载控制进度条
function Initialization(){
    var whichStep,actionGroup,actionState;
    //获取动作组，动作进度点，动作状态
    $.ajax({
        data: {
            eventId: $("#eventId").val()
        },
        url: "/publishProcessStep.htm",
        datetype: "json",
        success: function (data) {
            whichStep = data.responseContext.whichStep;
            actionGroup = data.responseContext.actionGroup;
            actionState = data.responseContext.actionState+"";
            //设置进度条长度
            var lastStep;
            switch (actionGroup){
                case 1:lastStep=4;break;
                case 2:lastStep=5;break;
                case 3:lastStep=3;break;
                case 4:lastStep=5;break;
            }
            //当动作为创建发布事件且成功时，发布准备可点
            if(whichStep==0)
            {
                if(actionState=="true")
                    $("#btn1").attr('disabled', false);
                return;
            }
            //动作为编辑ETC完成 下一步按钮可点
            if(whichStep==lastStep&&actionState=="true"){
                actionGroup=actionGroup-1+2;
                for(var i=1;i<=4;i++) {
                    if(i==actionGroup) {
                        $("#btn" + i).attr('disabled', false);
                        $("#step"+(i-1)).hide();
                    }
                    else {
                        $("#btn" + i).attr('disabled', true);
                    }
                }return;
            }
            //动作为ect开始时
            if (actionGroup==1&&whichStep==4&&(actionState == ""||actionState=="null")) {
                //添加编辑按钮
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
            btnUnclick();
            //控制进度条显示
            for(var i=1;i<=4;i++){
                if(i==actionGroup)
                {
                    $("#step"+i).show();
                }
                else
                    $("#step"+i).hide();
            }
            //设置进度条进度
            $("#processbar" + actionGroup).setStep(whichStep);
            if(actionState=="false"){
                var false_btn="<Button type='button' class='btn btn-info'>重试</Button>"
                $("#false_btn").html(false_btn);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //alert("很抱歉，获取进度失败，原因：" + errorThrown);
            $.notify({
                icon: '',
                message: "很抱歉，获取进度失败，原因"+ errorThrown

            },{
                type: 'info',
                timer: 1000
            });
        }
    });
    //var whichStep=$("#whichStep").val();
    //var actionGroup=$("#actionGroup").val()+"";
    //var actionState=$("#actionState").val();
}


//按钮设为不可点
function btnUnclick(){
    $("#btn1").attr('disabled',true);
    $("#btn2").attr('disabled',true);
    $("#btn3").attr('disabled',true);
    $("#btn4").attr('disabled',true);
}
