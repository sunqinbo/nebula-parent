$(function(){
    $("#permissionType").change(function(){
        if($("#permissionType option:selected").text()=="按钮"){
            $.ajax({
                type: "POST",
                url:"/permission/getList/noPid",
                async: false,
                success: function(data) {
                    var selectString=""
                    for(var i= 0,len=data.length;i<len;i++){
                        var perimission=data[i];
                        selectString=selectString+"<option value='"+perimission.id+"'>"+perimission.permissionCname+"</option>";
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
    })
    $("#submit").click(function(){
        $.ajax({
            type: "POST",
            url:"/permission/add",
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