$(function(){
    $("#submit").click(function(){
        $.ajax({
            type: "POST",
            url:"/permission/insertAclPermission.htm",
            data:$('#insertform').serialize(),
            async: false,
            success: function(data) {
                $.notify({
                    icon: '',
                    message: "添加权限成功"

                },{
                    type: 'info',
                    timer: 1000
                });
            },
            error: function(request) {
                $.notify({
                    icon: '',
                    message: "添加权限失败"+ errorThrown

                },{
                    type: 'info',
                    timer: 1000
                });
            }
        });
    })
})