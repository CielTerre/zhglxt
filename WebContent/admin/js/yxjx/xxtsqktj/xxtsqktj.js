$(function() {
	
	var now = new Date();
	var year = now.getFullYear(); //得到年份
	var month = now.getMonth();//得到月份
	var date = now.getDate();//得到日期
	month = month + 1;
	if (month < 10) month = "0" + month;
	if (date < 10) date = "0" + date;
	var time01 = year + "-" + month + "-" + "01";
	var time02 = year + "-" + month + "-" + date;
	
	
	$("#xxtsqktj_tjsjc").val(time01);
	$("#xxtsqktj_tjsjd").val(time02);
	
	var model = {
		path : adminContextPath + "/zhfw/srtjxx",
		qdid :"",
		resetDataForm : function() {
			$("#data-form").bootstrapValidator('resetForm', true);
			way.set("model.form.data",{
				sort:10
			});
		},
		getFormData : function() {
			var data =  way.get("model.form.data");
			return data?data:{};
		},
		setFormTitle : function(title) {
			way.set("model.form.title", title);
		},
		tableRefresh : function() {
			$('#qdlmxxts_table').bootstrapTable("refresh");
		},
		setParent:function(parentId){
			if (parentId && parentId !='0') {
				alert(parentId);
				
			}
		},
	};
	
	$("body").on("click", ".view", function() {
		model.qdid=$(this).data("id");
		$('#modal-view').modal('toggle');
		$('#wzlmxxgxl_table').bootstrapTable("refresh");
		
	});
	
	// 查询
	$("#xxtsqktj_qjcxan").click(function() {
		model.tableRefresh();
	});
	
	
	// 列表
	$('#qdlmxxts_table').bootstrapTable({
		
		url : model.path + '/selectQdtsqktj.do',
		pagination:false,
		queryParams:function(pageReqeust){
			var tjrqc=$("#xxtsqktj_tjsjc").val();
			var tjrqd=$("#xxtsqktj_tjsjd").val();
			pageReqeust.tjrqc = tjrqc;  //
		    pageReqeust.tjrqd = tjrqd; 
			return pageReqeust;
		},
		responseHandler : function(result) {
			if(result.responeMsg==null){
				var Data=[];
				if(result.data.length!=null){
					 var resultdata=result.data;
					 for ( var i = 0; i <resultdata.length; i++){
						 var name ="";
						 var lmvalue="";
						 var value="";
						 var list={};
						 var qdid=resultdata[i].QDID;
						 if(qdid=="QD000"){
							 name="核心系统";
						 }
						 if(qdid=="QD001"){
							 name="网厅";
						 }
						 if(qdid=="QD002"){
							 name="android_APP";
						 }
						 if(qdid=="QD003"){
							 name="网站";
						 }
						 if(qdid=="QD004"){
							 name="微信";
						 }
						 if(qdid=="QD005"){
							 name="微博";
						 }
						 if(qdid=="QD006"){
							 name="自助终端";
						 }
						 if(qdid=="QD007"){
							 name="12329热线";
						 }
						 if(qdid=="QD008"){
							 name="	短信";
						 }
						 if(qdid=="QD009"){
							 name="支付宝";
						 }
						 
						 value=resultdata[i].XXTSQDTJZ;
						 lmvalue=resultdata[i].LMGXQDTJZ;
						 list.qdid=qdid;
						 list.name=name;
						 list.lmvalue=lmvalue;
						 list.value=value;
						 Data.push(list);
					 }
			}
			
			};
			return {
				rows : Data
			};
		},
		onPostBody:function(){//渲染完后执行
			
		},
		rowStyle:function(row,index){//整合treegrid
			var classes = "treegrid-" + row.id
			if (row.parentId != '0') {
				classes = classes + " treegrid-parent-" + row.parentId;
			}
			return {classes:classes};
		},
		columns : [ 
//			{
//			checkbox : true
//		}, 
		{
			field : 'name',
			title : '渠道名称'
		},
		{
			field : 'lmvalue',
			title : '栏目内容更新量',
			 formatter : function(value, row, index) {
				    return "<a href='#' class='view text-success' data-id='" + row.qdid + "'>" + value + "</a>"
			}
		}, {
			field : 'value',
			title : '信息推送量'
		}]
	});


	// 列表
	$('#wzlmxxgxl_table').bootstrapTable({
		url : model.path + '/selectQdlmgxxq.do',
		pagination:false,
		queryParams:function(pageReqeust){
			var tjrqc=$("#xxtsqktj_tjsjc").val();
			var tjrqd=$("#xxtsqktj_tjsjd").val();
			pageReqeust.tjrqc = tjrqc;  //
		    pageReqeust.tjrqd = tjrqd; 
			pageReqeust.qdid = model.qdid;  //
			return pageReqeust;
		},
		responseHandler : function(result) {
			if(result.responeMsg==null){
			}
			return {
				rows : result.data
			};
		},
		//onPostBody:function(){//渲染完后执行
		//
		//},
		//rowStyle:function(row,index){//整合treegrid
		//	var classes = "treegrid-" + row.id
		//	if (row.parentId != '0') {
		//		classes = classes + " treegrid-parent-" + row.parentId;
		//	}
		//	return {classes:classes};
		//},
		columns : [ 
//			{
//			checkbox : true
//		},
		{
			field : 'CLASSNAME',
			title : '栏目名称'
		},
		{
			field : 'TJZ',
			title : '栏目内容更新量'
		}]
	});

});

