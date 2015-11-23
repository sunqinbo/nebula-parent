$(function(){
    showSelect2();

    $("#save").hide();
    //为编辑页面时
    if($("#isEdit").val()!=""){
        $.ajax({
            async: false,
            type:"post",
            data:{"empId":$("#empId").val()},
            url:"/role/getAclUserWithRolesByEmpId",
            datatype:"json",
            success: function (data) {
                $("#username").val(data["username"]);
                $("#mobilePhone").val(data["mobilePhone"]);
                $("#weixinAcc").val(data["weixinAcc"]);
                $("#qqAcc").val(data["qqAcc"]);
                $("#email").val(data["email"]);
                $("#nickname").val(data["nickname"]);
                $("#deptName").val(data["deptName"]);
                $("#jobTitle").val(data["jobTitle"]);
                $("#empId").val(data["empId"]);
                $("#supervisorEmpId").val(data["supervisorEmpId"]);
                $("#isEnable").val(data["isEnable"]);
                $("#password").val(data["password"]);
                if(data["isEnable"]==1) {
                    $("#enableRadio").attr("checked", true);
                }
                else
                    $("#unenableRadio").attr("checked", true);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.notify({
                    icon: '',
                    message: "很抱歉载入角色信息失败，原因"+ errorThrown

                },{
                    type: 'info',
                    timer: 1000
                });
            }
        });
        $("#submit").hide();
        $("#save").show();
    }

    //提交按钮
    $("#submit").click(function(){
        btnClick(true);
    });
})

//角色信息显示select2
function showSelect2(){
    var selectData;
    $.ajax({
        async: false,
        type:"post",
        data:{
            "id":$("#empId").val()
        },
        url:"/role/selectRole",
        datatype:"json",
        success: function (data) {
            selectData=data;
            $(".js-example-tags").select2({data:selectData},{
                tags: true
            })
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.notify({
                icon: '',
                message: "很抱歉载入角色失败，原因"+ errorThrown

            },{
                type: 'info',
                timer: 1000
            });
        }
    });
}


//新增，修改按钮点击事件
function btnClick(isCreate) {

    var isEnable = $('#isEnable input[name="isEnable"]:checked ').val();
    var username = $("#username").val();
    var mobilePhone = $("#mobilePhone").val();
    var weixinAcc = $("#weixinAcc").val();
    var qqAcc = $("#qqAcc").val();
    var email = $("#email").val();
    var nickname = $("#nickname").val();
    var deptName = $("#deptName").val();
    var jobTitle = $("#jobTitle").val();
    var empId = $("#empId").val();
    var supervisorEmpId = $("#supervisorEmpId").val();
    var password = $("#password").val();
    var url, tips;
    if (isCreate) {
        url = "/user/add";
        tips = "新增";
    }
    else {
        url = "/user/update";
        tips = "修改";
    }
    var roleList=$("#roleList").find("option:selected");
    var roleIds=[];
    for(var i=0;i<roleList.length;i++){
        roleIds.push(roleList[i].value);
    }
    $.ajax({
        type: "post",
        url: url,
        data: {
            "roleIds": roleIds,
            "isEnable": isEnable,
            "username": username,
            "weixinAcc": weixinAcc,
            "qqAcc": qqAcc,
            "mobilePhone":mobilePhone,
            "email": email,
            "nickname": nickname,
            "deptName": deptName,
            "jobTitle": jobTitle,
            "empId": empId,
            "supervisorEmpId": supervisorEmpId,
            "password": password
        },
        datatype: "json",
        success: function (data) {
            $.notify({
                icon: '',
                message: tips + "用户成功"

            }, {
                type: 'info',
                timer: 1000
            });
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.notify({
                icon: '',
                message: "很抱歉" + tips + "用户失败，原因" + errorThrown

            }, {
                type: 'info',
                timer: 1000
            });
        }
    })
}