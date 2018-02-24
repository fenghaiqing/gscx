var incomeManagerId;
var selectedBillingType;
var viewHeight=undefined;
var bodyHeight=undefined;
var isFirst=true;
var status=1;
//初始化实际收入列表页面
$(function () {
    changeMonthType('q_month_begin');
    changeMonthType('q_month_end');
    changeMonthType('q_month_bill');
    typeData = [];
    typeData.push({"text": "开具成功", "id": '00', "selected": false});
    typeData.push({"text": "空白作废票", "id": '02', "selected": false});
    typeData.push({"text": "已开作废票", "id": '03', "selected": false});
    typeData.push({"text": "待开发票", "id": '11', "selected": false});
    typeData.push({"text": "开具中发票", "id": '12', "selected": false});
    typeData.push({"text": "未开票", "id": '99', "selected": false});
    $("#q_billState").combobox("loadData", typeData);
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

    $.messager.defaults = {ok: "确定", cancel: "取消"};
    $('#list_data').datagrid({
        title: '开票管理列表',
        width: '100%',
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        loadMsg: '正在加载',
        collapsible: false,//是否可折叠的
        fit: true,//自动大小
        url: contextPath + '/income/queryAllBilling.do',
        remoteSort: false,
        checkOnSelect :true,
        singleSelect:false,// 是否单选
        pagination: true,//分页控件
        pageSize: 20,//每页显示的记录条数，默认为10
        pageList: [5, 10, 15, 20],//可以设置每页记录条数的列表
        fitColumns:true,
        columns: [[
            { field:'ck',checkbox:true },
            {
                field: 'xh', width: '5%', align: "center", title: '序号', formatter: function (value, row, index) {
                return index + 1;
            }
            },
            {field: 'billingKey', width: '15%', align: "center", title: '开票申请编号'},
            {field: 'bzCycle', width: '10%', align: "center", title: '报账月份'},
            {field: 'cycle', width: '10%', align: "center", title: '业务账期'},
            {field: 'projectId', width: '10%', align: "center", title: '项目号'},
            {field: 'projectName', width: '10%', align: "center", title: '项目名称'},
            {field: 'className', width: '18%', align: "center", title: '收入大类'},
            {field: 'sectionName', width: '18%', align: "center", title: '收入小类'},
            {field: 'deptName', width: '10%', align: "center", title: '责任部门'},
            {field: 'userName', width: '10%', align: "center", title: '责任人'},
            {
                field: 'billType',
                width: '10%',
                align: "center",
                title: '开票类型',
                formatter: function (value, row, index) {
                    if (value == '0')
                        return '已申请专票';
                    if (value == '1')
                        return '已申请普票';
                }
            },
            { field:'total',width : '10%',align : "center",title:'开票总金额',formatter: function (value, row, index) {
            	
            	if(row.billingStatus=="00"){
            		if(value!=null){
    					return '<a href="javascript:void(0)" onclick="viewBillings('+"'"+row.billingKey+"'"+')" style="text-decoration:none">'+value+'</a>';
    				}
            	}else{
            		return value;
            	}
            } },
            {
                field: 'billingStatus',
                width: '10%',
                align: "center",
                title: '开票审核状态',
                formatter: function (value, data, index) {
                    var result = '';
                    switch (data.billingStatus) {
                        case '00' :
                            result = '开具成功';
                            break;
                        case '02' :
                            result = '空白作废票';
                            break;
                        case '03' :
                            result = '已开作废票';
                            break;
                        case '11' :
                            result = '待开发票';
                            break;
                        case '12' :
                            result = '开具中发票';
                            break;
                        case '99' :
                            result = '未开票';
                            break;
                    }
                    return result;
                }
            },
            {
                field: 'oper', width: '20%', align: "center", title: '操作', formatter: function (value, data, index) {
                	
               if(data.billingKey==null||data.billingKey==undefined){
            	   return '';
               }
                if(data.billingKey.indexOf("qt") >= 0){
                	return "已关联其他系统开票信息";
                }else{
                	var a = '<a href="javascript:void(0)" onclick="queryBilling(' + "'" + data.billingKey + "'" + ',' + data.billType + ')"  class="l-btn l-btn-plain"><span class="l-btn-left"><span class="l-btn-text icon-search l-btn-icon-left">详情</span></span></a>';
                    if(data.billingStatus==''||data.billingStatus==null){
                    	var b = '<a href="javascript:void(0)" onclick="deleteBilling(' + "'" + data.billingKey + "'" + ',' + data.projectId + ')"  class="l-btn l-btn-plain"><span class="l-btn-left"><span class="l-btn-text icon-remove l-btn-icon-left">删除</span></span></a>';
                    	return a+b;
                    }
                    return a;
                }	
                
            }
            }
        ]],
        onLoadSuccess: function (data) {
        var dept=	 data.dept;
	        if(dept=='1'){
	        	$("#list_data").datagrid("hideColumn", "oper"); // 设置隐藏列    
	        }
            $(".easyui-tooltip").tooltip({
                onShow: function () {
                    $(this).tooltip('tip').css({
                        borderColor: '#000'
                    });
                }
            });
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
        onBeforeRefresh: function () {
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


function askSubmit(i) {
    console.info(i);
    selectedBillingType = i;
    doSearch();
}
function drawBill(projectId, incomeManagerId, billingType) {
    var s = '';
    if (billingType == '0') s = '专票'; else s = '普票';
    $.messager.confirm('提示', "你确定要开" + s + "吗？", function (r) {
        if (r) {
            drawBillReal(projectId, incomeManagerId, billingType);
        }
    });
}

function drawBillReal(projectId, incomeManagerId, drawType) {
    //var url = 'http://117.185.122.248/eas/SsoMiGu?boeJump=tax%26sType=cz%26priKey=';
    var url = 'http://117.185.122.246/eas/SsoMiGu?boeJump=tax%26sType=cz%26priKey=';
    if (drawType == '1')
       //url = 'http://117.185.122.248/eas/SsoMiGu?boeJump=tax%26sType=cp%26priKey=';
        url = 'http://117.185.122.246/eas/SsoMiGu?boeJump=tax%26sType=cp%26priKey=';
    var updateKey = ""; //更新的key
    $.ajax({
        dataType: "json",
        async: false, //此处一定要同步操作
        data: {
            projectId: projectId,
            incomeManagerId: incomeManagerId,
            drawType: drawType
        },
        method: "post",
        url: contextPath + "/income/putBillKey.do"
    }).done(function (data) {
        if (data.code == "100") {
            updateKey = data.message;
        } else {
            $.messager.alert('提示', data.message, 'warning');
            return;
        }
    }).fail(function () {
        $.messager.alert('提示', "本次操作失败，请重新操作", 'error');
        return false;
    });

    $.ajax({
        dataType: "json",
        data: {
            url: url + updateKey
        },
        method: "post",
        url: contextPath + "/sso/getSsoUrl.do"
    }).done(function (data) {
        if (data.code == "100") {
            var openUrl = data.message;
            //更新一条记录
            $.ajax({
                dataType: "json",
                async: false, //此处一定要同步操作
                data: {
                    projectId: projectId,
                    incomeManagerId: incomeManagerId,
                    drawType: drawType,
                    seq: updateKey
                },
                method: "post",
                url: contextPath + "/income/putBillingRecord.do"
            }).done(function (data) {
                if (data.code == "100") {
                    searchSubmit();
                    window.open(openUrl);
                } else {
                    $.messager.alert('提示', data.message, 'error');
                }
            });
        } else {
            $.messager.alert('提示', data.message, 'warning');
        }
    }).fail(function () {
        $.messager.alert('提示', "本次操作失败，请重新操作", 'error');
        return false;
    });
}


function queryBilling(seq, billType) {
    //var url = 'http://117.185.122.248/eas/SsoMiGu?boeJump=tax%26sType=cz%26priKey=';
    var url = 'http://117.185.122.246/eas/SsoMiGu?boeJump=tax%26sType=cz%26priKey=';
    if (billType == '1')
        //url = 'http://117.185.122.248/eas/SsoMiGu?boeJump=tax%26sType=cp%26priKey=';
    	url = 'http://117.185.122.246/eas/SsoMiGu?boeJump=tax%26sType=cp%26priKey=';
    $.ajax({
        dataType: "json",
        data: {
            url: url + seq
        },
        method: "post",
        url: contextPath + "/sso/getSsoUrl.do"
    }).done(function (data) {
        if (data.code == "100") {
            searchSubmit();
            window.open(data.message);
        } else {
            $.messager.alert('提示', data.message, 'warning');
        }
    }).fail(function () {
        $.messager.alert('提示', "本次操作失败，请重新操作", 'error');
        return false;
    });

}

//重置实际收入查询
function reset() {
    $("#q_month_begin").datebox('setValue', '');
    $("#q_month_end").datebox('setValue', '');
    $("#q_projectId").textbox('setValue', '');
    $("#q_projectName").textbox('setValue', '');
    $('#q_billState').combobox('setValue', '');
    $("#q_month_bill").datebox('setValue', '');
    $('#q_incomeClassId').combobox('setValue', '');
    $('#q_incomeSectionId').combobox('setValue', '');
    $('#q_dept').combobox('setValue', '');
    $('#q_userName').textbox('setValue', '');
    $('#q_bill_num').textbox('setValue', '');
    $('#q_bill_total').numberbox('setValue', '');
    
    $('#list_data').datagrid('load', {
        q_month_begin: '',
        q_month_end: '',
        q_projectId: '',
        q_projectName: '',
        q_billState: '',
        q_month_bill:'',
        q_incomeClassId:'',
        q_incomeSectionId:'',
        q_dept:'',
        q_userName:'',
        q_bill_num:'',
        q_bill_total:''
    });
}


//进行查询
function searchSubmit() {
    var q_month_begin = $('#q_month_begin').datebox('getValue');
    var q_month_end = $('#q_month_end').datebox('getValue');
    
    var beginYear = q_month_begin.split("-")[0];
    var beginMonth = q_month_begin.split("-")[1];
    
    var endYear = q_month_end.split("-")[0];
    var endMonth = q_month_end.split("-")[1];
   
    if(!isNull(beginMonth)&&beginMonth.length==1){
    	beginMonth = "0"+beginMonth;
    }
    
    if(!isNull(endMonth)&&endMonth.length==1){
    	endMonth = "0"+endMonth;
    }
    
    if(parseInt(endYear+endMonth)<parseInt(beginYear+beginMonth)){
    	$.messager.alert('提示', "业务账期结束日期不能小于开始日期", 'warning');
    	return;
    }
    var q_projectId = $('#q_projectId').val();
    var q_projectName = $('#q_projectName').val();
    var q_billState = $('#q_billState').combobox('getValue');
    
    var q_month_bill= $("#q_month_bill").datebox('getValue');
    var q_incomeClassId= $('#q_incomeClassId').combobox('getValue');
    var q_incomeSectionId =$('#q_incomeSectionId').combobox('getValue');
    var q_dept = $('#q_dept').combobox('getValue');
    var q_userName = $('#q_userName').textbox('getValue');
    var q_bill_num = $('#q_bill_num').textbox('getValue');
    var q_bill_total = $('#q_bill_total').numberbox('getValue');
    
    $('#list_data').datagrid('load', {
        q_month_begin: q_month_begin,
        q_month_end: q_month_end,
        q_projectId: q_projectId,
        q_projectName: q_projectName,
        q_billState: q_billState,
        q_month_bill:q_month_bill,
        q_incomeClassId:q_incomeClassId,
        q_incomeSectionId:q_incomeSectionId,
        q_dept:q_dept,
        q_userName:q_userName,
        q_bill_num:q_bill_num,
        q_bill_total:q_bill_total
    });
}


// 查询项目
function doSearch() {
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
        url: contextPath + '/income/queryAllDrawBillIncome.do',
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
            {field: 'estimateExplain', width: '24%', align: "center", title: '说明'}
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
//重置项目查询
function searchProjectReset() {
    $("#searchProject_projectId").textbox('setValue', '');
    $("#searchProject_projectName").textbox('setValue', '');
}

// 项目查询
function searchProjectSubmit() {
    var q_projectId = $('#searchProject_projectId').val();
    var q_projectName = $('#searchProject_projectName').val();
    $('#projectName').val("");
    $('#list_data_searchProject').datagrid('load', {
        projectId: q_projectId,
        projectName: q_projectName,
    });
}
//确定
function selected() {
    var selRow = $("#list_data_searchProject").datagrid("getChecked");
    if (selRow.length != 1) {
        $.messager.alert('告警', '请选择一行数据!', 'warning');
        return false;
    }
    var selectedIncomeManagerId = selRow[0].incomeManagerId;
    var selectedProjectId = selRow[0].projectId;
    $('#searchProject').window('close');
    $('#searchProject').hide();
    drawBill(selectedProjectId, selectedIncomeManagerId, selectedBillingType);
}

//导出开票收入
function exportFile(){
	
	var selRows = $('#list_data').datagrid('getChecked'); 
	
	var billingKeys = [];
	for (var i = 0; i < selRows.length; i++){
		var billingKey = selRows[i].billingKey;
		billingKeys.push(billingKey);
	}
	
	var q_month_begin = $('#q_month_begin').datebox('getValue');
    var q_month_end = $('#q_month_end').datebox('getValue');
    
    var beginYear = q_month_begin.split("-")[0];
    var beginMonth = q_month_begin.split("-")[1];
    
    var endYear = q_month_end.split("-")[0];
    var endMonth = q_month_end.split("-")[1];
   
    if(!isNull(beginMonth)&&beginMonth.length==1){
    	beginMonth = "0"+beginMonth;
    }
    
    if(!isNull(endMonth)&&endMonth.length==1){
    	endMonth = "0"+endMonth;
    }
    
    if(parseInt(endYear+endMonth)<parseInt(beginYear+beginMonth)){
    	$.messager.alert('提示', "业务账期结束日期不能小于开始日期", 'warning');
    	return;
    }
    
    var q_projectId = $('#q_projectId').val();
    var q_projectName = $('#q_projectName').val();
    var q_billState = $('#q_billState').combobox('getValue');
	
    var q_month_bill= $("#q_month_bill").datebox('getValue');
    var q_incomeClassId= $('#q_incomeClassId').combobox('getValue');
    var q_incomeSectionId =$('#q_incomeSectionId').combobox('getValue');
    var q_dept = $('#q_dept').combobox('getValue');
    var q_userName = $('#q_userName').textbox('getValue');
    var q_bill_num = $('#q_bill_num').textbox('getValue');
    var q_bill_total = $('#q_bill_total').numberbox('getValue');
    
	$.messager.confirm('提示',"你确定要导出开票信息吗？",function(r){
	    if (r)
	    {
	    	$.ajax({
	    	    dataType: "json",
	    	    data: {"q_month_begin":q_month_begin,"q_month_end":q_month_end,"q_projectId":q_projectId,
	    	    	"q_projectName":q_projectName,"q_billState":q_billState,"billingKeys":billingKeys.toString()
	    	    	 ,'q_month_bill':q_month_bill,'q_incomeClassId':q_incomeClassId,'q_incomeSectionId':q_incomeSectionId,
	    	         'q_dept':q_dept,'q_userName':q_userName,'q_bill_num':q_bill_num,'q_bill_total':q_bill_total
	    	    },
	    	    method: "post",
	    	    url: contextPath+"/income/exportBillFile.do"
	    	}).done(function(data){
	    		if(data.reCode=="100"){
	    	    	$("#exportform1").form("submit", {
	    	            url : contextPath+"/income/downloadFile.do",
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


function deleteBilling(seq, projectId)
{
	$.messager.confirm('提示',"你确定要删除该开票记录吗？",function(r){
	    if (r)
	    {
	    	$.ajax({
	    	    dataType: "json",
	    	    data: {"seq":seq,"projectId":projectId},
	    	    method: "post",
	    	    url: contextPath+"/income/deleteBilling.do"
	    	}).done(function(data){
	    	    if(data.reCode=="100"){
	    	    	$.messager.alert('提示','删除成功！');
	    	    	searchSubmit();
	    	    }else{
	    	    	$.messager.alert('告警',data.reStr,'warning');
	    	    	searchSubmit();
	    	    }
	        }).fail(function(){
	        	$.messager.alert('告警',"本次操作失败，请重新操作",'error');
	    		return false;
	        });
	    }
    });
}

function viewBillings(billingKey) {  
    $("#billingDataDetail").datagrid({
    	url:contextPath+'/income/viewBillings.do?billingKey='+billingKey,  
        loadMsg: "数据加载中", 
        rownumbers: true,
        columns:[[
			{ field:'invoiceCode',width : '20%',align : "center",title:'发票代码' },
			{ field:'invoiceNumber',width : '20%',align : "center",title:'发票号码' },
			{ field:'billingDate',width : '20%',align : "center",title:'开票日期' },
			{ field:'purchaseUnitName',width : '20%',align : "center",title:'购方单位名称' },
			{ field:'total',width : '20%',align : "center",title:'发票金额' }
		]],
    })  
    $("#billingDataDetailDiv").show();
    $("#billingDataDetailDiv").window({
		title:'发票明细查看',
		modal:true,
		minimizable:false,
		collapsible:false,
	});
}; 