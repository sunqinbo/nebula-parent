$(function(){
    $("#check_btn").click(function(){
        $.ajax({
            type:"POST",
            url: "/publish/list/describeRefreshTasks",
            dataType:"json",
            success: function (data) {
                if(!data.callbackMsg){
                    data=JSON.parse(data);
                }
                if(data.callbackMsg=="Error") {
                    nebula.common.alert.danger(data.responseContext, 1000);
                    return;
                }
                if(data.callbackMsg=="Success") {
                    var modal_tbString = ""
                    for (var i = 0, modallen = data.responseContext.tasks.length; i < modallen; i++) {
                        var task = data.responseContext.tasks[i];
                        modal_tbString += "<tr><td>" + task.taskId + "</td>" +
                            "<td>" + task.objectPath + "</td>" +
                            "<td>" + task.status + "</td>" +
                            "<td>" + task.creationTime + "</td>" +
                            "</tr>"
                    }
                    $("#modal_tb").html(modal_tbString);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                nebula.common.alert.danger("请求CDN列表失败，原因" + errorThrown, 1000);
            }
        })
    });
    $('#checkmodal').appendTo("body");
    $("#check_btn").click(function(){
        $('#checkmodal').modal('show');
    });
    $("#submit_btn").click(function(){
        $.ajax({
            type:"POST",
            url: "/publish/add/refreshObjectCaches",
            data:{
                objectPath:$("#cdnUrl_select").find("option:selected").text(),
                objectType:"Directory",
            },
            dataType:"json",
            success: function (data) {
                if(!data.callbackMsg){
                    data=JSON.parse(data);
                }
                if(data.callbackMsg=="Error") {
                    nebula.common.alert.danger(data.responseContext, 1000);
                    return;
                }
                nebula.common.alert.success("刷新CDN成功", 1000);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                nebula.common.alert.danger("刷新CDN失败，原因" + errorThrown, 1000);
            }
        })
    });
})