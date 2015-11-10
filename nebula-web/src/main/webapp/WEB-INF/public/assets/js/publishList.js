$(document).ready(function(){
    function gettb(){
        $.ajax({
                    type: "post",
                    url: "/PublishList",
                    data: {
                    },
                    datetype: "json",
                    success: function (data) {
                        var tbString="";
                        var b=data[0];
                        for(var i=0;i<data.length;i++){
                            tbString=tbString+"<tr><td>"+data[i]["id"]+"</td><td>"+
                                    data[i]["publishEnv"]+"</td><td>"+data[i]["publishProductCname"]+
                                "</td><td>"+""+
                                "</td><td>"+data[i]["submitDatetime"]+
                                "</td><td>"+""+
                                "</td><td>"+data[i]["isSuccessPublish"]+
                                "</td><td>"+""+"</td><td><a href='/publishProcess.htm?id="+
                                data[i]["id"]+"'>详情</a></td>"
                        }
                        $("tbody").html(tbString);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        alert("很抱歉，文件打开失败，原因：" + errorThrown);
                    }
                })
    };
    gettb();
})