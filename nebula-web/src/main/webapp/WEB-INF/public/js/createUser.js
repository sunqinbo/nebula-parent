$(function(){
    showSelect2();
    $("#submit").click(function(){
    });
})

//角色信息显示select2
function showSelect2(){
    var selectData;
    $.ajax({
        async: false,
        type:"post",
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
        url = "/role/insertAclRole.htm";
        tips = "新增";
    }
    else {
        url = "/role/updateAclRole.htm";
        tips = "修改";
    }
    var selected=$("#roleList").find("option:selected");
    var roleList=[];
    for(var i=0;i<selected.length;i++){
        roleList.push(abc[i].value);
    }
    $.ajax({
        type: "post",
        url: url,
        data: {
            //"id": $("#roleId").val(),
            "isEnable": isEnable,
            "username": username,
            "weixinAcc": weixinAcc,
            "qqAcc": qqAcc,
            "email": email,
            "nickname": nickname,
            "deptName": deptName,
            "jobTitle": jobTitle,
            "empId": empId,
            "supervisorEmpId": supervisorEmpId,
            "password": password,
            "permissionIds": permissionIds,
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