/**
 * 知识库分类
 * 
 * @param $
 * @returns
 */
;(function($) {
	$("body").append("<div id='zskflLayer' class='undisplay pd10'><ul id='zskflTree' class='ztree'></ul></div>");
	$.seezoon = $.extend($.seezoon,{
	    index:0,
		chooseZskfl:function(treeId,confirmCallback,clearCallback,filter){
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

				var url = adminContextPath + "/zhfw/zskfl/qryPage.do";

				$.post(url ,filter, function(respone) {
					//选择上级tree
					$.fn.zTree.init($("#zskflTree"), setting,respone.data);
					var treeObj = $.fn.zTree.getZTreeObj("zskflTree");
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
						  var treeObj = $.fn.zTree.getZTreeObj("zskflTree");
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
					  content:  $("#zskflLayer")
					});
			}
		}
	);
})(jQuery);