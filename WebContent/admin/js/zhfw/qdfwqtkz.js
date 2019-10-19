$(function() {
	var model = {
		path : adminContextPath + "/zhfw/qdfwjrxx",
		resetDataForm : function() {
			$("#data-form").bootstrapValidator('resetForm', true);
			// 表单默认值可以在这里设置
			way.set("model.form.data", null);
		},

		disableservice : function(Ids) {
			layer.confirm("确定禁用服务?", {
				shadeClose : true,
				icon : 3,
				anim : 6,
				btn : [ '确定', '取消' ]
			}, function() {
				$.post(model.path + "/Discontinuation.do", $.param({
					Ids : Ids
				}, true), function(respone) {
					if (respone.responeCode == "0") {
						layer.msg("服务禁用已申请");
						
					}else if(respone.responeCode == "1"){
						layer.msg("该服务已禁用");
					}
					model.tableRefresh();
				});
			});
		},
		enableservice : function(Ids) {
			layer.confirm("确定启用服务?", {
				shadeClose : true,
				icon : 3,
				anim : 6,
				btn : [ '确定', '取消' ]
			}, function() {
				$.post(model.path + "/startUp.do", $.param({
					Ids : Ids
				}, true), function(respone) {
					if (respone.responeCode == "0") {
						layer.msg("服务启用已申请");
						
					}else if(respone.responeCode == "1"){
						layer.msg("该服务已启用");
					}
					model.tableRefresh();
				});
			});
		},
		approvalservice : function(Ids) {
			layer.confirm("确定同意申请?", {
				shadeClose : true,
				icon : 3,
				anim : 6,
				btn : [ '确定', '取消' ]
			}, function() {
				$.post(model.path + "/adopt.do", $.param({
					Ids : Ids
				}, true), function(respone) {
					if (respone.responeCode == "0") {
						layer.msg("申请已通过");
						model.sptableRefresh();
					}
				});
			});			
		},
		dismissservice : function(Ids) {
			layer.confirm("确定驳回申请?", {
				shadeClose : true,
				icon : 3,
				anim : 6,
				btn : [ '确定', '取消' ]
			}, function() {
				$.post(model.path + "/dismiss.do", $.param({
					Ids : Ids
				}, true), function(respone) {
					if (respone.responeCode == "0") {
						layer.msg("申请已驳回");
						model.sptableRefresh();
					}
				});
			});
		},

		getFormData : function() {
			var data = way.get("model.form.data");
			return data ? data : {};
		},
		setFormTitle : function(title) {
			way.set("model.form.title", title);
		},
		tableRefresh : function() {
			$('#table').bootstrapTable("refresh");
		},
		sptableRefresh : function() {
			$('#sptable').bootstrapTable("refresh");
		},
		setFormDataById : function(id) {
			$.get(this.path + "/get.do", {
				id : id
			}, function(respone) {
				way.set("model.form.data", respone.data);
			})
		},
		setViewDataById : function(id) {
			$.get(this.path + "/get.do", {
				id : id
			}, function(respone) {
				way.set("model.view", respone.data);
				way.set("model.view.fwzt", $.getDictName('start_stop',
						respone.data.fwzt));
				way.set("model.view.sqzt", $.getDictName('start_stop',
						respone.data.sqzt));
			})
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
	// // 添加
	// $("#add").click(function() {
	// model.resetDataForm();
	// model.setFormTitle("<i class='fa fa-plus'>添加</i>");
	// $("#form-panel").modal('toggle');
	// });

	// 禁用服务
	$("#edit").click(function() {
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			var Ids = [];
			$.each(rows, function(i, v) {
				Ids.push(v.id);
			});
			model.disableservice(Ids);
		}
	});
	// 启用服务
	$("#add").click(function() {
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			var Ids = [];
			$.each(rows, function(i, v) {
				Ids.push(v.id);
			});
			model.enableservice(Ids);
		}
	});
	// 审批服务
	$("#adopt").click(function() {
		var rows = $('#sptable').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			var Ids = [];
			$.each(rows, function(i, v) {
				Ids.push(v.id);
			});
			model.approvalservice(Ids);
		}
	});
	// 驳回申请
	$("#dismiss").click(function() {
		var rows = $('#sptable').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			var Ids = [];
			$.each(rows, function(i, v) {
				Ids.push(v.id);
			});
			model.dismissservice(Ids);
		}
	});

	// 重置
	$("#reset").click(function() {
		way.set("model.search", null);
		model.tableRefresh();
	});
	// 审批列表
	$('#sptable').bootstrapTable({
		url : model.path + '/spxx.do',
		singleSelect : false,
		columns : [ {
			checkbox : true
		},
		// {
		// field : 'ywlsh',
		// title : '业务流水号',
		// formatter : function(value, row, index) {
		// return "<a href='#' class='view text-success' data-id='" + row.id +
		// "'>" + value + "</a>"
		// }
		// },
		// {
		// field : 'qdid',
		// title : '渠道'
		// },
		{
			field : 'qdid',
			title : '渠道名称'
		}, {
			field : 'fwid',
			title : '服务id'
		}, {
			field : 'fwsm',
			title : '服务名称'
		}, {
			field : 'flmc',
			title : '服务分类'
		}, {
			field : 'fwxz',
			title : '服务性质',
			formatter : function(value, row, index) {
				return $.getDictName('FWXZ', value);
			}
		}, {
			field : 'dbsx',
			title : '单笔上限'
		}, {
			field : 'dbxx',
			title : '单笔下限'
		}, {
			field : 'drsx',
			title : '单日上限'
		}, {
			field : 'drywsx',
			title : '单日业务上限'
		}, {
			field : 'fwzt',
			title : '服务状态',
			formatter : function(value, row, index) {
				return $.getDictName('FWZT', value);
			}
		}, {
			field : 'bgzt',
			title : '申请变更状态',
			formatter : function(value, row, index) {
				return $.getDictName('BGZT', value);
			}
		}, {
			field : 'sqzt',
			title : '审核状态',
			formatter : function(value, row, index) {
				return $.getDictName('SQZT', value);
			}
		} ]
	});

	// 控制列表
	$('#table').bootstrapTable({
		url : model.path + '/qryPage.do',
		singleSelect : false,
		columns : [ {
			checkbox : true
		},
		// {
		// field : 'ywlsh',
		// title : '业务流水号',
		// formatter : function(value, row, index) {
		// return "<a href='#' class='view text-success' data-id='" + row.id +
		// "'>" + value + "</a>"
		// }
		// },
		// {
		// field : 'qdid',
		// title : '渠道'
		// },
		{
			field : 'qdid',
			title : '渠道名称',
			formatter : function(value, row, index) {
				return $.getQdName(value);
			}
		}, {
			field : 'fwid',
			title : '服务id'
		}, {
			field : 'fwsm',
			title : '服务名称'
		}, {
			field : 'flmc',
			title : '服务分类'
		}, {
			field : 'fwxz',
			title : '服务性质',
			formatter : function(value, row, index) {
				return $.getDictName('FWXZ', value);
			}
		},
		/*
		 * { field : 'dbsx', title : '单笔上限' }, { field : 'dbxx', title : '单笔下限' }, {
		 * field : 'drsx', title : '单日上限' }, { field : 'drywsx', title :
		 * '单日业务上限' },
		 */
		{
			field : 'fwzt',
			title : '服务状态',
			formatter : function(value, row, index) {
				return $.getDictName('FWZT', value);
			}
		}, {
			field : 'bgzt',
			title : '申请变更状态',
			formatter : function(value, row, index) {
				return $.getDictName('BGZT', value);
			}
		}, {
			field : 'sqzt',
			title : '审核状态',
			formatter : function(value, row, index) {
				return $.getDictName('SQZT', value);
			}
		} ]
	});

});
