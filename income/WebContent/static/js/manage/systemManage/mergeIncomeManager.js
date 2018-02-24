var isSearch = true;
var viewHeight=undefined;
var bodyHeight=undefined;
var isFirst=true;
var status=1;
$(function(){
	changeMonthType("q_month");
	changeMonthType("bzCycleReal");
	$("#selectProject").hide();
	$('#searchMergeRealIncome').hide();
	$('#confirm').hide();
	 $(".smartSearch").click(function(){
		 if(isFirst){
			 viewHeight=$(".datagrid-view").prop("style").height;
			 bodyHeight=$(".datagrid-body").prop("style").height;
			 isFirst=false; 
		 }
		 $("#smartbr").toggle(100);
		 if(status==1){
			var el= $(".datagrid-view").prop("style").height;
			var body=$(".datagrid-body").prop("style").height;
			var height =parseInt(el.substr(0,el.indexOf("px")));
			var bdHeight=parseInt(body.substr(0,body.indexOf("px")));
			$(".smartSearch .l-btn-text").removeClass("icon-m-angle-down").addClass("icon-m-angle-up")
			 $(".datagrid-view").prop("style").height=(height-70)+"px";
			 $(".datagrid-body").css("height",(bdHeight-70)+"px");
			 status=2;
		 }else{
			 $(".smartSearch .l-btn-text").removeClass("icon-m-angle-up").addClass("icon-m-angle-down")
			 $(".datagrid-view").prop("style").height=viewHeight;
			 $(".datagrid-body").css("height",bodyHeight);
			 status=1;
		 }
		
	 });
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	$('#list_data').datagrid({
		title:'合并实际收入管理列表',  
		width: '100%',  
		height: 'auto',  
		nowrap: false,  
		striped: true,
		border: true, 
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的  
		fit: true,//自动大小  
		url:contextPath + '/mergeIncome/queryMergeIncome.do',
		remoteSort:false,   
		checkOnSelect :true,
		singleSelect:false,// 是否单选
		pagination:true,//分页控件  
		pageSize: 20,//每页显示的记录条数，默认为10
		pageList: [5,10,15,20],//可以设置每页记录条数的列表
		columns:[[
					{ field:'ck',checkbox:true},
					{ field:'eastimateStatus',hidden:'true'},
					{ field:'bzCycleReal',width : '5%',align : "center",title:'报账月份' },
					{ field:'cycle',width : '5%',align : "center",title:'业务账期' },
					{ field:'projectId',width : '5%',align : "center",title:'项目号' },
					{ field:'projectName',width : '15%',align : "center",title:'项目名称' },
					{ field:'className',width : '15%',align : "center",title:'收入大类' },
			        { field:'sectionName',width : '15%',align : "center",title:'收入小类' },
					{ field:'realIncome',width : '10%',align : "center",title:'实际收入(含税)'},
		            { field:'realIncomeTax',width : '10%',align : "center",title:'税率',
		            	 formatter:function(val,rowData,rowIndex){
		            	        if(val!=null)
		            	            return val.toFixed(2);
		            	   }
		            },
		            { field:'realAmount',width : '10%',align : "center",title:'税额',
		            	 formatter:function(val,rowData,rowIndex){
		            	        if(val!=null)
		            	            return val.toFixed(2);
		            	   }
		            },
		            { field:'realExclusiveTax',width : '10%',align : "center",title:'实际收入(不含税)' ,
		            	 formatter:function(val,rowData,rowIndex){
		            	        if(val!=null)
		            	            return val.toFixed(2);
		            	   }
		            },
					{ field:'offset',width : '10%',align : "center",title:'是否冲销预估收入',	
						formatter:function (value){
							if(value==='1'){
								return '是'
							}else{
								return '否'
							}
						}
					},
					{ field:'isNeedBill',width : '10%',align : "center",title:'是否需要开票',	
						formatter:function (value){
							if(value==='1'){
								return '是'
							}else if(value==='0'){
								return '否'
							}else{
								return ''
							}
						}
					},
					   { field:'deptName',width : '8%',align : "center",title:'责任部门' },
			            { field:'userName',width : '8%',align : "center",title:'责任人' },
					{ field:'incomeManagerId',width : '10%',align : "center",title:'合并明细',
						formatter:function (value, data, index) {
								return '<a href="javascript:void(0);" onClick="viewDetail('+value+')">查看</a>'
							}
					},
		          	{ field:'auditDept',hidden:'true'},
		          	{ field:'auditPerson',hidden:'true'}
				]],
				onLoadSuccess: function(data){
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
		            //沒有数据时仍显示滚动条
		            if(data.total == 0){
		                    $("#list_data").datagrid("insertRow",{row:{}});
		                    $("tr[datagrid-row-index='0']").css({"visibility":"hidden"});
		                    $(page).pagination({  
		                		beforePageText: '第',//页数文本框前显示的汉字  
		                		afterPageText: '页    共 0 页',  
		                		displayMsg: '当前显示0 - 0 条记录   共 0 条记录'});
		                }
			},
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
	$('#q_dept').combobox({
	    url:contextPath+"/actualIncome/queryAllDep.do",
	    valueField:'deptCode',
	    textField:'deptName',
	    editable:false,
	});

	// 验证开始时间小于结束时间
	$.extend($.fn.validatebox.defaults.rules,
					{
						end : {
							validator : function(value, param) {
								var startDate = $("#startDate").datebox(
										"getValue");
								var startTmp = new Date(startDate.replace(/-/g,
										"/"));
								var endTmp = new Date(value.replace(/-/g, "/"));

								if (startTmp != 'Invalid Date') {
									if (startTmp <= endTmp) {
										isSearch = true;
									} else {
										isSearch = false;
									}
									if (isSearch) {
										var $dateinput = $("input.textbox-text.textbox-text-readonly.validatebox-text");
										if ($dateinput) {
											$dateinput.each(function(index, e) {
														console.log($(e));
														if ($(e).hasClass("validatebox-invalid")) {
															$(e).removeClass('validatebox-invalid')
														}
													})
											var $dataSpan = $("span.textbox.combo.datebox.textbox-invalid");
											$dataSpan.each(function(index, e) {
												console.log($(e));
												if ($(e).hasClass("textbox-invalid")) {
													$(e).removeClass('textbox-invalid')
												}
											})
										}
									}
									return startTmp <= endTmp;
								} else {
									return true;
								}
							},
							message : '结束时间要大于开始时间！'
						},
						start : {
							validator : function(value, param) {
								var endDate = $("#endDate").datebox("getValue");
								var endTmp = new Date(endDate.replace(/-/g, "/"));
								var startTmp = new Date(value.replace(/-/g, "/"));
								if (endTmp != 'Invalid Date') {
									if (startTmp <= endTmp) {
										isSearch = true;
									} else {
										isSearch = false;
									}
									if (isSearch) {
										var $dateinput = $("input.textbox-text.textbox-text-readonly.validatebox-text");
										if ($dateinput) {
											$dateinput.each(function(index, e) {
														console.log($(e));
														if ($(e).hasClass(
																		"validatebox-invalid")) {
															$(e).removeClass(
																			'validatebox-invalid')
														}
													})
											var $dataSpan = $("span.textbox.combo.datebox.textbox-invalid");
											$dataSpan.each(function(index, e) {
												if ($(e).hasClass("textbox-invalid")) {
													$(e).removeClass('textbox-invalid')
												}
											})
										}
									}
									return startTmp <= endTmp;
								} else {
									return true;
								}

							},
							message : '开始时间要小于结束时间！'
						}
					});
});


//重置合并实际收入查询
function reset(){
	$("#bzCycleReal").datebox('setValue','');
	$("#q_month").datebox('setValue','');
	$("#q_projectId").textbox('setValue','');
	$("#q_projectName").textbox('setValue','');
	$("#q_incomeClassId").combobox('setValue','');
	$("#q_incomeSectionId").combobox('setValue','');
	$("#q_dept").combobox('setValue','');
	$("#q_userName").textbox('setValue','');
	$("#est_income").numberbox('setValue','');
	$("#est_exclusive_tax").numberbox('setValue','');
	$("#q_cx").combobox('setValue','');
}


//合并实际收入查询
function searchSubmit(){
	var bzCycleReal =$("#bzCycleReal").datebox('getValue','');
	var q_month = $('#q_month').datebox('getValue');
	var q_projectId = $('#q_projectId').val();
	var q_projectName = $('#q_projectName').val();
	var q_incomeClassId = $("#q_incomeClassId").combobox('getValue');
	var q_incomeSectionId = $("#q_incomeSectionId").combobox('getValue');
	var q_dept =$("#q_dept").combobox('getValue');
	var q_userName =$("#q_userName").val();
	var est_income =$("#est_income").val();
	var est_exclusive_tax = $("#est_exclusive_tax").val();
	var q_cx = $("#q_cx").combobox('getValue','');
	
	
	$('#list_data').datagrid('load', {
		q_month : q_month,
		q_projectId : q_projectId,
		q_projectName : q_projectName,
		bzCycleReal:bzCycleReal,
		q_incomeClassId:q_incomeClassId,
		q_incomeSectionId:q_incomeSectionId,
		q_dept:q_dept,
		q_userName:q_userName,
		rl_income:est_income,
		rl_exclusive_tax:est_exclusive_tax,
		q_cx:q_cx
	});
}

function viewDetails(incomeManagerId) {  
    $("#dataDetail").datagrid({
    	url:contextPath+'/income/viewRealIncomeDetails.do?incomeManagerId='+incomeManagerId,  
        loadMsg: "数据加载中",  
        columns:[[
			{ field:'productName',width : '44%',align : "center",title:'产品名称' },
			{ field:'sellingPrice',width : '30%',align : "center",title:'售价' },
			{ field:'porductNumber',width : '30%',align : "center",title:'件数' },
		]],
    });
    $("#dlg").show();
	$('#dlg').window({
		title:'实际收入明细',
		modal:true,
		minimizable:false,
		collapsible:false,
	});
}; 

//取消
function dismiss(id){
	var tbId =id;
	$('#'+tbId).window('close');	
	$('#'+tbId).hide();
}


/*========================查询可以合并的实际收入===============================*/
//查询可以合并的实际收入
function doSearch(){
	changeMonthType("startDate");
	changeMonthType("endDate");
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	$('#list_data_search').datagrid({
		width: '70%',  
		height: '70%',  
		nowrap: false,
		queryParams:{detp_id : null},
		striped: true,
		border: true, 
		loadMsg: '正在加载',
		collapsible:false,// 是否可折叠的
		fit: true,// 自动大小
		url:contextPath + '/mergeIncome/queryMergeRealIncome.do',  
		remoteSort:false,   
		checkOnSelect :true,
		singleSelect:false,// 是否单选
		pagination:false,// 分页控件
		//pageSize: 20,// 每页显示的记录条数，默认为10
		//pageList: [5,10,15,20],// 可以设置每页记录条数的列表
		columns:[[
					{ field:'ck',checkbox:true},
					{ field:'CYCLE',width : '10%',align : "center",title:'业务账期' },
					{ field:'PROJECT_ID',width : '10%',align : "center",title:'项目号' },
					{ field:'PROJECT_NAME',width : '20%',align : "center",title:'项目名称' },
					{ field:'REAL_INCOME',width : '14%',align : "center",title:'实际收入（含税）' },
					{ field:'REAL_INCOME_TAX',width : '8%',align : "center",title:'税率' },
					{ field:'OFFSET',width : '7%',align : "center",title:'冲销状态',
						formatter:function(value){
							if(value=='0'){
								return '未冲销'
							}
							if(value=='1'){
								return '已冲销'
							}
						}
					},
					{ field:'DEPT_NAME',width : '10%',align : "center",title:'责任部门' },
					{ field:'USER_NAME',width : '10%',align : "center",title:'责任人' },
					{ field:'INCOME_MANAGER_ID',hidden:true},
					
				]],
		toolbar: '#tb_searchMergeRealIncome'
	});  
	

	$('#searchMergeRealIncome').show();
	$('#searchMergeRealIncome').window({
		title:'可合并实际收入查询',
		width:'70%',
		height:'70%',
		modal:true,
		minimizable:false,
		collapsible:false
	});
	
	$('#dept_id').combobox({
	    url: contextPath + '/mergeIncome/getDept.do',//对应的ashx页面的数据源
	    method: 'post',
	    valueField: 'deptCode',//绑定字段ID
	    textField: 'deptName',//绑定字段Name
	    panelHeight: 'auto',//自适应
	    multiple: true,
	    formatter: function (row) {
	        var opts = $(this).combobox('options');
	        return '<input type="checkbox" class="combobox-checkbox" id="' + row[opts.valueField] + '">' + row[opts.textField]
	    },

	    onShowPanel: function () {
	        var opts = $(this).combobox('options');
	        var target = this;
	        var values = $(target).combobox('getValues');
	        $.map(values, function (value) {
	            var el = opts.finder.getEl(target, value);
	            el.find('input.combobox-checkbox')._propAttr('checked', true);
	        })
	    },
	    onLoadSuccess: function () {
	        var opts = $(this).combobox('options');
	        var target = this;
	        var values = $(target).combobox('getValues');
	        $.map(values, function (value) {
	            var el = opts.finder.getEl(target, value);
	            el.find('input.combobox-checkbox')._propAttr('checked', true);
	        })
	    },
	    onSelect: function (row) {
	        var opts = $(this).combobox('options');
	        var el = opts.finder.getEl(this, row[opts.valueField]);
	        el.find('input.combobox-checkbox')._propAttr('checked', true);
	    },
	    onUnselect: function (row) {
	        var opts = $(this).combobox('options');
	        var el = opts.finder.getEl(this, row[opts.valueField]);
	        el.find('input.combobox-checkbox')._propAttr('checked', false);
	    }
	});

}
function searchProjectSubmit(){
	if(!isSearch){
		$.messager.alert("提示", "开始时间不能大于结束时间！", "warning");
		return;
	}
	var startDate = $('#startDate').datebox('getValue');
	var endDate = $('#endDate').datebox('getValue');
	 var opts =$('#dept_id').next().children("input.textbox-value");
	 var dept_id='';
	 opts.each(function(e){
		 dept_id+=$(this).val()+",";
	 });
	 dept_id = dept_id.substr(0,dept_id.lastIndexOf(","));
	$('#list_data_search').datagrid('load', {
		dept_id : dept_id,
		startDate:startDate,
		endDate:endDate
	});
}

//重置查询
function searchProjectReset(){
	 var opts =$('#dept_id').next().children("input.textbox-value");
	 var dept_id='';
	 opts.each(function(e){
		$(this).val('');
	 });
	 $('#dept_id').combobox('setValue','');
	 $('#startDate').datebox('setValue','');
	 $('#endDate').datebox('setValue','');
	 isSearch = true;
}

var products=null;

//确定
function selected(){
	products=new Array();
	var selRow = $("#list_data_search").datagrid("getChecked");
	if (selRow.length < 1) {
		$.messager.alert('告警','请选择一行数据!','warning');
		return false;
	}
	var item =selRow[0];
	if(selRow.length>1){
		for (var i = 1; i < selRow.length; i++) {
			
				if(item.REAL_INCOME_TAX!=selRow[i].REAL_INCOME_TAX){
					flag =false;
					projectId=selRow[i].PROJECT_ID;
					tax=selRow[i].REAL_INCOME_TAX
					$.messager.alert('告警',"项目号："+selRow[i].PROJECT_ID+"，税率："+selRow[i].REAL_INCOME_TAX+"与其他实际收入不同",'warning');
					
					return
				}
				if(item.OFFSET!=selRow[i].OFFSET){
					$.messager.alert('告警',"项目号："+selRow[i].PROJECT_ID+"，冲销状态："+(selRow[i].OFFSET=='0'?'未冲销':'已冲销')+" <br/>与其他实际收入不同",'warning');
					return
				}

		}
	}
	products=selRow;
	$('#searchMergeRealIncome').window('close');
	$('#searchMergeRealIncome').hide();
	queryProject();
}



/*=======================================================*/



//查询项目
function queryProject() {
	
	$('#list_data_project').datagrid({
		loadMsg : '正在加载',
		width: '100%',  
		height: '100%',
		url : contextPath + '/mergeIncome/searchProject.do',
		columns : [ [
		{ field:'ck',checkbox:true
		},{
			field : 'PROJECT_ID',
			width : '10%',
			align : 'center',
			title : '项目号',
		}, {
			field : 'PROJECT_NAME',
			width : '20%',
			align : 'center',
			title : '项目名称',
			
		}, {
			field : 'INCOME_CLASS',
			width : '23%',
			align : 'center',
			title : '收入大类',
			
		}, {
			field : 'INCOME_SECTION',
			width : '24%',
			align : 'center',
			title : '收入小类',
			
		}, {
			field : 'DEPT_NAME',
			width : '10%',
			align : 'center',
			title : '责任部门',
			
		}, {
			field : 'USER_NAME',
			width : '10%',
			align : 'center',
			title : '责任人',
			
		}
		] ],
		striped: true,
		border: true, 
		loadMsg: '正在加载',
		collapsible:false,// 是否可折叠的
		fit: true,// 自动大小
		remoteSort:false,   
		checkOnSelect :true,
		singleSelect:true,// 是否单选
		pagination:true,// 分页控件
		pageSize: 20,// 每页显示的记录条数，默认为10
		pageList: [5,10,15,20],// 可以设置每页记录条数的列表
		toolbar : '#tb_searchProject'
	});
	//设置分页控件  
	var page = $('#list_data_project').datagrid('getPager');
	$(page).pagination({  
		beforePageText: '第',//页数文本框前显示的汉字  
		afterPageText: '页    共 {pages} 页',  
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
		onBeforeRefresh:function(){ 
			$(this).pagination('loading'); 
			$(this).pagination('loaded'); 
		}
	});
	$("#selectProject").show();
	$('#selectProject').window({
		title : '选择项目',
		width : '70%',
		height : '70%',
		modal : true,
		minimizable : false,
		collapsible : false,
	});
}

//重置实际收入查询
function resetProjectSearch(){
	$("#projectId").textbox('setValue','');
	$("#projectName").textbox('setValue','');

}

//合并实际收入查询
function searchProject(){
	var projectId = $('#projectId').val();
	var projectName = $('#projectName').val();
	$('#list_data_project').datagrid('load', {
		projectId : projectId,
		projectName : projectName,
	});
}

var incomManagerId ="";
var projectId ="";
function doMerge(){
	incomManagerId ="";
	projectId ="";
	var selRow = $("#list_data_project").datagrid("getChecked");
	if (selRow.length != 1) {
		$.messager.alert('告警','请选择一行数据!','warning');
		return false;
	}
	
	for(var i=0;i<products.length;i++){
		var item = products[i];
		incomManagerId+=item.INCOME_MANAGER_ID+","
	}
	incomManagerId=incomManagerId.substr(0,incomManagerId.lastIndexOf(','));
	 projectId =selRow[0].PROJECT_ID;
	$("#data_info").datagrid({
        columns:[[
			{ field:'CYCLE',width : '20%',align : "center",title:'业务账期' },
			{ field:'PROJECT_ID',width : '40%',align : "center",title:'项目' },
			{ field:'REAL_INCOME',width : '30%',align : "center",title:'实际收入（含税）' },
		]]
    });
	$('#confirm').show();
	$('#confirm').window({
		title : '确认合并？',
		width : '30%',
		height : '40%',
		modal : true,
		minimizable : false,
		collapsible : false,
	});
	
	 $("#data_info").datagrid('loadData',products);
}


function merge(){
	dismiss('confirm');
	$.ajax({
		url:contextPath+'/mergeIncome/mergeRealIncome.do',
		type:"post",
		dataType:'json',
		data:{"ids":incomManagerId,"projectId":projectId},
		success:function(data){
			if(data.status=='1'){
				$.messager.alert('提示','操作成功!','info');
				dismiss('selectProject');
				searchSubmit();
			}else{
				$.messager.alert('提示',data.msg,'error');
				return;
			}
		},
		error:function(){
			$.messager.alert('提示','操作失败!','error');
		}
	})
}


function cancelMerge(){
	
	var rows = $("#list_data").datagrid('getChecked');
	var items=new Array();
	var item={
			incomeManagerId:null,
			cycle:null,
			projectId:null
	}
	if(rows.length<1){
		$.messager.alert('提示','请选择一条数据','warnning');
		return ;
	}
	for(var i=0;i<rows.length;i++){
		item={incomeManagerId:rows[i].incomeManagerId,
				cycle:rows[i].cycle,
				projectId:rows[i].projectId
				}
		items.push(item);
	}
	 $.messager.confirm('提示','确定要取消合并吗？', function(r){
	      if(r){
	    	 $.ajax({
	    		 url:contextPath+'/mergeIncome/revokeMerge.do',
	    		 data:JSON.stringify(items),
	    		 type:"post",
	    		 dataType:'json',
	    		 contentType:'application/json',
	    		 success:function(data){
	    			 if(data.status=='1'){
	    				 $.messager.alert("提示",'操作成功！',"info");
	    				 searchSubmit();
	    				 return;
	    			 }else{
	    				 $.messager.alert("提示",data.msg,"danger");
	    			 }
	    		 },
	    		 error:function(){
	    			 $.messager.alert("提示",'操作失败！',"danger");
	    		 }
	    	 }); 
	      }
	    }
	 );
	
}




//合并明细
function viewDetail(incomeManagerId) {  
    $("#hisData").datagrid({
    	url:contextPath+'/mergeIncome/viewMergeDetail.do',
    	queryParams:{
    		mergeId:incomeManagerId
    	},
        loadMsg: "数据加载中",  
        columns:[[
			{ field:'CYCLE',width : '10%',align : "center",title:'业务账期' },
			{ field:'PROJECT_ID',width : '15%',align : "center",title:'项目号' },
			{ field:'PROJECT_NAME',width : '31%',align : "center",title:'项目名称' },
			{ field:'REAL_INCOME',width : '15%',align : "center",title:'实际收入（含税）'},
			{ field:'DEPT_NAME',width : '15%',align : "center",title:'责任部门'},
			{ field:'USER_NAME',width : '15%',align : "center",title:'责任人'}
		]],
    });
    $("#hisDiv").show();
	$('#hisDiv').window({
		width : '60%',
		height : '50%',
		title:'合并明细',
		modal:true,
		minimizable:false,
		collapsible:false,
	});
}; 


