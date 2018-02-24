//初始化合同列表页面
$(function(){
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	$('#list_data').datagrid({
		title:'预算信息列表',  
		width: '100%',  
		height: 'auto',  
		nowrap: false,  
		striped: true,
		border: true, 
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的  
		fit: true,//自动大小  
		url:contextPath + '/estimate/queryContractBudget.do',  
		remoteSort:false,   
		checkOnSelect :false,
		singleSelect:false,//是否单选  
		pagination:true,//分页控件  
		pageSize: 20,//每页显示的记录条数，默认为10
		pageList: [5,10,15,20],//可以设置每页记录条数的列表
		columns:[[
					{ field:'ck',checkbox:true },
			//		{ field:'budgetResultId',hidden:'true'},
					{ field:'budgetYear',width : '9%',align : "center",title:'预算年度' },
					{ field:'budgetDeptCode',width : '10%',align : "center",title:'预算使用部门' },
					{ field:'deptCode',width : '10%',align : "center",title:'预算编制部门' },
					{ field:'budgetProjectNumber',width : '10%',align : "center",title:'预算项目号' },
					{ field:'budgetProjectName',width : '13%',align : "center",title:'预算项目名称',
						formatter:function (value, data, index) {
							if (!isNull(value)&& value.length>15) {
								str=value.substring(0,14);
								return '<a title="'+value+'"  class="easyui-tooltip">'+str+'...</a>'
								}else{
									return value;
								}
							}	
					},
					{ field:'budgetSubjectCode',width : '8%',align : "center",title:'预算科目编码' },
					{ field:'budgetSubjectName',width : '12%',align : "center",title:'预算科目名称',
						formatter:function (value, data, index) {
							if (!isNull(value)&& value.length>12) {
								str=value.substring(0,11);
								return '<a title="'+value+'"  class="easyui-tooltip">'+str+'...</a>'
								}else{
									return value;
								}
							}	
					},
					{ field:'budgetAmount',width : '10%',align : "center",title:'预算可用金额' },
					{ field:'budgetTotalMoney',width : '10%',align : "center",title:'预算总金额' },
					{ field:'budgetResultId',width : '7%',align : "center",title:'预算编制ID' }
				]],
				onLoadSuccess: function (data) {
		            if (data.Exception != null) {
		            	$.messager.alert('告警','预算接口数据查询异常，请检查！','warning');
		            }
		            $(".easyui-tooltip").tooltip({
		                onShow: function () {
		                    $(this).tooltip('tip').css({
		                        borderColor: '#000'
		                    });
		                }
		            });
		        },
		toolbar: '#tb'
	});  
	
	$("#budgetYear").textbox('textbox').attr("placeholder","请输入年份(如："+new Date().format("yyyy")+")");
	//设置分页控件  
	var p = $('#list_data').datagrid('getPager');
	$(p).pagination({  
		beforePageText: '第',//页数文本框前显示的汉字  
		afterPageText: '页    共 {pages} 页',  
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
		onBeforeRefresh:function(){ 
			$(this).pagination('loading'); 
			$(this).pagination('loaded'); 
		}
	});
});


//重置合同查询
function reset(){
	$("#budgetResultId").textbox('setValue','');
	$("#budgetYear").textbox('setValue','');
	$('#budgetDeptCode').textbox('setValue','');

}

//预算项目查询
function searchSubmit(){
	var budgetResultId = $('#budgetResultId').val();
	var budgetYear = $('#budgetYear').val();
	var budgetDeptCode = $('#budgetDeptCode').val();
	if(isNull(budgetResultId) &&(isNull(budgetYear) || isNull(budgetDeptCode))){
		$.messager.alert("提示","预算年度和预算使用部门都不能为空！");
		return ;
	}
	$('#list_data').datagrid('load', {
		budgetResultId : budgetResultId,
		budgetYear : budgetYear,
		budgetDeptCode : budgetDeptCode,
	});
}

function Budget(budgetYear,budgetDeptUse,budgetDeptDraw,budgetProjectNumber,budgetProjectName,
		budgetSubjectCode,budgetAvailableAmount,budgetTotalAmount,budgetResultId,projectId){
	
	this.budgetYear = budgetYear; 
	this.budgetDeptUse = budgetDeptUse; 
	this.budgetDeptDraw = budgetDeptDraw; 
	this.budgetProjectNumber = budgetProjectNumber; 
	this.budgetProjectName = budgetProjectName; 
	this.budgetSubjectCode = budgetSubjectCode; 
	this.budgetAvailableAmount = budgetAvailableAmount; 
	this.budgetTotalAmount = budgetTotalAmount; 
	this.budgetResultId = budgetResultId; 
	this.projectId = projectId; 
}

//关联合同
function doAssociatedContract(){
	var menuName=parent.$('#home-center-tabs').tabs('getSelected').panel('options').title;
	var projectId = menuName.substr(8);
	
	var selRow = $("#list_data").datagrid("getChecked");
	if (selRow.length == 0) {
		$.messager.alert('告警','请至少选择一行数据!','warning');
		return false;
	}
	var budgetName="";
	var budgets = new Array();
	for (var i = 0; i < selRow.length; i++){
		var budget = new Budget(
				selRow[i].budgetYear,
				selRow[i].budgetDeptCode,
				selRow[i].deptCode,
				selRow[i].budgetProjectNumber,
				selRow[i].budgetProjectName,
				selRow[i].budgetSubjectCode,
				selRow[i].budgetAmount,
				selRow[i].budgetTotalMoney,
				selRow[i].budgetResultId,
				projectId
			); 
		budgets.push(budget);
		
		if(i==selRow.length-1){
			budgetName = budgetName+selRow[i].budgetProjectNumber;
		}else{
			budgetName = budgetName+selRow[i].budgetProjectNumber+"、";
		}
		
	}
	console.log(budgets);
	
	$.messager.confirm('提示',"确定要与   "+budgetName+"  关联预算吗？",function(r){
		if (r){
			$.ajax({
			    dataType: "json",
			    data: JSON.stringify(budgets),
			    method: "post",
			    contentType:"application/json",   
			    url: contextPath + "/estimate/associatedBudget.do"
			}).done(function(data){
			    if(data.status=="100"){
			    	var failMsg='';
			    	var sucMsg='';
			    	if(!isNull(data.fail)){
			    		failMsg=data.fail.substring(0,data.fail.lastIndexOf(","))+'已与本项目绑定，本次绑定失败。'
			    	}
			    	if(!isNull(data.successful)){
			    	sucMsg=data.successful.substring(0,data.successful.lastIndexOf(","))+"绑定成功。";
			    	}
			    	$.messager.alert('提示',failMsg+"<br/>"+sucMsg);
			    	$("#list_data").datagrid('clearSelections').datagrid('clearChecked');
			    	searchSubmit();
			    }else{
			    	$.messager.alert('提示',data.msg,'warning');
			    	$('#list_data').datagrid('reload',{});
			    }
		    }).fail(function(){
		    	$.messager.alert('提示',"本次操作失败，请重新操作",'error');
				return false;
		    });	    
		    }else{
		    	$("#list_data").datagrid("clearSelections");
		    }
	    });
}

