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
    $("#submit").click(function(){
        if($("#insertform").valid()) {
            $.ajax({
                type: "POST",
                url: "/permission/add",
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
    })
})