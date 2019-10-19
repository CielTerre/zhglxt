$(function() {
	var model = {
		path : adminContextPath + "/zhfw/lysh",
		disableservice : function(Ids) {
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
						layer.msg("通过成功");
					}
					model.tableRefresh();
				});
			});
		},
		enableservice : function(Ids) {
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
						layer.msg("驳回成功");
					}
					model.tableRefresh();
				});
			});
		},

		tableRefresh : function() {
			$('#table').bootstrapTable("refresh");
		},
		getFormData : function() {
			var data = way.get("model.view.data");
			return data ? data : {};
		},
		setFormTitle : function(title) {
			way.set("model.form.title", title);
		},
		setViewDataById : function(id) {
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
	};
	/**
	 * 查看
	 */
	$("body").on("click", ".view", function() {
		var id = $(this).data("id");
		model.setViewDataById(id);
		$("#modal-view").modal('toggle');
	});
	/**
	 * 工单查看
	 */
	$("body").on("click", ".gdview", function() {
		var id = $(this).data("id");
		model.setGdViewDataById(id);
		$('#modal-gdview').modal('toggle');
	});
	// 查询
	$("#search").click(function() {
		model.tableRefresh();
	});

	// 通过
	$("#adopt,#tg").click(function() {
		var rows = $('#table').bootstrapTable("getSelections");
		var Ids = [];
		$.each(rows, function(i, v) {
			Ids.push(v.guestbookid);
		});
		if(!Ids.length){
			
			Ids=way.get("model.view.guestbookid");
		}
		model.disableservice(Ids);

	});
	// 驳回
	$("#dismiss,#bh").click(function() {
		var rows = $('#table').bootstrapTable("getSelections");
		var Ids = [];
		$.each(rows, function(i, v) {
			Ids.push(v.guestbookid);
		});
		if(!Ids.length){
			
			Ids=way.get("model.view.guestbookid");
		}
		console.log(Ids);
		model.enableservice(Ids);
	});

	// 列表
	$('#table')
			.bootstrapTable(
					{
						url : model.path + '/qryPage.do',
						// singleSelect : false,
						columns : [
								{
									checkbox : true
								},
								{
									field : 'title',
									title : '留言标题',
									formatter : function(value, row, index) {
										return "<a href='#' class='view text-success' data-id='"
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
									title : '留言人'
								}, {
									field : 'lxfs',
									title : '联系方式'
								}, {
									field : 'addtime',
									title : '留言日期'
								}, {
									field : 'shzt',
									title : '处理状态',
									formatter : function(value, row, index) {
										return $.getDictName('SHZT', value);
									}
								}, {
									field : 'retime',
									title : '回复时间'
								},{
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
