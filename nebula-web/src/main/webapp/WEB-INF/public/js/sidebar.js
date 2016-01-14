$(function(){
    setMenuItem();
    $("#sidebar_ul li").click(function(){
        var hideflag=false;
        if($(this).children("ul").is(':hidden')){　　//如果node是隐藏的则显示node元素，否则隐藏
            hideflag=true;
        }
        $("#sidebar_ul li").children("ul").slideUp(500);
        //$(this).children("ul").toggle();
        if(hideflag){
            $(this).children("ul").slideDown(500);
        }
    });
})

function setMenuItem(){
    var url=location.href;
    url=url.split("/");
    url=url[url.length-1];
    //url="/"+url;
    //$("#sidebar_ul li").children("ul").slideUp(500);
    $("#sidebar_ul >li >ul >li >a").each(function(index){
        var liUrl=$(this).attr("href").split("/");
        liUrl=liUrl[liUrl.length-1];
        if ( liUrl== url){
            $(this).parent().parent().show();
        }
    });
}