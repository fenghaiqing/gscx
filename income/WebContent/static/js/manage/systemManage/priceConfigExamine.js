var editIndex = undefined;
//初始化定价配置审核列表页面
$(function(){
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	$('#list_data').datagrid({
		title:'定价配置审核列表',  
		width: '100%',  
		height: 'auto',  
		nowrap: false,  
		striped: true,
		border: true, 
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的  
		fit: true,//自动大小  
		url:contextPath + '/examine/queryAllExamine.do',  
		remoteSort:false,   
		checkOnSelect :false,
		singleSelect:false,//是否单选  
		pagination:true,//分页控件  
		pageSize: 20,//每页显示的记录条数，默认为10
		pageList: [5,10,15,20],//可以设置每页记录条数的列表
		columns:[[
		          	{ field:'priceConfigId',hidden:'true'},
					{ field:'priceConfigNumber',width : '10%',align : "center",title:'审核流水号' },
					{ field:'projectId',width : '10%',align : "center",title:'项目号' },
					{ field:'projectDeptName',width : '10%',align : "center",title:'责任部门' },
					{ field:'projectUserName',width : '10%',align : "center",title:'责任人' },
					{ field:'submitAuditTime',width : '10%',align : "center",title:'提交时间' },
					{ field:'performAuditTime',width : '10%',align : "center",title:'审核时间' },
					{ field:'priceConfigAuditResult',width : '10%',align : "center",title:'审核结果',formatter:function(value,row, index){
						if(value=='1')
							return '待审核';
						if(value=='2')
							return '审核通过';
						if(value=='3')
							return '驳回'
					} },
					{ field:'handlingSuggestion',width : '10%',align : "center",title:'处理意见',formatter:function(value, row, index) {   
						var content = '';   
						var abValue = value +'';   
						if(value != undefined&&value != null){      
							if(value.length>=6) {         
								abValue = value.substring(0,6) + "...";         
								content = '<span  title="' + value + '" class="easyui-tooltip">' + abValue + '</span>';      
							}else{         
								content = '<span  title="' + abValue + '" class="easyui-tooltip">' + abValue + '</span>';      
							}   
						}   
						return content;
					}  },
					{ field : 'opt',width : '25%',align : "center",title : '操作',
						formatter : function(value, rec, index) {
							if(rec.priceConfigAuditResult=='1'){
								var a = "<a href='javascript:void(0)'  onclick='examine("
									+ "\""+rec.priceConfigId+"\"" 
									+ ")' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit l-btn-icon-left'>审核</span></span></a>";
								var b = "<a href='javascript:void(0)'  onclick='examineHistory("
									+"\""+ rec.priceConfigId +"\")' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit l-btn-icon-left'>审核历史</span></span></a>";
								return a + b;
							}else{
								var b = "<a href='javascript:void(0)'  onclick='examineHistory("
									+"\""+ rec.priceConfigId +"\")' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit l-btn-icon-left'>审核历史</span></span></a>";
								return b;
							}
						}
					}
				]],
		toolbar: '#tb'
	});  
	
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
	
	//初始化责任部门下拉列表
	$('#q_projectDeptId').combobox({
	    url:contextPath+"/manage/queryAllDep.do",
	    valueField:'deptCode',
	    textField:'deptName',
	    editable:false
	});
	
});

//定价配置审核查询
function searchSubmit(){
	var currentUserId = $('#currentUserId').val();
	var q_projectId = $('#q_projectId').val();
	var q_projectDeptId = $('#q_projectDeptId').combobox('getValue');
	var q_projectUserName = $('#q_projectUserName').val();
	var q_priceConfigAuditResult = $('#q_priceConfigAuditResult').combobox('getValue');
	
	$('#list_data').datagrid('load', {
		q_projectId : q_projectId,
		q_projectDeptId : q_projectDeptId,
		q_projectUserName : q_projectUserName,
		q_priceConfigAuditResult : q_priceConfigAuditResult
	});
}

//重置定价配置审核查询
function reset(){
	$("#q_projectId").textbox('setValue','');
	$('#q_projectDeptId').combobox('setValue','');
	$("#q_projectUserName").textbox('setValue','');
	$("#q_priceConfigAuditResult").combobox('setValue','1');
}

//审核
function examine(priceConfigId){
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	$('#list_data_viewPriceConfigInfo').datagrid({
		width: '100%',  
		height: '60%',  
		nowrap: false,  
		striped: true,
		border: true, 
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的  
		url:contextPath + '/priceConfig/queryAllPriceConfigInfo.do', 
		queryParams : {
			type : 'update',
			priceConfigId : priceConfigId
		},
		columns:[[
					{ field:'projectId',width : '10%',align : "center",title:'项目号' },
					{ field:'projectName',width : '15%',align : "center",title:'项目名称' },
					{ field:'productId',width : '10%',align : "center",title:'产品编号' },
					{ field:'productName',width : '10%',align : "center",title:'产品名称' },
					{ field:'purchasePrice',width : '10%',align : "center",title:'采购价' },
					{ field:'minSellPrice',width : '10%',align : "center",title:'最低售价' },
					{ field:'offerStartTime',width : '10%',align : "center",title:'报价生效时间' },
					{ field:'offerEndTime',width : '10%',align : "center",title:'报价失效时间' },
					{ field:'illustrate',width : '20%',align : "center",title:'说明' }
					
				]]
	});  
	
    $("#viewPriceConfigInfo").show();
	$('#viewPriceConfigInfo').window({
		title:'待审核的定价配置列表',
	});
	$('#viewPriceConfigInfo_priceConfigId').val(priceConfigId);
	$("#handlingSuggestion").textbox('setValue','');
}

//提交审核
function submitExamine(auditResult){
	
	var priceConfigId = $('#viewPriceConfigInfo_priceConfigId').val();
	var handlingSuggestion = $('#handlingSuggestion').val();
	if($.trim(handlingSuggestion)==""){
		$.messager.alert('告警','处理意见不能为空！','warning');
		return false;
	}
	var info = "";
	if(auditResult=='2'){
		info = "审批";
	}else{
		info = "驳回";
	}
	$.messager.confirm('提示',"确定要"+info+"该批数据吗？",function(r){
		if (r){
			$.ajax({
			    dataType: "json",
			    data: {"auditResult":auditResult,"priceConfigId":priceConfigId,"handlingSuggestion":handlingSuggestion},
			    method: "post",
			    url: contextPath + "/examine/submitExamine.do"
			}).done(function(data){
			    if(data.reCode=="100"){
			    	$.messager.alert('提示',data.reStr);
			    	$("#viewPriceConfigInfo").window('close');
			    	searchSubmit();
			    }else{
			    	$.messager.alert('提示',data.reStr,'warning');
			    	$("#viewPriceConfigInfo").window('close');
			    	searchSubmit();
			    }
		    }).fail(function(){
		    	$.messager.alert('提示',"本次操作失败，请重新操作",'error');
				return false;
		    });	    
		    }else{
		    }
	    });
}

//审核历史
function examineHistory(priceConfigId){
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	$('#list_data_viewPriceConfigInfoHis').datagrid({
		width: '100%',  
		height: '90%',  
		nowrap: false,  
		striped: true,
		border: true, 
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的  
		url:contextPath + '/examine/queryAllPriceConfigInfoHis.do', 
		queryParams : {
			priceConfigId : priceConfigId
		},
		columns:[[
					{ field:'rn',width : '10%',align : "center",title:'序号' },
					{ field:'projectId',width : '15%',align : "center",title:'项目号' },
					{ field:'projectName',width : '15%',align : "center",title:'项目名称' },
					{ field:'submitAuditTime',width : '15%',align : "center",title:'提交时间' },
					{ field:'performAuditTime',width : '15%',align : "center",title:'审核时间' },
					{ field:'auditResult',width : '10%',align : "center",title:'审核结果',formatter:function(value,row, index){
						if(value=='2')
							return '审核通过';
						if(value=='3')
							return '驳回'
					}  },
					{ field:'handlingSuggestion',width : '20%',align : "center",title:'处理意见',formatter:function(value, row, index) {   
						var content = '';   
						var abValue = value +'';   
						if(value != undefined&&value != null){      
							if(value.length>=6) {         
								abValue = value.substring(0,6) + "...";         
								content = '<span  title="' + value + '" class="easyui-tooltip">' + abValue + '</span>';      
							}else{         
								content = '<span  title="' + abValue + '" class="easyui-tooltip">' + abValue + '</span>';      
							}   
						}   
						return content;
					}  },
					
				]]
	});  
	
    $("#viewPriceConfigInfoHis").show();
	$('#viewPriceConfigInfoHis').window({
		title:'审核历史列表',
	});
}
//关闭窗口
function closeWindow(win)
{
	$("#" + win+ "").window('close');
}

