
//用户信息统计
$(function() {
    
	yhnlqktj();	//年龄情况统计
	
//	yhxbqkgj();	//用户性别情况统计
	
	// 查询
	$("#yhxxtj_cx").click(function() {
		yhnlqktj();	//年龄情况统计
		
//		yhxbqkgj();	//用户性别情况统计
	});
	

});

//用户年龄情况统计
function yhnlqktj(){
	var dom = document.getElementById("g_yhnl");
    var myChart = echarts.init(dom);
    var app = {};
    var option = null;

    option = {
        title: {
            text: '用户年龄统计'
        },
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data:[],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:"用户人数",
                type:'bar',
                barWidth: '60%'
            }
        ]
    };
    
    yhxbqkgj(myChart,option);
}

//用户性别情况统计
function yhxbqkgj(myChart01,option01){
	var dom = document.getElementById("g_yhxb");
    var myChart = echarts.init(dom,'light');  //白色背景
    var weatherIcons = {
        'Sunny': './data/asset/img/weather/sunny_128.png',
        'Cloudy': './data/asset/img/weather/cloudy_128.png',
        'Showers': './data/asset/img/weather/showers_128.png'
    };

    var option = {
        title: {
            text: '男女性别情况统计',
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
            data: ['男', '女']
        },
        series : [
            {
                type: 'pie',
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

    loadData(myChart01,option01,myChart,option);
}

function loadData(myChart01,option01,myChar02,option02){
	var yhxz=$("input[name='dlyzfs']:checked").val();
	$.ajax({
		 url:adminContextPath + "/zhfw/srtjxx/selectYhxxtj.do",
		 timeout:20000,
		 type:"post",
		 data:{
			    "tjlb":yhxz
		 },
		 dataType:"json",
		 success:function(result){
			 if(result.responeMsg==null){
				 var nlqkResult=result.data.nlqkResult;
				 var xbqkResult=result.data.xbqkResult;
				 if(nlqkResult.length!=0){
					 var Data01=[];
					 var Data02=[];
					 for ( var i = 0; i <nlqkResult.length; i++){
						 var name=nlqkResult[i].NLD;
						 if(name==""||name==null){
							 name="";
						 }
						 
						 var value=nlqkResult[i].NLDRS;
						 if(value==""||value==null){
							 value="0";
						 }
						 
						 Data01.push(name);
						 Data02.push(value);
					 }
					 option01.xAxis[0].data=Data01;
					 option01.series[0].data=Data02;
					 myChart01.setOption(option01, true);
				 }
				 
				 
				 if(nlqkResult.length!=0){
					 
					 var Data=[];
					 for ( var i = 0; i <xbqkResult.length; i++){
					 	 var name ="";
					 	 var value="";
					 	 var list={};
					 	 value=xbqkResult[i].XBRS;
					 	 name=xbqkResult[i].XINGBIE;
					 	 list.name=name;
					 	 list.value=value;
					 	 Data.push(list);
					 	 
					 }
					 option02.series[0].data=Data;
					 myChar02.setOption(option02, true);
				 }
				 
			 }

		 },
		 complete: function (XMLHttpRequest, textStatus) {
	            if(textStatus == 'timeout'){
//	            	loadData(myChart01,option01,myChar02,option02);
	            }
		 },
		 error:function(){
//			 alert("尊敬的用户您好！当前页面连接异常，请刷新重试或退出页面重新登入！");
		 }
	});
}




























