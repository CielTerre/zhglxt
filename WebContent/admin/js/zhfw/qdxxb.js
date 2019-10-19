$(function() {
    	var model = {
		path : adminContextPath + "/zhfw/qdxxb",
		resetDataForm : function() {
			$("#data-form").bootstrapValidator('resetForm', true);
			//表单默认值可以在这里设置
			way.set("model.form.data",null);
		},
		binding : function(id,status) {
			layer.confirm("确定绑定/解绑?", {
				shadeClose : true,
				icon : 3,
				anim : 6,
				btn : [ '确定', '取消' ]
			}, function() {
				$.post(model.path + "/bindServiceList.do", $.param({
			    fwid:id,qdid:way.get('model.fwsearch.qdid') ,status:status
				}, true), function(respone) {
					layer.msg("操作成功");
					$('#fwTable').bootstrapTable("refresh");
				});
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
				    way.set("model.view.qdzt",$.getDictName('QDZT',respone.data.qdzt));
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
	// 服务配置弹出窗查询
	$("#fwcx").click(function() {
		$('#fwTable').bootstrapTable("refresh");
	});

	// 服务配置弹出窗重置
	$("#reset").click(function() {
		var qdid = way.get("model.fwsearch.qdid");
		way.set("model.fwsearch.qdid",qdid);
		way.set("model.fwsearch",null);
		$('#fwTable').bootstrapTable("refresh");
	});

	//配置渠道服务
	$("#config").click(function() {
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {



			way.set("model.fwsearch",null);
			way.set("model.fwsearch.qdid",rows[0].qdid);
			$('#fwTable').bootstrapTable("refresh");
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
						way.set("model.fwsearch.fwfl",treeNode.id);
						$('#fwTable').bootstrapTable("refresh");
					}
				}
			};
			$.post(adminContextPath + "/zhfw/fwflb/qryPage.do", function(respone) {
				//选择上级tree
				$.fn.zTree.init($("#fwflMenuTree"), setting,respone.data);
				var treeObj = $.fn.zTree.getZTreeObj("fwflMenuTree");
				treeObj.expandAll(true);
			});

			layer.open({
				title:"<i class='fa fa-plus'>服务配置</i>",
				shadeClose:true,
				icon: 1,
				area: ['80%', '80%'],
				type: 1,
				maxmin:true,
				content:  $("#form-config")
			});


		//	model.setFormTitle("<i class='fa fa-edit'>服务配置</i>");
			//$("#form-config").modal('toggle');
			configTableInit(rows[0].qdid);
		}
	});

	//绑定解绑操作
	$("body").on("click", ".updateState", function() {
		var id = $(this).data("id");
		var status = $(this).data("status");
		var fwsm =  $(this).data("fwsm");
		layer.confirm('确定执行'+(status == '1'?'解绑':'绑定')+"服务("+fwsm+")?", {
			shadeClose : true,
			icon : 3,
			anim : 6,
			btn : [ '确定', '取消' ]
			// 按钮
		}, function() {
			$.post(model.path + "/bindService.do",{fwid:id,qdid:way.get('model.fwsearch.qdid') ,status:status},function(respone){
				layer.msg("操作成功");
				$('#fwTable').bootstrapTable("refresh");
			});
		});
	});
  //批量绑定/解绑
	//$("#binding").click(function() {
	//	var rows = $('#fwTable').bootstrapTable("getSelections");
	//	if (rows.length == 0) {
	//		layer.msg("请选择一行");
	//	} else {
	//		var id = [];
	//		var status = [];
	//		$.each(rows, function(i, v) {
	//			id.push(v.FWID);
	//			status.push(v.BINDSTATE)
	//		});
	//		model.binding(id,status);
	//	}
	//});
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

	$("#genToken").click(function(){
		var id = model.getFormData().qdid;
		var optUrl = model.path + "/genToken.do";
		$.post(optUrl, $("#data-form").serialize(), function(respone) {
			way.set("model.form.data.token", respone.data);
		});
	})

	
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
		/*	{
			field : 'qdsm',
			title : '渠道说明信息',
			},
			{
			field : 'qdjrip',
			title : '渠道接入IP',
			},
			{
			field : 'qdjrzh',
			title : '渠道接入账号',
			},
			{
			field : 'qdjrmm',
			title : '渠道接入密码',
			},*/
			{
			field : 'qdlxr',
			title : '渠道联系人'
			},
			{
			field : 'sjhm',
			title : '联系人手机号码'
			},
	/*		{
			field : 'zdbfl',
			title : '最大并发量',
			},
			{
			field : 'cgbflts',
			title : '超过最大并发量时提示信息',
			},*/
	
		/*	{
			field : 'beizhu',
			title : '备注',
			},*/
			{
			field : 'token',
			title : '访问令牌'
			},
			{
				field : 'qdzt',
				title : '渠道状态 ',//01 启用  02 停用  03 已销户
				formatter : function(value, row, index) {
				    return $.getDictName('QDZT',value);
				 }
			},
		/*	{
			field : 'drfwsx',
			title : '单日访问上限',
			},
			{
			field : 'tzjrtj',
			title : '停止接入条件',
			},*/
				

			{
				field:'qdktrq',
				title:'渠道开通日期'
			}
		 ]
	});
	
});

function configTableInit(qdid){
// 列表
	$('#fwTable').bootstrapTable({
		url :  adminContextPath + "/zhfw/fwdjxx" + '/qryBindService.do',
		pagination:false,
		queryParams:function(){
			return {fwsm:way.get("model.fwsearch.fwsm") , fwfl: way.get("model.fwsearch.fwfl"),qdid:way.get("model.fwsearch.qdid")};
		},
		height:600,
		fixedScroll:true,
		responseHandler : function(res) {
			return {
				rows : res.data
			};
		},
		singleSelect:false,
		columns : [ {
			checkbox : true
		},
			{
				field : 'FWID',
				title : '服务ID'
			},
			//{
			//	field : 'fwmc',
			//	title : '接口'
			//},
			{
				field : 'FWSM',
				title : '服务名称'
			},
			{
				field : 'FLMC',
				title : '服务分类'
			},
			{
				field:'BINDSTATE',
				title:'操作',
				formatter : function(value, row, index) {
					var oper =  "<a  href='#' class='text-success updateState sf-permission-ctl'  data-id='" + row.FWID+ "' data-fwsm='" + row.FWSM+ "' data-sf-permission='zhfw:fwflb:save' data-status='0'>绑定</a>";
					if(row.BINDSTATE == '1'){
						oper =  "<a  href='#' class='text-danger updateState sf-permission-ctl'  data-id='" + row.FWID+ "' data-fwsm='" + row.FWSM+ "' data-sf-permission='zhfw:fwflb:save' data-status='1'>解绑</a>";
					}

					return oper;
				}
			}
		]
	});
}
