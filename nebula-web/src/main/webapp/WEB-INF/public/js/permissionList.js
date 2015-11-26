$(function(){
    getjtb(1);
    $("#create_btn").click(function(){
        location.href="/permission/add.htm";
    });
})

//页面加载显示列表
function getjtb(pageNum){
    $.ajax({
        type: "post",
        url: "/permission/list",
        data: {
            "pageSize":10,
            "pageNum":pageNum
        },
        datetype: "json",
        success: function (data) {
            var tbString="";
            var totalPage;
            for(var i=0;i<data["list"].length;i++){
                tbString=tbString+"<tr><td>"+data["list"][i]["id"]+"</td><td>"+
                    data["list"][i]["permissionName"]+"</td><td>"+data["list"][i]["permissionCname"]+
                    "</td><td>"+data["list"][i]["permissionDesc"]+
                    "</td><td>"+data["list"][i]["permissionType"]+
                    "</td><td>"+data["list"][i]["url"]+
                    "</td><td><div id='listBtn' class='btn-group'><button class='btn btn-success btn-sm' type='button'>编辑</button>"+
                    "<button class='btn btn-danger btn-sm' type='button'>删除</button>"+"</div></td>"
            }
            $("tbody").html(tbString);
            listBtn(pageNum);
            totalPage=data["pages"];
            pageSort(totalPage,pageNum);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.notify({
                icon: '',
                message: "很抱歉，获取列表失败，原因"+ errorThrown

            },{
                type: 'info',
                timer: 1000
            });
        }
    })
}

//分页的div控制
function pageSort(totalPage,pageNum){
    if(totalPage>0) {
        var pageSortString="<ul class='pagination'> <li><a href='#'>上一页</a></li>";
        for (var i = 1; i <= totalPage; i++) {
            pageSortString=pageSortString+"<li><a href='#'>"+i+"</a></li>";
        }
        pageSortString=pageSortString+" <li><a href='#'>下一页</a></li> </ul>";
    }
    $("#pageSort").html(pageSortString);
    $(".pagination>li").each(function(){
        if(pageNum+""==$(this).text())
            $(this).addClass("active");
        var sortNum=pageNum;
        $(this).click(function(){
            if($(this).text()=="上一页")
            {
                if(sortNum==1){
                    return;
                }
                sortNum=sortNum-1;
            }
            else if($(this).text()=='下一页')
            {
                if(sortNum==totalPage){
                    return;
                }
                sortNum=sortNum-1+2;
            }
            else{
                sortNum=$(this).text();
            }
            getjtb(sortNum);
        });
    });
}

//删除，编辑按钮事件
function listBtn(pageNum){
    $("#listBtn>button").each(function(){
        if($(this).text()=="删除") {
            $(this).click(function(){
                var id=$(this).parent().parent().parent().children().eq(0);
                $.ajax({
                    type:"post",
                    url:"/permission/delete",
                    data:{"id":id.text()},
                    datatype:"json",
                    success: function (data) {
                        getjtb(pageNum);
                        $.notify({
                            icon: '',
                            message: "删除权限成功"

                        },{
                            type: 'info',
                            timer: 1000
                        });
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        $.notify({
                            icon: '',
                            message: "很抱歉，删除权限失败，原因"+ errorThrown

                        },{
                            type: 'info',
                            timer: 1000
                        });
                    }
                });
            });
        }
        if($(this).text()=="编辑"||$(this).text()=="保存") {
            $(this).click(function(){
                if($(this).text()=="编辑") {
                    $(this).text("保存");
                }
                else {
                    $(this).text("编辑");
                    var id=$(this).parent().parent().parent().children().eq(0).children().eq(0).val();
                    var permissionName=$(this).parent().parent().parent().children().eq(1).children().eq(0).val();
                    var permissionCname=$(this).parent().parent().parent().children().eq(2).children().eq(0).val();
                    var permissionDesc=$(this).parent().parent().parent().children().eq(3).children().eq(0).val();
                    var permissionType=$(this).parent().parent().parent().children().eq(4).children().eq(0).val();
                    var url=$(this).parent().parent().parent().children().eq(5).children().eq(0).val();
                    $.ajax({
                        type:"post",
                        url:"/permission/update",
                        data:{"id":id,
                            "permissionName":permissionName,
                            "permissionCname":permissionCname,
                            "permissionDesc":permissionDesc,
                            "permissionType":permissionType,
                            "url":url
                        },
                        datatype:"json",
                        success: function (data) {
                            getjtb(pageNum);
                            $.notify({
                                icon: '',
                                message: "修改权限成功"

                            },{
                                type: 'info',
                                timer: 1000
                            });
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            $.notify({
                                icon: '',
                                message: "很抱歉，修改权限失败，原因"+ errorThrown

                            },{
                                type: 'info',
                                timer: 1000
                            });
                        }
                    });
                }
                var whichTd=0;
                $(this).parent().parent().siblings("td").each(function() {  // 获取当前行的其他单元格
                    whichTd++;
                    obj_text = $(this).find("input:text");    // 判断单元格下是否有文本框
                    var width=$(this).width();
                    if(whichTd==5){
                        var selectString="<select name='permissionType' class='form-control'>"+
                        "<option value='menu'>菜单</option><option value='button'>按钮</option></select>"
                        $(this).html(selectString);
                    }
                    else {
                        if (!obj_text.length)   // 如果没有文本框，则添加文本框使之可以编辑
                            $(this).html("<input class='form-control' style='width:" + width + "px;' type='text' value='" + $(this).text() + "'>");
                        else   // 如果已经存在文本框，则将其显示为文本框修改的值
                            $(this).html(obj_text.val());
                    }
                });
            })
        }
    });
}