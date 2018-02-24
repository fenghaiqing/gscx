//初始化两非项目分部门报表页面
$(function(){
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	$('#list_data').datagrid({
		title:'两非项目分部门报表(注：报表中涉及金额的单位都为元)',  
		width: '100%',  
		height: 'auto',  
		nowrap: false,  
		striped: true,
		border: true, 
		fitColumns: true,
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的  
		fit: true,//自动大小  
		url:contextPath + '/twoNonReport/querySectorIncome.do',  
		remoteSort:false,   
		checkOnSelect :false,
		singleSelect:false,//是否单选  
		pagination:true,//分页控件  
		pageSize: 20,//每页显示的记录条数，默认为10
		pageList: [5,10,15,20],//可以设置每页记录条数的列表
		columns:[[
					{ field:'monthId',width : '5%',align : "center",title:'账期' },
					{ field:'deptId',hidden:'true'},
					{ field:'deptName',width : '8%',align : "center",title:'责任部门' },
					{ field:'className',width : '10%',align : "center",title:'收入大类' },
					{ field:'sectionName',width : '10%',align : "center",title:'收入小类' },
					{ field:'estimateTax',width : '10%',align : "center",title:'总预估收入金额<br>（不含税）' },
					{ field:'realTax',width : '10%',align : "center",title:'总实际收入金额<br>（不含税）' },
					{ field:'actualIncome',width : '10%',align : "center",title:'总实际收款金额<br>（含税）' },
					{ field:'costAmountTotal',width : '10%',align : "center",title:'总成本金额' },
					{ field:'profitAmount',width : '10%',align : "center",title:'利润（金额）' },
					{ field:'profitRatio',width : '8%',align : "center",title:'成本利润率'},
					{ field:'profitRatio2',width : '8%',align : "center",title:'销售利润率'}
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

//重置搜索框
function reset(){
	$('#q_dept').combobox('setValue','');
}


//条件查询
function searchSubmit(){
	var q_dept = $('#q_dept').combobox('getValue');
	$('#list_data').datagrid('load', {
		q_dept : q_dept
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
	});

//导出excel
function exportExc(){
	var dept = $('#q_dept').combobox('getValue');
	$.messager.confirm('提示',"你确定要全量导出该查询结果吗？",function(r){
	    if (r)
	    {
	    	$.ajax({    
	    		dataType: "json",
	    	    data: {	
		    	    	dept:dept
	    	    	   },
	    	    type: "post",
	    	    url: contextPath+"/twoNonReport/exportExc2.do"
	    	}).done(function(data){
	    		if(data.reCode=="100"){
	    	    	$("#exportform1").form("submit", {
	    	            url : contextPath+"/twoNonReport/downloadFile.do",
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