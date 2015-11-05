$(document).ready(function(){
    $("#file_btn").click(function(){
        //alert("点击了");
        var textArea="<textarea id='fileView' disabled=true rows='16' cols='80'></textarea>";
        var btn="<button id='edit_btn' type='button' class='btn btn-info'>编辑etc</button>"+
            "<button id='save_btn' type='button' style='margin-left: 30px' class='btn btn-info'>保存etc</button>";
        $("#textputer").empty().append(textArea);
        $("#btnputer").empty().append(btn);
        var path=$("#path").val();
        $.ajax({
                type:"post",
                url:"/filePath",
                data:{
                    "path":path
                },
                datetype:"json",
                success:function(data){
                    var context=data.responseContext[0];
                    for(var i=1;i<data.responseContext.length;i++)
                        context+="\r\n"+data.responseContext[i];
                    $("#fileView").val(context);
                },
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                    alert("很抱歉，文件打开失败，原因：" + errorThrown);
                }
            })
        $("#edit_btn").click(function(){
            $("#fileView").attr("disabled",false);
        });
        $("#save_btn").click(function(){
            $("#fileView").attr("disabled",true);
            var filecontent=$("#fileView").val();
            $.ajax({
                type:"post",
                url:"/fileSave",
                data:{
                    "path":path,
                    "filecontent":filecontent
                },
                datetype:"json",
                success:function(data){
                    alert("保存成功");
                    $("#fileView").val(data.responseContext);
                },
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                    alert("很抱歉，文件保存失败，原因：" + errorThrown);
                }
            })
        });
    });

    //$("#edit_btn").click(function(){
    //    alert("进来了");
    //    //$("#fileView").attr("disabled",false);
    //});

    $('#tree-div')
        //.on("changed.jstree", function (e, data) {
        //    if(data.selected.length) {
        //        var selectedObj = data.instance.get_node(data.selected[0]);
        //        // 树节点操作 div
        //        $("#selected-tree-nodename").html(selectedObj.text);
        //        $("#selected-tree-id").html(selectedObj.id);
        //        if(selectedObj.id){
        //            $("#op-btn-group").show();
        //        }
        //
        //        // 增改操作 div
        //        $("#add-child-show-parent-info").text(selectedObj.text);
        //        //$("#modify-show-node-info").text(selectedObj.text);
        //        //$("#modify-node-name").val(selectedObj.text);
        //        $("#modify-node-div").hide();
        //    }
        //})
        .jstree({
            'core' : {
                'data' : {
                    "url" : "/etcList.htm",
                    "data" : function (node) {
                        return { "id" : node.id };
                    }
                }
            }
        });
})