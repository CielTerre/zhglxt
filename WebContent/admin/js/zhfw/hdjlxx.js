$(function() {
    var model = {
        path : adminContextPath + "/zhfw/hdjlxx",
        tableRefresh : function() {
            $('#table').bootstrapTable("refresh");
        }

    };
    $("#search").click(function() {
        model.tableRefresh();
    });
    //查看
    $("body").on("click",".hdjlxx-see",function(){
        var ywlsh = $(this).data("id");
        layer.open({
            title:"查看",
            type: 2, //iframe
            maxmin:true,
            area: ['95%', '95%'],
            content: '/admin/pages/zhfw/hdjlxxls.html?ywlsh='+ywlsh  //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        });
    });

    $('#table').bootstrapTable({
        url : model.path + '/qryPage.do',
        columns : [
            {
                field:'ywlsh',
                title:'业务流水号',
                visible:false
            },
            {
                field : 'yhxm',
                title : '用户姓名',
                formatter:function (value) {
                    return value || '匿名用户';
                }
            },
            {
                field : 'fqsj',
                title : '咨询时间'
            },
            {
                field : 'kfxm',
                title : '客服姓名'
            },
            {
                field : 'oper',
                title : '操作',
                formatter : function(value, row, index) {
                    return "<a  href='#' class='text-success sf-permission-ctl hdjlxx-see' data-sf-permission='zhfw:hdjlxx:qry' data-id='" + row.ywlsh + "'>查看</a>";
                }
            }

        ]
    });


});