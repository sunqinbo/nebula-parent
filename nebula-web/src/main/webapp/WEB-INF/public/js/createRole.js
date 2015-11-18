$(function(){
    $("#submit").click(function(){
        $.ajax({
            type: "POST",
            url:"/userRole/insertAclUserRole.htm",
            data:$('#insertform').serialize(),
            async: false,
            success: function(data) {
                $.notify({
                    icon: '',
                    message: "添加角色成功"

                },{
                    type: 'info',
                    timer: 1000
                });
            },
            error: function(request) {
                $.notify({
                    icon: '',
                    message: "添加角色失败"+ errorThrown

                },{
                    type: 'info',
                    timer: 1000
                });
            }
        });
    })
})