//初始化收入差额列表页面
$(function(){
	changeMonthType('q_month_start');
	changeMonthType('q_month_end');
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	$('#list_data').datagrid({
		title:'预估与实际收入差额展示列表',  
		width: '100%',  
		height: 'auto',  
		nowrap: false,  
		striped: true,
		border: true, 
		fitColumns: true,
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的  
		fit: true,//自动大小  
		url:contextPath + '/actualIncome/queryAllIncomeDiff.do',  
		remoteSort:false,   
		checkOnSelect :false,
		singleSelect:false,//是否单选  
		pagination:true,//分页控件  
		pageSize: 20,//每页显示的记录条数，默认为10
		pageList: [5,10,15,20],//可以设置每页记录条数的列表
		columns:[[
			  		{ field:'index',width : '5%', align: "center",title:'序号',formatter:function(val,row,index){
				     var options = $("#list_data").datagrid('getPager').data("pagination").options;
				     var currentPage = options.pageNumber==0?1:options.pageNumber;
				     var pageSize = options.pageSize;
				     return (pageSize * (currentPage -1))+(index+1);
				    }},
					{ field:'projectId',width : '10%',align : "center",title:'项目号' },
					{ field:'projectName',width : '10%',align : "center",title:'项目名称' },
					{ field:'deptName',width : '10%',align : "center",title:'责任部门' },
					{ field:'personName',width : '10%',align : "center",title:'责任人' },
					{ field:'cycle',width : '5%',align : "center",title:'业务账期' },
					{ field:'estimateIncome',width : '13%',align : "center",title:'预估收入<br>（含税）' },
					{ field:'realIncome',width : '13%',align : "center",title:'实际收入<br>（含税）' },
					{ field:'deferenceInIncome',width : '14%',align : "center",title:'差额（元，含税）<br>(实际收入-预估收入)'},
					{ field:'offSet',width : '10%',align : "center",title:'实际收入是否已冲销<br>预估收入',formatter:function(value,row, index){
						if(value=='0')
							return '否';
						if(value=='1')
							return '是'
					}}
				]],
		toolbar: '#tb'
	});  
	
	//设置分页控件  
	var page = $('#list_data').datagrid('getPager');
	$(page).pagination({  
		beforePageText: '第',//页数文本框前显示的汉字  
		afterPageText: '页    共 {pages} 页',  
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
		onBeforeRefresh:function(){ 
			$(this).pagination('loading'); 
			$(this).pagination('loaded'); 
		}
	});
});
var lastDate= undefined;

//重置搜索框
function reset(){
	$("#q_projectId").textbox('setValue','');
	$("#q_projectName").textbox('setValue','');
	$('#q_dept').combobox('setValue','');
	$('#q_person').combobox('setValue','');
	$("#q_month_start").datebox('setValue','');
	$("#q_month_end").datebox('setValue','');
}


//条件查询
function searchSubmit(){
	var q_projectId = $('#q_projectId').val();
	var q_projectName = $('#q_projectName').val();
	var q_dept = $('#q_dept').combobox('getValue');
	var q_person = $('#q_person').combobox('getValue');
	var q_month_start = $('#q_month_start').datebox('getValue');
	var q_month_end = $('#q_month_end').datebox('getValue');
	$('#list_data').datagrid('load', {
		q_projectId : q_projectId,
		q_projectName : q_projectName,
		q_dept : q_dept,
		q_person : q_person,
		q_month_start : q_month_start,
		q_month_end : q_month_end
	});
}

//审核人查询
$(document).ready(function () {
		$('#q_dept').combobox({
		    url:contextPath+"/actualIncome/queryAllDep.do",
		    valueField:'deptCode',
		    textField:'deptName',
		    editable:false
		});
	
		$("#q_dept").combobox({
			onChange: function (n,o) {
				var deptId = $('#q_dept').combobox('getValue');
				$('#q_person').combobox('setValue','');
				 $.ajax({  
					    type: "POST",  
					    url: contextPath+"/actualIncome/queryDepPerson.do",  
					    data: {deptId: deptId},  
					    dataType: "json",  
					    success: function(response){//调用成功  
					    var options=$("#q_person").combobox('options');  
					    options.textField="userName";//必须要和数据库查询的字段一样(大小写敏感)  
					    options.valueField="userId";  
					    options.editable = false;
						//加载数据  
						$("#q_person").combobox("loadData",response);  
					    },  
					 }); 
				
			}
		});
	});

//判断是否选取部门
$(document).ready(function () {
	$("#q_person").combo({
		onShowPanel: function () {
			var deptId = $('#q_dept').combobox('getValue');
			if(deptId == null || deptId ==''){
				$.messager.alert('提示', '请先选取责任部门！');
				$("#q_person").combo('hidePanel');
			}
		}
	})
});


//导出excel
function exportExc(){
	var projectId = $('#q_projectId').val();
	var projectName = $('#q_projectName').val();
	var dept = $('#q_dept').combobox('getValue');
	var person = $('#q_person').combobox('getValue');
	var monthStart = $('#q_month_start').datebox('getValue');
	var monthEnd = $('#q_month_end').datebox('getValue');
	$.messager.confirm('提示',"你确定要全量导出该查询结果吗？",function(r){
	    if (r)
	    {
	    	$.ajax({    
	    		dataType: "json",
	    	    data: {	projectId:projectId,
		    	    	projectName:projectName,
		    	    	dept:dept,
		    	    	person:person,
		    	    	monthStart:monthStart,
		    	    	monthEnd:monthEnd
	    	    	   },
	    	    type: "post",
	    	    url: contextPath+"/actualIncome/exportExc.do"
	    	}).done(function(data){
	    		if(data.reCode=="100"){
	    	    	$("#exportform1").form("submit", {
	    	            url : contextPath+"/actualIncome/downloadFile.do",
	    	            onSubmit : function(param) {
	    	            	param.fileName = data.fileName;
	    	                return this;
	    	            }
	    	        });
	    	    }else{
	    	    	$.messager.alert('提示',data.reStr,'warning');
	    	    }
	        }).fail(function(){
	        	$.messager.alert('提示',"本次操作失败，请重新操作",'error');
	    		return false;
	        });
	    }
    });
}