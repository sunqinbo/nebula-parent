$(function(){
    $("#insertform").validate({
        rules:{
            permissionName:{required:true},
            permissionCname:{required:true},
            permission:{required:true},
            permissionDesc:{required:true},
            permissionType:{required:true},
            isEnable:{required:true},
        },
        messages:{
            permissionName:{required:"权限名称不能为空"},
            permissionCname:{required:"权限中文名称不能为空"},
            permission:{required:"权限不能为空"},
            permissionDesc:{required:"权限描述不能为空"},
            permissionType:{required:"权限类型不能为空"},
            isEnable:{required:"请选择是否启用"}
        }
    });

    //为编辑页面时
    $("#save").hide();
    if($("#isEdit").val()!=""){
        $.ajax({
            async: false,
            type:"post",
            data:{"permissionId":$("#permissionId").val()},
            url:"/permission/get/permissionId",
            datatype:"json",
            success: function (data) {
                $("#permissionName").val(data["permissionName"]);
                $("#permissionCname").val(data["permissionCname"]);
                $("#permissionDesc").val(data["permissionDesc"]);
                $("#permission").val(data["permission"]);
                $("#permissionType").val(data["permissionType"]);
                selectControl();
                $("#pidSelect option").each(function(){
                   if($(this).val()==data["pid"]) {
                       $("#pidSelect").val(data["pid"]);
                   }
                });
                $("#url").val(data["url"]);
                if(data["isEnable"]==1) {
                    $("#enableRadio").attr("checked", true);
                }
                else
                    $("#unenableRadio").attr("checked", true);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.notify({
                    icon: '',
                    message: "很抱歉载入权限信息失败，原因"+ errorThrown

                },{
                    type: 'info',
                    timer: 1000
                });
            }
        });
        $("#submit").hide();
        $("#save").show();
    }

    $("#permissionType").change(function(){
        selectControl();
    });


    //提交按钮
    $("#submit").click(function(){
        if($("#insertform").valid()) {
            btnClick(true)
        }
    })

    //保存按钮
    $("#save").click(function(){
        if($("#insertform").valid()) {
            btnClick(false);
        }
    });
})

function btnClick(isCreate){
    var url,tips;
    if(isCreate){
        url="/permission/add";
        tips="新增";
    }
    else{
        url="/permission/update";
        tips="修改";
    }

    $.ajax({
        type: "POST",
        url: url,
        data: $('#insertform').serialize(),
        async: false,
        success: function (data) {
            $.notify({
                icon: '',
                message: "添加权限成功"

            }, {
                type: 'info',
                timer: 1000
            });
        },
        error: function (request) {
            $.notify({
                icon: '',
                message: "添加权限失败" + errorThrown

            }, {
                type: 'info',
                timer: 1000
            });
        }
    });
}

//下拉框控制
function selectControl(){
    if($("#permissionType option:selected").text()=="按钮"){
        $.ajax({
            type: "POST",
            url:"/permission/getList/noPid",
            async: false,
            success: function(data) {
                var selectString=""
                for(var i= 0,len=data.length;i<len;i++){
                    var perimission=data[i];
                    if(perimission.id!=$("#permissionId").val()) {
                        selectString = selectString + "<option value='" + perimission.id + "'>" + perimission.permissionCname + "</option>";
                    }
                }
                $("#pidSelect").html(selectString);
            },
            error: function(request) {
                $.notify({
                    icon: '',
                    message: "查询页面失败"+ errorThrown

                },{
                    type: 'info',
                    timer: 1000
                });
            }
        });
        $("#pidSelectDiv").show();
    }
    else{
        $("#pidSelect").empty();
        $("#pidSelectDiv").hide();
    }
}