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
                var id=data["list"][i]["id"];
                var pid=data["list"][i]["pid"];
                //var isEnable=data["list"][i]["isEnable"];
                //var permission=data["list"][i]["permission"];
                var edit_btn="";
                var delete_btn="";
                for(var j= 0,len=globelLoginUserPermission.length;j<len;j++) {
                    if (globelLoginUserPermission[j] == "permission:update") {
                        edit_btn = "<button class='btn btn-success btn-sm' type='button'>编辑</button>";
                    }
                    if (globelLoginUserPermission[j] == "permission:delete") {
                        delete_btn = "<button class='btn btn-danger btn-sm' type='button'>删除</button>";
                    }
                }
                tbString=tbString+"<tr><td>"+data["list"][i]["id"]+"</td><td>"+
                    data["list"][i]["permissionName"]+"</td><td>"+data["list"][i]["permissionCname"]+
                    "</td><td>"+data["list"][i]["permissionDesc"]+
                    "</td><td>"+data["list"][i]["permissionType"]+
                    "</td><td>"+data["list"][i]["url"]+
                    "</td><td><div id='listBtn' class='btn-group'>"+edit_btn+delete_btn+
                    "</div></td></tr>"
                    //"<td>"+"<input type='text' style='display:none' id='"+id+"pid' value='"+pid+"'>"+
                    //"<input type='text' style='display:none' id='"+id+"permission' value='"+permission+"'>"+
                    //"<input type='text' style='display:none' id='"+id+"isEnable' value='"+isEnable+"'>"+"</td>";
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
                var ms = confirm("确认删除么？");
                if (ms == true) {
                    var id = $(this).parent().parent().parent().children().eq(0);
                    $.ajax({
                        type: "post",
                        url: "/permission/delete",
                        data: {"id": id.text()},
                        datatype: "json",
                        success: function (data) {
                            getjtb(pageNum);
                            $.notify({
                                icon: '',
                                message: "删除权限成功"

                            }, {
                                type: 'info',
                                timer: 1000
                            });
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            $.notify({
                                icon: '',
                                message: "很抱歉，删除权限失败，原因" + errorThrown

                            }, {
                                type: 'info',
                                timer: 1000
                            });
                        }
                    });
                }
            });
        }
        if($(this).text()=="编辑") {
            $(this).click(function() {
                var id = $(this).parent().parent().parent().children().eq(0);
                window.open('/permission/update.htm?id=' + id.text());
            })
        }
    });
}