//用户信息统计
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
	
	
	$("#blywlxfx_tjsjc").val(time01);
	$("#blywlxfx_tjsjd").val(time02);
	
	yhmydcx();	//用户满意度
	yhtslcx();	//用户投诉率
	
	// 查询
	$("#blywlxfx_qjcxan").click(function() {
		yhmydcx();
		yhtslcx();
	});
	
});


//用户满意度
function yhmydcx(){
	var dom = document.getElementById("g_yhmyd");
    var myChart = echarts.init(dom);  
    var weatherIcons = {
        'Sunny': './data/asset/img/weather/sunny_128.png',
        'Cloudy': './data/asset/img/weather/cloudy_128.png',
        'Showers': './data/asset/img/weather/showers_128.png'
    };

    var option = {
        title: {
            text: '用户满意度',
           // subtext: '虚构数据',
            left: 'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            // orient: 'vertical',
            // top: 'middle',
            bottom: 10,
            left: 'center',
            data: ['核心系统', '网厅', 'android_APP', '网站', '微信', '微博', '	自助终端', '12329热线', '短信', '支付宝']
        },
        series : [
            {   
            	name:'业务占比',
                type: 'pie',
//                color: ['#dd6b66','#759aa0','#e69d87','#8dc1a9','#ea7e53','#eedd78','#73a373','#73b9bc','#7289ab', '#91ca8c','#f49f42'],
                radius : '65%',
                center: ['50%', '50%'],
                selectedMode: 'single',
                data:[

                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    loadData_yhmyd(myChart,option);
}

function loadData_yhmyd(chart,option){
//	var tjsjc=$().val();
//	var tjsjc=$().val();
//	
//	$.ajax({
//		 url:_rootPath+"/action/grjbxxcxManager",
//		 type:"post",
//		 data:{"exeType":"selectGrjbxx",
//			    "yhdllb":"01"
//		 },
//		 dataType:"json",
//		 success:function(result){
//			 if(result.result.rtncode == "00000"){
//				 
//				 
//			 }else{
//				 alert(result.result.rtnmsg);
//			 }
//			 	 
//		 },
//		 error:function(){
//			 alert("尊敬的用户您好！当前页面连接异常，请刷新重试或退出页面重新登入！");
//		 }
//	});
	
    option.series[0].data = [ {
        value:535,
        name: '	核心系统'
    },
    {
    	value:510, 
    	name: '网厅'
    },
    {
    	value:123, 
    	name: 'android_APP'
    },
    {
    	value:230, 
    	name: '网站'
    },
    {
    	value:40, 
    	name: '微信'
    },
    {
    	value:322, 
    	name: '微博'
    },
    {
    	value:210, 
    	name: '自助终端'
    },
    {
    	value:50, 
    	name: '12329热线'
    },
    {
    	value:90, 
    	name: '短信'
    },
    {
    	value:80, 
    	name: '支付宝'
    }
    ];

    chart.setOption(option, true);
}

//用户满意度
function yhtslcx(){
	var dom = document.getElementById("g_yhtsl");
    var myChart = echarts.init(dom);  
    var weatherIcons = {
        'Sunny': './data/asset/img/weather/sunny_128.png',
        'Cloudy': './data/asset/img/weather/cloudy_128.png',
        'Showers': './data/asset/img/weather/showers_128.png'
    };

    var option = {
        title: {
            text: '用户投诉率',
           // subtext: '虚构数据',
            left: 'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            // orient: 'vertical',
            // top: 'middle',
            bottom: 10,
            left: 'center',
            data: ['核心系统', '网厅', 'android_APP', '网站', '微信', '微博', '	自助终端', '12329热线', '短信', '支付宝']
        },
        series : [
            {   
            	name:'业务占比',
                type: 'pie',
//                color: ['#dd6b66','#759aa0','#e69d87','#8dc1a9','#ea7e53','#eedd78','#73a373','#73b9bc','#7289ab', '#91ca8c','#f49f42'],
                radius : '65%',
                center: ['50%', '50%'],
                selectedMode: 'single',
                data:[

                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    loadData_yhtsl(myChart,option);
}

function loadData_yhtsl(chart,option){
//	var tjsjc=$().val();
//	var tjsjc=$().val();
//	
//	$.ajax({
//		 url:_rootPath+"/action/grjbxxcxManager",
//		 type:"post",
//		 data:{"exeType":"selectGrjbxx",
//			    "yhdllb":"01"
//		 },
//		 dataType:"json",
//		 success:function(result){
//			 if(result.result.rtncode == "00000"){
//				 
//				 
//			 }else{
//				 alert(result.result.rtnmsg);
//			 }
//			 	 
//		 },
//		 error:function(){
//			 alert("尊敬的用户您好！当前页面连接异常，请刷新重试或退出页面重新登入！");
//		 }
//	});
	
    option.series[0].data = [ {
        value:535,
        name: '	核心系统'
    },
    {
    	value:510, 
    	name: '网厅'
    },
    {
    	value:123, 
    	name: 'android_APP'
    },
    {
    	value:230, 
    	name: '网站'
    },
    {
    	value:40, 
    	name: '微信'
    },
    {
    	value:322, 
    	name: '微博'
    },
    {
    	value:210, 
    	name: '自助终端'
    },
    {
    	value:50, 
    	name: '12329热线'
    },
    {
    	value:90, 
    	name: '短信'
    },
    {
    	value:80, 
    	name: '支付宝'
    }
    ];

    chart.setOption(option, true);
}
























