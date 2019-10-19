$(function() {
	var model = {
	        path : adminContextPath + "/zhfw/yhqyxx",
	        tableRefresh : function() {
	            $('#table').bootstrapTable("refresh");
	        },
	        init : function(){
	        	way.set("model.search.qylx", '01');
	        }

	    };
	
	model.init();
	
	$("#search").click(function() {
        model.tableRefresh();
    });
	
	$('#table').bootstrapTable({
        url : model.path + '/qryPage.do',
        columns : [
            {
                field : 'QYLX',
                title : '签约类型',
                formatter: function (value) {
                    if (value) {
                    	return $.getDictName('QYLX', value);
                    }
                }
            },
            {
                field : 'ZH',
                title : '账号'
            },
            {
                field : 'MC',
                title : '名称'
            },
            {
                field : 'ZJH',
                title : '证件号',
                formatter: function (value) {
                    if (value) {
                        return '****' + value.substring(value.length - 4);
                    }
                }
            },
            {
                field : 'QYYH',
                title : '签约银行'
            },
            {
                field : 'QYYHZHM',
                title : '签约银行账户名'
            },
            {
                field : 'QYYHZH',
                title : '签约银行账号',
                formatter: function (value) {
                    if (value) {
                        return '****' + value.substring(value.length - 4);
                    }
                }
            },
            {
            	field : 'QYRQ',
            	title : '签约日期',
            	formatter: function (value) {
                    if (value) {
                        return value.split(" ")[0];
                    } else {
                        return '-';
                    }
                }
            },
            {
                field : 'DQZTC',
                title : '签约状态'
            }

        ]
    });
	
});