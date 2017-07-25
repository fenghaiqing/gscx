 $(document).ready(function () {
        // 路径配置
        require.config({
            paths: {
                echarts: './js/echart/build/dist/'
            }
        });
        
   
       
       
       
       
       
       
       
       var time1 = new Date().Format("yyyy-M");
       query(time1);
    })
    
    function totable(year,preYear){
	 	var td="<td>本年</td>"
	 		var monthDate=['-1','-2','-3','-4','-5','-6','-7','-8','-9','-10','-11','-12']
		$.ajax({
       		url: "/mybatis/queryRant/selectByear.do",
               type:"get",
               data:{"date":year},
               dataType:"json",
               contentType:"application/json",
               success:function(data){
            	  if(data.length>0){
            		  for(var i=0;i<monthDate.length;i++){
               		   var flag =false;
               		   for(var j=0;j<data.length;j++){
               			   var str =data[j].month;
               			   if(str.indexOf(monthDate[i])>=0){
               				 td+="<td>"+data[j].rant+"</td>" 
               				   flag=true;
               				   break;
               			   }
               		   }
               		   if(!flag){
               			 td+="<td>"+0+"</td>" 
               		   }
               	   }
            	  }
            	  $("#thisyear").append(td);
               },error:function(){
            	   alert("操作失败！");
               }
       	});
	 	var prtd="<td>上年</td>"
	 	$.ajax({
       		url: "/mybatis/queryRant/selectByear.do",
               type:"get",
               data:{"date":preYear},
               dataType:"json",
               contentType:"application/json",
               success:function(data){
            	  if(data.length>0){
            		  for(var i=0;i<monthDate.length;i++){
               		   var flag =false;
               		   for(var j=0;j<data.length;j++){
               			   var str =data[j].month;
               			   if(str.indexOf(monthDate[i])>=0){
               				prtd+="<td>"+data[j].rant+"</td>" 
               				   flag=true;
               				   break;
               			   }
               		   }
               		   if(!flag){
               			prtd+="<td>"+0+"</td>" 
               		   }
               	   }
            	  }
            	  $("#preyear").append(prtd);
               },error:function(){
            	   alert("操作失败！");
               }
       	});
 }
 
 // scriber();
 function getUpPackeingRat(year,preMonth){
 	var date=year;
 	var rantDate=[];
 	var cakeDate= [];
 	var contrastList=[];
 	var monthData=['7-1','7-2','7-3','7-4','7-5','7-6','7-7','7-8','7-9','7-10',
     	'7-11','7-12','7-13','7-14','7-15','7-16','7-17','7-18','7-19','7-20',
     	'7-21','7-22','7-23','7-24','7-25','7-26','7-27','7-28','7-29','7-30']
 		var monthDataRant=[];
 	var testTpData=[ '振幅测试','性能测试', '应力测试','噪音测试'];
	  	var tpData=[];
	  	var tlist=[];
 	var queryMonthUrl ="/mybatis/queryRant/selectByDate.do";
	$.ajax({
		url: queryMonthUrl,
        type:"get",
        data:{"date":date},
        dataType:"json",
        contentType:"application/json",
        success:function(data){
     	   rantDate= data;
     	   for(var i=0;i<monthData.length;i++){
     		   var flag =false;
     		   for(var j=0;j<rantDate.length;j++){
     			   var str =rantDate[j].createDate;
     			   if(str.indexOf(monthData[i])>=0){
     				   monthDataRant.push((rantDate[j].point/10));
     				   flag=true;
     				   break;
     			   }
     		   }
     		   if(!flag){
     			   monthDataRant.push(0);
     		   }
     	   }
     	   
        },error:function(){
     	   alert("操作失败！");
        }
	});
	$.ajax({
		url: queryMonthUrl,
        type:"get",
        data:{"date":preMonth},
        dataType:"json",
        contentType:"application/json",
        success:function(data){
     	   cakeDate= data;
     	   for(var i=0;i<monthData.length;i++){
     		   var flag =false;
     		   for(var j=0;j<cakeDate.length;j++){
     			   var str =cakeDate[j].createDate;
     			   if(str.indexOf(monthData[i])>=0){
     				   contrastList.push((cakeDate[j].point/10));
     				   flag=true;
     				   break;
     			   }
     		   }
     		   if(!flag){
     			   contrastList.push(0);
     		   }
     	   }
     	   
        },error:function(){
     	   alert("操作失败！");
        }
	});
 scriber(monthData,monthDataRant,contrastList);
};
 
function scriber(monthData,monthDataRant,contrastList){
    // 使用
    require(
            [
                'echarts',
                'echarts/chart/line', // 使用柱状图就加载line模块，按需加载
                'echarts/chart/bar', // 使用柱状图就加载line模块，按需加载
                'echarts/chart/pie' // 使用柱状图就加载pie模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                // 开箱合格率南丁格尔玫瑰图-上月
                var myChart = ec.init(document.getElementById('main-csbhgb'));
                var option = {
                	    title : {
                	        text: '投诉处理即时率 趋势分析',
                	        subtext: '纯属虚构'
                	    },
                	    tooltip : {
                	        trigger: 'axis',
                	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                	    },
                	    legend: {
                	        data:['本年','上年']
                	    },
                	    
                	    toolbox: {
                	        show : true,
                	        feature : {
                	            mark : {show: true},
                	            dataView : {show: true, readOnly: false},
                	            magicType : {show: true, type: ['line', 'bar']},
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    calculable : true,
                	    xAxis : [
                	        {
                	            type : 'category',
                	            data :monthData
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'value'
                	        }
                	    ],
                	    series : [
                	        {
                	            name:'本年',
                	            type:'bar',
                	            radius: [0, 100],
                	            data:monthDataRant,
                	            markPoint : {
                	                data : [
                	                    {type : 'max', name: '最大值'},
                	                    {type : 'min', name: '最小值'}
                	                ]
                	            },
                	            markLine : {
                	                data : [
                	                    {type : 'average', name: '平均值'}
                	                ]
                	            }
                	        },
                	        {
                	            name:'上年',
                	            type:'bar',
                	            data:contrastList,
                	            markPoint : {
                	                data : [
                	                    {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
                	                    {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
                	                ]
                	            },
                	            markLine : {
                	                data : [
                	                    {type : 'average', name : '平均值'}
                	                ]
                	            }
                	        }
                	    ]
                	};
                	                    
                // 为echarts对象加载数据
                myChart.setOption(option);
              
            }
    );

};



   function showPritcutr(){
	   $("#Pictur").show();
	   $("#tables").css("display","none");
   }
   function showTable(){
	   $("#tables").show();
	   $("#Pictur").css("display","none");
   }
   
   function query(item){
	   var date="";

	   if(item==undefined ||item==""|| item==null){
		   date= $("#name").val();
	   }else{
		   date=item;
	   }
	    
	   var year = date.substring(0,4);
	   var monte=date.substring(date.indexOf("-")+1);
	 
	   var preYear=parseInt(year)-1;
	   var preMonth=preYear+"-"+monte;
	   getUpPackeingRat(date,preMonth);
       totable(year,preYear)
       	queryRant(date)
   }
   
   function queryRant(date){
	   var result=0
	   var monte=date.substring(date.indexOf("-")+1);
	   $.ajax({
			url: "/mybatis/queryRant/selectRant.do",
	        type:"get",
	        data:{"date":date},
	        dataType:"json",
	        contentType:"application/json",
	        success:function(data){
	        	$("#result").html(monte+"月份处理及时率为"+(data.thisrant/10)+"%"+"与上月（"+(data.prerant/10)+"%）相比提升"+(parseFloat(data.thisrant)-parseFloat(data.prerant)).toFixed(2)+"%"
	        	+"与去年同期比（"+(data.lastrant/10)+"）下降"+(parseFloat(data.lastrant)-parseFloat(data.thisrant)).toFixed(2)+"%");
	        },error:function(){
	     	  
	        }
		});
	  
	  
   }
   
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) { // author: meizz
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds() // 毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

