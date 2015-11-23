$(function(){
    getLtb(1);
})

//页面加载显示列表
function getLtb(pageNum){
    $.ajax({
        type: "post",
        url: "/user/selectAllPagingUser",
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
                    data["list"][i]["username"]+"</td><td>"+data["list"][i]["mobilePhone"]+
                    "</td><td>"+data["list"][i]["weixinAcc"]+
                    "</td><td>"+data["list"][i]["qqAcc"]+
                    "</td><td>"+data["list"][i]["email"]+
                    "</td><td>"+data["list"][i]["nickname"]+
                    "</td><td>"+data["list"][i]["deptName"]+
                    "</td><td>"+data["list"][i]["jobTitle"]+
                    "</td><td>"+data["list"][i]["empId"]+
                    "</td><td>"+data["list"][i]["supervisorEmpId"]+
                    "</td><td>"+data["list"][i]["isEnable"]+
                    "</td><td>"+data["list"][i]["password"]+
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
            getLtb(sortNum);
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
                    url:"/user/deleteUser.htm",
                    data:{"id":id.text()},
                    datatype:"json",
                    success: function (data) {
                        getLtb(pageNum);
                        $.notify({
                            icon: '',
                            message: "删除角色成功"

                        },{
                            type: 'info',
                            timer: 1000
                        });
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        $.notify({
                            icon: '',
                            message: "很抱歉，删除角色失败，原因"+ errorThrown

                        },{
                            type: 'info',
                            timer: 1000
                        });
                    }
                });
            });
        }
        if($(this).text()=="编辑") {
            $(this).click(function() {
                var empId = $(this).parent().parent().parent().children().eq(9);
                window.open('/editUser.html?id=' + empId.text());
            })
        }
    });
}