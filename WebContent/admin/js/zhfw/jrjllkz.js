$(function() {
    	var model = {
		path : adminContextPath + "/zhfw/qdxxb",
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
				way.set("model.form.data.qdzt",$.getDictName('QDZT',respone.data.qdzt));

			})
		},
		setViewDataById:function(id){
			$.get(this.path + "/get.do",{id:id},function(respone){
					way.set("model.view",respone.data);
				    way.set("model.view.qdzt",$.getDictName('QDZT',respone.data.qdzt));
					way.set("model.view.tzjrtj",$.getDictName('TZTJ',respone.data.tzjrtj));
			})
		},
		init:function(){//需要初始化的功能
		}
	};
	model.init();
	// 校验
	$("#data-form").bootstrapValidator().on("success.form.bv", function(e) {// 提交
		e.preventDefault();
		
		var id = model.getFormData().qdid;
		var zhczrxm = model.getFormData().zhczrxm;
		var optUrl = model.path + "/save.do";
		if (zhczrxm) {
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
			model.setFormDataById(rows[0].qdid);
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
					id : rows[0].qdid
				}, function(respone) {
					if (respone.responeCode == "0") {
						layer.msg("删除成功");
						model.tableRefresh();
					} 
				});
			});
		}
	});



	$("body").on("click", ".updateState", function() {
		var id = $(this).data("id");
		var status = $(this).data("status");

		layer.confirm('确定执行'+(status == '01'?'启用':'停用')+"操作?", {
			shadeClose : true,
			icon : 3,
			anim : 6,
			btn : [ '确定', '取消' ]
			// 按钮
		}, function() {
			$.post(model.path + "/updateDqzt.do",{id:id,qdzt:status},function(respone){
				layer.msg("操作成功");
				model.tableRefresh();
			});
		});


	});

	
	// 列表
	$('#table').bootstrapTable({
		url : model.path + '/qryPage.do',
		columns : [ {
			checkbox : true,
			align:"center",
			halign:'center'
		}, 
			{
			field : 'qdid',
			title : '渠道ID',
            formatter : function(value, row, index) {
			    return "<a href='#' class='view text-success' data-id='" + row.qdid + "'>" + value + "</a>"
			 }
			},
			{
			field : 'qdmc',
			title : '渠道名称'
			},

			{
			field : 'qdlxr',
			title : '渠道联系人'
			},
			{
			field : 'sjhm',
			title : '联系人手机号码'
			},

			{
				field : 'drfwsx',
				title : '单日访问上限(次)'
			},
			{
				field : 'drywlsx',
				title : '单日业务上限(笔)'
			},
			{
				field : 'zdbfl',
				title : '并发访问上限'
			},
			{
				field : 'tzjrtj',
				title : '停止接入条件',
				formatter : function(value, row, index) {
					return $.getDictName('TZTJ',value);
				}
			},
		/*	{
			field : 'beizhu',
			title : '备注',
			},*/
			//{
			//field : 'token',
			//title : '访问令牌'
			//},
			{
				field : 'qdzt',
				title : '渠道状态 ',//01 启用  02 停用  03 已销户
				formatter : function(value, row, index) {
				    return $.getDictName('QDZT',value);
				 }
			},

			{
				field:'qdktrq',
				title:'渠道开通日期'
			},

			{
				field:'oper',
				title:'操作',
				formatter : function(value, row, index) {
					var oper =  "<a  href='#' class='text-success updateState sf-permission-ctl'  data-id='" + row.qdid+ "' data-sf-permission='zhfw:fwflb:save' data-status='01'>启用</a>";
					if(row.qdzt == '01'){
						 oper =  "<a  href='#' class='text-success updateState sf-permission-ctl'  data-id='" + row.qdid+ "' data-sf-permission='zhfw:fwflb:save' data-status='02'>停用</a>";
					}

					return oper;
				}
			}
		 ]
	});
	
});
