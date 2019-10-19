
$(function() {
	var model = {
			path : adminContextPath + "/zhfw/srtjxx",
			resetDataForm : function() {
				$("#data-form").bootstrapValidator('resetForm', true);
				way.set("model.form.data",{
					sort:10
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
					model.setParent(respone.data.parentId);
				})
			},
			setParent:function(parentId){
				
				
			}
		};
	
	
	echartDialhyyhzb(0);
	echartDialyhzcl(0);
	
	// 查询
	$("#qdzcqktj_qdlxan").click(function() {
		indexEcharzchyqk();
//		$('#table').bootstrapTable("refresh");
		
	});
	
	
	//列表
	$('#table').bootstrapTable({
		url : model.path + "/selectQdzcqktj.do",
		pagination:false,
		queryParams:function(pageReqeust){
//			var qdid=$("#qdzcqktj_qdlbxlk").val();
//			pageReqeust.qdid = qdid;  
			return pageReqeust;
		},
		responseHandler : function(result) {
			console.log(result);
			if(result.responeMsg==null){
				var Data=[];
				var list01={};
				var list02={};
				list01.name="当前注册人数";
				list02.name="已关闭注册人数";
				var wtdqzcrs=0;
				var appdqzcrs=0;
				var wzdqzcrs=0;
				var wxdqzcrs=0;
				
				var wtygbzcrs=0;
				var appygbzcrs=0;
				var wzygbzcrs=0;
				var wxygbzcrs=0;
				if(result.data.length!=null){
					 var resultdata=result.data;
					 for ( var i = 0; i <resultdata.length; i++){
						 var qdid=resultdata[i].QDID;
						 if(qdid=="QD001"){
							 wtdqzcrs=resultdata[i].DQZCRS;
							 wtygbzcrs=resultdata[i].YGBZCRS;
						 }
						 if(qdid=="QD002"){
							 appdqzcrs=resultdata[i].DQZCRS;
							 appygbzcrs=resultdata[i].YGBZCRS;
						 }
						 if(qdid=="QD003"){
							 wzdqzcrs=resultdata[i].DQZCRS;
							 wzygbzcrs=resultdata[i].YGBZCRS;
						 }
						 if(qdid=="QD004"){
							 wxdqzcrs=resultdata[i].DQZCRS;
							 wxygbzcrs=resultdata[i].YGBZCRS;
						 }
						 
						 list01.name="当前注册人数";
						 list02.name="已关闭注册人数";
						 
					 }
					 
			}
				
			list01.wtzcrs=wtdqzcrs;
			list01.appzcrs=appdqzcrs;
			//list01.wzzcrs=wzdqzcrs;
			list01.wxzcrs=wxdqzcrs;
			list02.wtzcrs=wtygbzcrs;
			list02.appzcrs=appygbzcrs;
			//list02.wzzcrs=wzygbzcrs;
			list02.wxzcrs=wxygbzcrs;
			
			Data.push(list01);
			Data.push(list02);
			};
			return {
				rows : Data
			};
		},
		onPostBody:function(){//渲染完后执行
		},
		rowStyle:function(row,index){//整合treegrid
			var classes = "treegrid-" + row.id
			if (row.parentId != '0') {
				classes = classes + " treegrid-parent-" + row.parentId;
			}
			return {classes:classes};
		},
		columns : [  {
			field : 'name',
			title : '统计项'
		},
		{
			field : 'wtzcrs',
			title : '网厅渠道'
		}, 
		{
			field : 'appzcrs',
			title : 'android_APP'
		},

		{
			field : 'wxzcrs',
			title : '微信'
		}]
	});
	
});



//查询交易数据
function indexEcharzchyqk(){
	var qdid=$("#qdzcqktj_qdlbxlk").val();
	$.ajax({
		url:adminContextPath + "/zhfw/srtjxx/selectQdzcqktj.do",
		data:{
		    "qdid":qdid
	    },
		type:'post',
		dataType:'json',
		success:function(result){
			if(result.responeMsg==null){
				var hyyhzb=0;
				var yhzcl=0;
				if(result.data.length!=0){
					hyyhzb = (result.data[0].HDYHBL* 100).toFixed(2) - 0;

					yhzcl = (result.data[0].YHZCBL* 100).toFixed(2) - 0;
					if(yhzcl>100){
						yhzcl=100;
					}
				}
				echartDialhyyhzb(hyyhzb);
				echartDialyhzcl(yhzcl);
			}
			
		},
		error:function(){
			 alert("尊敬的用户您好！当前页面连接异常，请刷新重试或退出页面重新登入！");
		 }
	});
};

//活动用户占比
function echartDialhyyhzb(hyyhzb){
	var dom = document.getElementById("echart_dial_hdyhzb");
	var myChart = echarts.init(dom,'light');  //白色背景
    var app = {};
    optionsb = null;
    optionsb = {
		    tooltip : {
		        formatter: "{a} <br/>{b} : {c}"
		    },
		    toolbox: {
		        feature: {
		           // restore: {},		//还原
		            saveAsImage: {}	//保存为图
		        }
		    },
		    series: [
		        {
		            name: '业务指标',
		            type: 'gauge',
		            detail: {formatter:'{value}'},
		            data: [{value: 0, name: '活动用户占比'}]
		        }
		    ]
		};

		/*setInterval(function () {
		    optionWz.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
		    myChart.setOption(optionWz, true);
		},2000);*/
    
        optionsb.series[0].data[0].value = hyyhzb;
	    myChart.setOption(optionsb, true);
}


//用户注册率
function echartDialyhzcl(yhzcl){
	var dom = document.getElementById("echart_dial_yhzcl");
	var myChart = echarts.init(dom,'light');  //白色背景
    var app = {};
    optioncg = null;
    optioncg = {
		    tooltip : {
		        formatter: "{a} <br/>{b} : {c}"
		    },
		    toolbox: {
		        feature: {
		           // restore: {},		//还原
		            saveAsImage: {}	//保存为图
		        }
		    },
		    series: [
		        {
		            name: '业务指标',
		            type: 'gauge',
		            detail: {formatter:'{value}'},
		            data: [{value: 0, name: '用户注册率'}]
		        }
		    ]
		};

		/*setInterval(function () {
		    optionWt.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
		    myChart.setOption(optionWt, true);
		},2000);*/
    
        optioncg.series[0].data[0].value = yhzcl;
	    myChart.setOption(optioncg, true);
}































