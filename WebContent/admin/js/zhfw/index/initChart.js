$(function() {
   // var dom = $('#p_chart');
	
	//echartPie();	//折线图Demo
    
	echartDialWanagzhan();	//网站并发访问量图表生成
	
	echartDialWanagting();	//网厅并发访问量图表生成
	
	echartDialWeixin();	//微信并发访问量图表生成
	
	echartHistogramQdtj();	//网站，网厅，App, 微信，微博。各渠道访问量图标生成
	
	indexEchartData();	//查询：当日访问量，当日业务受理量，当日咨询人数，当日投诉人数
	
	indexEcharBffwl();	//获取并发访问量的数据
	
	indexEchartQdfwl();	//查询网站，网厅，App，微信，微博,渠道访问量
});



//网站并发访问量
function echartDialWanagzhan(wzbffwl){
	var dom = document.getElementById("echart_dial_wangzhan");
    var myChart = echarts.init(dom);
    var app = {};
    optionWz = null;
    optionWz = {
		    tooltip : {
		        formatter: "{a} <br/>{b} : {c}"
		    },
		    toolbox: {
		        feature: {
		           // restore: {},		//还原
		           // saveAsImage: {}	//保存为图
		        }
		    },
		    series: [
		        {
		            name: '业务指标',
		            type: 'gauge',
		            detail: {formatter:'{value}'},
		            data: [{value: 50, name: '网站并发访问量'}]
		        }
		    ]
		};

		/*setInterval(function () {
		    optionWz.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
		    myChart.setOption(optionWz, true);
		},2000);*/
    	
    	if(isNaN(wzbffwl)){
    		wzbffwl = 0;
    	}
	    optionWz.series[0].data[0].value = wzbffwl;
	    myChart.setOption(optionWz, true);
}


//网厅并发访问量
function echartDialWanagting(wtbffwl){
	var dom = document.getElementById("echart_dial_wangting");
    var myChart = echarts.init(dom);
    var app = {};
    optionWt = null;
    optionWt = {
		    tooltip : {
		        formatter: "{a} <br/>{b} : {c}"
		    },
		    toolbox: {
		        feature: {
		           // restore: {},		//还原
		           // saveAsImage: {}	//保存为图
		        }
		    },
		    series: [
		        {
		            name: '业务指标',
		            type: 'gauge',
		            detail: {formatter:'{value}'},
		            data: [{value: 50, name: '网厅并发访问量'}]
		        }
		    ]
		};

		/*setInterval(function () {
		    optionWt.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
		    myChart.setOption(optionWt, true);
		},2000);*/
    	
		if(isNaN(wtbffwl)){
			wtbffwl = 0;
		}
	    optionWt.series[0].data[0].value = wtbffwl;
	    myChart.setOption(optionWt, true);
}

//微信并发访问量
function echartDialWeixin(wxbffwl){
	var dom = document.getElementById("echart_dial_weixin");
    var myChart = echarts.init(dom);
    var app = {};
    optionWx = null;
    optionWx = {
    		//backgroundColor: '#ecf0f5',	//设置背景颜色
		    tooltip : {
		        formatter: "{a} <br/>{b} : {c}"
		    },
		    toolbox: {
		        feature: {
		           // restore: {},		//还原
		           // saveAsImage: {}	//保存为图
		        }
		    },
		    series: [
		        {
		            name: '业务指标',
		            type: 'gauge',
		            detail: {formatter:'{value}'},
		            data: [{value: 50, name: '微信并发访问量'}]
		        }
		    ]
		};

		/*setInterval(function () {
		    optionWx.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
		    myChart.setOption(optionWx, true);
		},2000);*/
    	
	    if(isNaN(wxbffwl)){
	    	wxbffwl = 0;
		}
	    optionWx.series[0].data[0].value = wxbffwl;
	    myChart.setOption(optionWx, true);
}

//网站，网厅，App, 微信。各渠道访问量
function echartHistogramQdtj(timeArr,wzArr,wtArr,appArr,wxArr,zdArr,rxArr,dxArr){
	var dom = document.getElementById("echart_histogram_qdtj");
    var myChart = echarts.init(dom);
    var app = {};
    optionQdtj = null;
    var optionQdtj = {
    	    title: {
    	        text: '渠道访问量'
    	    },
    	    tooltip: {
    	        trigger: 'axis'
    	    },
    	    legend: {
    	        data:['网站','网厅','App','微信','自助终端','12329热线','12329短信']
    	    },
    	    grid: {
    	        left: '3%',
    	        right: '4%',
    	        bottom: '3%',
    	        containLabel: true
    	    },
    	    toolbox: {
    	        feature: {
    	            //saveAsImage: {}
    	        }
    	    },
    	    xAxis: {
    	        type: 'category',
    	        boundaryGap: false,
    	        data: timeArr
    	    },
    	    yAxis: {
    	        type: 'value'
    	    },
    	    series: [
    	        {
    	            name:'网站',
    	            type:'line',
    	            //stack: '总量',
    	            data:wzArr
    	        },
    	        {
    	            name:'网厅',
    	            type:'line',
    	            //stack: '总量',
    	            data:wtArr
    	        },
    	        {
    	            name:'App',
    	            type:'line',
    	            //stack: '总量',
    	            data:appArr
    	        },
    	        {
    	            name:'微信',
    	            type:'line',
    	            //stack: '总量',
    	            data:wxArr
    	        },
    	        {
    	            name:'自助终端',
    	            type:'line',
    	            //stack: '总量',
    	            data:zdArr
    	        },
				{
					name:'12329热线',
					type:'line',
					//stack: '总量',
					data:rxArr
				},
				{
					name:'12329短信',
					type:'line',
					//stack: '总量',
					data:dxArr
				}
    	    ]
    	};

		myChart.setOption(optionQdtj, true);
		
		
}



//查询：当日访问量，当日业务受理量，当日咨询人数，当日投诉人数
function indexEchartData(){
	var model = {
			path : adminContextPath + "/main"
		};
	$.ajax({
		url: model.path + "/index.do",
		data:{},
		type:'post',
		dataType:'json',
		success:function(result){
			var data = result.data;
			var countfwl = data[0].countfwl;	//当日访问量
			var countywsll = data[0].countywsll;	//当日业务受理量
			var countzxrs = data[0].countzxrs;	//当日咨询人数
			var counttsrs = data[0].counttsrs;	//当日投诉人数
			$("#countfwl").html(countfwl);
			$("#countywsll").html(countywsll);
			$("#countzxrs").html(countzxrs);
			$("#counttsrs").html(counttsrs);
		}
	});
}



//查询并发访问量
function indexEcharBffwl(){
	var model = {
			path : adminContextPath + "/main"
		};
	// setInterval(function () {
		$.ajax({
			url: model.path + "/indexbffwl.do",
			data:{},
			type:'post',
			dataType:'json',
			success:function(result){
				var data = result.data;
				
				var tmpWz = data.tmpWz;
				var tmpWt = data.tmpWt;
				var tmpWx = data.tmpWx;
				
				echartDialWanagzhan(tmpWz);
				echartDialWanagting(tmpWt);
				echartDialWeixin(tmpWx);
			}
		});
		
		/*var wzbffwl = (Math.random() * 100).toFixed(2) - 0;
		var wtbffwl = (Math.random() * 100).toFixed(2) - 0;
		var wxbffwl = (Math.random() * 100).toFixed(2) - 0;
		echartDialWanagzhan(wzbffwl);
		echartDialWanagting(wtbffwl);
		echartDialWeixin(wxbffwl);*/
		
	// },2000);
	
	
}





//查询:网站，网厅，App，微信，微博，渠道访问量
function indexEchartQdfwl(){
	var model = {
			path : adminContextPath + "/main"
		};
	var timeStr = '';
	for(var i=1; i<8; i++){
		var date = new Date();
		date.setDate(date.getDate()-i);
		var time = date.Format("yyyy/MM/dd");
		timeStr += (time+'|');
	}
	timeStr = timeStr.substring(0,timeStr.lastIndexOf('|'));
	$.ajax({
		url: model.path + "/indexqdfwl.do",
		data:{'timeStr':timeStr},
		type:'post',
		dataType:'json',
		success:function(result){
			console.log(result);
			var list = result.data;
			var timeArr = [];
			var wzArr = [];
			var wtArr = [];
			var appArr = [];
			var wxArr = [];
			var zdArr = [];
			var rxArr = [];
			var dxArr = [];

			for(var i=0; i<list.length; i++){
				timeArr.push(list[i].columnvalue);
				wzArr.push(list[i].countwzqdfwl);
				wtArr.push(list[i].countwtqdfwl);
				appArr.push(list[i].countappqdfwl);
				wxArr.push(list[i].countwxqdfwl);
				zdArr.push(list[i].countzdqdfwl);
				rxArr.push(list[i].countrxqdfwl);
				dxArr.push(list[i].countdxqdfwl);
			}
			echartHistogramQdtj(timeArr,wzArr,wtArr,appArr,wxArr,zdArr,rxArr,dxArr);
		}
	});
}


//Format("输入你想要的时间格式:yyyy-MM-dd,yyyyMMdd")
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}




























