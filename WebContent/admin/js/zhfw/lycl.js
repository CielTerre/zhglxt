$(function() {
	var model = {
		path : adminContextPath + "/zhfw/lycl",
		resetDataForm : function() {
			$("#data-form").bootstrapValidator('resetForm', true);
			// 表单默认值可以在这里设置
			way.set("model.form.data", null);
			$('#data-form').find('input,textarea,select,button').attr(
					'disabled', false);
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
		setFormDataById : function(id) {
			$.get(this.path + "/get.do", {
				id : id
			}, function(respone) {
				console.log(respone);
				way.set("model.form", respone);
				way.set("model.form.data.lyqd", $.getDictName('XXFBQD',
						respone.data.lyqd));
				way.set("model.form.data.classid", $.getDictName('CLASSID',
						respone.data.classid));
			});
		},
		 //留言
		setLyViewDataById : function(id) {
			$("#modal-view").find(".form-control-static").empty();
			$.get(this.path + "/get.do", {
				id : id
			}, function(respone) {				
				way.set("model.view", respone.data);
				way.set("model.view.lyqd", $.getDictName('XXFBQD',
						respone.data.lyqd));
				way.set("model.view.classid", $.getDictName('CLASSID',
						respone.data.classid));
				way.set("model.view.shzt", $.getDictName('SHZT',
						respone.data.shzt));

			})
		},
		//工单
		 setGdViewDataById: function (id) {
	            $("#modal-gdview").find(".form-control-static").empty();
	            $.get(this.path + "/getgd.do", {id: id}, function (respone) {
	                way.set("model.gdview", respone.data);
	                way.set("model.gdview.lyqdid", $.getQdName(respone.data.lyqdid));
	                way.set("model.gdview.gdly", $.getDictName('GDLY', respone.data.gdly));
	                way.set("model.gdview.jjcd", $.getDictName('JJCD', respone.data.jjcd));
	                way.set("model.gdview.dqzt", $.getDictName('CLZT', respone.data.dqzt));
	                way.set("model.gdview.yqwcsj", respone.data.yqwcsj ? respone.data.yqwcsj.substring(0, 10) : '');
	                way.set("model.gdview.clr", $.getSysUserName(respone.data.clr));
	                way.set("model.gdview.hfzt", $.getDictName('yes_no', respone.data.hfzt));
	                way.set("model.gdview.hfsj", respone.data.hfsj ? respone.data.hfsj.substring(0, 10) : '');
	                way.set("model.gdview.yhmyd", $.getDictName('YHMYD', respone.data.yhmyd));
	            });
	        },
		init : function() {// 需要初始化的功能
		}
	};
	model.init();
	// 校验
	$("#data-form").bootstrapValidator().on("success.form.bv", function(e) {//生成工单 保存
		e.preventDefault();				
		var rows = $('#table').bootstrapTable("getSelections");
		var id = rows[0].guestbookid;
		console.log(rows);
		console.log(rows[0].gdbh);
		var optUrl = model.path + "/save.do?id="+id+"&gdbhs="+rows[0].gdbh;
		$.post(optUrl, $("#data-form").serialize(), function(respone) {
			if (respone.responeCode == '0') {				
				layer.msg("保存成功");								
			}else if(respone.responeCode == '1'){
				layer.msg("工单已保存过了");	
			}
			$("#form-panel").modal('toggle');
			model.tableRefresh();
		});
	});
	$("#form-hfly").bootstrapValidator().on("success.form.bv", function(e) {//回复留言 保存
		e.preventDefault();	
		var rows = $('#table').bootstrapTable("getSelections");
		var id = rows[0].guestbookid;
	    optUrl = model.path + "/update.do?id="+id;

		$.post(optUrl, {recontent:way.get("model.form.data.recontent")}, function(respone) {
			if (respone.responeCode == '0') {
				layer.msg("保存成功");
				model.tableRefresh();
				$("#form-hfly").modal('toggle');
			}
		});
	});

	// 查询
	$("#search").click(function() {
		model.tableRefresh();
	});
	//选择的查询
	$("#xzsearch").click(function() {
		//console.log(model.xzform.model.search.gjz);
		way.set("model.search.gjz",way.get("model.xzform.model.search.gjz"));
		$('#xztable').bootstrapTable("refresh");
	});
	// 生成工单
	$("#add").click(function() {
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			model.resetDataForm();
			$.get(model.path + "/get.do", {
				id : rows[0].guestbookid
			}, function(respone) {
				if (respone.responeCode == '0') {									
					way.set("model.gdform.data.lyqdid", respone.data.lyqd);
					way.set("model.gdform.data.gdbt", respone.data.title);
					way.set("model.gdform.data.gdnr",  respone.data.content);
					way.set("model.gdform.data.xgywlsh",  respone.data.guestbookid);
				}
			});
			way.set("model.gdform.title", "<i class='fa fa-plus'>生成工单</i>");
			$("#form-panel").modal('toggle');
		}
	});
	// 提交审核
	$("#submission").click(function() {
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			layer.confirm("确定提交?", {
				shadeClose : true,
				icon : 3,
				anim : 6,
				btn : [ '确定', '取消' ]
			}, function() {
				$.post(model.path + "/submission.do", {
					id : rows[0].guestbookid
				}, function(respone) {
					if (respone.responeCode == "0") {
						layer.msg("提交成功");
					}
					model.tableRefresh();
				});
			});
		}
	});

	/**
	 * 留言查看
	 */
	$("body").on("click", ".lyview", function() {
		var id = $(this).data("id");
		model.setLyViewDataById(id);
		$('#modal-view').modal('toggle');
	});
	/**
	 * 工单查看
	 */
	$("body").on("click", ".gdview", function() {
		var id = $(this).data("id");
		model.setGdViewDataById(id);
		$('#modal-gdview').modal('toggle');
	});
	// 回复留言
	$("#edit").click(function() {
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			model.resetDataForm();
			model.setFormDataById(rows[0].guestbookid);
			model.setFormTitle("<i class='fa fa-edit'>回复留言</i>");
			$("#form-hfly").modal('toggle');
		}
	});
	// 选择
	$("#buttonssion").click(function() {
		$("#form-xz").modal('toggle');
	});
	// 保存
	$("#xz").click(function() {
		var rows = $('#xztable').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			way.set("model.form.data.recontent", rows[0].zsnr);
		}
	});
	// 删除
	$("#delete").click(function() {
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			layer.confirm("确定删除?", {
				shadeClose : true,
				icon : 3,
				anim : 6,
				btn : [ '确定', '取消' ]
			}, function() {
				$.post(model.path + "/delete.do", {
					id : rows[0].guestbookid
				}, function(respone) {
					if (respone.responeCode == "0") {
						layer.msg("删除成功");
					}
					model.tableRefresh();
				});
			});
		}
	});
	// 选择列表
	$('#xztable').bootstrapTable({
		url : model.path + '/zsnr.do',
		
		columns : [ {
			checkbox : true
		}, {
			field : 'zsnr',
			title : '知识内容',
		} ]
	});

	// 列表
	$('#table')
			.bootstrapTable(
					{
						url : model.path + '/qryPage.do',
						//singleSelect : false,
						columns : [
								{
									checkbox : true
								},
								{
									field : 'title',
									title : '留言标题',
									formatter : function(value, row, index) {
										return "<a href='#' class='lyview text-success' data-id='"
												+ row.guestbookid
												+ "'>"
												+ value + "</a>"
									}
								}, {
									field : 'classid',
									title : '留言类型',
									formatter : function(value, row, index) {
										return $.getDictName('CLASSID', value);
									}
								}, {
									field : 'lyqd',
									title : '来源渠道',
									formatter : function(value, row, index) {
										return $.getDictName('XXFBQD', value);
									}
								}, {
									field : 'username',
									title : '留言人',
								}, {
									field : 'lxfs',
									title : '联系方式',
								}, {
									field : 'addtime',
									title : '留言日期',
								}, {
									field : 'shzt',
									title : '处理状态',
									formatter : function(value, row, index) {
										return $.getDictName('SHZT', value);
									}
								}, {
									field : 'retime',
									title : '回复时间',
								}, {
									field : 'gdbh',
									title : '工单编号',
									formatter : function(value, row, index) {
										return "<a href='#' class='gdview text-success' data-id='"
												+ row.ywlsh
												+ "'>"
												+ (row.gdbh==undefined?"":row.gdbh) + "</a>"
												
									}
								}

						]
					});

});
