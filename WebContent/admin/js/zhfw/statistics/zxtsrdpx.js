$(function() {	
	var model = {				
			path : adminContextPath + "/zhfw/statistics/zxtsrdpx",
			tableRefresh : function() {
			$('#table').bootstrapTable("refresh");			
		},
		
	};	    
	

	// 查询
	$("#search").click(function() {
		model.tableRefresh();
	});

   //列表
	$('#table').bootstrapTable({
		url : model.path + '/query.do',
		columns : [ 
		{
			field : 'fxmc',
			title : '标题'
		},
		{
			field : 'flid',
			title : '分类'
		},
		{
			field : 'hits',
			title : '访问数量'
		} ,
		{
			field : 'llrq',
			title : '发布日期'
		},
		{
			field : 'zhfwrq',
			title : '最后访问日期'
		}
		   
		]
	
	});
	
	
	
	
});

