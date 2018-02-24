//初始化实际收款列表页面
var viewHeight=undefined;
var bodyHeight=undefined;
var isFirst=true;
var status=1;
$(function(){
	changeMonthType('q_month');
	changeMonthType('q_record_month');
	$("#q_incomeState").combobox('setValue','');
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	
	//高级查询下拉
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
			 $(".datagrid-view").prop("style").height=(height-76)+"px";
			 $(".datagrid-body").css("height",(bdHeight-76)+"px");
			 status=2;
		 }else{
			 $(".smartSearch .l-btn-text").removeClass("icon-m-angle-up").addClass("icon-m-angle-down")
			 $(".datagrid-view").prop("style").height=viewHeight;
			 $(".datagrid-body").css("height",bodyHeight);
			 status=1;
		 }
	 });
	
	
	$('#list_data').datagrid({
		title:'实际收款管理列表',  
		width: '100%',  
		height: 'auto',  
		nowrap: false,  
		striped: true,
		border: true, 
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的  
		fit: true,//自动大小  
		url:contextPath + '/actualIncome/queryAllActualIncome.do',  
		remoteSort:false,   
		checkOnSelect :true,
		singleSelect:false,// 是否单选
		pagination:true,//分页控件  
		pageSize: 20,//每页显示的记录条数，默认为10
		pageList: [5,10,15,20],//可以设置每页记录条数的列表
		columns:[[
					{ field:'ck',checkbox:true },
					{ field:'cycle',width : '10%',align : "center",title:'业务账期' },
					{ field:'bzCycle',width : '10%',align : "center",title:'报账月份' },
					{ field:'projectId',width : '9%',align : "center",title:'项目号' },
					{ field:'projectName',width : '15%',align : "center",title:'项目名称' },
					{ field:'className',width : '20%',align : "center",title:'收入大类' },
					{ field:'sectionName',width : '20%',align : "center",title:'收入小类' },
//		          	{ field:'billingKey',width : '15%',align : "center",title:'开票申请编号'},
					{ field:'billingKey',width : '15%',align : "center",title:'开票申请编号',formatter:function(value,row, index){
						if(row.isNeedBill=='1'||row.isNeedBill ==null )
							return value;
						if(row.isNeedBill=='0')
							return '-'
					}},
//					{ field:'invoiceCode',width : '10%',align : "center",title:'发票代码' },
//					{ field:'invoiceNumber',width : '10%',align : "center",title:'发票号码' },
//					{ field:'billingDate',width : '10%',align : "center",title:'开票时间' },
					{ field:'billingIncome',width : '10%',align : "center",title:'开票总金额',formatter:function(value,row, index){
						if(value=='-'||value ==null ){
							return value
							}else{
							return '<a href="javascript:void(0)" onclick="showBilldetail('+"'"+row.billingKey+"'"+')" style="text-decoration:none">'+value+'</a>'
							}
					}},
					{ field:'income',width : '10%',align : "center",title:'实际收款' },
					{ field:'incomeDate',width : '10%',align : "center",title:'实际收款日期' },
					{ field:'incomeOptionsUrl',width : '5%',align : "center",title:'附件',formatter: function (value, row, index) {
						if(!isNull(row.incomeOptionsUrl)){
							return '<a href="javascript:void(0)" onclick="downloadAttach('+"'"+row.incomeOptionsUrl+"'"+')" style="text-decoration:none">下载</a>';
						}
		            } },//实际收款附件路径
					{ field:'incomeStatus',width : '10%',align : "center",title:'实际收款状态',formatter:function(value,row, index){
						if(value=='1')
							return '待审核（'+row.auditPerson+'）';
						if(value=='2')
							return '审核通过';
						if(value=='3')
							return '驳回'
					}},
					{ field:'deptName',width : '10%',align : "center",title:'责任部门' },
					{ field:'userName',width : '10%',align : "center",title:'责任人' },
					{ field:'isNeedBill',hidden:'true'},
//					{ field:'isNeedBill',width : '10%',align : "center",title:'是否属于<br>开票后收款',	
//						formatter:function (value){
//							if(value==='1'){
//								return '是'
//							}else if(value==='0'){
//								return '否'
//							}else{
//								return ''
//							}
//						}
//					},
					{ field:'history',width : '5%',align : "center",title:'审核历史<br>记录',formatter: function (value, row, index) {
						if(!isNull(row.incomeStatus)){
							return '<a href="javascript:void(0)" onclick="showHis('+"'"+row.billingKey+"'"+')" style="text-decoration:none">查看</a>';
						}
		            }}
				]],
		        onLoadSuccess: function (data) {
		          
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
		    			 if(data.total==0){
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
	
});
var lastDate= undefined;

//重置实际收款查询
function reset(){
	$("#q_month").datebox('setValue','');
	$("#q_projectId").textbox('setValue','');
	$("#q_projectName").textbox('setValue','');
	$('#q_incomeState').combobox('setValue','');
	$('#q_record_month').datebox('setValue','');
	$('#q_incomeClassId').combobox('setValue','');
	$('#q_incomeSectionId').combobox('setValue','');
	$('#q_dept').combobox('setValue','');
	$('#q_userName').textbox('setValue','');
	$('#q_bill_num').textbox('setValue','');
	$('#q_bill_total').textbox('setValue','');
	$('#q_income').numberbox('setValue','');
	$('#income_date').datebox('setValue','');
}


//实际收款查询
function searchSubmit(){
	var q_month = $('#q_month').datebox('getValue');
	var q_projectId = $('#q_projectId').val();
	var q_projectName = $('#q_projectName').val();
	var q_incomeState = $('#q_incomeState').combobox('getValue');
	
	var q_record_month = $('#q_record_month').datebox('getValue');
	var q_incomeClassId = $('#q_incomeClassId').combobox('getValue');
	var q_incomeSectionId = $('#q_incomeSectionId').combobox('getValue');
	var q_dept = $('#q_dept').combobox('getValue');
	var q_userName = $('#q_userName').textbox('getValue');
	var q_bill_num = $('#q_bill_num').textbox('getValue');
	var q_bill_total = $('#q_bill_total').textbox('getValue');
	var q_income = $('#q_income').numberbox('getValue');
	var income_date = $('#income_date').datebox('getValue');
	$('#list_data').datagrid('load', {
		q_month : q_month,
		q_projectId : q_projectId,
		q_projectName : q_projectName,
		q_incomeState : q_incomeState,
		 q_record_month : q_record_month,
		 q_incomeClassId : q_incomeClassId,
		 q_incomeSectionId:q_incomeSectionId,
		 q_dept:q_dept,
		 q_userName :q_userName,
		 q_bill_num :q_bill_num,
		 q_bill_total:q_bill_total,
		 q_income:q_income,
		 income_date:income_date
	});
}

//选择更新项目
function selected(){
	var selRow = $("#list_data").datagrid("getChecked");
	if (selRow.length != 1) {
		$.messager.alert('告警','请选择一行数据!','warning');
		return false;
	}
	$("#billingKey").val(selRow[0].billingKey);
	updateIncome();
}

//显示更新框
function updateIncome() {
	$("#edit_income").numberbox('setValue',"")
	$("#opration").val("update");
	$("#fileURL").html("");
	$("#edit_file").filebox('setValue','');
	$('#Audit_dept').combobox('setValue','');
	$('#Audit_person').combobox('setValue','');
	var billingKey = $("#billingKey").val();
	$.ajax({
		url : contextPath + '/actualIncome/editActualIncome.do',
		type : "post",
		dataType: "json",
		data : {"billingKey":billingKey},
		success : function(data) {
					var income =data.income;
					var status =data.status;
					if(null!=status){
						$.messager.alert("提示",data.str)
						return
					}
					if(null!=income){
						// 加载下拉框 弹出窗口
						$("#month_act").html(income.cycle);
						$("#projectId_act").html(income.projectId);
						$("#projectName_act").html(income.projectName);
						$("#billingIncome").html(income.billingIncome);
						$('#Audit_dept').combobox({
						    url:contextPath+"/actualIncome/queryAllDep.do",
						    valueField:'deptCode',
						    textField:'deptName',
						    editable:false
						});
						$("#editIncomeDiv").show();
						$('#editIncomeDiv').window({
							title : '更新实际收款',
							width : '60%',
							height : '90%',
							modal : true,
							minimizable : false,
							collapsible : false
						});
						var time1 = new Date().format("yyyy-MM-DD");
						 $("#q_actualMonth").datebox("setValue",time1);
						 
						$("#edit_income").numberbox('setValue',income.income);
						$("#q_actualMonth").datebox('setValue',income.incomeDate);
						if(income.incomeOptionsUrl != null){
							$("#old_file").val(income.incomeOptionsUrl);
							var start=income.incomeOptionsUrl.lastIndexOf("/");
							$("#fileURL").html(income.incomeOptionsUrl.substring(start+1));
						}	 
					}else{
						$.messager.alert('告警', '更新实际收款失败！', 'warning');
					}		
		},
		error : function() {
				$.messager.alert('告警', '更新实际收款失败！', 'warning');
		}
	});
}

//审核人查询
$(document).ready(function () {
		$("#Audit_dept").combobox({
			onChange: function (n,o) {
				var deptId = $('#Audit_dept').combobox('getValue');
				$('#Audit_person').combobox('setValue','');
				 $.ajax({  
					    type: "POST",  
					    url: contextPath+"/actualIncome/queryDepPersonByRole.do",  
					    data: {deptId: deptId},  
					    dataType: "json",  
					    success: function(response){//调用成功  
					    var options=$("#Audit_person").combobox('options');  
					    options.textField="userName";//必须要和数据库查询的字段一样(大小写敏感)  
					    options.valueField="userId";  
					    options.editable = false;
					//加载数据  
					$("#Audit_person").combobox("loadData",response);  
					    },  
					 }); 
				
			}
		});
	});

//审核人查询2
$(document).ready(function () {
		$("#Audit_dept_add").combobox({
			onChange: function (n,o) {
				var deptId = $('#Audit_dept_add').combobox('getValue');
				$('#Audit_person_add').combobox('setValue','');
				 $.ajax({  
					    type: "POST",  
					    url: contextPath+"/actualIncome/queryDepPersonByRole.do",  
					    data: {deptId: deptId},  
					    dataType: "json",  
					    success: function(response){//调用成功  
					    var options=$("#Audit_person_add").combobox('options');  
					    options.textField="userName";//必须要和数据库查询的字段一样(大小写敏感)  
					    options.valueField="userId";  
					    options.editable = false;
					//加载数据  
					$("#Audit_person_add").combobox("loadData",response);  
					    },  
					 }); 
				
			}
		});
	});
//更新实际收款提交审核
function submitActual(){
	var formData = new FormData($("#editIncomeForm")[0]);
	var income =$('#edit_income').val();
	var date = $("#q_actualMonth").datebox("getValue");
	var file = $("#edit_file").filebox("getValue");
	var old_file = $("#fileURL").text();
	var dept = $('#Audit_dept').combobox('getValue');
	var person = $('#Audit_person').combobox('getValue');
	var billingKey = $("#billingKey").val();

	if(isNull(income)){
		$.messager.alert("提示","实际收款不能为空！")
		return;
	}
	if(isNull(date)){
		$.messager.alert("提示","实际收款日期不能为空！")
		return;
	}
	if(isNull(file)){
		if(isNull(old_file)){
		$.messager.alert("提示","请选择附件！")
		return;
		}
	}
	if(isNull(dept)){
		$.messager.alert("提示","审核部门不能为空！")
		return;
	}
	if(isNull(person)){
		$.messager.alert("提示","审核人不能为空！")
		return;
	}
	$.ajax({
		url : contextPath + '/actualIncome/checkIncome.do',
		type : "post",
		async: false, 
		data : {"billingKey":billingKey},
		success : function(data){		
					//满足需要开票的状态，且实际收款<=发票金额  或者不需要开票
					if((data != ""&&data.isNeedBill=='1'&&Number(data.billMoney) >= Number(income))||(data != ""&&data.isNeedBill=='0')){
						$.ajax({
							url : contextPath + '/actualIncome/updateIncome.do',
							type : "post",
							data : formData,
							processData : false,
							contentType : false,
							success : function(data, status) {
										$("#edit_file").filebox('setValue','');
										$("#old_file").val("");
										if (data.status == 1) {
											$.messager.alert('提示', data.msg);
											$("#editIncomeDiv").window('close');
											searchSubmit();
											return
										}else{
											$.messager.alert('提示', data.msg);
										}
							},
							error : function() {
										$("#edit_file").filebox('setValue','');
										$("#old_file").val("");
										$.messager.alert('告警', '提交审核失败！', 'warning');
							}
						});
					}else{
						$("#edit_file").filebox('setValue','');
						$("#old_file").val("");
						if(isNull(data.billMoney)){
							$.messager.alert('提示','未获取到发票金额，请开票提交发票金额！');
						}else{					
							$.messager.alert('提示','填写的实际收款金额大于发票总金额（含税），发票总金额（含税）为<font color="red">'+data.billMoney+'</font>元，请重新填写！');
						}
						return
					}
		},
		error : function() {
					$("#edit_file").filebox('setValue','');
					$("#old_file").val("");
					$.messager.alert('告警', '提交审核失败！', 'warning')
					return 
		}
	});
}



//取消
function dismisActual(){
	var option= $("#opration").val();
	var tbId =undefined;
	if(option=='update'){
		tbId="editIncomeDiv"
	}else if(option=='add'){
		tbId="addIncomeDiv"
	}
	$('#'+tbId).window('close');			
}

//判断是否选取部门
$(document).ready(function () {
	$("#Audit_person").combo({
		onShowPanel: function () {
			var deptId = $('#Audit_dept').combobox('getValue');
			if(deptId == null || deptId ==''){
				$.messager.alert('提示', '请先选取审核部门！');
				$("#Audit_person").combo('hidePanel');
			}
		}
	})
})

//判断是否选取部门2
$(document).ready(function () {
	$("#Audit_person_add").combo({
		onShowPanel: function () {
			var deptId = $('#Audit_dept_add').combobox('getValue');
			if(deptId == null || deptId ==''){
				$.messager.alert('提示', '请先选取审核部门！');
				$("#Audit_person_add").combo('hidePanel');
			}
		}
	})
})
//下载模板文件
function downloadFile() {
	var fileURL=$("#old_file").val()

	$("#downloadForm").form("submit", {
		url : contextPath + "/actualIncome/downLoad.do",
		data:{"fileURL":fileURL},
		onSubmit : function(param) {
			return this;
		},
		success : function(result) {
				if(result == 1){
					$.messager.alert('提示', "附件下载成功！", 'warning');
				}else if(result == 0){
					$.messager.alert('提示', "附件下载失败！", 'warning');
				}else{
					$.messager.alert('提示', "附件下载失败！", 'warning');
				}
		}
	});

}

//审核历史查询
function showHis(billingKey) {  
    $("#hisData").datagrid({
    	url:contextPath+'/actualIncome/showHis.do?billingKey='+billingKey,  
        loadMsg: "数据加载中",  
        columns:[[
			{ field:'auditPerson',width : '20%',align : "center",title:'处理人' },
			{ field:'auditDept',width : '20%',align : "center",title:'处理部门' },
			{ field:'createDate',width : '20%',align : "center",title:'处理时间' },
			{ field:'dealResult',width : '20%',align : "center",title:'处理结果',formatter:function(value,row, index){
				if(value=='0')
					return '提交审核';
				if(value=='1')
					return '审核通过';
				if(value=='2')
					return '审核通过，流程结束';
				if(value=='3')
					return '驳回'
			} },
			{ field:'dealOptions',width : '20%',align : "center",title:'处理意见',
				formatter:function (value, data, index) {
					if (!isNull(value)&& value.length>10) {
						str=value.substring(0,10)+"...";
						return '<span title="'+value+'"  class="easyui-tooltip">'+str+'</span>'
						}else{
							return value;
						}
					} }
		]],
	   toolbar:"#pt"
    });
    $("#hisDiv").show();
	$('#hisDiv').window({
		title:'审核历史记录',
		modal:true,
		minimizable:false,
		collapsible:false,
	});
	if(!isNull(billingKey)){
		$("#prt").attr("href",contextPath+"/actualIncome/printIncomePage.do?billingKey="+billingKey);
	}else{
		$("#prt").attr("href","javascript:void(0)");
	}
}; 

//下载附件
function downloadAttach(url){
	$("#downloadAttach").form("submit", {
        url : contextPath+'/actualIncome/downloadAttach.do',
        onSubmit : function(param) {
        	param.url = url
            return this;
        },
        success : function(result) {
        	if (null != result && "" != result)
        	{
        		$.messager.alert('提示', "附件下载功能异常，文件不存在，请检查！",'warning');
        	}
        }
    });
}

//导出实际收款
function exportFile(){
	
	var selRows = $('#list_data').datagrid('getChecked'); 
	
	var billingKeys = [];
	for (var i = 0; i < selRows.length; i++){
		var billingKey = selRows[i].billingKey;
		billingKeys.push(billingKey);
	}
	
	var q_month = $('#q_month').datebox('getValue');
	var q_projectId = $('#q_projectId').val();
	var q_projectName = $('#q_projectName').val();
	var q_incomeState = $('#q_incomeState').combobox('getValue');
	
	var q_record_month = $('#q_record_month').datebox('getValue');
	var q_incomeClassId = $('#q_incomeClassId').combobox('getValue');
	var q_incomeSectionId = $('#q_incomeSectionId').combobox('getValue');
	var q_dept = $('#q_dept').combobox('getValue');
	var q_userName = $('#q_userName').textbox('getValue');
	var q_bill_num = $('#q_bill_num').textbox('getValue');
	var q_bill_total = $('#q_bill_total').textbox('getValue');
	var q_income = $('#q_income').numberbox('getValue');
	var income_date = $('#income_date').datebox('getValue');
	
	$.messager.confirm('提示',"你确定要导出实际收款吗？",function(r){
	    if (r)
	    {
	    	$.ajax({
	    	    dataType: "json",
	    	    data: {"q_month":q_month,"q_projectId":q_projectId,"q_projectName":q_projectName,
	    	    	"q_incomeState":q_incomeState,
	    	    	"billingKeys":billingKeys.toString(),
	    	    	 "q_record_month" : q_record_month,
	    			 "q_incomeClassId" : q_incomeClassId,
	    			 "q_incomeSectionId" :q_incomeSectionId,
	    			 "q_dept":q_dept,
	    			 "q_userName" :q_userName,
	    			 "q_bill_num" :q_bill_num,
	    			 "q_bill_total" :q_bill_total,
	    			 "q_income" :q_income,
	    			 "income_date" : income_date
	    	    },
	    	    method: "post",
	    	    url: contextPath+"/actualIncome/realIncomeExportFile.do"
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

//新增实际收款查询项目
function searchProjectSearch() {
    searchProjectReset();
    $.messager.defaults = {ok: "确定", cancel: "取消"};
    $('#list_data_searchProject').datagrid({
        width: '70%',
        height: '70%',
        nowrap: false,
        queryParams: {
            projectId: null,
            projectName: null
        },
        striped: true,
        border: true,
        loadMsg: '正在加载',
        collapsible: false,// 是否可折叠的
        fit: true,// 自动大小
        url: contextPath + '/actualIncome/queryAllNoBillIncome.do',
        remoteSort: false,
        checkOnSelect: true,
        singleSelect: true,// 是否单选
        pagination: true,// 分页控件
        pageSize: 20,// 每页显示的记录条数，默认为20
        pageList: [5, 10, 15, 20],// 可以设置每页记录条数的列表
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'incomeManagerId', hidden: 'true'},
            {field: 'cycle', width: '10%', align: "center", title: '业务账期'},
            {field: 'projectId', width: '10%', align: "center", title: '项目号'},
            {field: 'projectName', width: '20%', align: "center", title: '项目名称'},
            {field: 'auditDept', width: '15%', align: "center", title: '责任部门'},
            {field: 'auditPerson', width: '17%', align: "center", title: '责任人'},
            {field: 'realExplain', width: '29%', align: "center", title: '实际收入说明'}
        ]],
        toolbar: '#tb_searchProject'
    });

    // 设置分页控件
    var p = $('#list_data_searchProject').datagrid('getPager');
    $(p).pagination({
        beforePageText: '第',// 页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onBeforeRefresh: function () {
            $(this).pagination('loading');
            $(this).pagination('loaded');
        }
    });

    $('#searchProject').show();
    $('#searchProject').window({
        title: '查询项目',
        width: '70%',
        height: '70%',
        modal: true,
        minimizable: false,
        collapsible: false
    });
}

//对于弹出的项目进行查询
//重置按钮
function searchProjectReset() {
    $("#searchProject_projectId").textbox('setValue', '');
    $("#searchProject_projectName").textbox('setValue', '');
}

// 项目查询按钮
function searchProjectSubmit() {
    var q_projectId = $('#searchProject_projectId').val();
    var q_projectName = $('#searchProject_projectName').val();
    $('#list_data_searchProject').datagrid('load', {
        projectId: q_projectId,
        projectName: q_projectName,
    });
}
//确定按钮
function searchProjectSelected() {
    var selRow = $("#list_data_searchProject").datagrid("getChecked");
    if (selRow.length != 1) {
        $.messager.alert('告警', '请选择一行数据!', 'warning');
        return false;
    }
    var cycle = selRow[0].cycle;
    var selectedProjectId = selRow[0].projectId;
    var selectedProjectName = selRow[0].projectName;
    $('#searchProject').window('close');
    $('#searchProject').hide();
    addIncome(cycle,selectedProjectId, selectedProjectName);
}

//显示新增项目编辑框
function addIncome(cycle,selectedProjectId, selectedProjectName){
	$("#opration").val("add");
	$("#edit_income_add").numberbox('setValue',"")
	$("#q_actualMonth_add").datebox('setValue','');
	$("#edit_file_add").filebox('setValue','');
	$('#Audit_dept_add').combobox('setValue','');
	$('#Audit_person_add').combobox('setValue','');

	// 加载下拉框 弹出窗口
	$("#month_act_add").html(cycle);
	$("#projectId_act_add").html(selectedProjectId);
	$("#projectName_act_add").html(selectedProjectName);

	$('#Audit_dept_add').combobox({
	    url:contextPath+"/actualIncome/queryAllDep.do",
	    valueField:'deptCode',
	    textField:'deptName',
	    editable:false
	});
	$("#addIncomeDiv").show();
	$('#addIncomeDiv').window({
		title : '新增实际收款',
		width : '80%',
		height : '90%',
		modal : true,
		minimizable : false,
		collapsible : false
	});
}

//新增项目提交审核
function submitAddActual(){
	var monthId = $('#month_act_add').html();
	var projectId = $('#projectId_act_add').html();
	var projectName = $('#projectName_act_add').html();
	var formData = new FormData($("#addIncomeForm")[0]);
	var income =$('#edit_income_add').val();
	var date = $("#q_actualMonth_add").datebox("getValue");
	var file = $("#edit_file_add").filebox("getValue");
	var dept = $('#Audit_dept_add').combobox('getValue');
	var person = $('#Audit_person_add').combobox('getValue');

	if(isNull(monthId)){
		$.messager.alert("提示","业务账期为空！")
		return;
	}
	if(isNull(projectId)){
		$.messager.alert("提示","项目号为空！")
		return;
	}
	if(isNull(projectName)){
		$.messager.alert("提示","项目名称为空！")
		return;
	}
	if(isNull(income)){
		$.messager.alert("提示","实际收款不能为空！")
		return;
	}
	if(isNull(date)){
		$.messager.alert("提示","实际收款日期不能为空！")
		return;
	}
	if(isNull(file)){
		$.messager.alert("提示","请选择附件！")
		return;
	}
	if(isNull(dept)){
		$.messager.alert("提示","审核部门不能为空！")
		return;
	}
	if(isNull(person)){
		$.messager.alert("提示","审核人不能为空！")
		return;
	}
	formData.append("monthId",monthId);
	formData.append("projectId",projectId);
	formData.append("projectName",projectName);
	
	$.ajax({
		url : contextPath + '/actualIncome/addIncome.do',
		type : "post",
		data : formData,
		processData : false,
		contentType : false,
		success : function(data, status) {
					$("#edit_file_add").filebox('setValue','');
					if (data.status == 1) {
						$.messager.alert('提示', data.msg);
						$("#addIncomeDiv").window('close');
						searchSubmit();
						return
					}else{
						$.messager.alert('提示', data.msg);
					}
		},
		error : function() {
					$("#edit_file_add").filebox('setValue','');
					$.messager.alert('告警', '提交审核失败！', 'warning');
		}
	});
}

//展示发票明细
function showBilldetail(billingKey){
	if(isNull(billingKey)){
		$.messager.alert("提示","查询开票明细异常，未获取到开票编号！")
		return;
	}
	
    $("#billDetail").datagrid({
    	url:contextPath+"/actualIncome/showBilldetail.do?billingKey="+billingKey,  
        loadMsg: "数据加载中",  
        rownumbers: true,
        columns:[[
			{ field:'invoiceCode',width : '20%',align : "center",title:'发票代码' },
			{ field:'invoiceNumber',width : '20%',align : "center",title:'发票号码' },
			{ field:'total',width : '15%',align : "center",title:'发票金额' },
			{ field:'purchaseUnitName',width : '29%',align : "center",title:'购方单位名称' },
			{ field:'billingDate',width : '15%',align : "center",title:'开票时间' },
		]],
    });
    $("#dlg").show();
	$('#dlg').window({
		title:'开票明细',
		modal:true,
		minimizable:false,
		collapsible:false,
	});
}