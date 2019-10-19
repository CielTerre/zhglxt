$(function() {
	var model = {
	        path : adminContextPath + "/zhfw/rzcx",
	        tableRefresh : function() {
	            $('#table').bootstrapTable("refresh");
	        },
	        init : function() {// 需要初始化的功能

				// 左侧部门树
				var setting = {
					data : {
						simpleData : {
							enable : true,
							idKey : "fwid",
							pIdKey : "parentId"
						},
						key : {
							name : 'flmc'
						}
					},
					callback : {
						onClick : function(event, treeId, treeNode) {
							way.set("model.search.fwfl", treeNode.id);
							model.tableRefresh();
						}
					}
				};
				$.post(adminContextPath + "/zhfw/fwflb/qryPage.do", function(
						respone) {
					// 选择上级tree
					$.fn.zTree.init($("#fwflMenuTree"), setting, respone.data);
					var treeObj = $.fn.zTree.getZTreeObj("fwflMenuTree");
					treeObj.expandAll(true);
				});

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
                field : 'yhzh',
                title : '用户账号'
            },
            {
                field : 'qdid',
                title : '访问渠道',
                formatter : function(value, row, index) {
    				return $.getQdName(value);
    			}
            },
            {
                field : 'fwid',
                title : '服务ID'
            },
            {
                field : 'fwmc',
                title : '服务名称'
            },
            {
            	field : 'rq',
            	title : '访问日期'
//            	formatter: function (value) {
//                    if (value) {
//                        return value.split(" ")[0];
//                    } else {
//                        return '-';
//                    }
//                }
            },
            {
            	field : 'qqlx',
            	title : '请求类型',
            	formatter : function(value, row, index) {
            		switch(value){
            		case '01':
            			return '正常请求';
            		case '02':
            			return '被拒请求';
            		case '03':
            			return '异常请求';
            		}
				}
            }

        ]
    });
	
});