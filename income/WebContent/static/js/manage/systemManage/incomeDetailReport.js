//初始化两非收入明细报表页面
$(function(){
	$.messager.defaults = { ok: "确定", cancel: "取消" };
    changeMonthType('q_month_begin');
    changeMonthType('q_month_end');
	$('#list_data').datagrid({
		title:'两非收入明细报表(注：报表中涉及金额的单位都为元)',  
		width: '100%',  
		height: 'auto',  
		nowrap: false,  
		striped: true,
		border: true, 
		fitColumns: true,
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的  
		fit: true,//自动大小  
		url:contextPath + '/twoNonReport/queryIncomeDetail.do',  
		remoteSort:false,   
		checkOnSelect :false,
		singleSelect:false,//是否单选  
		pagination:true,//分页控件  
		pageSize: 20,//每页显示的记录条数，默认为10
		pageList: [5,10,15,20],//可以设置每页记录条数的列表
		columns:[[
					{ field:'incomeManagerId',hidden:'true'},
					{ field:'cycle',width : '5%',align : "center",title:'业务账期' },
					{ field:'projectId',width : '5%',align : "center",title:'项目号' },
					{ field:'projectName',width : '15%',align : "center",title:'项目名称' },
		            { field:'estimateIncome',width : '10%',align : "center",title:'预估收入(含税)' },
		            { field:'estimateIncomeTax',width : '10%',align : "center",title:'税率' },
		            { field:'estimateExclusiveTax',width : '10%',align : "center",title:'预估收入(不含税)' },
//					{ field:'estimateExplain',width : '10%',align : "center",title:'预估收入说明',
//						formatter:function (value, data, index) {
//							if (!isNull(value)&& value.length>18) {
//								str=value.substring(0,18);
//								return '<span title="'+value+'"  class="easyui-tooltip">'+str+'</span>'
//								}else{
//									return value;
//								}
//							}
//					},
		            { field:'realIncome',width : '10%',align : "center",title:'实际收入(含税)' },
		            { field:'realIncomeTax',width : '10%',align : "center",title:'税率' },
		            { field:'realExclusiveTax',width : '10%',align : "center",title:'实际收入(不含税)' },
//					{ field:'realExplain',width : '10%',align : "center",title:'实际收入说明',
//						formatter:function (value, data, index) {
//							if (!isNull(value)&& value.length>10) {
//								str=value.substring(0,10);
//								return '<span title="'+value+'"  class="easyui-tooltip">'+str+'</span>'
//								}else{
//									return value;
//								}
//							}
//					},
					{ field:'offset',width : '10%',align : "center",title:'是否冲销预估收入'},
					{ field:'isneedBill',width : '10%',align : "center",title:'是否需要开票'},
					{ field:'billingKey',width : '10%',align : "center",title:'开票申请编号'},
					{ field:'billingType',width : '10%',align : "center",title:'开票类型'},
					{ field:'total',width : '10%',align : "center",title:'开票总金额'},
					{ field:'invoiceStatus',width : '10%',align : "center",title:'开票审核状态'},
					{ field:'income',width : '10%',align : "center",title:'实际收款'},
					{ field:'incomeDate',width : '10%',align : "center",title:'实际收款日期'},
					{ field:'incomeOptionUrl',width : '10%',align : "center",title:'实际收款附件'},
					{ field:'incomeStatus',width : '10%',align : "center",title:'实际收款状态'}
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
});

//重置搜索框
function reset(){
    $("#q_month_begin").datebox('setValue', '');
    $("#q_month_end").datebox('setValue', '');
    $("#q_projectId").textbox('setValue', '');
    $("#q_projectName").textbox('setValue', '');
}


//条件查询
function searchSubmit(){
    var q_month_begin = $('#q_month_begin').datebox('getValue');
    var q_month_end = $('#q_month_end').datebox('getValue');
    var q_projectId = $('#q_projectId').val();
    var q_projectName = $('#q_projectName').val();
	$('#list_data').datagrid('load', {
		q_month_begin : q_month_begin,
		q_month_end : q_month_end,
		q_projectId : q_projectId,
		q_projectName : q_projectName
	});
}

//导出excel
function exportExc(){
    var q_month_begin = $('#q_month_begin').datebox('getValue');
    var q_month_end = $('#q_month_end').datebox('getValue');
    var q_projectId = $('#q_projectId').val();
    var q_projectName = $('#q_projectName').val();
	$.messager.confirm('提示',"你确定要全量导出该查询结果吗？",function(r){
	    if (r)
	    {
	    	$.ajax({    
	    		dataType: "json",
	    	    data: {	
		    			q_month_begin : q_month_begin,
		    			q_month_end : q_month_end,
		    			q_projectId : q_projectId,
		    			q_projectName : q_projectName
	    	    	   },
	    	    type: "post",
	    	    url: contextPath+"/twoNonReport/exportIncomeDetail.do"
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