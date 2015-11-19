$(function(){
    //$("#submit").click(function(){
    //    $.ajax({
    //        type: "POST",
    //        url:"/userRole/insertAclUserRole.htm",
    //        data:$('#insertform').serialize(),
    //        async: false,
    //        success: function(data) {
    //            $.notify({
    //                icon: '',
    //                message: "��ӽ�ɫ�ɹ�"
    //
    //            },{
    //                type: 'info',
    //                timer: 1000
    //            });
    //        },
    //        error: function(request) {
    //            $.notify({
    //                icon: '',
    //                message: "��ӽ�ɫʧ��"+ errorThrown
    //
    //            },{
    //                type: 'info',
    //                timer: 1000
    //            });
    //        }
    //    });
    //})

    //var setting = {
    //    isSimpleData : true,              //数据是否采用简单 Array 格式，默认false
    //    treeNodeKey : "id",               //在isSimpleData格式下，当前节点id属性
    //    treeNodeParentKey : "pId",        //在isSimpleData格式下，当前节点的父节点id属性
    //    showLine : true,                  //是否显示节点间的连线
    //    checkable : true                  //每个节点上是否显示 CheckBox
    //};
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
    $.ajax({
        type:"post",
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
    $("#submit").click(function(){
        var treeObj = $.fn.zTree.getZTreeObj("permissionList");
        var nodes=treeObj.getCheckedNodes();
        //var childNodes = treeObj.transformTozTreeNodes(nodes);
        //var nodes1 = new Array();
        //for(i = 0; i < childNodes.length; i++) {
        //    nodes1[i] = childNodes[i].id;
        //}
        var permissionIds=[];
        for(var i=0;i<nodes.length;i++){
            if(nodes[i]["pId"]!=null)
                permissionIds.push(nodes[i]["id"]);
        }
        var isEnable=$('#isEnable input[name="isEnable"]:checked ').val();
        var roleName=$("#roleName").val();
        var roleCname=$("#roleCname").val();
        var roleDesc=$("#roleDesc").val();
        $.ajax({
            type:"post",
            url:"/role/insertAclRole.htm",
            data:{
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
                    message: "新增角色成功"

                },{
                    type: 'info',
                    timer: 1000
                });
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.notify({
                    icon: '',
                    message: "很抱歉新增角色失败，原因"+ errorThrown

                },{
                    type: 'info',
                    timer: 1000
                });
            }
        })
    });
    function onClick(e,treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("permissionList");
        zTree.checkNode(treeNode,"",true,false);
    }
})