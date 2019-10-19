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
	
	
	$("#qdywzbfx_tjsjc").val(time01);
	$("#qdywzbfx_tjsjd").val(time02);

    
	ywblsjdfx();	//业务办理时间段分析
	
	blywlxfx();	//办理业务类型分析
	
	
	// 查询
	$("#qdywzbfx_cx").click(function() {
		ywblsjdfx();	//业务办理时间段分析
		
		blywlxfx();	//办理业务类型分析
	});

});

//办理业务类型分析
function blywlxfx(){
	var dom = document.getElementById("ywbllxsj");
    var myChart = echarts.init(dom);
    var app = {};
    var option = null;
    
    option = {
        title: {
            text: '办理业务类型分析'
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
                name:"业务量",
                type:'bar',
                barWidth: '60%'
            }
        ]
    };

    loadBlywlxfxData(myChart,option);
}

function loadBlywlxfxData(chart,option){
    
	var tjrqc=$("#qdywzbfx_tjsjc").val();
	var tjrqd=$("#qdywzbfx_tjsjd").val();
	var qdid=$("#qdyxzb_qdlbxlk").val();
	
	$.ajax({
		 url:adminContextPath + "/zhfw/srtjxx/selectQdyxzbtj.do",
		 type:"post",
		 data:{"qdid":qdid,
			    "tjrqc":tjrqc,
			    "tjrqd":tjrqd
		 },
		 dataType:"json",
		 success:function(result){
			 if(result.responeMsg==null){
				 var ywbllxResult=result.data.ywbllxResult;
				 if(ywbllxResult.length!=0){
					 var Data01=[];
					 var Data02=[];
					 for ( var i = 0; i <ywbllxResult.length; i++){
						 var name ="";
						 var value="";
						 var ywlb=ywbllxResult[i].TJZBMX;
						 if(ywlb=="001"){
							 name="缴存类";
						 }
						 if(ywlb=="002"){
							 name="提取类";
						 }
						 if(ywlb=="003"){
							 name="贷款类";
						 }
						 if(ywlb=="004"){
							 name="信息发布类";
						 }
						 if(ywlb=="005"){
							 name="信息查询类";
						 }
						 if(ywlb=="006"){
							 name="互动交流类";
						 }
						 
						 value=ywbllxResult[i].QDTJZ;
						 Data01.push(name);
						 Data02.push(value);
					 }
					 option.xAxis[0].data=Data01;
					 option.series[0].data=Data02;
					 
				 }
//				 option.xAxis[0].data=['提取','贷款','缴交','开户', '转移', '封存', '启封','基数变更'];
//				 option.series[0].data=[820, 932, 901, 934, 1290, 1330, 1320,1291];
				 chart.setOption(option, true);
			 }

		 },
		 error:function(){
			 alert("尊敬的用户您好！当前页面连接异常，请刷新重试或退出页面重新登入！");
		 }
	});
	
}



//业务办理时间段分析
function ywblsjdfx(){
	var dom = document.getElementById("ywblsjfx");
    var myChart = echarts.init(dom);
    var app = {};
    var option = null;

    option = {

    	    title: {
    	        text: '访问时段分析'
    	    },
    	    tooltip: {
    	        trigger: 'axis',
    	        axisPointer: {
    	            type: 'cross'
    	        }
    	    },
    	    toolbox: {
    	        show: true,
    	        feature: {
    	            saveAsImage: {}
    	        }
    	    },
    	    xAxis:  {
    	        type: 'category',
    	        data:['00:00','01:00', '02:00', '03:00', '04:00', '05:00', '06:00', '07:00', '08:00', '09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00', '22:00', '23:00'],
    	        boundaryGap: false
    	    },
    	    yAxis: {
    	        type: 'value',
    	        axisLabel: {
    	            formatter: '{value} '
    	        },
    	        axisPointer: {
    	            snap: true
    	        }
    	    },
    	    visualMap: {
    	        show: false,
    	        dimension: 0,
    	        pieces: [{
    	            lte: 6,
    	            color: 'green'
    	        }, {
    	            gt: 6,
    	            lte: 8,
    	            color: 'red'
    	        }, {
    	            gt: 8,
    	            lte: 14,
    	            color: 'green'
    	        }, {
    	            gt: 14,
    	            lte: 17,
    	            color: 'red'
    	        }, {
    	            gt: 17,
    	            color: 'green'
    	        }]
    	    },
    	    series: [
    	        {
    	            name:'业务量',
    	            type:'line',
    	            smooth: true,
    	            data: []
    	        }
    	    ]

    };

    loadBlywblsjdfxData(myChart,option);
}

function loadBlywblsjdfxData(chart,option){
    var tjrqc=$("#qdywzbfx_tjsjc").val();
	var tjrqd=$("#qdywzbfx_tjsjd").val();
	var qdid=$("#qdyxzb_qdlbxlk").val();
	
	$.ajax({
		 url:adminContextPath + "/zhfw/srtjxx/selectQdyxzbtj.do",
		 type:"post",
		 data:{"qdid":qdid,
			    "tjrqc":tjrqc,
			    "tjrqd":tjrqd
		 },
		 dataType:"json",
		 success:function(result){
			 if(result.responeMsg==null){
				 var Data01=[];
				 var Data02=[];
				 var ywblsjdResult=result.data.ywblsjdResult;
				 if(ywblsjdResult.length!=0){
					 for ( var i = 0; i <ywblsjdResult.length; i++){
						 var name ="";
						 var value="";
						 value=ywblsjdResult[i].QDTJZ;
						 Data01.push(name);
						 Data02.push(value);
					 }
					 option.series[0].data=Data02;
				 }
				 chart.setOption(option, true);
			 }

		 },
		 error:function(){
			 alert("尊敬的用户您好！当前页面连接异常，请刷新重试或退出页面重新登入！");
		 }
	});
}
























