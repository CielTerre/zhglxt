$(function() {
	way.set("model.search.bgzt","Y");
    	var model = {
		path : adminContextPath + "/zhfw/qdfwjrxx",
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
				    way.set("model.view.fwzt",$.getDictName('start_stop',respone.data.fwzt));
				    way.set("model.view.sqzt",$.getDictName('start_stop',respone.data.sqzt));
			})
		},
		init:function(){//需要初始化的功能

			//左侧部门树
			var setting = {
				data : {
					simpleData : {
						enable : true,
						idKey : "fwid",
						pIdKey : "parentId"
					},
					key:{
						name:'flmc'
					}
				},
				callback : {
					onClick : function(event, treeId, treeNode) {
						way.set("model.search.fwfl",treeNode.id);
						model.tableRefresh();
					}
				}
			};
			$.post(adminContextPath + "/zhfw/fwflb/qryPage.do", function(respone) {
				//选择上级tree
				$.fn.zTree.init($("#fwflMenuTree"), setting,respone.data);
				var treeObj = $.fn.zTree.getZTreeObj("fwflMenuTree");
				treeObj.expandAll(true);
			});

		}
	};
	model.init();
	// 校验
	$("#data-form").bootstrapValidator().on("success.form.bv", function(e) {// 提交
		e.preventDefault();
		
		var id = model.getFormData().id;
		var optUrl = model.path + "/save.do";
		if (id) {
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
		var id = $(this).data("id");
		model.setViewDataById(id);
		$('#modal-view').modal('toggle');
	});
	// 查询
	$("#search").click(function() {
		model.tableRefresh();
	});
	//// 添加
	//$("#add").click(function() {
	//	model.resetDataForm();
	//	model.setFormTitle("<i class='fa fa-plus'>添加</i>");
	//	$("#form-panel").modal('toggle');
	//});
	// 编辑
	$("#edit").click(function() {
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			model.resetDataForm();
			model.setFormDataById(rows[0].id);
			model.setFormTitle("<i class='fa fa-edit'>编辑</i>");
			$("#form-panel").modal('toggle');
		}
	});
	//// 删除
	//$("#delete").click(function() {
	//	var rows = $('#table').bootstrapTable("getSelections");
	//	if (rows.length == 0) {
	//		layer.msg("请选择一行");
	//	} else {
	//		layer.confirm('确定删除？', {
	//			shadeClose : true,
	//			icon : 3,
	//			anim : 6,
	//			btn : [ '确定', '取消' ]
	//		// 按钮
	//		}, function() {
	//			$.post(model.path  + "/delete.do", {
	//				id : rows[0].id
	//			}, function(respone) {
	//				if (respone.responeCode == "0") {
	//					layer.msg("删除成功");
	//					model.tableRefresh();
	//				}
	//			});
	//		});
	//	}
	//});
	// 列表
	$('#table').bootstrapTable({
		url : model.path + '/qryPage.do',
		columns : [ {
			checkbox : true
		}, 
			//{
			//field : 'ywlsh',
			//title : '业务流水号',
            //formatter : function(value, row, index) {
			//    return "<a href='#' class='view text-success' data-id='" + row.id + "'>" + value + "</a>"
			// }
			//},
			//{
			//	field : 'qdid',
			//	title : '渠道'
			//},
			{
			field : 'qdid',
			title : '渠道名称',
			formatter : function(value, row, index) {
				return $.getQdName(value);
			}
			},
			{
			field : 'fwid',
			title : '服务id'
			},
			{
				field : 'fwsm',
				title : '服务名称'
			},
			{
				field : 'flmc',
				title : '服务分类'
			},
			{
				field : 'fwxz',
				title : '服务性质',
				formatter : function(value, row, index) {
					return $.getDictName('FWXZ',value);
				}
			},
			{
				field : 'dbsx',
				title : '单笔上限'
			},
			{
				field : 'dbxx',
				title : '单笔下限'
			},
			{
				field : 'drsx',
				title : '单日上限'
			},
			{
				field : 'drywsx',
				title : '单日业务上限'
			},
			{
			field : 'fwzt',
			title : '服务状态',
			formatter : function(value, row, index) {
			    return $.getDictName('FWZT',value);
			 }
			}
		 ]
	});
	
});
