$(function() {
	
	var now = new Date();
	var year = now.getFullYear(); //得到年份
	var month = now.getMonth();//得到月份
	var date = now.getDate();//得到日期
	month = month + 1;
	if (month < 10) month = "0" + month;
	if (date < 10) date = "0" + date;
	var time01 = year + "-" + month + "-" + "01";
	var time02 = year + "-" + month + "-" + date;
	
	
	$("#qdfwjyqktj_tjsjc").val(time01);
	$("#qdfwjyqktj_tjsjd").val(time02);
	
	
	 
	indexEcharjyqk();        //交易情况
	
	// 查询
	$("#qdfwjyqktj_qjcxan").click(function() {
		indexEcharjyqk(); 
	});
	
});


//查询交易数据
function indexEcharjyqk(chart,option){
	var tjrqc=$("#qdfwjyqktj_tjsjc").val();
	var tjrqd=$("#qdfwjyqktj_tjsjd").val();
	var qdid=$("#qdfwjyqktj_qdlbxlk").val();
	
	if(tjrqc==""||tjrqd==""){
		alert("请输入统计区间时间！");
	}
	
	$.ajax({
		url:adminContextPath + "/zhfw/srtjxx/selectQdjyqktj.do",
		data:{
			"qdid":qdid,
		    "tjrqc":tjrqc,
		    "tjrqd":tjrqd
	    },
		type:'post',
		dataType:'json',
		success:function(result){
			console.log(result);
			if(result.responeMsg==null){
				var jyqktjResult=result.data.jyqktjResult;
				var ywblsjdResult=result.data.ywblsjdResult;
				if(jyqktjResult!=null){
					var jycgl=0;
					var jysbl=0;
					if(jyqktjResult.length!=0){
						jycgl = (jyqktjResult[0].YWQQCGBL* 100).toFixed(2) - 0;
						jysbl = (jyqktjResult[0].YWBLYCBL* 100).toFixed(2) - 0;
					}
					echartDialjycgl(jycgl);
			        echartDialjysbl(jysbl);
				}
			}

		},
		error:function(){
			 alert("尊敬的用户您好！当前页面连接异常，请刷新重试或退出页面重新登入！");
		 }
	});
};

//交易成功率
function echartDialjycgl(jycgl){
	var dom = document.getElementById("echart_dial_ywblcg");
	var myChart = echarts.init(dom,'light');  //白色背景
    var app = {};
    optionsb = null;
    optionsb = {
		    tooltip : {
		        formatter: "{a} <br/>{b} : {c}"
		    },
		    toolbox: {
		        feature: {
//		            restore: {},		//还原
		            saveAsImage: {}	    //保存为图
		        }
		    },
		    series: [
		        {
		            name: '业务指标',
		            type: 'gauge',
		            detail: {formatter:'{value}'},
		            data: [{value: 0, name: '业务办理成功率'}]
		        }
		    ]
		};

		/*setInterval(function () {
		    optionWz.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
		    myChart.setOption(optionWz, true);
		},2000);*/
    
        optionsb.series[0].data[0].value = jycgl;
	    myChart.setOption(optionsb, true);
}


//交易失败率
function echartDialjysbl(jysbl){
	var dom = document.getElementById("echart_dial_ywblsb");
	var myChart = echarts.init(dom,'light');  //白色背景
    var app = {};
    optioncg = null;
    optioncg = {
		    tooltip : {
		        formatter: "{a} <br/>{b} : {c}"
		    },
		    toolbox: {
		        feature: {
//		            restore: {},		//还原
		            saveAsImage: {}	    //保存为图
		        }
		    },
		    series: [
		        {
		            name: '业务指标',
		            type: 'gauge',
		            detail: {formatter:'{value}'},
		            data: [{value: 0, name: '异常交易率'}]
		        }
		    ]
		};

        optioncg.series[0].data[0].value = jysbl;
	    myChart.setOption(optioncg, true);
}


//function loadqdfwlData(chart,option){
//    var series_data01 = [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3];
//    var series_data02 = [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3];
//    var markPoint_data= [
//        {name : '年最高', value : 162.2, xAxis: 6, yAxis: 163},
//        {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
//    ];
//    option.series[1].markPoint.data = markPoint_data;
    //以下可换成ajxa
    
//    var tjrqc=$("#qdfwjyqktj_tjsjc").val();
//	var tjrqd=$("#qdfwjyqktj_tjsjd").val();
//	if(tjrqc==""||tjrqd==""){
//		alert("请输入统计区间时间！");
//	}
//	var qdid=$("#qdfwjyqktj_qdlbxlk").val();
//    $.ajax({
//		url:adminContextPath + "/zhfw/srtjxx/selectQdjyqktj.do",
//		data:{
//			"qdid":qdid,
//		    "tjrqc":tjrqc,
//		    "tjrqd":tjrqd
//	    },
//		type:'post',
//		dataType:'json',
//		success:function(result){
//			if(result.responeMsg==null){
//				if(ywbllxResult.length!=0){
//					 var Data01=[];
//					 var Data02=[];
//					 var Data03=[];
//					 for ( var i = 0; i <ywbllxResult.length; i++){
//						 var name ="";
//						 var value="";
//						 var ywlb=ywbllxResult[i].TJZBMX;
//						 if(ywlb=="001"){
//							 name="缴存类";
//						 }
//						 value=ywbllxResult[i].QDTJZ;
//						 Data01.push(name);
//						 Data02.push(value);
//					 }
//					 option.xAxis[0].data=Data01;
//					 option.series[0].data=Data02;
//					 option.series[1].data =data03;
//					 chart.setOption(option, true);
//				 }
//			}
//
//		},
//		error:function(){
//			 alert("尊敬的用户您好！当前页面连接异常，请刷新重试或退出页面重新登入！");
//		 }
//	});
//    option.series[0].data = series_data01;
//    option.series[1].data = series_data02;
//    
//    
//    option.xAxis[0].data = ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'];
//    chart.setOption(option, true);
//}






























