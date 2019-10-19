$(function() {
    	var model = {
		path : adminContextPath + "/zhfw/lyhfgd",
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
		setTreeNodeMap: function (arr) { //储存分类key value
            for (var i = 0; i < arr.length; i++) {
                way.set("model.flTree.flid" + arr[i].id, arr[i].flmc);
            }
        },
        getTreeNodeName :function (flids) {//根据分类key 和 parentIds找所有父节点名称
	    var name = '';
	    var flidArr = flids.split(",");
	    for(var i = 0 ; i < flidArr.length;i++){
            var result = way.get("model.flTree.flid"+flidArr[i]);
	        if (result){
                if(name){
                    name += ' ';
                }
	            name += result;
            }
        }
            return name;
        },
        setViewDataById: function (id) {
            $("#modal-view").find(".form-control-static").empty();
            $.get(this.path + "/get.do", {id: id}, function (respone) {
            	console.log(respone);
                way.set("model.view", respone.data);  
                way.set("model.view.lyqd", $.getDictName('XXFBQD',respone.data.lyqd));
                way.set("model.view.classid", $.getDictName('CLASSID', respone.data.classid)); 
                way.set("model.view.shzt", $.getDictName('SHZT', respone.data.shzt));          
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
	// 校验
	$("#data-form").bootstrapValidator().on("success.form.bv", function(e) {// 提交
		e.preventDefault();
		var rows = $('#table').bootstrapTable("getSelections");
		var id = rows[0].guestbookid;
		var optUrl = model.path + "/update.do?id="+id;	
		$.post(optUrl,$("#data-form").serialize(), function(respone) {
			if (respone.responeCode == '0') {
				layer.msg("保存成功");
				model.tableRefresh();
				$("#form-panel").modal('toggle');
			}
		});
	});

	//点击服务分类选择框
	$("#form-zskfl-input").click(function(){
		$.seezoon.chooseZskfl(way.get("model.form.data.flid"),function(treeNode){
			way.set("model.form.data.flid",treeNode.id);
            way.set("model.form.data.zslx",treeNode.zslx);
			way.set("model.form.data.flmc",treeNode.flmc);
			way.set("model.form.data.gjz",model.getTreeNodeName(treeNode.parentIds)+" "+treeNode.flmc);
			return true;
		},function(){
			way.set("model.form.data.flid",null);
			way.set("model.form.data.flmc",null);
		},{'zslx':zslx});
	});

	/**
	 * 查看
	 */
	$("body").on("click", ".view", function() {				
		
		var id = $(this).data("id");
		model.setViewDataById(id);
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
	// 查询
	$("#search").click(function() {
		model.tableRefresh();
	});
	// 加入知识库
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
					way.set("model.form.data.fxmc",respone.data.title);
					way.set("model.form.data.zsnr",respone.data.recontent);
				}
			});
			way.set("model.form.title", "<i class='fa fa-plus'>加入知识库</i>");

			$("#form-panel").modal('toggle');
		}
		
	});


	// 列表
	$('#table').bootstrapTable({
		url : model.path + '/qryPage.do',
		columns : [ {
			checkbox : true
		}, {
			field : 'title',
			title : '留言标题',
			formatter: function (value, row, index) {
                return "<a href='#' class='view text-success' data-id='" + row.guestbookid + "'>" + value + "</a>"
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
