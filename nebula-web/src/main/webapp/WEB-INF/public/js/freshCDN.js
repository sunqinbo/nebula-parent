$(function(){
    $("#check_btn").click(function(){
        $.ajax({
            type:"POST",
            url: "/publish/list/describeRefreshTasks",
            dataType:"json",
            success: function (data) {
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
                else{
                    $.notify({
                        icon: '',
                        message: data.responseContext
                    }, {
                        type: 'danger',
                        timer: 1000
                    });
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.notify({
                    icon: '',
                    message: "请求CDN列表失败，原因" + errorThrown
                }, {
                    type: 'danger',
                    timer: 1000
                });
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
                if(data.callbackMsg=="Error"){
                    $.notify({
                        icon: '',
                        message: data.responseContext
                    }, {
                        type: 'danger',
                        timer: 1000
                    });
                }
                else {
                    $.notify({
                        icon: '',
                        message: "刷新CDN成功"
                    }, {
                        type: 'info',
                        timer: 1000
                    });
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.notify({
                    icon: '',
                    message: "刷新CDN失败，原因" + errorThrown
                }, {
                    type: 'danger',
                    timer: 1000
                });
            }
        })
    });
})