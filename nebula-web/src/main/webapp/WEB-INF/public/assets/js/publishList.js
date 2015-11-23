$(document).ready(function(){
    function gettb(pageNum){
        $.ajax({
                    type: "post",
                    url: "/publish_event/publishList",
                    data: {
                        "pageSize":10,
                        "pageNum":pageNum
                    },
                    datetype: "json",
                    success: function (data) {
                        var tbString="";
                        var totalPage;
                        for(var i=0;i<data["list"].length;i++){
                            tbString=tbString+"<tr><td>"+data["list"][i]["id"]+
                                "</td><td>"+data["list"][i]["publishSubject"]+"</td><td>"+
                                    data["list"][i]["publishEnv"]+"</td><td>"+data["list"][i]["模块"]+
                                "</td><td>"+data["list"][i]["submitDatetime"]+
                                "</td><td>"+""+
                                "</td><td>"+data["list"][i]["isSuccessPublish"]+
                                "</td><td>"+data["list"][i]["submitEmpId"]+
                                "</td><td>"+data["list"][i]["submitEmpId"]+
                                "</td><td>"+data["list"][i]["publishProductCname"]+
                                "</td><td>"+""+"</td><td><a href='/publishProcess.htm?id="+
                                data["list"][i]["id"]+"'>详情</a></td>"
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
                        var sortNum=pageNum;
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
                                gettb(sortNum);
                            });
                        });
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        $.notify({
                            icon: '',
                            message: "很抱歉，列表查询失败，原因"+ errorThrown

                        },{
                            type: 'info',
                            timer: 1000
                        });
                    }
                })
    };
    gettb(1);
})