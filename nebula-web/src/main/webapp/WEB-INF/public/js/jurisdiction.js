$(function(){
    getjtb(1);
})

//页面加载显示列表
function getjtb(pageNum){
    $.ajax({
        type: "post",
        url: "/",
        data: {
        },
        datetype: "json",
        success: function (data) {
            var tbString="";
            var totalPage;
            for(var i=0;i<data["list"].length;i++){
                tbString=tbString+"<tr><td>"+data["list"][i]["id"]+"</td><td>"+
                    data["list"][i]["permission_name"]+"</td><td>"+data["list"][i]["permission_desc"]+
                    "</td><td>"+data["list"][i]["type"]+
                    "</td><td>"+data["list"][i]["url"]+
                    "</td><td><div class='btn-group'><button class='btn btn-success btn-sm' type='button'>编辑</button>"+
                    "<button class='btn btn-danger btn-sm' type='button'>删除</button>"+"</div></td>"
            }
            totalPage=data["pages"];
            if(totalPage>0) {
                var pageSortString="<ul class='pagination'> <li><a href='#'>上一页</a></li>";
                for (var i = 1; i <= totalPage; i++) {
                    pageSortString=pageSortString+"<li><a href='#'>"+i+"</a></li>";
                }
                pageSortString=pageSortString+" <li><a href='#'>下一页</a></li> </ul>";
            }
            $("tbody").html(tbString);
            $("#pageSort").html(pageSortString);
            sortNum=pageNum;
            $(".pagination>li").each(function(){
                if(pageNum+""==$(this).text())
                    $(this).addClass("active");
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
                        if(sortNum==data["pages"]){
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