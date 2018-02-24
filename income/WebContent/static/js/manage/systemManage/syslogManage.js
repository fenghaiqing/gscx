// JavaScript Document
$(function(){
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	
	//datagrid初始化  
	$('#list_data').datagrid({
		title:'操作日志列表',  
		iconCls:'icon-edit',//图标  
		width: 770,  
		height: 'auto',  
		nowrap: false,  
		striped: true,
		border: true, 
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的  
		fit: true,//自动大小  
		url:contextPath + '/syslog/queryAllSyslog.do', 
		remoteSort:false, 
		checkOnSelect :false,//checkOnSelect 如果为true，当用户点击行的时候该复选框就会被选中或取消选中。如果为false，当用户仅在点击该复选框的时候才会呗选中或取消。
		singleSelect:false,//是否单选  
		pagination:true,//分页控件  
		//rownumbers:true,//行号
		pageSize: 20,//每页显示的记录条数，默认为10
		pageList: [5,10,15,20],//可以设置每页记录条数的列表
		columns:[[
			//{ field:'ck',checkbox:true,width :78,align : "center"},
			{ field:'rn',width : '5%',align : "center",title:'' },
			{ field:'userId',width : '10%',align : "center",title:'用户ID' },
			{ field:'userName',width : '10%',align : "center",title:'用户姓名' },
			{ field:'logDate',width : '10%',align : "center",	 title:'操作时间' },
			{ field:'operation',width : '20%',align : "center", title:'操作内容'},   
			{ field:'comments',width : '30%',align : "center",title:'操作说明'
			//	formatter:function(value, rowData, rowIndex) {
			//	    if (value.length >490) {
			//	        return "<span id='com"+rowIndex+"'>"+value.substr(0,50) +"<span style='color:blue'>>></span></span>";
			//	    }
			//	    return value;
			//	},
			},
			{ field:'userLoginIp',width : '10%',align : "center", title:'登录用户IP'},
			
		]],
//		onClickRow:function (index, row) { 
//			console.log($("#com"+index).text().length);
//			if($("#com"+index).text().length>52){
//				$("#com"+index).html((row.comments).substr(0,50)+"<span style='color:blue'>>></span>");
//			}else{
//				$("#com"+index).html(row.comments+"<span style='color:blue'><<</span>");
//			}
//		},
		toolbar: '#tb',
			
	});
	
	
	//设置分页控件  
	var p = $('#list_data').datagrid('getPager');  
	$(p).pagination({   
		beforePageText: '第',//页数文本框前显示的汉字  
		afterPageText: '页    共 {pages} 页',  
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
		onBeforeRefresh:function(){ 
			$(this).pagination('loading'); 
			// alert('before refresh'); 
			$(this).pagination('loaded'); 
		}
	});
	
		
});
//function showall(val,i){
//	//$("#com"+i).innerHTML=val;
//	$("#com"+i).empty();//jQuery方法一
//	$("#com"+i).html("<span  id='com"+i+"'  onclick='showhalf("+"\""+ val +"\""+",\""+ i +"\""
//								+ ")'>"+val+"<span style='color:red'><<</span></span>");//jQuery方法二
//		
//}
//function showhalf(vall,ii){
//	//$("#com"+i).innerHTML=val;
//	//$("#com"+ii).empty();//jQuery方法一
//	$("#com"+ii).html("<span  id='com"+ii+"'  onclick='showall("+"\""+ vall +"\""+",\""+ ii +"\""
//								+ ")'>"+vall.substr(0,50)+"<span style='color:red'>>>...</span></span>");//jQuery方法二
//		
//}

//5个参数查询
function searchSubmit(){
	var userName = $('#userName').val();
	var operation = $('#operation').val();
	var startDate = $('#startDate').datebox('getValue');
	var endDate = $('#endDate').datebox('getValue');
	if(new Date(startDate).getTime()>new Date(endDate).getTime()){
		$.messager.alert('告警','开始时间不能大于结束时间','warning');
 		return false;
	}
	
	//alert(key2);
	$('#list_data').datagrid('load', {
		userName : userName,
		operation : operation,
		startDate : startDate,
		endDate : endDate,
		chaxun : "tiaojian"
		
	});
}
