$(function() {
    	var model = {
		path : adminContextPath + "/zhfw/zskmx",
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
            $("#modal-view").find(".form-control-static").empty();
			$.get(this.path + "/get.do",{id:id},function(respone){
				way.set("model.view",respone.data);
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
		init:function(){//需要初始化的功能
			//左侧部门树
			var setting = {
				data : {
					simpleData : {
						enable : true,
						idKey : "id",
						pIdKey : "parentId"
					},
					key:{
						name:'flmc'
					}
				},
				callback : {
                    onRightClick:OnRightClick,
					onClick : function(event, treeId, treeNode) {
						way.set("model.search.flid",treeNode.id);
						way.set("model.search.zslx",zslx);

						model.tableRefresh();
					}
				}
			};

			way.set("model.search.zslx",zslx);

			$.post(adminContextPath + "/zhfw/zskfl/qryPage.do",{'zslx':zslx}, function(respone) {
				//选择上级tree
				$.fn.zTree.init($("#zskflMenuTree"), setting,respone.data);
				var treeObj = $.fn.zTree.getZTreeObj("zskflMenuTree");
				treeObj.expandAll(true);
                model.setTreeNodeMap(respone.data);
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

	//点击服务分类选择框
	$("#form-zskfl-input").click(function(){
		$.seezoon.chooseZskfl(way.get("model.form.data.flid"),function(treeNode){

			way.set("model.form.data.flid",treeNode.id);
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
	// 查询
	$("#search").click(function() {
		model.tableRefresh();
	});
	// 添加
	$("#add").click(function() {
		model.resetDataForm();
		way.set("model.form.data.zslx",zslx);
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
			model.setFormDataById(rows[0].id);
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
		columns : [ {
			checkbox : true
		},
			{
				field : 'flmc',
				title : '分类名称'
			},
			{
				field : 'fxmc',
				title : '分项名称',
				formatter : function(value, row, index) {
					return "<a href='#' class='view text-success' data-id='" + row.id + "'>" + value + "</a>"
				}
			},
            {
                field : 'gjz',
                title : '关键字'
            },
            {
                field : 'llrq',
                title : '录入日期',
				formatter:function (value) {
					return value.substring(0,11);
                }
            }
		 ]
	});


	// 在ztree上的右击事件
    function OnRightClick(event, treeId, treeNode) {
        if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
            currNode = null;
            showRMenu("root", event.clientX, event.clientY);
        } else if (treeNode && !treeNode.noR) {
            currNode = treeNode;
            showRMenu("node", event.clientX, event.clientY);
        }
    }

	//显示右键菜单
    function showRMenu(type, x, y) {
    	if(type == 'root'){
    		$("#rMenu a[name='d']").css("visibility","hidden"); //右击不是树节点时 不显示删除
    		$("#rMenu a[name='u']").css("visibility","hidden"); //右击不是树节点时 不显示修改
		}else if(type =='node'){
            $("#rMenu a[name='d']").css("visibility","");
            $("#rMenu a[name='u']").css("visibility","");
		}
        $("#rMenu ul").show();
        $("#rMenu").css({"top": y + "px", "left": x + "px", "visibility": "visible"}); //设置右键菜单的位置、可见
        $("body").bind("mousedown", onBodyMouseDown);
    }

	//隐藏右键菜单
    function hideRMenu() {
        if ($("#rMenu")) $("#rMenu").css({"visibility": "hidden"}); //设置右键菜单不可见
        $("body").unbind("mousedown", onBodyMouseDown);
    }

    //鼠标按下事件 鼠标点别处 隐藏菜单
    function onBodyMouseDown(event) {
        if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
            $("#rMenu").css({"visibility": "hidden"});
        }
    }

    $("#rMenu a").click(function (event) {
        way.set("model.zskfl.form.data",null); //重置form
        way.set("model.zskfl.form.data.zslx",zslx);
        switch (event.target.name){
			case 'c':
                way.set("model.zskfl.form.title","新增");
                way.set("model.zskfl.form.data.parentId", currNode ? currNode.id : '');
                $("#zskfl-form-panel").modal('toggle'); //显示
				break;
			case 'u':
                way.set("model.zskfl.form.title","修改");
                way.set("model.zskfl.form.data.id",currNode.id);
                way.set("model.zskfl.form.data.flmc",currNode.flmc);
                way.set("model.zskfl.form.data.flcx",currNode.flcx);
                $("#zskfl-form-panel").modal('toggle'); //显示
				break;
			case 'd':
                layer.confirm('确定删除？', {
                    shadeClose : true,
                    icon : 3,
                    anim : 6,
                    btn : [ '确定', '取消' ]
                    // 按钮
                }, function() {
                    $.post(adminContextPath + "/zhfw/zskfl/delete.do", {
                        id : currNode.id
                    }, function(respone) {
                        if (respone.responeCode == "0") {
                            layer.msg("删除成功");
                            model.init();
                        }
                    });
                });
				break;
			default:
				console.log("不知道点了哪里");
		}
    });

    $("#fl-save").click(function() {// 提交
        var data = way.get("model.zskfl.form.data");
        data = data ? data : {};
        var id = data.id;
        var optUrl = adminContextPath + "/zhfw/zskfl/save.do";
        if (id) {
            optUrl = adminContextPath + "/zhfw/zskfl/update.do";
        }
        $.post(optUrl, $("#zskfl-data-form").serialize(), function(respone) {
            if (respone.responeCode == '0') {
                layer.msg("保存成功");
                model.init();
                $("#zskfl-form-panel").modal('toggle');
            }
        });
    });
});
