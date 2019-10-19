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
	
	$("#fwfwl_tjsjc").val(time01);
	$("#fwfwl_tjsjd").val(time02);
	
	qdfwljz();              //服务访问量
	 
	
	
	// 查询
	$("#fwfwl_qjcxan").click(function() {
		qdfwljz();
	});
});

//服务访问量
function qdfwljz(){
//	var dom = document.getElementById("txtb_qdfwl");
//	//两套主题，分别为 'light' 和 'dark'，不选颜色使用默认的
//    var myChart = echarts.init(dom,'light');  //白色背景
////    var myChart = echarts.init(dom,'dark'); //黑色背景
//    var app = {};
//    option = null;
//    option = {
//    	    title : {
//    	        text: '渠道所提供栏目和服务的访问量'
////    	        subtext: '纯属虚构'
//    	    },
//    	    tooltip : {
//    	        trigger: 'axis'
//    	    },
//    	    legend: {
//    	        data:['栏目','服务']
//    	    },
//    	    toolbox: {
//    	        show : true,
//    	        feature : {
//    	            dataView : {show: true, readOnly: false},
//    	            magicType : {show: true, type: ['line', 'bar']},
//    	            restore : {show: true},
//    	            saveAsImage : {show: true}
//    	        }
//    	    },
//    	    calculable : true,
//    	    xAxis : [
//    	        {
//    	            type : 'category',
//    	            data : []
//    	        }
//    	    ],
//    	    yAxis : [
//    	        {
//    	            type : 'value'
//    	        }
//    	    ],
//    	    series : [
//    	        {
//    	            name:'栏目',
//    	            type:'bar',
////    	            color: ['#dd6b66'],   //根据需求修改颜色
//    	            data:[],
//    	            markPoint : {
//    	                data : [
//    	                    {type : 'max', name: '最大值'},
//    	                    {type : 'min', name: '最小值'}
//    	                ]
//    	            },
//    	            markLine : {
//    	                data : [
//    	                    {type : 'average', name: '平均值'}
//    	                ]
//    	            }
//    	        },
//    	        {
//    	            name:'服务',
//    	            type:'bar',
//    	            data:[],
//    	            markPoint : {
//    	                data : [
//    	                	{type : 'max', name: '最大值'},
//    	                    {type : 'min', name: '最小值'}
//    	                ]
//    	            },
//    	            markLine : {
//    	                data : [
//    	                    {type : 'average', name : '平均值'}
//    	                ]
//    	            }
//    	        }
//    	    ]
//    	};
	
		var dom = document.getElementById("txtb_qdfwl");
	    var myChart = echarts.init(dom);
	    var app = {};
	    var option = null;
	    
	    option = {
	        title: {
	            text: '服务访问量'
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
	                name:"访问量",
	                type:'bar',
	                barWidth: '60%'
	            }
	        ]
	    };
    
	    indexEcharfwfwl(myChart,option);
	    
}

//查询交易数据
function indexEcharfwfwl(chart,option){
	var tjrqc=$("#fwfwl_tjsjc").val();
	var tjrqd=$("#fwfwl_tjsjd").val();
	
	if(tjrqc==""||tjrqd==""){
		alert("请输入统计区间时间！");
	}
	
	$.ajax({
		url:adminContextPath + "/zhfw/srtjxx/selectfwfwltj.do",
		data:{
		    "tjrqc":tjrqc,
		    "tjrqd":tjrqd
	    },
		type:'post',
		dataType:'json',
		success:function(result){
			console.log(result);
			if(result.responeMsg==null){
				var ywblsjdResult=result.data.ywblsjdResult;
				if(ywblsjdResult!=null){
					if(ywblsjdResult.length!=0){
						 var Data01=[];
						 var Data02=[];
						 for ( var i = 0; i <ywblsjdResult.length; i++){
							 var name ="";
							 var value="";
							 name=ywblsjdResult[i].QDID;
							 if(name=="QD000"){
								 name="核心系统";
							 }
							 if(name=="QD001"){
								 name="网厅";
							 }
							 if(name=="QD002"){
								 name="android_APP";
							 }
							 if(name=="QD003"){
								 name="网站";
							 }
							 if(name=="QD004"){
								 name="微信";
							 }
							 if(name=="QD005"){
								 name="微博";
							 }
							 if(name=="QD006"){
								 name="自助终端";
							 }
							 if(name=="QD007"){
								 name="12329热线";
							 }
							 if(name=="QD008"){
								 name="短信";
							 }
							 if(name=="QD009"){
								 name="支付宝";
							 }
							 
							 value=ywblsjdResult[i].QDTJZ;
							 Data01.push(name);
							 Data02.push(value);
						 }
						 option.xAxis[0].data=Data01;
						 option.series[0].data=Data02;
						 chart.setOption(option, true);
					 }
				}
				
			}

		},
		error:function(){
			 alert("尊敬的用户您好！当前页面连接异常，请刷新重试或退出页面重新登入！");
		 }
	});
};
































