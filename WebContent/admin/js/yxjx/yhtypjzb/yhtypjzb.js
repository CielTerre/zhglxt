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
	
	
	$("#yhtybjzb_tjsjc").val(time01);
	$("#yhtybjzb_tjsjd").val(time02);
	
	
	yhmydcx();	//用户满意度
	yhtslcx();	//用户投诉率
	
	// 查询
	$("#yhtybjzb_qjcxan").click(function() {
		yhmydcx();
		yhtslcx();
	});
	
});


//用户满意度
function yhmydcx(){
	var dom = document.getElementById("g_yhmyd");
    var myChart = echarts.init(dom,'light');  //白色背景
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
            data: ['不满意数', '基本满意数', '满意数']
        },
        series : [
            {   
            	name:'用户满意度',
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
	var qdid = way.get('model.search.qdid');
	$.ajax({
		 url:adminContextPath + "/zhfw/srtjxx/selectQdyhtytj.do",
		 type:"post",
		 data:{"qdid":qdid
		 },
		 dataType:"json",
		 success:function(result){
			 if(result.responeMsg==null){
				 var yhmydResult=result.data.yhmydResult;
				 var Data=[];
				 if(yhmydResult.length!=0){
					 for ( var i = 0; i <yhmydResult.length; i++){
						 var name ="";
						 var value="";
						 var list={};
						 var tjzbmx=yhmydResult[i].TJZBMX;
						 if(tjzbmx=="0"){
							 name="不满意数";
						 }
						 if(tjzbmx=="1"){
							 name="基本满意数";
						 }
						 if(tjzbmx=="2"){
							 name="满意数";
						 }

						 
						 value=yhmydResult[i].PJRS;

						 list.name=name;
						 list.value=value;
						 Data.push(list);
					 }
					 option.series[0].data=Data;
				 }
				 chart.setOption(option, true);
			 }

		 },
		 error:function(){
			 alert("尊敬的用户您好！当前页面连接异常，请刷新重试或退出页面重新登入！");
		 }
	});
	
}

//用户满意度
function yhtslcx(){
	var dom = document.getElementById("g_yhtsl");
    var myChart = echarts.init(dom,'light');  //白色背景

	var option = null;
	 option = {
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
				name: '投诉率',
				type: 'gauge',
				detail: {formatter:'{value}%'},
				data: [{value: '', name: '投诉率'}]
			}
		]
	};

    loadData_yhtsl(myChart,option);
}

function loadData_yhtsl(chart,option){
	var qdid = way.get('model.search.qdid');
	
	$.ajax({
		 url:adminContextPath + "/zhfw/srtjxx/selectQdyhtytj.do",
		 type:"post",
		 data:{"qdid":qdid
		 },
		 dataType:"json",
		 success:function(result){
			 if(result.responeMsg==null){
				 var yhtslResult=result.data.yhtslResult;
				 var Data=[];
				 if(yhtslResult.length!=0){
					 option.series[0].data[0].value=yhtslResult[0].YHTSL;

				 }
				 chart.setOption(option, true);
			 }

		 },
		 error:function(){
			 alert("尊敬的用户您好！当前页面连接异常，请刷新重试或退出页面重新登入！");
		 }
	});
	
}
























