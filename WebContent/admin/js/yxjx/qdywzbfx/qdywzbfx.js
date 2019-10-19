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
	
	
	qdywzbfxcx();	//渠道业务分析占比
	
	// 查询
	$("#qdywzbfx_qjcxan").click(function() {
		qdywzbfxcx();
	});
	
});


//用户性别情况统计
function qdywzbfxcx(){
	var dom = document.getElementById("g_qdywzbfx");
    var myChart = echarts.init(dom,'light');  //白色背景
    var weatherIcons = {
        'Sunny': './data/asset/img/weather/sunny_128.png',
        'Cloudy': './data/asset/img/weather/cloudy_128.png',
        'Showers': './data/asset/img/weather/showers_128.png'
    };

    var option = {
        title: {
            text: '渠道业务占比分析',
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

    loadData(myChart,option);
}

function loadData(chart,option){
	var tjrqc=$("#qdywzbfx_tjsjc").val();
	var tjrqd=$("#qdywzbfx_tjsjd").val();
	var ywlb=$("#qdywzbfx_ywlb").val();
	
	$.ajax({
		 url:adminContextPath + "/zhfw/srtjxx/selectQdywzbtj.do",
		 type:"post",
		 data:{"ywlb":ywlb,
			    "tjrqc":tjrqc,
			    "tjrqd":tjrqd
		 },
		 dataType:"json",
		 success:function(result){
			 if(result.responeMsg==null){
				 if(result.data.length!=null){
					 var resultdata=result.data;
					 var Data=[];
					 for ( var i = 0; i <resultdata.length; i++){
						 var name ="";
						 var value="";
						 var list={};
						 var qdid=resultdata[i].QDID;
						 if(qdid=="QD000"){
							 name="核心系统";
						 }
						 if(qdid=="QD001"){
							 name="网厅";
						 }
						 if(qdid=="QD002"){
							 name="android_APP";
						 }
						 if(qdid=="QD003"){
							 name="网站";
						 }
						 if(qdid=="QD004"){
							 name="微信";
						 }
						 if(qdid=="QD005"){
							 name="微博";
						 }
						 if(qdid=="QD006"){
							 name="自助终端";
						 }
						 if(qdid=="QD007"){
							 name="12329热线";
						 }
						 if(qdid=="QD008"){
							 name="	短信";
						 }
						 if(qdid=="QD009"){
							 name="支付宝";
						 }
						 
						 value=resultdata[i].QDTJZ;
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


























