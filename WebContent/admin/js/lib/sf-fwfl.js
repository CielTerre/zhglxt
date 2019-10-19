/**
 * 服务分类选择器
 * $.seezoon.chooseFwfl('需要默认选中的treeId','点确定后的回调方法','点清清除的回调方法','是否按权限过滤部门数据');
 * @param $
 * @returns
 */
;(function($) {
	$("body").append("<div id='fwflLayer' class='undisplay pd10'><ul id='fwflTree' class='ztree'></ul></div>");
	$.seezoon = $.extend($.seezoon,{
	    index:0,
		chooseFwfl:function(treeId,confirmCallback,clearCallback,filter){
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
						onDblClick : function(event, treeId, treeNode) {
							if (confirmCallback(treeNode)){
								layer.close($.seezoon.index);
							}
						}
					}
				};
				var url = adminContextPath + "/zhfw/fwflb/qryPage.do";
				if (filter) {
					url = adminContextPath + "/zhfw/fwflb/qryAllWithScope.do";
				}
				$.post(url , function(respone) {
					//选择上级tree
					$.fn.zTree.init($("#fwflTree"), setting,respone.data);
					var treeObj = $.fn.zTree.getZTreeObj("fwflTree");
					treeObj.expandAll(true);
					if (treeId) {//默认反选
						treeObj.selectNode(treeObj.getNodeByParam("id",treeId));
					}
				});
				this.index = layer.open({
				 	  title:'上级分类',
				 	  offset: '50px',
		              skin: 'layui-layer-molv',
		              shadeClose:true,
				 	  icon: 1,
				 	  area: ['300px', '450px'],
					  type: 1, 
					  btn: ['确认','清除'],
					  yes:function(){
						  var treeObj = $.fn.zTree.getZTreeObj("fwflTree");
						  var nodes = treeObj.getSelectedNodes();
						  if (nodes.length == 0) {
							  layer.msg("请选择分类");
							  return false;
						  }
						  if (confirmCallback(nodes[0])){
								layer.close($.seezoon.index);
						   }
					  },
					  btn2:function(){
						  clearCallback();
						  layer.close($.seezoon.index);
					  },
					  content:  $("#fwflLayer")
					});
			}
		}
	);
})(jQuery);