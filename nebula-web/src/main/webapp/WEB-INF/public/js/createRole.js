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
                    message: "��ӽ�ɫ�ɹ�"

                },{
                    type: 'info',
                    timer: 1000
                });
            },
            error: function(request) {
                $.notify({
                    icon: '',
                    message: "��ӽ�ɫʧ��"+ errorThrown

                },{
                    type: 'info',
                    timer: 1000
                });
            }
        });
    })
})