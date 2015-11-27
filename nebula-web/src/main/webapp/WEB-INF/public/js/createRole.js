$(function(){
    //var setting = {
    //    isSimpleData : true,              //数据是否采用简单 Array 格式，默认false
    //    treeNodeKey : "id",               //在isSimpleData格式下，当前节点id属性
    //    treeNodeParentKey : "pId",        //在isSimpleData格式下，当前节点的父节点id属性
    //    showLine : true,                  //是否显示节点间的连线
    //    checkable : true                  //每个节点上是否显示 CheckBox
    //};
    //var permissionList;

    $("#insertform").validate({
        rules:{
            roleName:{required:true},
            roleCname:{required:true},
            roleDesc:{required:true},
            //permissionList:{required:true},
            isEnable:{required:true},
        },
        messages:{
            roleName:{required:"角色名称不能为空"},
            roleCname:{required:"角色中文名称不能为空"},
            roleDesc:{required:"角色描述不能为空"},
            //permissionList:{required:"角色的权限不能为空"},
            isEnable:{required:"请选择是否启用"}
        }
    });


    //为编辑页面时
    $("#save").hide();
    if($("#isEdit").val()!=""){
        $.ajax({
            async: false,
            type:"post",
            data:{"roleId":$("#roleId").val()},
            url:"/role/get/roleId",
            datatype:"json",
            success: function (data) {
                $("#roleName").val(data["roleName"]);
                $("#roleCname").val(data["roleCname"]);
                $("#roleDesc").val(data["roleDesc"]);
                //permissionList=data["aclPermissions"];
                if(data["isEnable"]==1) {
                    $("#enableRadio").attr("checked", true);
                }
                else
                    $("#unenableRadio").attr("checked", true);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.notify({
                    icon: '',
                    message: "很抱歉载入角色信息失败，原因"+ errorThrown

                },{
                    type: 'info',
                    timer: 1000
                });
            }
        });
        $("#submit").hide();
        $("#save").show();
    }

    //zTree的配置
    var checkSetting =
    {
        check: {
            enable: true,
            chkboxType: { "Y": "ps", "N": "ps" }
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        view: {
            nameIsHTML: true
        },
        callback: {
            onClick: onClick
        }
    };

    var zNodes;
    //查询权限列表，放入zTree
    $.ajax({
        async: false,
        type:"post",
        data:{
            "id":$("#roleId").val()
        },
        url:"/permission/selectPermission",
        datatype:"json",
        success: function (data) {
            zNodes=data;
            $.fn.zTree.init($("#permissionList"), checkSetting, zNodes);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.notify({
                icon: '',
                message: "很抱歉载入权限失败，原因"+ errorThrown

            },{
                type: 'info',
                timer: 1000
            });
        }
    });

    //提交按钮
    $("#submit").click(function(){
        if($("#insertform").valid()) {
            btnClick(true);
        }
    });

    //保存按钮
    $("#save").click(function(){
        if($("#insertform").valid()) {
            btnClick(false);
        }
    });
    //zTree结点点击控制选中
    function onClick(e,treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("permissionList");
        zTree.checkNode(treeNode,"",true,false);
    }


})

function btnClick(isCreate){
    var treeObj = $.fn.zTree.getZTreeObj("permissionList");
    var nodes=treeObj.getCheckedNodes();
    if(nodes.length<1){
        $("#permissionTip").html("请选择角色权限");
        return;
    }
    else  $("#permissionTip").html();
    var permissionIds=[];
    for(var i=0;i<nodes.length;i++){
        //if(nodes[i]["pId"]!=null)
            permissionIds.push(nodes[i]["id"]);
    }
    var isEnable=$('#isEnable input[name="isEnable"]:checked ').val();
    var roleName=$("#roleName").val();
    var roleCname=$("#roleCname").val();
    var roleDesc=$("#roleDesc").val();
    var url,tips;
    if(isCreate){
        url="/role/add";
        tips="新增";
    }
    else{
        url="/role/update";
        tips="修改";
    }

    $.ajax({
        type:"post",
        url:url,
        data:{
            "id":$("#roleId").val(),
            "isEnable":isEnable,
            "roleName":roleName,
            "roleCname":roleCname,
            "roleDesc":roleDesc,
            "permissionIds":permissionIds,
        },
        datatype:"json",
        success: function (data) {
            $.notify({
                icon: '',
                message: tips+"角色成功"

            },{
                type: 'info',
                timer: 1000
            });
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $.notify({
                icon: '',
                message: "很抱歉"+tips+"角色失败，原因"+ errorThrown

            },{
                type: 'info',
                timer: 1000
            });
        }
    })
}
