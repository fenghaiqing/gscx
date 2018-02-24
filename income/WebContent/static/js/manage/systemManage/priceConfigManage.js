
var editIndex = undefined;
// 初始化定价配置列表页面
$(function() {

	$('#importPriceConfigDiv').hide();
	$('#addPriceConfigDiv').hide();
	$.messager.defaults = {
		ok : "确定",
		cancel : "取消"
	};

});


// 关闭窗口
function closeWindow(win) {
	$("#" + win + "").window('close');
}

// 下载模板文件
/*function downloadFile() {
	$("#downloadForm").form("submit", {
		url : contextPath + "/priceConfig/downLoad.do",
		onSubmit : function(param) {
			return this;
		},
		success : function(result) {
			if (null != result && "" != result) {
				$.messager.alert('提示', result, 'warning');
			}
		}
	});
}*/

// 导入定价信息配置界面
function importPriceConfig() {
	$("#all").prop("checked",false);
	$("#plus").prop("checked",false);
	var selRow = $("#list_data").datagrid("getChecked");
	if (selRow.length != 1) {
		$.messager.alert('告警','请选择一行数据!','warning');
		return false;
	}
	var projectId=selRow[0].projectId
	/* $('#priceCfgAuditUser').combobox('setValue', ''); */
	$('#illustrate').val('');
	$('#exportExcel').filebox('setValue','');
	$('#priceConfig_projectid').val(projectId);
	$("#importPriceConfigDiv").show();
	$('#importPriceConfigDiv').window({
		title : '导入定价配置',
		width : '45%',
		height : '50%',
		modal : true,
		minimizable : false,
		collapsible : false
	});
	
}

// JS校验form表单信息
function checkData() {
	
	var fileDir = $("#exportExcel").filebox('getValue');
	
	console.log(fileDir);
	
	
	var suffix = fileDir.substr(fileDir.lastIndexOf("."));
	if ("" == fileDir) {
		$.messager.alert('告警', '请选择需要导入的Excel文件！', 'warning');
		return false;
	}
	if (".xls" != suffix && ".xlsx" != suffix) {
		$.messager.alert('告警', '请选择Excel格式的文件导入！', 'warning');
		return false;
	}
	
	return true;
}

// 确认导入
function importExcl() {
	if (checkData()) {
		/* if(!$("#all").prop("checked")&&!$("#plus").prop("checked")){
			 $.messager.alert('提示',"请选择导入方式!");
			 return false;
		 }*/
		var formData = new FormData($("#exportPricCfg")[0]);
		/* if($("#all").prop("checked")){
			 formData.append("importType","all");
		 }else{
			 formData.append("importType","plus");
		 }*/
		$.ajax({
			url : contextPath + '/priceConfig/priceCfgImport.do',
			type : "post",
			data : formData,
			processData : false,
			contentType : false,
			success : function(data, status) {
				if (data.status == 1) {
					$.messager.alert('提示', data.msg);
					$("#exportExcel").val("");
					searchSubmit();
					$("#importPriceConfigDiv").window('close');
					
				} else {
					$.messager.alert('告警', data.msg, 'warning');
				}
			},
			error : function() {
				function errorMsg() {
					$.messager.alert('告警', '导入excel出错！', 'warning');
				}
			}
		});
	}
}

// 新增
function addPriceConfig() {
	var selRow = $("#list_data").datagrid("getChecked");
	if (selRow.length != 1) {
		$.messager.alert('告警','请选择一行数据!','warning');
		return false;
	}
	var projectId=selRow[0].projectId
	
	$('#addPriceConfigTable').datagrid({
		loadMsg : '正在加载',
		url : contextPath + '/priceConfig/queryAllPriceConfigInfo.do',
		queryParams : {
			type : 'add'
		},
		columns : [ [
		{
			field : 'productId',
			width : '8%',
			align : 'center',
			title : '产品编号',
			editor : 'textbox'
		}, {
			field : 'productName',
			width : '10%',
			align : 'center',
			title : '产品名称',
			editor : 'textbox'
		}, 
		 {
			field : 'vendorId',
			width : '8%',
			align : 'center',
			title : '厂商编码',
			editor : 'textbox'
		}, 
		 {
			field : 'vendorName',
			width : '10%',
			align : 'center',
			title : '厂商名称',
			editor : 'textbox'
		}, {
			field : 'purchasePrice',
			width : '8%',
			align : 'center',
			title : '采购价',
			editor : {
				type : 'numberbox',
				options : {
					precision : 2
				}
			}
		}, {
			field : 'minSellPrice',
			width : '8%',
			align : 'center',
			title : '最低售价',
			editor : {
				type : 'numberbox',
				options : {
					precision : 2
				}
			}
		}, {
			field : 'offerStartTime',
			width : '14%',
			align : 'center',
			title : '报帐生效月份',
			editor :'textbox',
		}, {
			field : 'offerEndTime',
			width : '14%',
			align : 'center',
			title : '报帐失效月份',
			editor :'textbox',
		}, {
			field : 'illustrate',
			width : '16%',
			align : 'center',
			title : '说明',
			editor : 'textbox'
		}, {
			field : 'priceConfigInfoId',
			hidden : 'true'
		},
		 {
			field : 'password',
			width : '5%',
			align : "center",
			title : '操作',
			formatter:function(value,row,index){
				return "<a href='javascript:void(0)' onclick='removeit(this)' style='text-decoration:none'>删除</a>";
			}
		}
		] ],
		toolbar : '#tb_addPriceConfig'
	});
	$("#opration").val("add");
// $("#priceConfigId").val(projectId);
	$("#projectId").val(projectId);
	// 加载下拉框 弹出窗口
	baseinfo('新增定价配置');
	
	$("#tb_btn_toolbar").show();
	$("#tb_btn_view_toolbar").hide();
}

// 修改
function editPriceCfg(pcId) {
	$('#addPriceConfigTable').datagrid({
		loadMsg : '正在加载',
		url:contextPath + '/priceConfig/selectMiguPriceConfigByProject.do',
		queryParams : {
			projectId : pcId
		},
		width: '100%',  
		height: 'auto',  
		nowrap: false,  
		striped: true,
		collapsible:false,// 是否可折叠的
		fit: true,// 自动大小
		singleSelect:false,// 是否单选
		pagination:true,// 分页控件
		pageSize: 20,// 每页显示的记录条数，默认为10
		pageList: [5,10,15,20],// 可以设置每页记录条数的列表
		columns : [ [
			
		{
			field : 'productId',
			width : '8%',
			align : 'center',
			title : '产品编号',
			editor : 'textbox',
			formatter: function (value, data, index) {
				if (!isNull(value)&& value.length>6) {
				return '<span title="'+value+'"  class="easyui-tooltip">'+value+'</span>'
				}else{
					return value;
				}
			}
		}, {
			field : 'productName',
			width : '10%',
			align : 'center',
			title : '产品名称',
			editor : 'textbox',
			formatter: function (value, data, index) {
				if (  !isNull(value)&& value.length>6) {
					
					return '<span title="'+value+'"  class="easyui-tooltip">'+value+'</span>'
				
					}else{
						return value;
					}
				}
		},
		 {
			field : 'vendorId',
			width : '8%',
			align : 'center',
			title : '厂商编码',
			editor : 'textbox'
		}, 
		 {
			field : 'vendorName',
			width : '10%',
			align : 'center',
			title : '厂商名称',
			editor : 'textbox'
		}, {
			field : 'purchasePrice',
			width : '8%',
			align : 'center',
			title : '采购价',
			editor : {
				type : 'numberbox',
				options : {
					precision : 2
				}
			}
		}, {
			field : 'minSellPrice',
			width : '8%',
			align : 'center',
			title : '最低售价',
			editor : {
				type : 'numberbox',
				options : {
					precision : 2
				}
			}
		},{
			field : 'offerStartTime',
			width : '14%',
			align : 'center',
			title : '报帐生效月份',
			editor : 'textbox',
		}, {
			field : 'offerEndTime',
			width : '14%',
			align : 'center',
			title : '报帐失效月份',
			editor :'textbox'
		}, {
			field : 'illustrate',
			width : '17%',
			align : 'center',
			title : '说明',
			editor : 'textbox',
			formatter: function (value, data, index) {
				if ( !isNull(value)&& value.length>14) {
					return '<span title="'+value+'"  class="easyui-tooltip">'+value+'</span>'
				}else{
					return value;
				}
			}
		},
		{
			field : 'projectId',
			hidden : 'true'
		}, {
			field : 'priceConfigInfoId',
			hidden : 'true'
		} ,{
			field : 'password',
			width : '4%',
			align : "center",
			title : '操作',
			formatter:function(value,row,index){
				return "<a href='javascript:void(0)'onclick='removeit(this)' style='text-decoration:none'>删除</a>";
			}
		}
		 
		] ],
		onLoadSuccess: function(){
	            $(".easyui-tooltip").tooltip({
	                onShow: function () {
	                    $(this).tooltip('tip').css({
	                        borderColor: '#000'
	                    });
	                }
	            });
		},
		singleSelect : false,
		selectOnCheck : true,
		checkOnSelect : true,
		toolbar : '#tb_addPriceConfig'
	});
	
	$("#opration").val("update");
	$("#projectId").val(pcId);
	$('#priceConfigWin_projectId').val(pcId);
		baseinfo('查看定价配置');
	
		$("#tb_btn_toolbar").hide();
		$("#tb_btn_view_toolbar").show();

}

// 定价配置查询
function search_priceConfigWin(){
	var priceConfigWin_projectId = $('#priceConfigWin_projectId').val();
	var q_productId = $('#q_productId').textbox('getValue');
	var q_productName = $('#q_productName').textbox('getValue');
	$('#addPriceConfigTable').datagrid('load', {
		projectId : priceConfigWin_projectId,
		q_productId : q_productId,
		q_productName : q_productName
	});
}

// 重置定价配置查询
function reset_priceConfigWin(){
	$("#q_priceConfigNumber").textbox('setValue','');
	$("#q_productId").textbox('setValue','');
	$('#q_productName').textbox('setValue','');
}



// 加载基础信息
function baseinfo(title) {

	$("#addPriceConfigDiv").show();
	$('#addPriceConfigDiv').window({
		title : title,
		width : '90%',
		height : '80%',
		modal : true,
		closable:true,
		maximizable:true,
		minimizable:true,
		minimizable : false,
		collapsible : false,
		onBeforeClose : function() {
			editIndex = undefined;
			
			searchSubmit();
		}
	});
}

function endEditing() {
	if (editIndex == undefined) {
		return true
	}
	if ($('#addPriceConfigTable').datagrid('validateRow', editIndex)) {
		var ed = $('#addPriceConfigTable').datagrid('getEditor', {
			index : editIndex,
			field : 'userId'
		});

		$('#addPriceConfigTable').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function onClickRow(index) {
	var option = $("#opration").val();
	
	// 查看状态 不启用编辑
	if ('view' == option) {
		return;
	}
	$('#addPriceConfigTable').datagrid('beginEdit', index);
	
	setPlaceholder('addPriceConfigTable','offerStartTime',index);
	setPlaceholder('addPriceConfigTable','offerEndTime',index);
	$('#addPriceConfigTable').datagrid('clearSelections');
	if (editIndex != index) {
		if (endEditing()) {
			$('#addPriceConfigTable').datagrid('selectRow', index).datagrid(
					'beginEdit', index);
			editIndex = index;
		} else {

			$('#addPriceConfigTable').datagrid('selectRow', editIndex);
		}
	}
}

function append() {
	if (endEditing()) {
		$('#addPriceConfigTable').datagrid('appendRow', {
			state : 'P'
		});
		
		editIndex = $('#addPriceConfigTable').datagrid('getRows').length - 1;
		$('#addPriceConfigTable').datagrid('selectRow', editIndex).datagrid(
				'beginEdit', editIndex);
		setPlaceholder('addPriceConfigTable','offerStartTime',editIndex);
		setPlaceholder('addPriceConfigTable','offerEndTime',editIndex);
		$('#addPriceConfigTable').datagrid('clearSelections');
	}
	
}

function removeit(target) {
	var rowIndex = getRowIndex(target);
	$('#addPriceConfigTable').datagrid('endEdit', rowIndex);
	var opration = $("#opration").val();
		$.messager.confirm('提示', "确定要删除这条数据？", function(r) {
			if(r){
				if ("add" !== opration) {
					 	var data = $('#addPriceConfigTable').datagrid('getData').rows[rowIndex]
						delSelect(data);
						$('#addPriceConfigTable').datagrid('deleteRow', rowIndex);
				} else {
						$('#addPriceConfigTable').datagrid('deleteRow', rowIndex);
				}
				editIndex = undefined;
			}else{
				$('#addPriceConfigTable').datagrid('beginEdit', rowIndex)
			}
		});

}

function getChanges(type) {
	$('#addPriceConfigTable').datagrid('endEdit', editIndex);
	// var rows = $('#addPriceConfigTable').datagrid('getChanges');
	 var rows = $('#addPriceConfigTable').datagrid('getRows');
	var opration = $("#opration").val();
	var priceConfigInfos = new Array();
	var projectId =$("#projectId").val();
	if (rows.length > 0) {

		for (var i = 0; i < rows.length; i++) {
			if (isNull(rows[i].productId)) {
				$.messager.alert("提示", "产品编号不能为空！",
						"warning")
				return;
			}
			if (isNull(rows[i].productName)) {
				$.messager.alert("提示", "产品名称不能为空！",
						"warning")
				return;
			}
		

			if (isNull(rows[i].minSellPrice)) {
				$.messager.alert("提示", "最低售价不能为空！",
						"warning")
				return;
			}
			if (isNull(rows[i].offerStartTime)) {
				$.messager.alert("提示", "报价周期开始时间不能为空！",
						"warning")
				return;
			}else{
				if(rows[i].offerStartTime.length==6){
					var reg=/^[0-9]*$/
					if(!reg.test(rows[i].offerStartTime)){
						$.messager.alert("提示", "请输入正确格式的日期(yyyyMM)例：201706！",
						"warning");
						return 
					}
				}else{
					$.messager.alert("提示", "请输入正确格式的日期(yyyyMM)例：201706！",
					"warning");
					return ;
				}
			}
			if (isNull(rows[i].offerEndTime)) {
				$.messager.alert("提示", "报价周期结束时间不能为空！",
						"warning")
				return;
			}else{
				if(rows[i].offerEndTime.length==6){
					var reg=/^[0-9]*$/
					if(!reg.test(rows[i].offerEndTime)){
						$.messager.alert("提示", "请输入正确格式的日期(yyyyMM)例：201706！",
						"warning");
						return 
					}
				}else{
					$.messager.alert("提示", "请输入正确格式的日期(yyyyMM)例：201706！",
					"warning");
					return ;
				}
			}
			var priceConfigInfo = new PriceConfigInfo(
					rows[i].projectId,
					rows[i].productId,
					rows[i].productName,
					rows[i].purchasePrice,
					rows[i].minSellPrice,
					rows[i].offerStartTime,
					rows[i].offerEndTime,
					rows[i].illustrate == "" ? null
							: rows[i].illustrate,
					rows[i].priceConfigInfoId,rows[i].vendorId,rows[i].vendorName);
			priceConfigInfos.push(priceConfigInfo);
		}

		if(rows.length>1){
			for (var i = 0; i < rows.length; i++) {
				for(var j=i+1;j<priceConfigInfos.length;j++){
					if(rows[i].productId==priceConfigInfos[j].productId
							&&rows[i].offerStartTime==priceConfigInfos[j].offerStartTime
								&&rows[i].offerEndTime==priceConfigInfos[j].offerEndTime){
						$.messager.alert("提示","同一报价周期同一产品在同一项目内只能有一条定价信息!");
						return ;
					}
				}
			}
		}


		var data = {
			"list" : priceConfigInfos,
			// "aduitUser" : priceConfigAuditUser,
			"projectId" : projectId,
			"status" : type,
			"opration" : opration,
			// "priceConfigId" : priceConfigId
		}
		$.messager
				.confirm('提示',"确定要" + (type == '1' ? '提交' : '保存') + "这" + rows.length+"条定价配置信息？",
						function(r) {
							if (r) {
								$.ajax({
											dataType : "json",
											data : JSON.stringify(data),
											method : "post",
											contentType : "application/json",
											url : contextPath
													+ "/priceConfig/addMiGupriceConfig.do"
												})
										.done(
												function(data) {
													if (data.status == "1") {
														$.messager.alert('提示',
																"操作成功！");
														closeWindow('addPriceConfigDiv');
														searchSubmit();
													} else {
														$.messager.alert('提示',
																data.msg,
																'warning');
													}
												}).fail(
												function() {
													$.messager.alert('提示',
															"本次操作失败，请重新操作",
															'error');
													return;
												});
							}
						});
	} else {
		$.messager.alert('提示', "请选择提交的数据！",
				'warning');
		return;
	}

}

function updatePriceConfig(type) {
	$('#addPriceConfigTable').datagrid('endEdit', editIndex);
	 var rows = $('#addPriceConfigTable').datagrid('getChanges');
	var opration = $("#opration").val();
	var priceConfigInfos = new Array();
	var projectId =$("#projectId").val();
	if (rows.length > 0) {

		for (var i = 0; i < rows.length; i++) {
			if (isNull(rows[i].productId)) {
				$.messager.alert("提示", "产品编号不能为空！",
						"warning")
				return;
			}
			if (isNull(rows[i].productName)) {
				$.messager.alert("提示", "产品名称不能为空！",
						"warning")
				return;
			}
		
			if (isNull(rows[i].purchasePrice)) {
				$.messager.alert("提示", "采购价不能为空！",
						"warning")
				return;
			}
			if (isNull(rows[i].minSellPrice)) {
				$.messager.alert("提示", "最低售价不能为空！",
						"warning")
				return;
			}
			if (isNull(rows[i].offerStartTime)) {
				$.messager.alert("提示", "报价周期开始时间不能为空！",
						"warning")
				return;
			}else{
				if(rows[i].offerStartTime.length==6){
					var reg=/^[0-9]*$/
					if(!reg.test(rows[i].offerStartTime)){
						$.messager.alert("提示", "请输入正确格式的日期(yyyyMM)例：201706！",
						"warning");
						return 
					}
				}else{
					$.messager.alert("提示", "请输入正确格式的日期(yyyyMM)例：201706！",
					"warning");
					return ;
				}
			}
			if (isNull(rows[i].offerEndTime)) {
				$.messager.alert("提示", "报价周期结束时间不能为空！",
						"warning")
				return;
			}else{
				if(rows[i].offerEndTime.length==6){
					var reg=/^[0-9]*$/
					if(!reg.test(rows[i].offerEndTime)){
						$.messager.alert("提示", "请输入正确格式的日期(yyyyMM)例：201706！",
						"warning");
						return 
					}
				}else{
					$.messager.alert("提示", "请输入正确格式的日期(yyyyMM)例：201706！",
					"warning");
					return ;
				}
			}
			var priceConfigInfo = new PriceConfigInfo(
					rows[i].projectId,
					rows[i].productId,
					rows[i].productName,
					rows[i].purchasePrice,
					rows[i].minSellPrice,
					rows[i].offerStartTime,
					rows[i].offerEndTime,
					rows[i].illustrate == "" ? null
							: rows[i].illustrate,
					rows[i].priceConfigInfoId,rows[i].vendorId,rows[i].vendorName);
			priceConfigInfos.push(priceConfigInfo);
		}

		if(rows.length>1){
			for (var i = 0; i < rows.length; i++) {
				for(var j=i+1;j<priceConfigInfos.length;j++){
					if(rows[i].productId==priceConfigInfos[j].productId
							&&rows[i].offerStartTime==priceConfigInfos[j].offerStartTime
								&&rows[i].offerEndTime==priceConfigInfos[j].offerEndTime){
						$.messager.alert("提示","同一报价周期同一产品在同一项目内只能有一条定价信息!");
						return ;
					}
				}
			}
		}


		var data = {
			"list" : priceConfigInfos,
			"projectId" : projectId,
			"status" : type,
			"opration" : opration
		}
		$.messager
				.confirm('提示',"确定要修改这" + rows.length+"条定价配置信息？",
						function(r) {
							if (r) {
								$.ajax({
											dataType : "json",
											data : JSON.stringify(data),
											method : "post",
											contentType : "application/json",
											url : contextPath
													+ "/priceConfig/addMiGupriceConfig.do"
												})
										.done(
												function(data) {
													if (data.status == "1") {
														if(isNull(data.msg)){
															$.messager.alert('提示',
															"操作成功！");
														}else{
															$.messager.alert('提示',
															data.msg+"产品定价信息已被使用，不能做修改！");
														}
														search_priceConfigWin();
														// closeWindow('addPriceConfigDiv');
													} else {
														$.messager.alert('提示',
																data.msg,
																'warning');
													}
												}).fail(
												function() {
													$.messager.alert('提示',
															"本次操作失败，请重新操作",
															'error');
													return;
												});
							}
						});
	} else {
		$.messager.alert('提示', "数据未做修改！",
				'warning');
		return;
	}

}





// 删除定价配置信息
function delSelect(row) {
	$('#addPriceConfigTable').datagrid('endEdit');
	console.log(row);
	var priceConfigIds = new Array();
		if (!isNull(row.priceConfigInfoId)) {
			priceConfigIds.push(row.priceConfigInfoId);
		}
	
	if (priceConfigIds.length > 0) {
		$.ajax({
			dataType : "json",
			data : JSON.stringify(priceConfigIds),
			method : "post",
			contentType : "application/json",
			url : contextPath + "/priceConfig/delPriceConfigInfo.do"
		}).done(function(data) {
			if (data.status == "1") {
				if(isNull(data.msg)){
					$.messager.alert('提示', "操作成功！");
				}else{
					$.messager.alert('提示', data.msg);
					search_priceConfigWin();
				}
			
			} else {
				$.messager.alert('提示', data.msg, 'warning');
			}
		}).fail(function() {
			$.messager.alert('提示', "本次操作失败，请重新操作", 'error');
			return;
		});
	}

}

function PriceConfigInfo(projectId, productId, productName,
		purchasePrice, minSellPrice, offerStartTime, offerEndTime, illustrate,
		priceConfigInfoId,vendorId,vendorName) {
	this.projectId = projectId;
	this.productId = productId;
	this.productName = productName;
	this.purchasePrice = purchasePrice;
	this.minSellPrice = minSellPrice;
	this.offerStartTime = offerStartTime;
	this.offerEndTime = offerEndTime;
	this.illustrate = illustrate;
	this.priceConfigInfoId = priceConfigInfoId;
	this.vendorId=vendorId;
	this.vendorName=vendorName;
}

function isNull(mixed_var) {
	var key;

	if (mixed_var === ""
			|| mixed_var === null || mixed_var === false
			|| typeof mixed_var === 'undefined') {
		return true;
	}

	if (typeof mixed_var == 'object') {
		for (key in mixed_var) {
			return false;
		}
		return true;
	}
	return false;
}

function setPlaceholder(id,field,editIndex){
	var $edit = $('#'+id).datagrid('getEditor', {index: editIndex, field: field });// 获取编辑属性
	if($edit){
		if ($($edit.target).hasClass('textbox-f')) {
				$($edit.target).textbox('textbox').attr("placeholder","请输入年月(例：201706)");
		}
	}
}

function getRowIndex(target) {
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}


//定价配置导出
function exportFile(){
	
	var priceConfigWin_projectId = $('#priceConfigWin_projectId').val();
	var q_productId = $('#q_productId').textbox('getValue');
	var q_productName = $('#q_productName').textbox('getValue');
	
	
	$.messager.confirm('提示',"你确定要导出定价配置吗？",function(r){
	    if (r)
	    {
	    	$.ajax({
	    	    dataType: "json",
	    	    data: {"priceConfigWin_projectId":priceConfigWin_projectId,"q_productId":q_productId,"q_productName":q_productName},
	    	    method: "post",
	    	    url: contextPath+"/priceConfig/exportFile.do"
	    	}).done(function(data){
	    		if(data.reCode=="100"){
	    	    	$("#exportform1").form("submit", {
	    	            url : contextPath+"/priceConfig/downloadFile.do",
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
