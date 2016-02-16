$(document).ready(function(){
    //关闭当前页面
    $("#btn_close").click(function(){
        window.close();
    });

    /** 解决弹窗的问题，先将modal移到body层 */
    $('#dirmodal').appendTo("body");
    $('#filemodal').appendTo("body");
    $("#newDir_btn").click(function(){
        $('#dirmodal').modal('show');
    });
    $("#newFile_btn").click(function(){
        $('#filemodal').modal('show');
    });

    //新增目录，新增文件操作
    $("#dirSubmit_btn").click(function(){
        newDirOrFile("dir");
        //$.ajax({
        //    type:"POST",
        //    async:false,
        //    url:"/etc_edit/newFile",
        //    data:{
        //        type:"dir",
        //        fileName:$("#addDir_input").val()+"\\"+$("#dirName").val()
        //    },
        //    success: function (data) {
        //        $('#dirmodal').modal('hide');
        //        var type="success";
        //        if(data.callbackMsg=="Error"){
        //            type="danger"
        //        }
        //        $.notify({
        //            icon: '',
        //            message: data.responseContext
        //
        //        }, {
        //            type: type,
        //            timer: 1000
        //        });
        //        $("#tree-div").jstree(true).refresh_node("F:\\121D");
        //    },
        //    error: function (XMLHttpRequest, textStatus, errorThrown) {
        //        nebula.common.alert.danger("很抱歉，创建失败，原因"+ errorThrown, 1000);
        //    }
        //});
    });
    $("#fileSubmit_btn").click(function(){
        newDirOrFile("file");
    })

    //生成jstree
    $('#tree-div')
        .on("changed.jstree", function (e, data) {
            if(data.selected.length) {
                var selectedObj = data.instance.get_node(data.selected[0]);
                var textArea = "<textarea id='fileView' style='resize: none;background-color: #C0C0C0' disabled=true rows='16' cols='80'></textarea>";
                var btn = "<button id='edit_btn' type='button' class='btn btn-info'>编辑etc</button>" +
                    "<button id='save_btn' type='button' style='margin-left: 30px' class='btn btn-info'>保存etc</button>";
                $("#textputer").empty().append(textArea);
                $("#btnputer").empty().append(btn);
                var path = selectedObj.id;
                var fileOrdir=path.substr(path.length-1);
                path=path.substr(0,path.length-1);
                if(fileOrdir=="D") {
                    $("#textputer").empty();
                    $("#btnputer").empty();
                    $("#addFile_div").show();
                    $("#filetip_lb").text("将在目录"+path+"下创建:");
                    $("#dirtip_lb").text("将在目录"+path+"下创建:");
                    $("#addDir_input").val(path);
                    $("#nodefresh_input").val(selectedObj.id);
                    return;
                }
                $("#addFile_div").hide();
                $.ajax({
                    type: "post",
                    url: "/etc_edit/filePath",
                    data: {
                        "path": path
                    },
                    datetype: "json",
                    success: function (filedata) {
                        var context = filedata.responseContext[0];
                        for (var i = 1; i < filedata.responseContext.length; i++)
                            context += "\r\n" + filedata.responseContext[i];
                        $("#fileView").val(context);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        $.notify({
                            icon: '',
                            message: "很抱歉，文件打开失败，原因"+ errorThrown

                        },{
                            type: 'info',
                            timer: 1000
                        });
                    }
                })
                //文本设置可编辑
                $("#edit_btn").click(function(){
                    $("#fileView").attr("disabled",false);
                    $("#fileView").css("background-color","transparent");
                });
                //保存文本更改
                $("#save_btn").click(function(){
                    $("#fileView").attr("disabled",true);
                    $("#fileView").css("background-color","#C0C0C0");
                    var filecontent=$("#fileView").val();
                    $.ajax({
                        type:"post",
                        url:"/etc_edit/fileSave",
                        data:{
                            "path":path,
                            "filecontent":filecontent
                        },
                        datetype:"json",
                        success:function(data){
                            $.notify({
                                icon: '',
                                message: "保存成功"

                            },{
                                type: 'info',
                                timer: 1000
                            });
                            $("#fileView").val(data.responseContext);
                        },
                        error : function(XMLHttpRequest, textStatus, errorThrown) {
                            $.notify({
                                icon: '',
                                message: "很抱歉，文件保存失败，原因"+ errorThrown

                            },{
                                type: 'info',
                                timer: 1000
                            });
                        }
                    })
                });
            }
        })
        .jstree({
            'core' : {
                'data' : {
                    "url" : "/etc_edit/etcList",
                    "data" : function (node) {
                        return { "id" : node.id ,"eventId":$("#event-id").val()};
                    }
                }
            }
        });
})
//创建目录，文件
function newDirOrFile(type){
    //var fileName=$("#addDir_input").val()+"\\"+$("#dirName").val();
    var fileName=$("#addDir_input").val()+"/"+$("#dirName").val();
    if(type=="file"){
        //fileName=$("#addDir_input").val()+"\\"+$("#fileName").val();
        fileName=$("#addDir_input").val()+"/"+$("#fileName").val();
    }
    $.ajax({
        type:"POST",
        async:false,
        url:"/etc_edit/newFile",
        data:{
            type:type,
            fileName:fileName
        },
        success: function (data) {
            $('#dirmodal').modal('hide');
            $('#filemodal').modal('hide');
            var type="success";
            if(data.callbackMsg=="Error"){
                type="danger"
            }
            $.notify({
                icon: '',
                message: data.responseContext

            }, {
                type: type,
                timer: 1000
            });
            $("#tree-div").jstree(true).refresh_node( $("#nodefresh_input").val());
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            nebula.common.alert.danger("很抱歉，创建失败，原因"+ errorThrown, 1000);
        }
    });
}