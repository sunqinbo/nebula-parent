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

    var setting = {
        isSimpleData : true,              //数据是否采用简单 Array 格式，默认false
        treeNodeKey : "id",               //在isSimpleData格式下，当前节点id属性
        treeNodeParentKey : "pId",        //在isSimpleData格式下，当前节点的父节点id属性
        showLine : true,                  //是否显示节点间的连线
        checkable : true                  //每个节点上是否显示 CheckBox
    };
    //var zNodes;
    var nodes = [
        {id:1, pId:0, name: "父节点1"},
        {id:11, pId:1, name: "子节点1"},
        {id:12, pId:1, name: "子节点2"}
    ];
    $.fn.zTree.init($("#permissionList"), setting, nodes);
    //$.ajax({
    //    type:"post",
    //    url:"/permission/selectPermission",
    //    datatype:"json",
    //    success: function (data) {
    //        zNodes=data;
    //        $.fn.zTree.init($("#permissionList"), setting, zNodes);
    //    },
    //    error: function (XMLHttpRequest, textStatus, errorThrown) {
    //        $.notify({
    //            icon: '',
    //            message: "很抱歉载入权限失败，原因"+ errorThrown
    //
    //        },{
    //            type: 'info',
    //            timer: 1000
    //        });
    //    }
    //})
})