//初始化合同列表页面
$(function(){
	
	fomartAddMonthType('q_conStartTime');
	fomartAddMonthType('q_conEndTime');
	 $("#q_conNumber").textbox("textbox").attr("placeholder","年月日+6位流水");
	 $("#q_conNo").textbox("textbox").attr("placeholder","咪咕合同[年份]两位公司标识+5位流水");
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	$('#list_data').datagrid({
		title:'合同管理列表',  
		width: '100%',  
		height: 'auto',  
		nowrap: false,  
		striped: true,
		border: true, 
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的  
		fit: true,//自动大小  
		url:contextPath + '/contract/queryAllContract.do',  
		remoteSort:false,   
		checkOnSelect :false,
		singleSelect:false,//是否单选  
		pagination:true,//分页控件  
		pageSize: 20,//每页显示的记录条数，默认为10
		pageList: [5,10,15,20],//可以设置每页记录条数的列表
		columns:[[
					{ field:'ck',checkbox:true },
					{ field:'conId',hidden:'true'},
					{ field:'conName',hidden:'true'},
					{ field:'conBigType',hidden:'true'},
					{ field:'conCurrencyAmount',hidden:'true'},
					{ field:'conBankAbstrct',hidden:'true'},
					{ field:'conBankAccountNum',hidden:'true'},
					{ field:'conTaxQualified',hidden:'true'},
					{ field:'conTaxamount',hidden:'true'},
					{ field:'conNumber',width : '10%',align : "center",title:'合同流水号' },
					{ field:'conNo',width : '10%',align : "center",title:'合同编号' },
					{ field:'conCapitalNatureName',width : '10%',align : "center",title:'合同类型' },
					{ field:'conUndertakeDeptname',width : '10%',align : "center",title:'责任部门' },
					{ field:'conProjectContrctor',width : '10%',align : "center",title:'责任人' },
					{ field:'conStartTime',width : '8%',align : "center",title:'合同开始时间' },
					{ field:'conEndTime',width : '8%',align : "center",title:'合同结束时间' },
					{ field:'conTypePayrec',width : '10%',align : "center",title:'合同支付类型' },
					{ field:'conVendorId',width : '10%',align : "center",title:'供应商ID' },
					{ field:'conVendorName',width : '10%',align : "center",title:'供应商名称' }
				]],
				onLoadSuccess: function (data) {
		            if (data.Exception != null) {
		            	$.messager.alert('告警','合同接口数据查询异常，请检查！','warning');
		            }
		        },
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
});


//重置合同查询
function reset(){
	$("#q_conNumber").textbox('setValue','');
	$("#q_conNo").textbox('setValue','');
	$('#q_conStartTime').datetimebox('setValue','');
	$('#q_conEndTime').datetimebox('setValue','');
}

//合同查询
function searchSubmit(){
	var q_conNumber = $('#q_conNumber').val();
	var q_conNo = $('#q_conNo').val();
	var q_conStartTime = $('#q_conStartTime').datetimebox('getValue');
	var q_conEndTime = $('#q_conEndTime').datetimebox('getValue');
	
	if (q_conNumber.length>15){
		$.messager.alert('告警','合同流水号超过长度','warning');
		return false;
	}
	if (isNaN(q_conNumber)){
		$.messager.alert('告警','合同流水号只能为数字','warning');
		return false;
	}
	if(q_conNumber.indexOf('<') != -1 ||q_conNumber.indexOf('>') != -1 ||q_conNumber.indexOf('%') != -1 || q_conNumber.indexOf('_') != -1){
		$.messager.alert('告警','合同流水号不允许出现 %、_、<、> 等字符，请重新输入','warning');
		return false;
	}
	if (q_conNo.length>50){
		$.messager.alert('告警','合同流水号超过长度','warning');
		return false;
	}
	if($.trim(q_conStartTime)==""||q_conStartTime==null){
		$.messager.alert('告警','同步开始时间为必选项！','warning');
		return false;
	}
	if($.trim(q_conEndTime)==""||q_conEndTime==null){
		$.messager.alert('告警','同步结束时间为必选项！','warning');
		return false;
	}
	
	$('#list_data').datagrid('load', {
		q_conNumber : q_conNumber,
		q_conNo : q_conNo,
		q_conStartTime : q_conStartTime,
		q_conEndTime : q_conEndTime,
	});
}

function Contract(conId,conProjectContrctor,conUndertakeDeptname,conNo,conName,
		conNumber,conBigType,conStartTime,conEndTime,conTypePayrec,conCurrencyAmount,
		conVendorId,conVendorName,conBankAbstrct,conBankAccountNum,conTaxQualified,
		conTaxamount,conCapitalNatureName,projectId){
	
	this.conId = conId; 
	this.conProjectContrctor = conProjectContrctor; 
	this.conUndertakeDeptname = conUndertakeDeptname; 
	this.conNo = conNo; 
	this.conName = conName; 
	this.conNumber = conNumber; 
	this.conBigType = conBigType; 
	this.conStartTime = conStartTime; 
	this.conEndTime = conEndTime; 
	this.conTypePayrec = conTypePayrec; 
	this.conCurrencyAmount = conCurrencyAmount; 
	this.conVendorId = conVendorId; 
	this.conVendorName = conVendorName; 
	this.conBankAbstrct = conBankAbstrct; 
	this.conBankAccountNum = conBankAccountNum; 
	this.conTaxQualified = conTaxQualified; 
	this.conTaxamount = conTaxamount; 
	this.conCapitalNatureName = conCapitalNatureName; 
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
	var contractName="";
	var contracts = new Array();
	for (var i = 0; i < selRow.length; i++){
		var contract = new Contract(selRow[i].conId,selRow[i].conProjectContrctor,selRow[i].conUndertakeDeptname,
				selRow[i].conNo,selRow[i].conName,
				selRow[i].conNumber,selRow[i].conBigType,
				selRow[i].conStartTime,selRow[i].conEndTime,
				selRow[i].conTypePayrec,selRow[i].conCurrencyAmount,
				selRow[i].conVendorId,selRow[i].conVendorName,
				selRow[i].conBankAbstrct,selRow[i].conBankAccountNum,
				selRow[i].conTaxQualified,selRow[i].conTaxamount,
				selRow[i].conCapitalNatureName,projectId); 
		contracts.push(contract);
		
		if(i==selRow.length-1){
			contractName = contractName+selRow[i].conNo;
		}else{
			contractName = contractName+selRow[i].conNo+"、";
		}
		
	}
	console.log(contracts);
	
	$.messager.confirm('提示',"确定要与   "+contractName+"  合同绑定吗？",function(r){
		if (r){
			$.ajax({
			    dataType: "json",
			    data: JSON.stringify(contracts),
			    method: "post",
			    contentType:"application/json",   
			    url: contextPath + "/contract/doAssociatedContract.do"
			}).done(function(data){
			    if(data.reCode=="100"){
			    	$.messager.alert('提示',data.reStr);
			    	$("#list_data").datagrid('clearSelections').datagrid('clearChecked');
			    	searchSubmit();
			    }else{
			    	$.messager.alert('提示',data.reStr,'warning');
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
function fomartAddMonthType(id){
	var q_month = $('#'+id);
	q_month.datebox({
		closeText:'close',
        onShowPanel: function () {// 显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
                tds = p.find('td.calendar-day');
                $("div.datebox-button").css("display","none");
                tds.click(function (e) {
                	console.log(span.html());
                    e.stopPropagation(); // 禁止冒泡执行easyui给月份绑定的事件
                    var date = $(this).attr('abbr').replace(/,/g, "-")
                     q_month.datebox('hidePanel').datebox('setValue', date); // 设置日期的值
                    span.html(date);
                });
            yearIpt.unbind().hide();// 解绑年份输入框中任何事件
            yearSpan.hide();
            inputDate.hide();
        },
        parser: function (s) {
            if (!s) return new Date();
   
            var arr = s.split('-');
  
            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1,parseInt(arr[2], 10));
        },
        formatter: function (d) {
        	
        		return d.getFullYear() + '-' + (d.getMonth() + 1)+'-'+d.getDate();
        	}
	});
    var p = q_month.datebox('panel'), // 日期选择对象
        tds = false, // 日期选择对象中月份
        aToday = p.find('a.datebox-current'),
       yearIpt = p.find('input.calendar-menu-year'),// 年份输入框
        // 显示月份层的触发控件
       inputDate=p.find('span.textbox.easyui-fluid.spinner'),
       span = aToday.length ? p.find('div.calendar-title span') :// 1.3.x版本
       p.find('span.calendar-text'),
       yearSpan=p.find("div.calendar-menu-year-inner span"); // 1.4.x版本
        var _9b8=q_month.combo("panel");
        _9b8.unbind(".datebox").bind("click",function(e){
    	  p = q_month.datebox('panel');
    	  tds = p.find('td.calendar-day');
    	//  calendar-nav calendar-menu-month
    	  tds.click(function (e) {
    		  e.stopPropagation(); 
    		  var date = $(this).attr('abbr').replace(/,/g, "-")
    		  q_month.datebox('hidePanel').datebox('setValue', date); // 设置日期的值
    		  span.html(date);
    	  });
    	 
      });
}

