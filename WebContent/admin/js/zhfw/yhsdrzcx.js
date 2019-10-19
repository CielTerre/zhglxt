$(function() {
	var model = {
	        path : adminContextPath + "/zhfw/yhsdrzcx",
	        tableRefresh : function() {
	            $('#table').bootstrapTable("refresh");
	        }

	    };
	
	$("#search").click(function() {
        model.tableRefresh();
    });
	
	$('#table').bootstrapTable({
        url : model.path + '/qryPage.do',
        columns : [
            {
                field : 'yhlbdm',
                title : '用户类别',
                formatter: function (value) {
                    return $.getDictName('YHLBDM', value);
                }
            },
            {
                field : 'yhdlzh',
                title : '用户登录账号'
               /* formatter : function(value, row, index) {
    				return $.getQdName(value);
    			}*/
            },
            {
                field : 'lockTime',
                title : '锁定时间'
//                formatter: function (value) {
//                    if (value) {
//                        return value.split(" ")[0];
//                    } else {
//                        return '-';
//                    }
//                }
            },
            {
                field : 'lockRemark',
                title : '备注'
            }
        ]
    });
	
});