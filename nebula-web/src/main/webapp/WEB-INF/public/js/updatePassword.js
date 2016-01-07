$(function(){
    $("#passwordUpdateForm").validate({
        rules:{
            password:{required:true},
            newPassword:{required:true},
            repeatPassword:{
                required:true,
                equalTo:"#newPassword",
            },
        },
        messages:{
            password:{required:"密码不能为空"},
            newPassword:{required:"新密码不能为空"},
            repeatPassword:{required:"确认密码不能为空",
                equalTo: "两次输入的密码不一致",},
        }
    });
    $("#save_btn").click(function(){
        if($("#passwordUpdateForm").valid()) {
            $.ajax({
                type: "POST",
                url: "/user/update/myPassword",
                data: $('#passwordUpdateForm').serialize(),
                async: false,
                success: function (data) {
                    $.notify({
                        icon: '',
                        message: data.responseContext

                    }, {
                        type: 'info',
                        timer: 1000
                    });
                },
                error: function (request) {
                    $.notify({
                        icon: '',
                        message: "修改密码失败" + errorThrown

                    }, {
                        type: 'info',
                        timer: 1000
                    });
                }
            });
        }
    })
})