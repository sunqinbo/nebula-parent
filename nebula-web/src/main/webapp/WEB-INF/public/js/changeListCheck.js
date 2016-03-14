$(function () {
    $("#pass_btn").click(function(){
        etcApprove(true);
    });
    $("#unPass_btn").click(function(){
        etcApprove(false);
    });
});
function clickFileName(btn){
    var value="", orig1, orig2="";
    $("#etc_list>a>li").each(function () {
        $(this).removeClass("etc_list_on");
    })
    $(btn.children[0]).addClass("etc_list_on");
    $.ajax({
       type:"post",
        url:"/publish/viewEtcContent",
        datetype: "json",
        async: false,
        data: {
            eventId: $("#event-id").val(),
            key:$(btn.children[0]).text()
            //key:"\\test2\\a.txt"
        },
        success: function (data) {
            if(!data.callbackMsg){
                data=JSON.parse(data);
            }
            if(data.callbackMsg=="Error") {
                nebula.common.alert.danger(data.responseContext, 1000);
                return;
            }
            if(data.responseContext.publishFileContent.length>0) {
                value = data.responseContext.publishFileContent[0];
            }
            for (var i = 1; i < data.responseContext.publishFileContent.length; i++)
                value += "\r\n" + data.responseContext.publishFileContent[i];
            //value=orig1;
            if(data.responseContext.oldFileContent.length>0) {
                orig2 = data.responseContext.oldFileContent[0];
            }
            for (var i = 1; i < data.responseContext.oldFileContent.length; i++)
                orig2 += "\r\n" + data.responseContext.oldFileContent[i];
            initUI(value,orig1,orig2);
        },
        error: function (errorThrown) {
            nebula.common.alert.danger("很抱歉，获取etc文件失败，原因" + errorThrown,1000);
        }
    });
}
function initUI(value,orig1,orig2) {
    var panes = 2, highlight = true, connect = null, collapse = false;
    if (value == null) return;
    var target = document.getElementById("view");
    target.innerHTML = "";
    var dv = CodeMirror.MergeView(target, {
        value: value,
        origLeft: panes == 3 && !collapse && !connect ? orig1 : null,
        orig: orig2,
        lineNumbers: true,
        mode: "text/html",
        highlightDifferences: highlight,
        connect: connect,
        collapseIdentical: collapse,
    });
}

function etcApprove(isPass){
    var url="/publish/etcApprovePass";
    if(!isPass){
        url="/publish/etcApproveReject";
    }
    $.ajax({
        type: "post",
        url: url,
        datetype: "json",
        async: false,
        data: {
            id: $("#event-id").val(),
        },
        success: function (data) {
            if (!data.callbackMsg) {
                data = JSON.parse(data);
            }
            if (data.callbackMsg == "Error") {
                nebula.common.alert.danger(data.responseContext, 1000);
                return;
            }
            nebula.common.alert.success(data.responseContext, 1000);
            setTimeout(function () {
                window.location.href="/publish/process.htm?id="+$("#event-id").val();
            }, 1000);
        },
        error: function (errorThrown) {
            nebula.common.alert.danger("很抱歉，审批配置失败，原因" + errorThrown, 1000);
        }
    });
}
//function toggleDifferences() {
//    dv.setShowDifferences(highlight = !highlight);
//}

//window.onload = function() {
//    value='Leftssss\nsssdfd\nsdf\nsdf\nsdsdss\naaasdf\nsdfsd\nbbb\nnaaddddddddddddddddddddddddddddddddddddddd\nadssa\nasdas\nasdf\nssss\ndddd\nffff\nccc\naaa\nzzzz\nsss\nbbb\nfff\nvvv\n\ndfddd\nsss\nzzz\noooo\nppp\nyyy\ncccc\nssss\nhhhh\nssss';
//    orig1 = 'Leftssss\nsssdfd\nsdf\nsdf\nsdsdss\naaasdf\nsdfsd\nbbb\naaddddddddddddddddddddddddddddddddddddddda\nadssa\nasdas\nasdf\nssss\ndddd\nffff\nccc\naaa\nzzzz\nsss\nbbb\nfff\nvvv\n\ndfddd\nsss\nzzz\noooo\nppp\nyyyncccc\nssss\nhhhh\nsass';
//    orig2='Rightssss\nsssdfd\nsdf\nsdf\nsdsdss\naas\nsdfsd\nbbb\naaa\nadssa\nasdddddddddddddddddddddddddddddddddddddddddddas\nasdf\nsdd\ndffd\nffdg\nccc\naaa\nzzzz\nsss\nbbb\nfff\nvvv\n\ndfddd\nsss\nzs\noodf\nppfdgp\nyyyndfgcc\nssdfgdss\nhhssssh\nsdfgdfg';
//    initUI();
//};

//function mergeViewHeight(mergeView) {
//    function editorHeight(editor) {
//        if (!editor) return 0;
//        return editor.getScrollInfo().height;
//    }
//    return Math.max(editorHeight(mergeView.leftOriginal()),
//        editorHeight(mergeView.editor()),
//        editorHeight(mergeView.rightOriginal()));
//}

//function resize(mergeView) {
//    var height = mergeViewHeight(mergeView);
//    for(;;) {
//        if (mergeView.leftOriginal())
//            mergeView.leftOriginal().setSize(null, height);
//        mergeView.editor().setSize(null, height);
//        if (mergeView.rightOriginal())
//            mergeView.rightOriginal().setSize(null, height);
//
//        var newHeight = mergeViewHeight(mergeView);
//        if (newHeight >= height) break;
//        else height = newHeight;
//    }
//    mergeView.wrap.style.height = height + "px";
//}
