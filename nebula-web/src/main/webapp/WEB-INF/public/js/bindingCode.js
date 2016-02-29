$(function(){
    //生成二维码
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
                nebula.common.alert.success("二维码生成成功", 1000);
                var googleAuth=data.responseContext;
                var url=nebula.tools.qrCode.generateQRCodesGetUrl(googleAuth.label,googleAuth.bu,googleAuth.secret);
                nebula.tools.qrCode.generateQRCodesByOtpauthUrl(url,"qrcode");
            },
            error: function (errorThrown) {
                nebula.common.alert.danger("很抱歉生产二维码失败，原因"+ errorThrown, 1000);
            }
        });
    });
    //绑定动态验证码
    $("#binding_btn").click(function () {
        $.ajax({
            type:"POST",
            data:{
                username:$("#username").val(),
                codeFirstString:$("#codeFirstString").val(),
                codeSecondString:$("#codeSecondString").val()
            },
            url:"/bindingCode/gCodesVerifyAndBinding",
            success: function (data) {
                if(data.callbackMsg=="Error") {
                    nebula.common.alert.danger(data.responseContext, 1000);
                    return;
                }
                nebula.common.alert.success(data.responseContext, 1000);
                var googleAuth=data.responseContext;
                var url=nebula.tools.qrCode.generateQRCodesGetUrl(googleAuth.label,googleAuth.bu,googleAuth.secret);
                nebula.tools.qrCode.generateQRCodesByOtpauthUrl(url,"qrcode");
            },
            error: function (errorThrown) {
                nebula.common.alert.danger("很抱歉绑定动态验证码失败，原因"+ errorThrown, 1000);
            }
        });
    })
})