$(function(){
    $("#generateCode_btn").click(function () {
        $.ajax({
            type:"POST",
            data:{
                username:$("#username").val(),
                password:$("#password").val()
            },
            url:"/bindingCode/credentials",
            success: function (data) {
                if(data.callbackMsg=="Error") {
                    nebula.common.alert.danger(data.responseContext, 1000);
                    return;
                }
                var googleAuth=data.responseContext;
                var url=nebula.tools.qrCode.generateQRCodesGetUrl(googleAuth.label,googleAuth.bu,googleAuth.secret);
                nebula.tools.qrCode.generateQRCodesByOtpauthUrl(url,"qrcode");
            },
            error: function (errorThrown) {
                nebula.common.alert.danger("很抱歉生产二维码失败，原因"+ errorThrown, 1000);
            }
        });
    });
})