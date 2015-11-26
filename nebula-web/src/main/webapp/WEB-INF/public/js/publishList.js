$(document).ready(function(){
    $("#navbar-header-name").html("发布数据");

    function gettb(pageNum){
        $.ajax({
                    type: "post",
                    url: "/publish/list",
                    data: {
                        "pageSize":10,
                        "pageNum":pageNum
                    },
                    datetype: "json",
                    success: function (data) {
                        var tbString="";
                        var totalPage;
                        for(var i=0;i<data["list"].length;i++){
                            var event = data["list"][i];
                            tbString=tbString+"<tr><td>"+event.id+
                                "</td><td>"+event.publishSubject+
                                "</td><td>"+nebula.common.transform.publishEnv(event.publishEnv)+
                                "</td><td>"+event.submitDatetime+
                                "</td><td>"+nebula.common.transform.publishStatus(event.publishStatus)+
                                "</td><td>"+nebula.common.transform.isSuccessPublish(event.isSuccessPublish)+
                                "</td><td>"+(event.submitUser?event.submitUser.nickname:"")+
                                "</td><td>"+(event.publishUser?event.publishUser.nickname:"")+
                                "</td><td>"+event.publishProductCname+
                                "</td><td>"+
                                "</td><td><a href='/publish/process.htm?id="+event.id+"'>详情</a></td>"
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