//用户信息统计
$(function() {
    var model = {
        path : adminContextPath + "/zhfw/zhfwptReport",
        tableRefresh : function() {
            $('#table').bootstrapTable("refresh");
        },
        init:function(){//需要初始化的功能
            way.set("model.search.inputDatec",$.getLastMonthAndFirstDay());
            way.set("model.search.inputDated",$.getLastMonthAndDay());
        }
    };
    model.init();

    // 查询
    $("#search").click(function() {
        model.tableRefresh();
    });
    // 导出
    $("#report").click(function() {
        layer.confirm('确定导出？', {
            shadeClose : true,
            icon : 3,
            anim : 6,
            btn : [ '确定', '取消' ]
            // 按钮
        }, function() {
            var qdid = way.get("model.search.qdid");
            if(!qdid){
                qdid = '';
            }
            var inputDatec = way.get("model.search.inputDatec");
            if(!inputDatec){
                inputDatec = '';
            }
            var inputDated = way.get("model.search.inputDated");
            if(!inputDated){
                inputDated = '';
            }
            layer.msg("正在导出...");
            window.open(model.path + "/report.do?qdid=" + qdid + "&inputDatec=" + inputDatec + "&inputDated=" + inputDated)
        });
    });

    // 列表
    $('#table').bootstrapTable({
        url : model.path + '/qryPage.do',
        pageSize:30,
        searchTimeOut:2000,
        columns: [
            {
                field: 'gnfl',
                title: '功能分类'
            },
            {
                field: 'flmx',
                title: '分类明细'
            },
            {
                field: 'fwl',
                title: '服务量'
            }
        ]
    });
});
























