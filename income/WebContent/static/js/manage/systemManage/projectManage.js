var editIndex = undefined;
//初始化项目列表页面
$(function(){
	$('#addProjectDiv').hide();
	$('#viewProjectDiv').hide();
	$('#updateProjectDiv').hide();
	$('#viewAssociatedDiv').hide();
	$('#viewAssociatedBudgetDiv').hide();
	$('#priceConfigWin').hide();
//	$('#importPriceConfigDiv').hide();
//	$('#addPriceConfigDiv').hide();
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	$('#list_data').datagrid({
		title:'项目管理列表',  
		width: '100%',  
		height: 'auto',  
		nowrap: false,  
		striped: true,
		border: true, 
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的  
		fit: true,//自动大小  
		url:contextPath + '/project/queryAllProject.do',  
		remoteSort:false,   
		checkOnSelect :true,
		singleSelect:false,// 是否单选
		pagination:true,// 分页控件
		pageSize: 20,//每页显示的记录条数，默认为10
		pageList: [5,10,15,20],//可以设置每页记录条数的列表
		columns:[[
					{ field:'ck',checkbox:true },
					{ field:'projectId',width : '5%',align : "center",title:'项目号' },
					{ field:'projectName',width : '10%',align : "center",title:'项目名称' },
					{ field:'incomeClassName',width : '13%',align : "center",title:'收入大类' },
					{ field:'incomeSectionName',width : '13%',align : "center",title:'收入小类' },
					{ field:'projectDeptName',width : '8%',align : "center",title:'责任部门' },
					{ field:'projectUserName',width : '6%',align : "center",title:'责任人' },
					{ field:'incomeClassId',hidden:'true'},
					{ field:'incomeSectionId',hidden:'true'},
					{ field:'incomeSectionId',hidden:'true'},
					{ field:'projectUserId',hidden:'true'},
					{ field:'projectApplyUserName',hidden:'true'},
					{ field:'createDate',hidden:'true'},
					{ field:'comments',width : '10%',align : "center",
						formatter:function(value, row, index) {   
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
						} 
		           ,title:'说明' },
					{ field:'viewAssociated',width : '6%',align : "center",title:'已关联合同', 
						formatter : function(value, rec, index) {
							var url="";
							if(rec.contract!=null&&rec.contract!="0"){
								url = "<a href='javascript:void(0)'  onclick='viewAssociated("
									+ "\""+rec.projectId+"\","+ "\""+index+"\""
									+ ")' style='text-decoration:none'>查看</a>";
							}
										 
							return url;
						}
					},
					{ field:'viewAssociatedBudget',width : '6%',align : "center",title:'已关联预算', 
						formatter : function(value, rec, index) {
							var url ="";
							if(rec.budget!=null&&rec.budget!="0"){
										 url = "<a href='javascript:void(0)'  onclick='viewAssociatedBudget("
											+ "\""+rec.projectId+"\","+ "\""+index+"\""
											+ ")' style='text-decoration:none'>查看</a>";
									}
							
							return url;
						}
					},
					{ field:'priceConfig',width : '6%',align : "center",title:'定价配置', 
						formatter : function(value, rec, index) {
							var url="";
							if(rec.priceConfig!=null&&rec.priceConfig!="0"){
										 url = "<a href='javascript:void(0)'  onclick='editPriceCfg("
												+ "\""
												+ rec.projectId
												+ "\","
												+ "\""
												+ "\",\"update\""
												+ ")' style='text-decoration:none'>查看</a>";
									}
							return url;   
						}
					},
					{ field:'viewProject',width : '16%',align : "center",title:'项目操作', 
						formatter : function(value, rec, index) {
//							var view = "<a href='javascript:void(0)'  onclick='viewProject("
//								+ "\""+rec.projectId+"\","+ "\""+index+"\""
//								+ ")' style='text-decoration:none'>查看</a>";
//							var update = "<a href='javascript:void(0)'  onclick='viewProject("
//								+ "\""+rec.projectId+"\","+ "\""+index+"\""
//								+ ")' style='text-decoration:none; padding-left: 10px;'>修改</a>";
//							var del = "<a href='javascript:void(0)'  onclick='viewProject("
//								+ "\""+rec.projectId+"\","+ "\""+index+"\""
//								+ ")' style='text-decoration:none; padding-left: 10px;'>删除</a>";
							
							var view = "<a href='javascript:void(0)'  onclick='viewProject("
								+ "\""+rec.projectId+"\","+ "\""+index+"\""
								+ ")' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-search l-btn-icon-left'>查看</span></span></a>";
							if($('#currentUserId').val()==rec.projectUserId){
								var update = "<a href='javascript:void(0)'  onclick='updateProject("
									+ "\""+rec.projectId+"\","+ "\""+index+"\""
									+ ")' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit l-btn-icon-left'>修改</span></span></a>";
							}else{
								var update = "";
							}
							if($('#currentUserId').val()==rec.projectUserId){
								var del = "<a href='javascript:void(0)'  onclick='dellProject("
									+ "\""+rec.projectId+"\","+ "\""+index+"\""
									+ ")' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-remove l-btn-icon-left'>删除</span></span></a>";
							}else{
								var del = "";
							}
							return view+update+del;
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
	
	$('#q_incomeClassId').combobox({
		url:contextPath+"/project/queryIncomeClassId.do",
		valueField:'incomeId',
		textField:'incomeName',
		editable:false,
		onChange: function (id,value) {
			$('#q_incomeSectionId').combobox({
				url:contextPath+"/project/queryIncomeSectionId.do?incomeParentId="+id,
				valueField:'incomeId',
				textField:'incomeName',
				editable:false,
			});
		}
	});
});


//重置项目查询
function reset(){
	$("#q_projectId").textbox('setValue','');
	$("#q_projectName").textbox('setValue','');
	$('#q_incomeClassId').combobox('setValue','');
	$('#q_incomeSectionId').combobox('setValue','');
	$("#q_projectUserName").textbox('setValue','');
	$("#q_projectDeptId").combobox('setValue','');
}

//项目查询
function searchSubmit(){
	var currentUserDeptId = $('#currentUserDeptId').val();
	var currentUserId = $('#currentUserId').val();
	var q_projectId = $('#q_projectId').val();
	var q_projectName = $('#q_projectName').val();
	var q_incomeClassId = $('#q_incomeClassId').combobox('getValue');
	var q_incomeSectionId = $('#q_incomeSectionId').combobox('getValue');
	var q_projectUserName = $('#q_projectUserName').val();
	
	if((currentUserDeptId!=null&&currentUserDeptId=="1")||currentUserId=="0"){
		var q_projectDeptId = $('#q_projectDeptId').combobox('getValue');
		$('#list_data').datagrid('load', {
			q_projectId : q_projectId,
			q_projectName : q_projectName,
			q_incomeClassId : q_incomeClassId,
			q_incomeSectionId : q_incomeSectionId,
			q_projectUserName : q_projectUserName,
			q_projectDeptId : q_projectDeptId
		});
	}else{
		$('#list_data').datagrid('load', {
			q_projectId : q_projectId,
			q_projectName : q_projectName,
			q_incomeClassId : q_incomeClassId,
			q_incomeSectionId : q_incomeSectionId,
			q_projectUserName : q_projectUserName,
		});
	}
}

//关闭窗口
function closeWindow(win)
{
	$("#" + win+ "").window('close');
}

//新增项目
function addProject(){
	var currentUserDeptId = $('#currentUserDeptId').val();
	$('#add_incomeClassId').combobox('setValue','');
	$('#add_incomeSectionId').combobox('setValue','');
	$("#add_projectName").textbox('setValue','');
	
	$('#add_incomeClassId').combobox({
		url:contextPath+"/project/queryIncomeClassId.do",
		valueField:'incomeId',
		textField:'incomeName',
		editable:false,
		onChange: function (id,value) {
			$('#add_incomeSectionId').combobox({
				url:contextPath+"/project/queryIncomeSectionId.do?incomeParentId="+id,
				valueField:'incomeId',
				textField:'incomeName',
				editable:false,
			});
		}
	});
	
	$('#add_projectDeptId').combobox({
	    url:contextPath+"/manage/queryAllDep.do",
	    valueField:'deptCode',
	    textField:'deptName',
	    editable:false,
	    disabled:true
	});
	
	$('#add_projectDeptId').combobox('setValue',currentUserDeptId);
	
	$('#add_requisitionFile').val('');
	$('#add_oaScreenFile').val('');
	$('#add_proFileFile').val('');
	$("#add_comments").textbox('setValue','');
	
	$('#addProjectDiv').show();
	$('#addProjectDiv').window({
		title:'新增项目',
    	width:460,
		height:'80%',
		modal:true,
		minimizable:false,
		collapsible:false
	});
}

//提交项目
function submitProject(){
	var add_projectName = $('#add_projectName').val();
	var add_incomeClassId = $('#add_incomeClassId').combobox('getValue');
	var add_incomeSectionId = $('#add_incomeSectionId').combobox('getValue');
	var add_requisitionFile = $('#add_requisitionFile').filebox('getValue');
	var add_oaScreenFile = $('#add_oaScreenFile').filebox('getValue');
	
	console.log(add_requisitionFile);
	console.log(add_oaScreenFile);
	
	if($.trim(add_projectName)==""){
		$.messager.alert('告警','项目名称不能为空！','warning');
		return false;
	}
	if($.trim(add_incomeClassId)==""){
		$.messager.alert('告警','收入大类不能为空！','warning');
		return false;
	}
	if($.trim(add_incomeSectionId)==""){
		$.messager.alert('告警','收入小类不能为空！','warning');
		return false;
	}
	if($.trim(add_requisitionFile)==""){
		$.messager.alert('告警','请购单或呈批件不能为空！','warning');
		return false;
	}
	if($.trim(add_oaScreenFile)==""){
		$.messager.alert('告警','OA审批截图不能为空！','warning');
		return false;
	}
	if($.trim(add_oaScreenFile)==""){
		$.messager.alert('告警','OA审批截图不能为空！','warning');
		return false;
	}
	
	var formData = new FormData($( "#addProjectForm" )[0]);
	
	$.ajax({
	    data: formData,
	    method: "post",
	    async: false,  
        cache: false,  
        contentType: false,  
        processData: false,
	    url: contextPath+"/project/submitProject.do"
	}).done(function(data){
	    if(data.reCode=="100"){
	    	$.messager.alert('提示','操作成功！'); 
	    	$("#addProjectDiv").window('close');
	    	$("#list_data").datagrid('clearSelections').datagrid('clearChecked');
	    	$('#list_data').datagrid('reload',{});
	    }else if(data.reCode=="9"){
	    	$.messager.alert('提示',data.reStr); 
	    	$("#addProjectDiv").window('close');
	    	$("#list_data").datagrid('clearSelections').datagrid('clearChecked');
	    	$('#list_data').datagrid('reload',{});
	    }else{
	    	$.messager.alert('提示',data.reStr,'warning');
	    	$("#addProjectDiv").window('close');
	    	$('#list_data').datagrid('reload',{});
	    }
    }).fail(function(){
    	$.messager.alert('提示',"本次操作失败，请重新操作",'error');
    	$("#addProjectDiv").window('close');
		return false;
    });
}


//查看项目
function viewProject(projectId,index){
	$("#view_projectId").textbox('setValue','');
	$("#view_projectName").textbox('setValue','');
	$("#view_incomeClassName").textbox('setValue','');
	$("#view_incomeSectionName").textbox('setValue','');
	$("#view_projectDeptName").textbox('setValue','');
	$("#view_projectUserName").textbox('setValue','');
	$("#view_comments").textbox('setValue','');
	$("#view_projectApplyUserName").textbox('setValue','');
	$("#view_updateDate").textbox('setValue','');
	$("#view_requisitionFile").html('');
	$("#view_oaScreenFile").html('');
	$("#view_proFileFile").html('');
	
	var row = $('#list_data').datagrid('getData').rows[index];
	$("#view_projectId").textbox('setValue',row.projectId);
	$("#view_projectName").textbox('setValue',row.projectName);
	$("#view_incomeClassName").textbox('setValue',row.incomeClassName);
	$("#view_incomeSectionName").textbox('setValue',row.incomeSectionName);
	$("#view_projectDeptName").textbox('setValue',row.projectDeptName);
	$("#view_projectUserName").textbox('setValue',row.projectUserName);
	$("#view_comments").textbox('setValue',row.comments);
	$("#view_projectApplyUserName").textbox('setValue',row.projectApplyUserName);
	$("#view_updateDate").textbox('setValue',row.createDate);
	$("#view_requisitionFile").html(row.requisitionFilename);
	$("#view_oaScreenFile").html(row.oaScreenFilename);
	$("#view_proFileFile").html(row.proFileFilename);
	
	
	$('#viewProjectDiv').show();
	$('#viewProjectDiv').window({
		title:'查看项目详情',
    	width:460,
    	height:'95%',
		modal:true,
		minimizable:false,
		collapsible:false
	});
}

///修改项目
function updateProject(projectId,index){
	$("#update_projectId2").textbox('setValue','');
	$("#update_projectName").textbox('setValue','');
	$("#update_incomeClassName").textbox('setValue','');
	$("#update_incomeSectionName").textbox('setValue','');
	$("#update_projectDeptName").textbox('setValue','');
	$("#update_projectUserId").textbox('setValue','');
	$("#update_comments").textbox('setValue','');
	$("#update_projectApplyUserName").textbox('setValue','');
	$("#update_updateDate").textbox('setValue','');
	$('#update_requisitionFile').filebox('setValue','');
	$('#update_oaScreenFile').filebox('setValue','');
	$('#update_proFileFile').filebox('setValue','');
	
	$('#update_incomeClassId').combobox({
		url:contextPath+"/project/queryIncomeClassId.do",
		valueField:'incomeId',
		textField:'incomeName',
		editable:false,
		onChange: function (id,value) {
			$('#update_incomeSectionId').combobox({
				url:contextPath+"/project/queryIncomeSectionId.do?incomeParentId="+id,
				valueField:'incomeId',
				textField:'incomeName',
				editable:false,
			});
		}
	});
	
	var row = $('#list_data').datagrid('getData').rows[index];
	var projectDeptId = row.projectDeptId;
	$('#update_projectUserId').combobox({
		url:contextPath+"/manage/queryDepPerson.do?projectDeptId="+projectDeptId,
		valueField:'userId',
		textField:'userName',
		editable:false
	});
	$("#update_projectId").val(row.projectId);
	$("#update_projectId2").textbox('setValue',row.projectId);
	$("#update_projectName").textbox('setValue',row.projectName);
	$('#update_incomeClassId').combobox('setValue',row.incomeClassId);
	$('#update_incomeSectionId').combobox('setValue',row.incomeSectionId);
	$('#update_projectUserId').combobox('setValue',row.projectUserId);
	$("#update_projectDeptName").textbox('setValue',row.projectDeptName);
	$("#update_comments").textbox('setValue',row.comments);
	$("#update_projectApplyUserName").textbox('setValue',row.projectApplyUserName);
	$("#update_updateDate").textbox('setValue',row.createDate);
	$("#update_requisitionFile2").html(row.requisitionFilename);
	$("#update_oaScreenFile2").html(row.oaScreenFilename);
	$("#update_proFileFile2").html(row.proFileFilename);
	
	$('#updateProjectDiv').show();
	$('#updateProjectDiv').window({
		title:'修改项目详情',
    	width:700,
    	height:'95%',
		modal:true,
		minimizable:false,
		collapsible:false
	});
}

//修改项目
function doUpdateProject(){
	
	var update_projectId = $('#update_projectId').val();
	var update_projectName = $('#update_projectName').val();
	
	if($.trim(update_projectName)==""){
		$.messager.alert('告警','项目名称不能为空！','warning');
		return false;
	}
	
	var formData = new FormData($( "#updateProjectForm" )[0]);
	$.ajax({
	    data: formData,
	    method: "post",
	    async: false,  
        cache: false,  
        contentType: false,  
        processData: false,
	    url: contextPath+"/project/doUpdateProject.do"
	}).done(function(data){
	    if(data.reCode=="100"){
	    	$.messager.alert('提示','操作成功！'); 
	    	$("#updateProjectDiv").window('close');
	    	$("#list_data").datagrid('clearSelections').datagrid('clearChecked');
	    	$('#list_data').datagrid('reload',{});
	    }else if(data.reCode=="9"){
	    	$.messager.alert('提示',data.reStr); 
	    	$("#updateProjectDiv").window('close');
	    	$("#list_data").datagrid('clearSelections').datagrid('clearChecked');
	    	$('#list_data').datagrid('reload',{});
	    }else{
	    	$.messager.alert('提示',data.reStr,'warning');
	    	$("#updateProjectDiv").window('close');
	    	$('#list_data').datagrid('reload',{});
	    }
    }).fail(function(){
    	$.messager.alert('提示',"本次操作失败，请重新操作",'error');
    	$("#updateProjectDiv").window('close');
		return false;
    });
}

//删除项目
function dellProject(projectId,index)
{
	var row = $('#list_data').datagrid('getData').rows[index];
	var projectName = row.projectName;
	$.messager.confirm('提示',"确认删除该项目吗("+projectName+")?",function(r){
	    if (r)
	    {
			$.ajax({
			    dataType: "json",
			    data: {"projectId":projectId,"projectName":projectName},
			    method: "post",
			    url: contextPath+"/project/dellProject.do"
			}).done(function(data){
			    if(data.reCode=="100"){
			    	$.messager.alert('提示','操作成功！');
			    	$("#list_data").datagrid('clearSelections').datagrid('clearChecked');
			    	$('#list_data').datagrid('reload',{});
			    }else{
			    	$.messager.alert('提示',data.reStr,'warning');
			    	$('#list_data').datagrid('reload',{});
			    }
		    }).fail(function(){
		    	$.messager.alert('提示',"本次操作失败，请重新操作",'error');
				return false;
		    });
	    }
    });
}

//下载文件
function downloadFile(fileType){
	
	var filename = $('#'+fileType).html();
	
	var projectId;
	
	if(fileType.indexOf("view")>-1){
		projectId = $('#view_projectId').val();
	}else{
		projectId = $('#update_projectId').val();
	}
	
	
	$("#downloadForm").form("submit", {
        url : contextPath+"/project/downLoad.do",
        onSubmit : function(param) {
        	param.filename = filename,
        	param.projectId = projectId;
            return this;
        },
        success : function(result) {
        	if (null != result && "" != result)
        	{
        		$.messager.alert('提示',result,'warning');
        	}
        }
    });
}

function downloadPriceConfigFile(){

	$("#priceConfigdownloadForm").form("submit", {
        url : contextPath+"/priceConfig/downLoad.do",
        onSubmit : function(param) {
            return this;
        },
        success : function(result) {
        	if (null != result && "" != result)
        	{
        		$.messager.alert('提示',result,'warning');
        	}
        }
    });
}



//关联合同
function associatedContract() { 
	var selRow = $("#list_data").datagrid("getChecked");
	
	if (selRow.length != 1) {
		$.messager.alert('告警','请选择一行数据!','warning');
		return false;
	}
	parent.openTab(selRow[0].projectId,'关联合同_项目号', '/contract/contractManage.do');
}  


//关联预算
function associatedEstimate() { 
	var selRow = $("#list_data").datagrid("getChecked");
	
	if (selRow.length != 1) {
		$.messager.alert('告警','请选择一行数据!','warning');
		return false;
	}
	parent.openTab(selRow[0].projectId,'关联预算_项目号', '/estimate/estimateManager.do');
}  



//查看关联
function viewAssociated(projectId){
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	$('#list_data_viewAssociated').datagrid({
		width: '95%',  
		height: 'auto',  
		nowrap: false,  
		striped: true,
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的  
		fit: true,//自动大小  
		url:contextPath + '/contract/queryAllAssociatedContract.do', 
		queryParams: {
			projectId: projectId
		},
		remoteSort:false,   
		checkOnSelect :false,
		singleSelect:false,//是否单选  
		pagination:true,//分页控件  
		pageSize: 20,//每页显示的记录条数，默认为10
		pageList: [5,10,15,20],//可以设置每页记录条数的列表
		columns:[[
					{ field:'ck',checkbox:true },
					{ field:'conId',hidden:'true'},
					{ field:'projectId',hidden:'true'},
					{ field:'conNumber',width : '8%',align : "center",title:'合同流水号' },
					{ field:'conNo',width : '8%',align : "center",title:'合同编号' },
					{ field:'conCapitalNatureName',width : '8%',align : "center",title:'合同类型' },
					{ field:'conUndertakeDeptname',width : '8%',align : "center",title:'责任部门' },
					{ field:'conProjectContrctor',width : '8%',align : "center",title:'责任人' },
					{ field:'conStartTime',width : '8%',align : "center",title:'合同开始时间' },
					{ field:'conEndTime',width : '8%',align : "center",title:'合同结束时间' },
					{ field:'conTypePayrec',width : '8%',align : "center",title:'合同支付类型' },
					{ field:'conVendorId',width : '8%',align : "center",title:'供应商ID' },
					{ field:'conVendorName',width : '8%',align : "center",title:'供应商名称' }
					
				]],
		toolbar: '#tb_viewAssociated'
	});  
	
	//设置分页控件  
	var p = $('#list_data_viewAssociated').datagrid('getPager');
	$(p).pagination({  
		beforePageText: '第',//页数文本框前显示的汉字  
		afterPageText: '页    共 {pages} 页',  
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
		onBeforeRefresh:function(){ 
			$(this).pagination('loading'); 
			$(this).pagination('loaded'); 
		}
	});
    $("#viewAssociatedDiv").show();
	$('#viewAssociatedDiv').window({
		title:'已关联合同列表',
		modal:true,
		minimizable:false,
		collapsible:false
	});
	var viewAssociated_projectId = $('#viewAssociated_projectId').val(projectId);
}


//已关联合同查询
function search_viewAssociated(){
	var viewAssociated_projectId = $('#viewAssociated_projectId').val();
	var q_conNumber = $('#q_conNumber').val();
	var q_conNo = $('#q_conNo').val();
	
	$('#list_data_viewAssociated').datagrid('load', {
		projectId : viewAssociated_projectId,
		q_conNumber : q_conNumber,
		q_conNo : q_conNo
	});
}

//重置已关联合同查询
function reset_viewAssociated(){
	$("#q_conNumber").textbox('setValue','');
	$("#q_conNo").textbox('setValue','');
}

//取消已关联合同
function cancelAssociatedContract(){
	var viewAssociated_projectId = $('#viewAssociated_projectId').val();
	var selRow = $("#list_data_viewAssociated").datagrid("getChecked");
	if (selRow.length == 0) {
		$.messager.alert('告警','请至少选择一行数据!','warning');
		return false;
	}
	var contractName="";
	var conIds = [];
	for (var i = 0; i < selRow.length; i++){
		var conId = selRow[i].conId;		
		conIds.push(conId);
		
		if(i==selRow.length-1){
			contractName = contractName+selRow[i].conNo;
		}else{
			contractName = contractName+selRow[i].conNo+"、";
		}
	}
	
	$.messager.confirm('提示',"确定要与  "+contractName+"  解除合同绑定吗？",function(r){
		if (r){
			$.ajax({
			    dataType: "json",
			    data: {"conIds":conIds.toString(),"projectId":viewAssociated_projectId},
			    method: "post",
			    url: contextPath + "/contract/cancelAssociatedContract.do"
			}).done(function(data){
			    if(data.reCode=="100"){
			    	$.messager.alert('提示',data.reStr);
			    	$("#list_data_viewAssociated").datagrid('clearSelections').datagrid('clearChecked');
			    	search_viewAssociated();
			    }else{
			    	$.messager.alert('提示',data.reStr,'warning');
			    	$('#list_data_viewAssociated').datagrid('reload',{});
			    }
		    }).fail(function(){
		    	$.messager.alert('提示',"本次操作失败，请重新操作",'error');
				return false;
		    });	    
		    }else{
		    	$("#list_data_viewAssociated").datagrid("clearSelections");
		    }
	    });
}


//查看关联预算
function viewAssociatedBudget(projectId){
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	$('#list_data_viewAssociatedBudget').datagrid({
		width: '90%',  
		height: 'auto',  
		nowrap: false,  
		striped: true,
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的  
		fit: true,//自动大小  
		url:contextPath + '/estimate/viewAssociation.do', 
		queryParams: {
			projectId: projectId
		},
		remoteSort:false,   
		checkOnSelect :false,
		singleSelect:false,//是否单选  
		pagination:true,//分页控件  
		pageSize: 20,//每页显示的记录条数，默认为10
		pageList: [5,10,15,20],//可以设置每页记录条数的列表
		columns:[[
					{ field:'ck',checkbox:true },
					{ field:'budgetYear',width : '8%',align : "center",title:'预算年度' },
					{ field:'budgetDeptDraw',width : '8%',align : "center",title:'预算编制部门' },
					{ field:'budgetDeptUse',width : '8%',align : "center",title:'预算使用部门' },
					{ field:'budgetProjectNumber',width : '10%',align : "center",title:'预算项目号' },
					{ field:'budgetProjectName',width : '30%',align : "center",title:'预算项目名称' },
					{ field:'budgetSubjectCode',width : '8%',align : "center",title:'预算科目编码' },
					{ field:'budgetAvailableAmount',width : '10%',align : "center",title:'预算可用金额' },
					{ field:'budgetTotalAmount',width : '8%',align : "center",title:'预算总金额' },
					{ field:'budgetResultId',hidden:'true'},
					{ field:'projectId',hidden:'true'}
				]],
		toolbar: '#tb_viewAssociatedBudget'
	});  
	
	//设置分页控件  
	var p = $('#list_data_viewAssociatedBudget').datagrid('getPager');
	$(p).pagination({  
		beforePageText: '第',//页数文本框前显示的汉字  
		afterPageText: '页    共 {pages} 页',  
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
		onBeforeRefresh:function(){ 
			$(this).pagination('loading'); 
			$(this).pagination('loaded'); 
		}
	});
    $("#viewAssociatedBudgetDiv").show();
	$('#viewAssociatedBudgetDiv').window({
		title:'已关联预算列表',
		modal:true,
		minimizable:false,
		collapsible:false
	});
	var viewAssociated_projectId = $('#viewAssociatedBudget_projectId').val(projectId);
}

//查询关联预算
function search_viewAssociatedBudget(){
	var viewAssociated_projectId = $('#viewAssociatedBudget_projectId').val();
	var budgetProjectNumber = $('#budgetProjectNumber').val();
	var budgetProjectName = $('#budgetProjectName').val();
	
	$('#list_data_viewAssociatedBudget').datagrid('load', {
		projectId : viewAssociated_projectId,
		budgetProjectNumber : budgetProjectNumber,
		budgetProjectName : budgetProjectName
	});
}
//重置已关联合同查询
function reset_viewAssociatedBudget(){
	$("#budgetProjectNumber").textbox('setValue','');
	$("#budgetProjectName").textbox('setValue','');
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
//取消预算绑定
function cancelAssociatedBudget(){
	var viewAssociated_projectId = $('#viewAssociatedBudget_projectId').val();
	var selRow = $("#list_data_viewAssociatedBudget").datagrid("getChecked");
	if (selRow.length == 0) {
		$.messager.alert('告警','请至少选择一行数据!','warning');
		return false;
	}
	var budgetName="";
	var budgets =new Array();
	for (var i = 0; i < selRow.length; i++){
		var budget = new Budget(
				selRow[i].budgetYear,
				selRow[i].budgetDeptUse,
				selRow[i].budgetDeptDraw,
				selRow[i].budgetProjectNumber,
				selRow[i].budgetProjectName,
				selRow[i].budgetSubjectCode,
				selRow[i].budgetAvailableAmount,
				selRow[i].budgetTotalAmount,
				selRow[i].budgetResultId,
				viewAssociated_projectId
			); 
		budgets.push(budget);
		
		if(i==selRow.length-1){
			budgetName = budgetName+selRow[i].budgetProjectNumber;
		}else{
			budgetName = budgetName+selRow[i].budgetProjectNumber+"、";
		}
	}
	
	$.messager.confirm('提示',"确定要与  "+budgetName+"  解除预算关联吗？",function(r){
		if (r){
			$.ajax({
			    dataType: "json",
			    data: JSON.stringify(budgets),
			    method: "post",
			    contentType:"application/json",  
			    url: contextPath + "/estimate/cancelAssociation.do"
			}).done(function(data){
			    if(data.status=="1"){
			    	$.messager.alert('提示',data.msg.substring(0,data.msg.lastIndexOf(","))+"已从已关联预算列表中删除！");
			    	$("#list_data_viewAssociatedBudget").datagrid('clearSelections').datagrid('clearChecked');
			    	search_viewAssociatedBudget();
			    }else{
			    	$.messager.alert('提示',data.msg,'warning');
			    	$('#list_data_viewAssociatedBudget').datagrid('reload',{});
			    }
		    }).fail(function(){
		    	$.messager.alert('提示',"本次操作失败，请重新操作",'error');
				return false;
		    });	    
		    }else{
		    	$("#list_data_viewAssociatedBudget").datagrid("clearSelections");
		    }
	    });
}






//定价配置
//function priceConfig() { 
//	var selRow = $("#list_data").datagrid("getChecked");
//	
//	if (selRow.length != 1) {
//		$.messager.alert('告警','请选择一行数据!','warning');
//		return false;
//	}
//	//parent.openTab(selRow[0].projectId,'定价配置_项目编码', '/priceConfig/priceConfigManage.do');
//}  

//查看定价配置
//function viewPriceConfig(projectId) {
//	
//	$.messager.defaults = { ok: "确定", cancel: "取消" };
//	$('#data_list').datagrid({
//		width: '100%',  
//		height: 'auto',  
//		nowrap: false,  
//		striped: true,
//		loadMsg: '正在加载',
//		collapsible:false,//是否可折叠的  
//		fit: true,//自动大小  
//		url:contextPath + '/priceConfig/selectMiguPriceConfigByProject.do', 
//		queryParams: {
//			projectId: projectId
//		},
//		remoteSort:false,   
//		checkOnSelect :false,
//		singleSelect:false,//是否单选  
//		pagination:true,//分页控件  
//		pageSize: 20,//每页显示的记录条数，默认为10
//		pageList: [5,10,15,20],//可以设置每页记录条数的列表
//		columns:[[
//					{ field:'rn',width : '10%',align : "center",title:'序号' },
//					{ field:'projectId',hidden:true},
//					{ field:'productId',width : '10%',align : "center",title:'产品编号' },
//					{ field:'productName',width : '10%',align : "center",title:'产品名称' },
//					{ field:'purchasePrice',width : '10%',align : "center",title:'采购价' },
//					{ field:'minSellPrice',width : '10%',align : "center",title:'最低售价' },
//					{ field:'offerStartTime',width : '10%',align : "center",title:'报账生效月份' },
//					{ field:'offerEndTime',width : '10%',align : "center",title:'报账失效月份' },
//					{ field:'illustrate',width : '15%',align : "center",title:'说明' },
//					{ field:'illustrate',width : '10%',align : "center",title:'操作' }
//				]],
//		toolbar: '#tb_priceConfigWin'
//	});  
//	
//	//设置分页控件  
//	var p = $('#data_list').datagrid('getPager');
//	$(p).pagination({  
//		beforePageText: '第',//页数文本框前显示的汉字  
//		afterPageText: '页    共 {pages} 页',  
//		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
//		onBeforeRefresh:function(){ 
//			$(this).pagination('loading'); 
//			$(this).pagination('loaded'); 
//		}
//	});
//    $("#priceConfigWin").show();
//	$('#priceConfigWin').window({
//		title:'定价配置列表',
//		modal:true,
//		minimizable:false,
//		collapsible:false
//	});
//	
//	var priceConfigWin_projectId = $('#priceConfigWin_projectId').val(projectId);
//}



