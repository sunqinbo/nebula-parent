$(document).ready(function(){
    $("#navbar-header-name").html("发布数据");

    $("#select-bu").change(function(){
        var pid = $("#select-bu").val();
        $("#select-product").html("<option value=''>请选择</option>");
        $.ajax({
            url:"/publish/productTreeList/pid",
            type:"post",
            data:{"pid":pid},
            success:function(jsonData){
                if(jsonData.callbackMsg.match(/Success/)){
                    var productTrees = jsonData.responseContext;
                    for(var i=0;i<productTrees.length;i++){
                        var productTree = productTrees[i];
                        $("#select-product").append("<option value-svn='"+productTree.srcSvn+"' value-hidden='"+productTree.nodeName+"' value='"+productTree.id+"'>"+productTree.nodeCname+"</option>");
                    }
                }
            }
        });
    });

    gettb(1);
    //查询
    $("#query_btn").click(function(){
        //alert("查询");
        gettb(1);
    });
})

function gettb(pageNum){
    var publishBuCname=null;
    var publishProductCname=null;
    var publishStatus=null;
    if($("#select-bu").find("option:selected").text()!="请选择"){
        publishBuCname=$("#select-bu").find("option:selected").text();
    }
    if($("#select-product").find("option:selected").text()!="请选择"){
        publishProductCname=$("#select-product").find("option:selected").text();
    }
    if($("#select-status").find("option:selected").text()!="请选择"){
        publishStatus=$("#select-status").find("option:selected").val();
    }
    $.ajax({
        type: "post",
        url: "/publish/list",
        data: {
            "pageSize":10,
            "pageNum":pageNum,
            "starTime":$("#begintime").val(),
            "endTime":$("#endtime").val(),
            "publishBuCname":publishBuCname,
            "publishProductCname":publishProductCname,
            "publishStatus":publishStatus
        },
        datetype: "json",
        success: function (data) {
            var tbString="";
            var totalPage;
            for(var i=0;i<data["list"].length;i++){
                var event = data["list"][i];
                var detailString="";
                for(var j= 0,len=globelLoginUserPermission.length;j<len;j++) {
                    //权限判断
                    if(globelLoginUserPermission[j]=="publishevent:view")
                        detailString = detailString+"<button name='detail_button' class='btn btn-info btn-sm' type='button' onclick='detailBtn("+event.id+")'>详情</button>";
                    //detailString = detailString+"<a href='/publish/process.htm?id=" + event.id + "'>详情</a>";
                    //增加删除按钮
                    if(globelLoginUserPermission[j]=="publishevnt:delete"&&(event.publishStatus=="PENDING_APPROVE"||event.publishStatus=="PENDING_PRE")){
                        detailString=detailString+"<button name='delete_button' style='float: right;' class='btn btn-danger btn-sm' type='button'>删除</button>"
                    }
                }
                tbString=tbString+"<tr><td>"+event.id+
                    "</td><td>"+event.publishSubject+
                    "</td><td>"+nebula.common.transform.publishEnv(event.publishEnv)+
                    "</td><td>"+event.publishProductCname+
                    "</td><td>"+nebula.common.transform.publishStatus(event.publishStatus)+
                    "</td><td>"+event.submitDatetime+
                    "</td><td>"+(event.submitUser?event.submitUser.nickname:"")+
                    "</td><td>"+(event.publishUser?event.publishUser.nickname:"")+
                    "</td><td></td><td><div id='listBtn' class='btn-group'>"+detailString+"</div></td>";
            }
            totalPage=data["pages"];
            $('#pageSort').pagination({
                pages: totalPage,
                styleClass: ['pagination-large'],
                showCtrl: true,
                displayPage: 6,
                onSelect: function (num) {
                    gettb(num);  //分页点击
                }
            });
            //if(totalPage>0) {
            //    var pageSortString="<ul class='pagination'> <li><a href='#'>上一页</a></li>";
            //    for (var i = 1; i <= totalPage; i++) {
            //        pageSortString=pageSortString+"<li><a href='#'>"+i+"</a></li>";
            //    }
            //    pageSortString=pageSortString+" <li><a href='#'>下一页</a></li> </ul>";
            //}
            $("tbody").html(tbString);
            listBtn(pageNum);
            //$("#pageSort").html(pageSortString);
            //var sortNum=pageNum;
            //$(".pagination>li").each(function(){
            //    if(pageNum+""==$(this).text())
            //        $(this).addClass("active");
            //    $(this).click(function(){
            //        if($(this).text()=="上一页")
            //        {
            //            if(sortNum==1){
            //                return;
            //            }
            //            sortNum=sortNum-1;
            //        }
            //        else if($(this).text()=='下一页')
            //        {
            //            if(sortNum==data["pages"]){
            //                return;
            //            }
            //            sortNum=sortNum-1+2;
            //        }
            //        else{
            //            sortNum=$(this).text();
            //        }
            //        gettb(sortNum);
            //    });
            //});
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
function listBtn(pageNum) {
    $("#listBtn>button").each(function () {
        if($(this).text()=="删除") {
            $(this).click(function () {
                var ms = confirm("确认删除么？");
                if (ms == true) {
                    var id = $(this).parent().parent().parent().children().eq(0);
                    $.ajax({
                        type: "post",
                        url: "/publish/delete",
                        data: {"eventId": id.text()},
                        datatype: "json",
                        success: function (data) {
                            gettb(pageNum);
                            $.notify({
                                icon: '',
                                message: "成功删除发布事件"

                            }, {
                                type: 'info',
                                timer: 1000
                            });
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            $.notify({
                                icon: '',
                                message: "很抱歉，删除发布事件失败，原因" + errorThrown

                            }, {
                                type: 'info',
                                timer: 1000
                            });
                        }
                    });
                }
            });
        }
    });
}

function detailBtn(eventId){
    //window.open('/publish/process.htm?id='+eventId);
    window.location.href='/publish/process.htm?id='+eventId;
}

