$(function() {
		
		//weibo();
	
    	var model = {
		path : adminContextPath + "/zhfw/zhfwptqtarticle",
		resetDataForm : function() {
			$("#data-form").bootstrapValidator('resetForm', true);
			var dqzt = model.getFormData().dqzt;	//用变量暂时保存该值
			
			//表单默认值可以在这里设置
			way.set("model.form.data",null);	//情况表单数据
			//设置文章发布时间
			way.set("model.form.data.wzfbsj", getNowFormatDate());
			//设置文章发布状态
			way.set("model.form.data.dqzt", dqzt);
			
			//清楚残留的文章内容信息
			editor.html("");
			//清楚残留的发布渠道信息
			$("#xxfbqd-checkbox").find("input:checkbox").each(function(){
				$(this).prop("checked",false);
			});
			
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
				
				console.log(respone);
				
				//设置文章发布渠道的值
				var qdidStr = respone.data.qdid;
				$("#xxfbqd-checkbox").find("input:checkbox").each(function(){
					$(this).prop("checked",false);
				});
				if(qdidStr != null){
					if(qdidStr.indexOf(",") != -1){
						var qdidArr = qdidStr.split(",");
						$("#xxfbqd-checkbox").find("input:checkbox").each(function(){
							var val = $(this).val();
							for(var i=0; i<qdidArr.length; i++){
								if(val == qdidArr[i]){
									$(this).prop("checked",true);
								}
							}
						});
					}else{
						$("#xxfbqd-checkbox").find("input:checkbox").each(function(){
							var val = $(this).val();
							if(val == qdidStr){
								$(this).prop("checked",true);
							}
						});
					}
				}
				
				//设置文章内容的值
				var content = respone.data.content;
				editor.html("");
				editor.html(content);
				
				//设置附加选项的值
				var istop = respone.data.istop;
				if(istop == '01'){
					respone.data.istop = 'on';
				}
				var ishot = respone.data.ishot;
				if(ishot == '01'){
					respone.data.ishot = 'on';
				}
				var isflash = respone.data.isflash;
				if(isflash == '01'){
					respone.data.isflash = 'on';
				}
				
				way.set("model.form.data",respone.data);
			})
		},
		setViewDataById:function(id){
			$.get(this.path + "/get.do",{id:id},function(respone){
				//转换文章审核状态
				//$.getDictName('XXFBZT',value);
				var dqzt = respone.data.dqzt;
				dqzt = $.getDictName('XXFBZT',dqzt);
				respone.data.dqzt = dqzt;
				
				//转换文章发布渠道
				var qdidName = '';
				var qdid = respone.data.qdid;
				if(qdid.indexOf(",") != -1){
					var arr = qdid.split(",");
					for(var i=0; i<arr.length; i++){
						qdidName += ($.getDictName('XXFBQD',arr[i]) + ",");
					}
					qdidName = qdidName.substring(0, qdidName.lastIndexOf(","));
				}else{
					qdidName = $.getDictName('XXFBQD',qdid);
				}
				respone.data.qdgxid = qdidName;
				
				//转换是否置顶状态
				var istop = respone.data.istop;
				istop = $.getDictName('ISTOP',istop);
				respone.data.istop = istop;
				
				//转换是否推荐状态
				var ishot = respone.data.ishot;
				ishot = $.getDictName('ISHOT',ishot);
				respone.data.ishot = ishot;
				
				//中是否为图片状态
				var isflash = respone.data.isflash;
				isflash = $.getDictName('ISFLASH',isflash);
				respone.data.isflash = isflash;
				
				//将文章内容写入到页面
				$(".content-chakan").html("");
				$(".content-chakan").prepend(respone.data.content);
				
				way.set("model.view.data",null);
				way.set("model.view",respone.data);
			})
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
						way.set("model.form.addzsk.flid",treeNode.id);
						way.set("model.form.addzsk.zslx",zslx);

						model.tableRefresh();
					}
				}
			};
			way.set("model.form.addzsk.zslx",zslx);
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
		var id = model.getFormData().wzid;
		
		//选中的信息发布渠道
		var arr = new Array();
		$("#xxfbqd-checkbox").find("input:checkbox:checked").each(function(){
			arr.push($(this).val());
		});
		var vals = arr.join(",");
		if(vals == ""){
			alert("请选中一个发布渠道");
			return;
		}
		way.set("model.form.data.qdid", vals);
		
		editor.sync();	// 同步数据后可以直接取得textarea的value
		//设置文章内容
		var content = $("#editorId").val();
		//model.getFormData().content = content;
		
		//设置上传文件路径
		var filePath = $("#filePath").val();
		way.set("model.form.data.filePath", filePath);
		
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
	//加入知识库
	$("#data-addzsk").bootstrapValidator().on("success.form.bv", function(e) {// 提交
		e.preventDefault();
		editor1.sync();	// 同步数据后可以直接取得textarea的value
		var optUrl = model.path + "/savezsk.do";
		$.post(optUrl, $("#data-addzsk").serialize(), function(respone) {
			if (respone.responeCode == '0') {
				layer.msg("保存成功");
				model.tableRefresh();
				$("#form-addzsk").modal('toggle');
			}
		});
	});
	/**
	 * 查看
	 */
	$("body").on("click", ".view", function() {
		var id = $(this).html();
		model.setViewDataById(id);
		$('#modal-view').modal('toggle');
	});
	// 查询
	$("#search").click(function() {
		model.tableRefresh();
	});
	// 添加
	$("#add").click(function() {
		//文章审核状态
		way.set("model.form.data.dqzt", "00");
		model.resetDataForm();
		model.setFormTitle("<i class='fa fa-plus'>添加</i>");
		$("#form-panel").modal('toggle');
	});
	//提交并审核
	$("#addDqzt").click(function(){
		way.set("model.form.data.dqzt", "02");
	});
	// 编辑
	$("#edit").click(function() {
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			model.resetDataForm();
			model.setFormDataById(rows[0].wzid);
			model.setFormTitle("<i class='fa fa-edit'>编辑</i>");
			$("#form-panel").modal('toggle');
		}
	});
	//加入知识库
	$("#addzsk").click(function(){
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			way.set("model.form.addzsk.wzid", rows[0].wzid);
			way.set("model.form.addzsk.fxmc", rows[0].title);
			var id = rows[0].wzid;
			$.get(model.path + "/get.do",{id:id},function(respone){
				//设置文章内容的值
				var content = respone.data.content;
				editor1.html("");
				editor1.html(content);
			})
			model.setFormTitle("<i class='fa fa-plus'>加入知识库</i>");
			$("#form-addzsk").modal('toggle');
		}
	});
	//发布微博
	$("#wb_publish").off("click").click(function(){
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			var id = rows[0].wzid;
			$.get(model.path + "/get.do",{id:id},function(respone){
				//设置文章内容的值
				var artdescription = respone.data.artdescription;
				str = artdescription;
				
				/*WB2.anyWhere(function(W){
					if(num == 1){
						return;
					}
				    W.widget.publish({
				        action:"pubilish",
				        type:"web",
				        language:"zh_cn",
				        button_type:"red",
				        button_size:"middle",
				        refer:"y",
				        id: "wb_publish",
				        default_text : artdescription,
				        callback : function(o) {
				            //do something...
				        }
				    });
				    num = 1;
				});*/
				
				//weibo();
				
			})
		}
	});
	//点击服务分类选择框
	$("#form-zskfl-input").click(function(){
		$.seezoon.chooseZskfl(way.get("model.form.data.flid"),function(treeNode){

			way.set("model.form.addzsk.flid",treeNode.id);
			way.set("model.form.addzsk.flmc",treeNode.flmc);
			way.set("model.form.addzsk.gjz",model.getTreeNodeName(treeNode.parentIds)+" "+treeNode.flmc);
			return true;
		},function(){
			way.set("model.form.addzsk.flid",null);
			way.set("model.form.addzsk.flmc",null);
		},{'zslx':zslx});
	});
	//审核
	$("#up").click(function(){
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			//model.resetDataForm();
			//model.setFormDataById(rows[0].wzid);
			
			var wzidArr = new Array();
			for(var i=0; i<rows.length; i++){
				wzidArr.push(rows[i].wzid);
			}
			var wzidStr = wzidArr.join(",");
			
			
			layer.confirm('确定提交审核？', {
				shadeClose : true,
				icon : 3,
				anim : 6,
				btn : [ '确定', '取消' ]
			},function(){
				var dqzt = '03';
				//way.set("model.form.data.qdzt", dqzt);
				//way.set("model.form.data.wzid", id);
				var optUrl = model.path + "/up.do";
				$.post(optUrl, {wzid:wzidStr, dqzt:dqzt}, function(respone) {
					if (respone.responeCode == '0') {
						layer.msg("保存成功");
						//model.tableRefresh();
						//$("#form-panel").modal('toggle');
						model.tableRefresh();
					}
				});
			});
		}
	});
	//归档
	$("#gd").click(function(){
		var rows = $('#table').bootstrapTable("getSelections");
		if (rows.length == 0) {
			layer.msg("请选择一行");
		} else {
			//model.resetDataForm();
			//model.setFormDataById(rows[0].wzid);
			
			var wzidArr = new Array();
			for(var i=0; i<rows.length; i++){
				if(rows[i].dqzt != '03'){
					alert('不是 "已审核待归档" 状态');
					return;
				}
				wzidArr.push(rows[i].wzid);
			}
			var wzidStr = wzidArr.join(",");
			
			
			layer.confirm('确定提交审核？', {
				shadeClose : true,
				icon : 3,
				anim : 6,
				btn : [ '确定', '取消' ]
			},function(){
				var dqzt = '01';
				//way.set("model.form.data.qdzt", dqzt);
				//way.set("model.form.data.wzid", id);
				var optUrl = model.path + "/up.do";
				$.post(optUrl, {wzid:wzidStr, dqzt:dqzt}, function(respone) {
					if (respone.responeCode == '0') {
						layer.msg("保存成功");
						//model.tableRefresh();
						//$("#form-panel").modal('toggle');
						model.tableRefresh();
					}
				});
			});
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
					id : rows[0].wzid
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
		url : model.path + '/qryPageAddzsk.do',
		columns : [ {
			checkbox : true
		}, 
			{
			field : 'wzid',
			title : '文章id',
            formatter : function(value, row, index) {
			    return "<a href='#' class='view text-success' data-id='" + row.id + "'>" + value + "</a>"
			 }
			},
			{
			field : 'title',
			title : '文章名称',
			},
			{
			field : 'classname',	// 关联 WT_ZHFWPT_QT_CLASS， 确定分类栏目名称
			title : '分类',
			},
			{
			field : 'qdid',		// 关联 WT_ARTICLE_QDGX，确定发布渠道
			title : '发布渠道',
				formatter : function(value, row, index) {
					var qdidName = '';
					if(value != null){
						if(value.indexOf(",") != -1){
							var arr = value.split(",");
							for(var i=0; i<arr.length; i++){
								qdidName += ($.getDictName('XXFBQD',arr[i]) + ",");
							}
							qdidName = qdidName.substring(0, qdidName.lastIndexOf(","));
						}else{
							qdidName = $.getDictName('XXFBQD',value);
						}
				}
					return qdidName;
				}
			},
			{
			field : 'author',
			title : '作者',
			},	
			{
			field : 'wzfbsj',
			title : '发布时间',
			},	
			{
			field : 'dqzt',
			title : '状态',
				formatter : function(value, row, index) {
					return $.getDictName('XXFBZT',value);
				}
			},
			
				
				
			/*	
			{
			field : 'titlefontcolor',
			title : '标题颜色',
			},
			{
			field : 'copyfrom',
			title : '文章来源',
			},
			{
			field : 'dateandtime',
			title : '文章最后操作时间',
			},
			{
			field : 'hits',
			title : '文章点击次数',
			},
			{
			field : 'istop',
			title : '是否置顶',
			},
			{
			field : 'ishot',
			title : '是否推荐',
			},
			{
			field : 'isflash',
			title : '是否为图片',
			},
			{
			field : 'images',
			title : '图片链接',
			},
			{
			field : 'userid',
			title : '添加文章用户id',
			},
			{
			field : 'username',
			title : '添加文章用户名',
			},
			{
			field : 'zhczrq',
			title : '最后操作日期',
			},
			{
			field : 'zskid',
			title : '知识库id',
			},
			*/
			
		 ]
	});
	
	
	
	/**
     * 渠道字典列表与code转汉字
     */
    $.extend({
        //获取渠道列表
    	getXxfbqdList : function() {
            // 先从sessionStorage 取
            var typeDict = sessionStorage.getItem("model.xxfbqd" );
            if (typeDict) {
                return $.parseJSON(typeDict);
            } else {
                // 取消异步
                $.ajaxSetup({
                    async : false
                });
                var dict = [];
                $.get(adminContextPath + "/zhfw/qdxxb/qryAll.do", {

                }, function(respone) {
                    if (respone.responeCode == "0") {
                        dict = respone.data;
                        // 存入sessionStorage
                        if (dict) {
                            sessionStorage.setItem("model.xxfbqd" , JSON
                                .stringify(dict));
                        }
                    }
                });
                $.ajaxSetup({
                    async : true
                });
                return dict;
            }
        },
        // 字典code 转value
        getXxfbqdName : function(value) {
            if ( !value) {
                return null;
            }
            // 先从sessionStorage 取
            var typeMap = sessionStorage.getItem("model.xxfbqd.map" );
            if (typeMap) {
                var json = $.parseJSON(typeMap);
                return json[value];
            } else {
                var map = {};
                var dictList = $.getQdList();
                if (dictList) {
                    $.each(dictList, function(i, v) {
                        map[v.qdid] = v.qdmc
                    });
                    if (map) {
                        sessionStorage.setItem("model.xxfbqd.map", JSON
                            .stringify(map));
                    }
                    return map[value];
                }
            }
        },
        //渠道下拉框
        xxfbqdInputhandler:function(){
            /**
             * <div class="col-sm-5"> <label class="radio-inline"> <input type="radio"
             * required way-data="status" name="status" value="1">有效 </label> <label
             * class="radio-inline"> <input type="radio" required way-data="status"
             * name="status" value="0">无效 </label> </div>
             *
             * <div class="col-sm-5"> <label class="checkbox-inline"> <input
             * type="checkbox" required way-data="status" name="status" value="1">有效
             * </label> <label class="checkbox-inline"> <input type="checkbox" required
             * way-data="status" name="status" value="0">无效 </label> </div>
             *
             */
            // 字典渲染
            //$(".sf-radio,.sf-checkbox").each(function(i, v) {
            //	var inputName = $(this).data("sf-input-name");
            //	var dictType = $(this).data("sf-dict-type");
            //	var required = $(this).data("sf-required");
            //
            //	var dictList = $.getDictList(dictType);
            //	if (dictList) {
            //		$.each(dictList, function(j, k) {
            //			k.inputName = inputName;
            //			if (required) {
            //				k.required = required
            //			}
            //			// 禁用
            //			if (k.status == '0') {
            //				k.disabled = "disabled";
            //			}
            //		});
            //	}
            //	if ($(this).hasClass("sf-radio")) {
            //		$("#sf-radio-template").tmpl(dictList).appendTo(this);
            //	} else {
            //		$("#sf-checkbox-template").tmpl(dictList).appendTo(this);
            //	}
            //});
            /**
             * eg:
             * <div class="form-group">
             <label class="col-sm-3 control-label">用户类型</label>
             <div class="col-sm-5">
             <select class="form-control sf-select" way-data="userType"
             name="userType" data-sf-dict-type="sys_user_type">
             <option value="">请选择</option>
             </select>
             </div>
             </div>
             */
            $(".xxfbqd-select").each(function(i, v) {
                var dictList = $.getXxfbqdList();
                if (dictList) {
                    $.each(dictList, function(j, k) {
                        // 禁用
                        if (k.qdzt != '01') {
                            k.disabled = "disabled";
                        }else if(k.qdid != 'QD001' && k.qdid != 'QD002' && k.qdid != 'QD003' && k.qdid != 'QD004' && k.qdid != 'QD006'){
                        	k.disabled = "disabled";
                        }
                    	
                    });
                }
                $("#xxfbqd-template").tmpl(dictList).appendTo(this);
                //重新渲染
                $('.selectpicker').selectpicker('refresh');
            });
        }
    });
    
    $.xxfbqdInputhandler();
    
    
    //添加文本编辑器
    kedit('#editorId');
    kedit1('#editorZsk');
});


//获取当前系统时间
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + (date.getHours()>9?date.getHours():'0'+date.getHours()) + seperator2 + (date.getMinutes()>9?date.getMinutes():'0'+date.getMinutes())
            + seperator2 + (date.getSeconds()>9?date.getSeconds():'0'+date.getSeconds());
    return currentdate;
}


//在ztree上的右击事件
function OnRightClick(event, treeId, treeNode) {
    if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
        currNode = null;
        showRMenu("root", event.clientX, event.clientY);
    } else if (treeNode && !treeNode.noR) {
        currNode = treeNode;
        showRMenu("node", event.clientX, event.clientY);
    }
}


//添加文章内容中的文本编辑器
//全局变量
var editor;
function kedit(kedit){
	editor = KindEditor.create('#editorId' , {
		cssPath : '/admin/js/kindeditor/plugins/code/prettify.css',
		uploadJson : '/admin/js/kindeditor/jsp/upload_json.jsp',
		fileManagerJson : '/admin/js/kindeditor/jsp/file_manager_json.jsp',
		allowFileManager : true,
		afterCreate : function() {
			var self = this;
			KindEditor.ctrl(document, 13, function() {
				self.sync();
				document.forms['example'].submit();
			});
			KindEditor.ctrl(self.edit.doc, 13, function() {
				self.sync();
				document.forms['example'].submit();
			});
		},
		afterUpload: function (url) {
            var filePath = $("#filePath").val(); 
            //当上传多个图片时，第一个图片的路径保存后，不会再向标签中写入第二个图片的路径。
            //当修改文章时，此处将填充所有修改图片中，所上传的第一个图片的路径。
            if (filePath.length <= 0) {  
                $("#filePath").val(url);   
            }
        }
	});
	prettyPrint();
}

var editor1;
function kedit1(kedit){
	editor1 = KindEditor.create('#editorZsk' , {
		cssPath : '/admin/js/kindeditor/plugins/code/prettify.css',
		uploadJson : '/admin/js/kindeditor/jsp/upload_json.jsp',
		fileManagerJson : '/admin/js/kindeditor/jsp/file_manager_json.jsp',
		allowFileManager : true,
		afterCreate : function() {
			var self = this;
			KindEditor.ctrl(document, 13, function() {
				self.sync();
				document.forms['examplezsk'].submit();
			});
			KindEditor.ctrl(self.edit.doc, 13, function() {
				self.sync();
				document.forms['examplezsk'].submit();
			});
		},
		afterUpload: function (url) {
            var filePath = $("#filePathzsh").val(); 
            //当上传多个图片时，第一个图片的路径保存后，不会再向标签中写入第二个图片的路径。
            //当修改文章时，此处将填充所有修改图片中，所上传的第一个图片的路径。
            if (filePath.length <= 0) {  
                $("#filePathzsh").val(url);   
            }
        }
	});
	prettyPrint();
}


var str = "";
var num = 0;
function weibo(){
	WB2.anyWhere(function(W){
		if(num == 1){
			return;
		}
	    W.widget.publish({
	        action:"pubilish",
	        type:"web",
	        language:"zh_cn",
	        button_type:"red",
	        button_size:"middle",
	        refer:"y",
	        id: "wb_publish",
	        default_text : str,
	        callback : function(o) {
	            //do something...
	        }
	    });
	    num = 1;
	});
}

