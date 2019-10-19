$(function() {
    	var model = {
		path : adminContextPath + "/zhfw/fwflb",
		resetDataForm : function() {
			$("#data-form").bootstrapValidator('resetForm', true);
			//表单默认值可以在这里设置
			way.set("model.form.data",null);
		},
		getFormData : function() {
			var data =  way.get("model.form.data");
			return data?data:{};
		},
		setFormTitle : function(title) {
			way.set("model.form.title", title);
		},
		tableRefresh : function() {
			$('#table').bootstrapTable("refresh");
		},
		setFormDataById:function(id){
			$.get(this.path + "/get.do",{id:id},function(respone){
				way.set("model.form.data",respone.data);
			})
		},
		setViewDataById:function(id){
			$.get(this.path + "/get.do",{id:id},function(respone){
				way.set("model.view",respone.data);
				    way.set("model.view.sftj",$.getDictName('yes_no',respone.data.sftj));
			})
		},
		setParent:function(parentId){
			if (parentId && parentId !='0') {
				//查询上级部门名称
				$.get(this.path + "/get.do",{id:parentId},function(respone1){
					way.set("model.form.data.parentId",respone1.data.id);
				});
			}
		},
		init:function(){//需要初始化的功能
		}
	};
	model.init();
	// 校验
	$("#data-form").bootstrapValidator().on("success.form.bv", function(e) {// 提交
		e.preventDefault();
		
		var id = model.getFormData().fwid;
		var partentIds = model.getFormData().parentIds;
		var optUrl = model.path + "/save.do";
		if (partentIds) {
			optUrl = model.path + "/update.do";
		}
		$.post(optUrl, $("#data-form").serialize(), function(respone) {
			if (respone.responeCode == '0') {
				layer.msg("保存成功");
				model.tableRefresh();
				$("#form-panel").modal('toggle');
			}
		});
	});
	/**
	 * 查看
	 */
	$("body").on("click", ".view", function() {
		var id = $(this).data("fwid");
		model.setViewDataById(id);
		$('#modal-view').modal('toggle');
	});

	//添加下级分类
	$("body").on("click", ".addFwfl", function(){
		model.resetDataForm();
		model.setParent($(this).data("id"));
		model.setFormTitle("<i class='fa fa-plus'>添加</i>");
		$("#form-panel").modal('toggle');
	});

	// 查询
	$("#search").click(function() {
		model.tableRefresh();
	});
	// 添加
	$("#add").click(function() {
		model.resetDataForm();
		model.setFormTitle("<i class='fa fa-plus'>添加</i>");
		$("#form-panel").modal('toggle');
	});
	// 编辑
	$("#edit").click(function() {
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			model.resetDataForm();
			model.setFormDataById(rows[0].fwid);
			model.setFormTitle("<i class='fa fa-edit'>编辑</i>");
			$("#form-panel").modal('toggle');
		}
	});
	// 删除
	$("#delete").click(function() {
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			layer.confirm('确定删除？', {
				shadeClose : true,
				icon : 3,
				anim : 6,
				btn : [ '确定', '取消' ]
			// 按钮
			}, function() {
				$.post(model.path  + "/delete.do", {
					id : rows[0].id
				}, function(respone) {
					if (respone.responeCode == "0") {
						layer.msg("删除成功");
						model.tableRefresh();
					} 
				});
			});
		}
	});
	// 列表
	$('#table').bootstrapTable({
		url : model.path + '/qryPage.do',
		pagination:false,
		queryParams:function(){
			return {name:way.get("model.search.name")};
		},
		responseHandler : function(res) {
			return {
				rows : res.data
			};
		},
		onPostBody:function(){//渲染完后执行
			$("#table").treegrid({
				treeColumn:1,
				initialState:'expanded',
				saveState:true,
				expanderExpandedClass: 'glyphicon glyphicon-chevron-down',
				expanderCollapsedClass: 'glyphicon glyphicon-chevron-right'
			});
			$('#table').treegrid('render');
			$.bntPermissionHandler();
		},
		rowStyle:function(row,index){//整合treegrid
			var classes = "treegrid-" + row.id
			if (row.parentId != '0') {
				classes = classes + " treegrid-parent-" + row.parentId;
			}
			return {classes:classes};
		},
		columns : [ {
			checkbox : true
		}, 
			{
			field : 'fwid',
			title : '分类编号',
            formatter : function(value, row, index) {
			    return "<a href='#' class='view text-success' data-id='" + row.id + "'>" + value + "</a>"
			 }
			},
		/*	{
			field : 'parentId',
			title : '父分类编号'
			},*/
			{
			field : 'flmc',
			title : '分类名称'
			},
			{
			field : 'flcx',
			title : '分类次序'
			},
			{
			field : 'sftj',
			title : '是否统计',
			formatter : function(value, row, index) {
			    return $.getDictName('yes_no',value);
			 }
			},{
				field : 'oper',
				title : '操作',
				formatter : function(value, row, index) {
					var oper =  "<a  href='#' class='text-success addFwfl sf-permission-ctl'  data-id='" + row.fwid+ "' data-sf-permission='zhfw:fwflb:save'>添加下级分类</a>";
					return oper;
				}
			}
		 ]
	});
	
});
