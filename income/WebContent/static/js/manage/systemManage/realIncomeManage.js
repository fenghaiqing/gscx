var editIndex = undefined;
var selectedProjectId;
var incomeManagerId;
var isSaved=false;
var viewHeight=undefined;
var bodyHeight=undefined;
var isFirst=true;
var status=1;
//初始化实际收入列表页面
$(function(){
	changeMonthType('q_month');
	changeMonthType('bzCycleReal');
	$('#realIncomeStatus').combobox('setValue','');
	$('#refreshRealIncomeDiv').hide();
	$('#editRealIncomeDiv').hide();
	$("#mergeInfo").hide();

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
			$(".smartSearch .l-btn-text").removeClass("icon-m-angle-up").addClass("icon-m-angle-down")
			 $(".datagrid-view").prop("style").height=(height-70)+"px";
			 $(".datagrid-body").css("height",(bdHeight-70)+"px");
			 status=2;
		 }else{
				$(".smartSearch .l-btn-text").removeClass("icon-m-angle-down").addClass("icon-m-angle-up")
			 $(".datagrid-view").prop("style").height=viewHeight;
			 $(".datagrid-body").css("height",bodyHeight);
			 status=1;
		 }
		
	 });
	
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	$('#list_data').datagrid({
		title:'实际收入管理列表',  
		width: '100%',  
		height: 'auto',  
		nowrap: false,  
		striped: true,
		border: true, 
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的  
		fit: true,//自动大小  
		url:contextPath + '/income/queryAllRealIncome.do',
		queryParams:{'q_estimateState':'2'},
		remoteSort:false,   
		checkOnSelect :true,
		singleSelect:false,// 是否单选
		pagination:true,//分页控件  
		pageSize: 20,//每页显示的记录条数，默认为10
		pageList: [5,10,15,20],//可以设置每页记录条数的列表
		columns:[[
					{ field:'ck',checkbox:true},
					{ field:'eastimateStatus',hidden:'true'},
		          	{ field:'incomeManagerId',hidden:'true'},
		          	{ field:'bzCycleReal',width : '5%',align : "center",title:'报账月份' },
					{ field:'cycle',width : '5%',align : "center",title:'业务账期' },
					{ field:'projectId',width : '5%',align : "center",title:'项目号' },
					{ field:'projectName',width : '15%',align : "center",title:'项目名称' },
					{ field:'className',width : '15%',align : "center",title:'收入大类' },
			        { field:'sectionName',width : '15%',align : "center",title:'收入小类' },
					{ field:'realIncome',width : '10%',align : "center",title:'实际收入(含税)',formatter: function (value, row, index) {
						if(value!=null){
							return '<a href="javascript:void(0)" onclick="viewDetails('+"'"+row.incomeManagerId+"'"+')" style="text-decoration:none">'+value+'</a>';
						}
		            } },
		            { field:'realIncomeTax',width : '10%',align : "center",title:'税率' },
		            { field:'realAmount',width : '10%',align : "center",title:'税额' },
		            { field:'realExclusiveTax',width : '10%',align : "center",title:'实际收入(不含税)' },
//					{ field:'realOptionsUrl',hidden:true },
					{ field:'realOptionsUrl',width : '5%',align : "center",title:'附件',formatter: function (value, row, index) {
						if(!isNull(row.realOptionsUrl)){
							return '<a href="javascript:void(0)" onclick="downloadAttach('+"'"+row.realOptionsUrl+"'"+')" style="text-decoration:none">下载</a>';
						}
		            } },
					{ field:'realStatus',width : '15%',align : "center",title:'实际收入状态',formatter:function(value,row, index){
						if(value=='0')
							return '草稿';
						if(value=='1')
							return '待审核'+'（'+row.auditPerson+'）';
						if(value=='2')
							return '审核通过';
						if(value=='3')
							return '驳回'
					}  },
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
					{ field:'realExplain',width : '10%',align : "center",title:'实际收入说明',
						formatter:function (value, data, index) {
							if (!isNull(value)&& value.length>10) {
								str=value.substring(0,10);
								return '<span title="'+value+'"  class="easyui-tooltip">'+str+'</span>'
								}else{
									return value;
								}
							}
					},
					{ field:'mergeStatus',width : '10%',align : "center",title:'是否合并',	
						formatter:function (value,row){
							if(value==='0'){
								return '未合并'
							}else if(value==='1'){
								return '<a href="javascript:void(0)" onclick="viewMergeInfo('+"'"+row.mergeId+"'"+')" style="text-decoration:none">已合并</a>'
							}else{
								return ''
							}
						}
					},
					{ field:'history',width : '10%',align : "center",title:'审核历史记录',formatter: function (value, row, index) {
						if(!isNull(row.realStatus)){
							return '<a href="javascript:void(0)" onclick="showHis('+"'"+row.incomeManagerId+"'"+')" style="text-decoration:none">查看</a>';
						}
		            } },
		            { field:'dellRealManager',width : '10%',align : "center",title:'操作', 
						formatter : function(value, rec, index) {
							
							if($('#currentUserId').val()=='0'||$('#currentUserDeptId').val()=='1'){
								var del = "";
							}else{
								if(rec.realStatus=='0'||rec.realStatus=='3'){
									var del = "<a href='javascript:void(0)'  onclick='dellRealManager("
										+ "\""+rec.incomeManagerId+"\","+ "\""+index+"\""
										+ ")' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-remove l-btn-icon-left'>删除</span></span></a>";
								}
							}
							return del;
						}
					},
		          	{ field:'auditDept',hidden:'true'},
		          	{ field:'auditPerson',hidden:'true'}
				]],
				onLoadSuccess: function(data){
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
	
	if($('#currentUserId').val()=='0'||$('#currentUserDeptId').val()=='1'){
		$('#list_data').datagrid('hideColumn', 'dellRealManager');
	}else{
		$('#list_data').datagrid('showColumn', 'dellRealManager');
	}
	
	
	
	
	$("#add_realIncome").numberbox({  
		  "onChange":function(){  
			var realIncome =$('#add_realIncome').numberbox('getValue');
			var add_realIncomeTax = $('#add_realIncomeTax').combobox('getValue');
			if(add_realIncomeTax!=''){
				realIncome = parseFloat(realIncome)
				var realExclusiveTax = parseFloat(realIncome)/(parseFloat(1)+parseFloat(add_realIncomeTax));
				$("#add_realExclusiveTax").numberbox("setValue",parseFloat(realExclusiveTax).toFixed(2));
				$("#add_realAmount").numberbox("setValue",realIncome-(parseFloat(realExclusiveTax).toFixed(2)));
			}
		  }  
	}); 
	
	
	
	$("#refresh_Real_Income").numberbox({  
		  "onChange":function(){  
			var realIncome =$('#refresh_Real_Income').numberbox('getValue');
			var add_realIncomeTax = $('#refresh_Real_IncomeTax').combobox('getValue');
			if(add_realIncomeTax!=''){
				realIncome = parseFloat(realIncome)
				var realExclusiveTax = parseFloat(realIncome)/(parseFloat(1)+parseFloat(add_realIncomeTax));
				$("#refresh_Real_ExclusiveTax").numberbox("setValue",parseFloat(realExclusiveTax).toFixed(2));
				$("#refresh_Real_Amount").numberbox("setValue",realIncome-(parseFloat(realExclusiveTax).toFixed(2)));
			}
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

//重置实际收入查询
function reset(){
	$("#q_month").datebox('setValue','');
	$("#q_projectId").textbox('setValue','');
	$("#q_projectName").textbox('setValue','');
	$('#realIncomeStatus').combobox('setValue','');
	$("#q_incomeClassId").combobox('setValue','');
	$("#q_incomeSectionId").combobox('setValue','');
	$("#q_dept").combobox('setValue','');
	$("#q_userName").textbox('setValue','');
	$("#est_income").numberbox('setValue','');
	$("#est_exclusive_tax").numberbox('setValue','');
	$("#q_cx").combobox('setValue','');
	$("#q_bill").combobox('setValue','');
	$("#q_merge").combobox('setValue','');
	$("#bzCycleReal").datebox('setValue','');
	
}


 function addChangeTax(id,value) {
		var realIncome =$('#add_realIncome').numberbox('getValue');
		if(realIncome==''){
//			$.messager.alert('告警','请先添加产品，总收入不能为空!','warning');
//			return;
		}else{
			realIncome = parseFloat(realIncome)
			var realExclusiveTax = parseFloat(realIncome)/(parseFloat(1)+parseFloat(id));
			$("#add_realExclusiveTax").numberbox("setValue",parseFloat(realExclusiveTax).toFixed(2));
			$("#add_realAmount").numberbox("setValue",realIncome-(parseFloat(realExclusiveTax).toFixed(2)));
		}
	}


 function editChangeTax (id,value) {
		var realIncome =$('#refresh_Real_Income').numberbox('getValue');
		if(realIncome==''){
//			$.messager.alert('告警','请先添加产品，总收入不能为空!','warning');
//			return;
		}else{
			realIncome = parseFloat(realIncome)
			var realExclusiveTax = parseFloat(realIncome)/(parseFloat(1)+parseFloat(id));
			$("#refresh_Real_ExclusiveTax").numberbox("setValue",parseFloat(realExclusiveTax).toFixed(2));
			$("#refresh_Real_Amount").numberbox("setValue",realIncome-(parseFloat(realExclusiveTax).toFixed(2)));
		}
	}


//实际收入查询
function searchSubmit(){
	var q_month = $('#q_month').datebox('getValue');
	var q_projectId = $('#q_projectId').val();
	var q_projectName = $('#q_projectName').val();
	var realIncomeStatus = $('#realIncomeStatus').combobox('getValue');
	var q_incomeClassId = $("#q_incomeClassId").combobox('getValue');
	var q_incomeSectionId = $("#q_incomeSectionId").combobox('getValue');
	var q_dept =$("#q_dept").combobox('getValue');
	var q_userName =$("#q_userName").val();
	var est_income =$("#est_income").val();
	var est_exclusive_tax = $("#est_exclusive_tax").val();
	var q_cx = $("#q_cx").combobox('getValue','');
	var q_bill = $("#q_bill").combobox('getValue','');
	var q_merge = $("#q_merge").combobox('getValue','');
	var bzCycleReal = $('#bzCycleReal').datebox('getValue');
	
	
	$('#list_data').datagrid('load', {
		q_month : q_month,
		q_projectId : q_projectId,
		q_projectName : q_projectName,
		q_estimateState : '2',
		realIncomeStatus:realIncomeStatus,
		q_incomeClassId:q_incomeClassId,
		q_incomeSectionId:q_incomeSectionId,
		q_dept:q_dept,
		q_userName:q_userName,
		est_income:est_income,
		est_exclusive_tax:est_exclusive_tax,
		q_cx:q_cx,
		q_bill:q_bill,
		q_merge:q_merge,
		bzCycleReal:bzCycleReal
	});
}

var products=new Array();

//刷新实际收入
function refreshRealIncome() {	
	var rows =$("#list_data").datagrid('getSelections');
	if(rows.length!=1){
		$.messager.alert('提示','请选择一条数据！','warning');
		return ;
	}
	var row = rows[0];
	if(rows[0].realStatus =='1' || rows[0].realStatus =='2'){
		$.messager.alert("提示","请选择草稿或驳回状态的数据！");
		return ;
	}
	
	if(!isNull(row.realIncome)){
		$("#refresh_Real_Income").numberbox('setValue',row.realIncome);
	}else{
		$("#refresh_Real_Income").numberbox('setValue','');
	}
	
	$("#refresh_real_month").html(row.cycle);
	$("#refresh_real_project_Id").html(row.projectId);
	$("#refresh_real_project_Name").html(row.projectName);
	$("#incomeManagerId").val(row.incomeManagerId);
	$("#refresh_projectId").val(row.projectId);
	
	$("#refresh_Real_ExclusiveTax").numberbox('setValue',rows[0].realExclusiveTax)
	$("#refresh_Real_Amount").numberbox('setValue',rows[0].realAmount)
	
	if(rows[0].realIncomeTax==0){
		$('#refresh_Real_IncomeTax').combobox('setValue',"0.00");
	}else{
		$('#refresh_Real_IncomeTax').combobox('setValue',rows[0].realIncomeTax);
	}
	
	if(row.offset==='1'){
		$("#updBack").prop("checked", true);
	}
	
	if(!isNull(row.isNeedBill)){
//		$(":radio[name=billFlag2][value="+row.isNeedBill+"]").attr("checked","true");
		$(":radio[name='billFlag2'][value='" + row.isNeedBill + "']").prop("checked", "checked");
	}
	
	if(!isNull(row.realOptionsUrl)){
		$("#old_file").val(row.realOptionsUrl)
		var start=row.realOptionsUrl.lastIndexOf("/");
		$("#fileURL").html(row.realOptionsUrl.substring(start+1));
	}else{
		$("#old_file").val('')
		$("#fileURL").html('');
	}
	if(isNull(row.eastimateStatus)){
		$("#cxtitle").hide();
		$("#cxIpt").hide();
	}else{
		$("#cxtitle").show();
		$("#cxIpt").show();
	}
	
	
	
	if(!isNull(row.realExplain)){
		$("#handlingSuggestion").textbox('setValue',row.realExplain);
	}else{
		$("#handlingSuggestion").textbox('setValue','');
	}
	
	$('#refreshRealIncomeTable').datagrid({
		loadMsg : '正在加载',
		width: '100%',  
		height: '36%',
		url : contextPath + '/income/viewRealIncomeDetails.do',
		queryParams : {
			incomeManagerId : row.incomeManagerId
		},
		columns : [ [{
			field : 'productName',
			width : '30%',
			align : 'center',
			title : '产品',
			editor : {type:'combobox',options:{valueField:'priceConfigInfoId',textField:'productName',data:products}}
		}, {
			field : 'priceConfigInfoId',
			hidden:true,
			editor:'textbox'
		},  {
			field : 'sellingPrice',
			width : '30%',
			align : 'center',
			title : '售价',
			editor : {
				type : 'numberbox',
				options : {
					precision : 2
				}
			}
		}, {
			field : 'porductNumber',
			width : '30%',
			align : 'center',
			title : '件数',
			editor : 'numberbox'
		}, {
			field : 'incomeDetailId',
			hidden:true
		},
		 {
				field : 'password',
				width : '10%',
				align : "center",
				title : '操作',
				formatter:function(value,row,index){
					return "<a href='javascript:void(0)' onclick='removeit(this)' style='text-decoration:none'>删除</a>";
				}
			}
		] ],
		singleSelect: false,
		selectOnCheck: true,
		checkOnSelect: true,
		toolbar : '#tb_refreshRealIncome'
	});
	$("#opration").val("refresh");
	
	$("#handlingSuggestion").textbox("textbox").attr("placeholder","1000个字以内！");
	
	$('#Audit_dept').combobox({
	    url:contextPath+"/actualIncome/queryAllDep.do",
	    valueField:'deptCode',
	    textField:'deptName',
	    editable:false
	});
	$('#Audit_dept').combobox('setValue','');
	$('#Audit_person').combobox('setValue','');	
	// 加载下拉框 弹出窗口
	$("#refreshRealIncomeDiv").show();
	$('#refreshRealIncomeDiv').window({
		title : '更新实际收入',
		width : '85%',
		height : '95%',
		modal : true,
		minimizable : false,
		collapsible : false,
	    onBeforeClose: function () { //当面板关闭之前触发的事件
	    	editIndex = undefined;
	    	$("#handlingSuggestion").textbox("setValue","");
	    	return true;
         }
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


function endEditing(id) {
	if (editIndex == undefined) {
		return true
	}
	if ($('#'+id).datagrid('validateRow', editIndex)) {
		var ed = $('#'+id).datagrid('getEditor', {
			index : editIndex,
			field : 'productName'
		});
		if(ed){
			var text = $(ed.target).combobox('getText');
			$(ed.target).combobox('setValue',text);
		}
		
		$('#'+id).datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function onClickRow(index) {
	var option= $("#opration").val();
	var cycle=$("#refresh_real_month").html();
	var projectId=$("#refresh_projectId").val();
	var tbId ="refreshRealIncomeTable";
	var row = $('#'+tbId).datagrid('getRows')[index];
	$('#'+tbId).datagrid('beginEdit', index);
	var ed=$('#'+tbId).datagrid("getEditor",{index:index,field:"productName"})
	if(ed){
		$(ed.target).combobox("reload",contextPath + '/priceConfig/selectProduct.do?projectId='+projectId+"&cycle="+cycle);
		$(ed.target).combobox({
			editable:false,
			onSelect:function(){
			var value =	$(ed.target).combobox('getValue');
			var text = $(ed.target).combobox('getText');
			$(ed.target).combobox('setValue',text);
			var edit = $('#'+tbId).datagrid("getEditor",{index:editIndex,field:"priceConfigInfoId"});
			if(edit){
				$(edit.target).textbox("setValue",value);
			}
			}
		});
		$(ed.target).combobox('setValue',row.priceConfigInfoId);
	}
	var sellPriceEdit=$('#'+tbId).datagrid("getEditor",{index:index,field:"sellingPrice"});
	if(sellPriceEdit){
		$(sellPriceEdit.target).numberbox({
			onChange:function(newValue,oldValue){
				nowSellPrice=newValue;
				$(sellPriceEdit.target).numberbox('setValue',newValue);
				var rows = $('#'+tbId).datagrid('getRows');
				var totalPrice=0.0;
				for(var i =0;i<rows.length;i++){
					if(i!=index){
						totalPrice +=(parseFloat(rows[i].sellingPrice * rows[i].porductNumber));
					}
				}
				
				totalPrice+=(newValue * $(porductNumberEdit.target).numberbox('getValue'));
				$("#refresh_Real_Income").numberbox("setValue",totalPrice.toFixed(2));
			}
		});
	}
	
	var porductNumberEdit=$('#'+tbId).datagrid("getEditor",{index:index,field:"porductNumber"});
	if(porductNumberEdit){
		$(porductNumberEdit.target).numberbox({
			onChange:function(newValue,oldValue){
				noeProductNumber=newValue;
				$(porductNumberEdit.target).numberbox('setValue',newValue);
				var rows = $('#'+tbId).datagrid('getRows');
				var totalPrice=0.0;
				for(var i =0;i<rows.length;i++){
					if(i!=index){
						totalPrice +=(parseFloat(rows[i].sellingPrice * rows[i].porductNumber));
					}
				}
				totalPrice+=(newValue * $(sellPriceEdit.target).numberbox('getValue'));
				$("#refresh_Real_Income").numberbox("setValue",totalPrice.toFixed(2));
			}
		});
	}
	if (editIndex != index) {
		if (endEditing(tbId)) {
			$('#'+tbId).datagrid('selectRow', index).datagrid(
					'beginEdit', index);
			editIndex = index;
		} else {

			$('#'+tbId).datagrid('selectRow', editIndex);
		}
	}
}

function onClickAddRow(index) {
	var option= $("#opration").val();
	var cycle=undefined;
	var tbId =undefined;
		cycle= $("#add_month").datebox("getValue");
		tbId="addRealIncomeTable";
	if(isNull(cycle)){
		$.messager.alert("提示","请选择业务账期！","warning");
		return ;
	}
	var row = $('#'+tbId).datagrid('getRows')[index];
	$('#'+tbId).datagrid('beginEdit', index);
	var ed=$('#'+tbId).datagrid("getEditor",{index:index,field:"productName"});
	var textEdit=$('#'+tbId).datagrid("getEditor",{index:index,field:"priceConfigInfoId"});
	if(ed){
		$(ed.target).combobox("reload",contextPath + '/priceConfig/selectProduct.do?projectId='+selectedProjectId+"&cycle="+cycle);
		
		$(ed.target).combobox({
			editable:false,
			onSelect:function(){
			var value =	$(ed.target).combobox('getValue');
			var text = $(ed.target).combobox('getText');
			$(ed.target).combobox('setValue',text);
			var edit = $('#'+tbId).datagrid("getEditor",{index:editIndex,field:"priceConfigInfoId"});
			if(edit){
				$(edit.target).textbox("setValue",value);
			}
			}
		});
		if(textEdit){
			var priceConfigInfoId=$(textEdit.target).textbox("getValue");
			if(!isNull(priceConfigInfoId)){
				$(ed.target).combobox('setValue',priceConfigInfoId);
			}
			if(!isNull(row.priceConfigInfoId)){
				$(ed.target).combobox('setValue',row.priceConfigInfoId);
			}
		}
		
	}
	var sellPriceEdit=$('#'+tbId).datagrid("getEditor",{index:index,field:"sellingPrice"});
	if(sellPriceEdit){
		$(sellPriceEdit.target).numberbox({
			onChange:function(newValue,oldValue){
				nowSellPrice=newValue;
				$(sellPriceEdit.target).numberbox('setValue',newValue);
				var rows = $('#'+tbId).datagrid('getRows');
				var totalPrice=0.0;
				for(var i =0;i<rows.length;i++){
					if(i!=index){
						totalPrice +=(parseFloat(rows[i].sellingPrice * rows[i].porductNumber));
					}
				}
				
				totalPrice+=(newValue * $(porductNumberEdit.target).numberbox('getValue'));
				$("#add_realIncome").numberbox("setValue",totalPrice.toFixed(2));
			}
		});
	}
	
	var porductNumberEdit=$('#'+tbId).datagrid("getEditor",{index:index,field:"porductNumber"});
	if(porductNumberEdit){
		$(porductNumberEdit.target).numberbox({
			onChange:function(newValue,oldValue){
				noeProductNumber=newValue;
				$(porductNumberEdit.target).numberbox('setValue',newValue);
				var rows = $('#'+tbId).datagrid('getRows');
				var totalPrice=0.0;
				for(var i =0;i<rows.length;i++){
					if(i!=index){
						totalPrice +=(parseFloat(rows[i].sellingPrice * rows[i].porductNumber));
					}
				}
				totalPrice+=(newValue * $(sellPriceEdit.target).numberbox('getValue'));
				$("#add_realIncome").numberbox("setValue",totalPrice.toFixed(2));
			}
		});
	}
	if (editIndex != index) {
		if (endEditing(tbId)) {
			$('#'+tbId).datagrid('selectRow', index).datagrid(
					'beginEdit', index);
			editIndex = index;
		} else {

			$('#'+tbId).datagrid('selectRow', editIndex);
		}
	}
}

function removeitAddRow(target) {
	var rowIndex = getRowIndex(target);
	var option= $("#opration").val();
	var tbId =undefined;
	if(option=='update'){
		tbId="editEstimateIncomeTable"
	}else{
		tbId="addRealIncomeTable";
	}
	$('#'+tbId).datagrid('endEdit', rowIndex);
	var row =$('#'+tbId).datagrid("getRows")[rowIndex];
		$.messager.confirm('提示', "确定要删除这条数据？", function(r) {
			if(r){
				var realIncome =$("#add_realIncome").numberbox('getValue');
				var price = (parseFloat(realIncome)-parseFloat(row.sellingPrice * row.porductNumber)).toFixed(2);
				$("#add_realIncome").numberbox('setValue',price);
					$('#'+tbId).datagrid('deleteRow', rowIndex);
				editIndex = undefined;
			}else{
				$('#'+tbId).datagrid('beginEdit', rowIndex)
			}
		});

}




function append() {
	var option= $("#opration").val();
	var cycle=$("#refresh_real_month").html();
	var projectId=$("#refresh_projectId").val();
	var tbId ="refreshRealIncomeTable";
	if (endEditing(tbId)) {
		$('#'+tbId).datagrid('appendRow', {
			state : 'P'
		});
		editIndex = $('#'+tbId).datagrid('getRows').length - 1;
		$('#'+tbId).datagrid('selectRow', editIndex).datagrid(
				'beginEdit', editIndex);
		var ed=$('#'+tbId).datagrid("getEditor",{index:editIndex,field:"productName"})
		if(ed){
			$(ed.target).combobox("reload",contextPath + '/priceConfig/selectProduct.do?projectId='+projectId+"&cycle="+cycle)
			$(ed.target).combobox({
				editable:false,
				onSelect:function(){
				var value =	$(ed.target).combobox('getValue');
				var text = $(ed.target).combobox('getText');
				$(ed.target).combobox('setValue',text);
				var edit = $('#'+tbId).datagrid("getEditor",{index:editIndex,field:"priceConfigInfoId"});
				if(edit){
					$(edit.target).textbox("setValue",value);
				}
				}
			});
		}
		var sellPriceEdit=$('#'+tbId).datagrid("getEditor",{index:editIndex,field:"sellingPrice"});
		if(sellPriceEdit){
			$(sellPriceEdit.target).numberbox({
				onChange:function(newValue,oldValue){
					nowSellPrice=newValue;
					$(sellPriceEdit.target).numberbox('setValue',newValue);
					var rows = $('#'+tbId).datagrid('getRows');
					var totalPrice=0.0;
					for(var i =0;i<rows.length;i++){
						if(i!=editIndex){
							totalPrice +=(parseFloat(rows[i].sellingPrice * rows[i].porductNumber));
						}
					}
					totalPrice+=(newValue * $(porductNumberEdit.target).numberbox('getValue'));
					$("#refresh_Real_Income").numberbox("setValue",totalPrice.toFixed(2));
				}
			});
		}
		
		var porductNumberEdit=$('#'+tbId).datagrid("getEditor",{index:editIndex,field:"porductNumber"});
		if(porductNumberEdit){
			$(porductNumberEdit.target).numberbox({
				onChange:function(newValue,oldValue){
					noeProductNumber=newValue;
					$(porductNumberEdit.target).numberbox('setValue',newValue);
					var rows = $('#'+tbId).datagrid('getRows');
					var totalPrice=0.0;
					for(var i =0;i<rows.length;i++){
						if(i!=editIndex){
							totalPrice +=(parseFloat(rows[i].sellingPrice * rows[i].porductNumber));
						}
					}
					totalPrice+=(newValue * $(sellPriceEdit.target).numberbox('getValue'));
					$("#refresh_Real_Income").numberbox("setValue",totalPrice.toFixed(2));
				}
			});
		}
	}
}

function removeit(target) {
	var rowIndex = getRowIndex(target);
	var tbId ="refreshRealIncomeTable";
	$('#'+tbId).datagrid('endEdit', rowIndex);
	var row =$('#'+tbId).datagrid("getRows")[rowIndex];
		$.messager.confirm('提示', "确定要删除这条数据？", function(r) {
			if(r){
				if(isNull(row.incomeDetailId)){
					var realIncome =$("#refresh_Real_Income").numberbox('getValue');
					var price = (parseFloat(realIncome)-parseFloat(row.sellingPrice * row.porductNumber)).toFixed(2);
					$("#refresh_Real_Income").numberbox('setValue',price);
					$('#'+tbId).datagrid('deleteRow', rowIndex);
				}else{
					$.ajax({
						url:contextPath + '/incomeManager/delEstimateIncome.do',
						data:{"incomeDetailId":row.incomeDetailId,"type":"refresh"},
						type:"post",
						success:function(){
							var realIncome =$("#refresh_Real_Income").numberbox('getValue');
							var price = (parseFloat(realIncome)-parseFloat(row.sellingPrice * row.porductNumber)).toFixed(2);
							$("#refresh_Real_Income").numberbox('setValue',price);
							$('#'+tbId).datagrid('deleteRow', rowIndex);
						},
						error:function(){
							$.messager.alert("提示","删除失败！","warning");
						}
					});
				}
				editIndex = undefined;
			}else{
				$('#'+tbId).datagrid('beginEdit', rowIndex)
			}
		});
}

//取消
function dismisExamine(id){
	var tbId =id;
	$('#'+tbId).window('close');			
}


//保存
function doSubmit(val) {
		var ed = $('#refreshRealIncomeTable').datagrid('getEditor', {
			index : editIndex,
			field : 'productName'
		});
		if(ed){
			var text = $(ed.target).combobox('getText');
			$(ed.target).combobox('setValue',text);
		}
		$('#refreshRealIncomeTable').datagrid('endEdit', editIndex);
		var formData = new FormData($("#refreshRealIncomeForm")[0]);
		var realIncome =$("#refresh_Real_Income").val();
		var file = $("#add_file").filebox("getValue");
		var oldfile = $("#old_file").val();
		var incomeManagerId =$("#incomeManagerId").val();
		var realExplain=$("#handlingSuggestion").textbox("getValue");
		var dept = $('#Audit_dept').combobox('getValue');
		var person = $('#Audit_person').combobox('getValue');
		var billFlag2= $("input[name='billFlag2']:checked").val();
		var offset ="0";
		if($("#updBack").prop("checked")){
			offset=$("#updBack").val();
		}
		if(isNull(realIncome)){
			$.messager.alert("提示","实际总收入不能为空！")
			return;
		}
		if(isNull(file)&&isNull(oldfile)){
			$.messager.alert("提示","请选择附件！")
			return;
		}
		if(isNull(billFlag2)){
			$.messager.alert("提示","请选择是否需要开票！")
			return;
		}
		var rows = $('#refreshRealIncomeTable').datagrid('getRows');
		if(rows.length<1){
			$.messager.alert("提示","请添加实际收入明细！")
			return;
		}
		if(realExplain.length>1000){
			$.messager.alert("提示","实际收入说明在1000字以内！")
			return;
		}
		var datas = new Array();
		var total = 0;
		for(var i = 0;i<rows.length;i++){
			var item={
					sellingPrice:null,
					priceConfigInfoId:null,
					porductNumber:null,
					type:"R",
					incomeManagerId:incomeManagerId,
					incomeDetailId:null,
			}
			if(isNull(rows[i].priceConfigInfoId)){
				$.messager.alert("提示","产品不能为空！")
				return;
			}
			if(isNull(rows[i].sellingPrice)){
				$.messager.alert("提示","售价不能为空！")
				return;
			}else if((""+rows[i].sellingPrice).indexOf(".")>16){
				$.messager.alert("提示","售价不能大于16位数！")
				return;
			}
			if(isNull(rows[i].porductNumber)){
				$.messager.alert("提示","件数不能为空！")
				return;
			}else if(rows[i].porductNumber<=0){
				$.messager.alert("提示","件数必须大于0！")
				return;
			}else if(rows[i].porductNumber.length>16){
				$.messager.alert("提示","件数不能大于16位数！")
				return;
			}
			total+=parseFloat(rows[i].sellingPrice * rows[i].porductNumber);
			item.priceConfigInfoId=rows[i].priceConfigInfoId;
			item.sellingPrice=rows[i].sellingPrice;
			item.porductNumber=rows[i].porductNumber;
			item.incomeDetailId=rows[i].incomeDetailId;
			datas.push(item);
		}
		total=total.toFixed(2);
		if(total!=realIncome){
			$.messager.alert("提示","实际收入明细总额不等于实际总收入！")
			return;
		}
		//等于1时为审核
		if(val==1){
			if(isNull(dept)){
				$.messager.alert("提示","审核部门不能为空！")
				return;
			}
			if(isNull(person)){
				$.messager.alert("提示","审核人不能为空！")
				return;
			}
		}
		formData.append("list",JSON.stringify(datas));
		formData.append("status",val);
		formData.append("offset",offset);
		formData.append("billFlag2",billFlag2);
		
		$("#btn-item-edit button").attr("disabled","disabled");
		$.ajax({
			url : contextPath + '/incomeManager/refreshIncome.do',
			type : "post",
			data : formData,
			processData : false,
			contentType : false,
			success : function(data, status) {
				if (data.status == 1) {
								$.messager.alert('提示', data.msg);
								$("#add_file").filebox('setValue','');
								$("#refreshRealIncomeDiv").window('close');
								$("#btn-item-edit button").removeAttr("disabled");
								searchSubmit();
								return
							}else{
								$.messager.alert('提示', data.msg);
								$("#btn-item-edit button").removeAttr("disabled");
							}		
			},
			error : function() {
					$.messager.alert('告警', '刷新实际收入失败！', 'warning');
					$("#btn-item-edit button").removeAttr("disabled");
			}
		});
	
}

//下载模板文件
function downloadFile() {
	var fileURL=$("#old_file").val();
	$("#downloadForm").form("submit", {
		url : contextPath + "/incomeManager/downLoad.do",
		data:{"fileURL":fileURL},
		onSubmit : function(param) {
			return this;
		},
		success : function(result) {
			if (null != result && "" != result) {
				$.messager.alert('提示', result, 'warning');
			}
		}
	});
}



function getRowIndex(target) {
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}

//审核人查询
$(document).ready(function () {
		$("#Audit_dept").combobox({
			onChange: function (n,o) {
				var deptId = $('#Audit_dept').combobox('getValue');
				$('#Audit_person').combobox('setValue','');
				 $.ajax({  
					    type: "POST",  
					    url:contextPath+"/actualIncome/queryDepPersonByRole.do",  
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





//查询项目
function doSearch(){
	searchProjectReset();
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	$('#list_data_searchProject').datagrid({
		width: '70%',  
		height: '70%',  
		nowrap: false,
		queryParams:{projectId : null,
			projectName : null},
		striped: true,
		border: true, 
		loadMsg: '正在加载',
		collapsible:false,// 是否可折叠的
		fit: true,// 自动大小
		url:contextPath + '/project/queryAllProjectByUser.do',  
		remoteSort:false,   
		checkOnSelect :true,
		singleSelect:true,// 是否单选
		pagination:true,// 分页控件
		pageSize: 20,// 每页显示的记录条数，默认为10
		pageList: [5,10,15,20],// 可以设置每页记录条数的列表
		columns:[[
					{ field:'ck',checkbox:true},
					{ field:'projectId',width : '10%',align : "center",title:'项目号' },
					{ field:'projectName',width : '20%',align : "center",title:'项目名称' },
					{ field:'projectDeptName',width : '15%',align : "center",title:'责任部门' },
					{ field:'projectUserName',width : '17%',align : "center",title:'责任人' },
					{ field:'comments',width : '29%',align : "center",title:'说明' }
				]],
		toolbar: '#tb_searchProject'
	});  
	
	// 设置分页控件
	var p = $('#list_data_searchProject').datagrid('getPager');
	$(p).pagination({  
		beforePageText: '第',// 页数文本框前显示的汉字
		afterPageText: '页    共 {pages} 页',  
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
		onBeforeRefresh:function(){ 
			$(this).pagination('loading'); 
			$(this).pagination('loaded'); 
		}
	});
	
	$('#searchProject').show();
	$('#searchProject').window({
		title:'查询项目',
 	width:'70%',
		height:'70%',
		modal:true,
		minimizable:false,
		collapsible:false
	});
}


//项目查询
function searchProjectSubmit(){
	var q_projectId = $('#searchProject_projectId').val();
	var q_projectName = $('#searchProject_projectName').val();
	$('#projectName').val("");
	$('#list_data_searchProject').datagrid('load', {
		projectId : q_projectId,
		projectName : q_projectName,
	});
}

//重置项目查询
function searchProjectReset(){
	$("#searchProject_projectId").textbox('setValue','');
	$("#searchProject_projectName").textbox('setValue','');
}
var products=new Array();
//确定项目
function selected(){
	var selRow = $("#list_data_searchProject").datagrid("getChecked");
	if (selRow.length != 1) {
		$.messager.alert('告警','请选择一行数据!','warning');
		return false;
	}
	selectedProjectId = selRow[0].projectId;
	$("#projectName").val(selRow[0].projectName);
	$("#projectId").val(selectedProjectId);
	$('#searchProject').window('close');
	$('#searchProject').hide();
	addEstimateIncome();
}


//新增
function addEstimateIncome() {
	fomartAddMonthType('add_month');
	var estimateIncome =$("#add_GeneralIncome").val("");
	$("#add_GeneralIncome").numberbox('setValue',"")
	
	$("#add_realIncome").numberbox('setValue',"")
	$('#add_realIncomeTax').combobox('setValue','');
	$("#add_realExclusiveTax").numberbox('setValue',"")
	$("#add_realAmount").numberbox('setValue',"")
	
	$("#handlingSuggestion").textbox("setValue","");
	$("#add_real_file").textbox("setValue","");
	$('#addRealIncomeTable').datagrid({
		loadMsg : '正在加载',
		width: '100%',  
		height: '40%',
		url : contextPath + '/income/viewIncomeDetails.do',
		queryParams : {
			incomeManagerId : '0'
		},
		columns : [ [{
			field : 'productName',
			width : '10%',
			align : 'center',
			title : '产品',
			editor : {type:'combobox',options:{valueField:'priceConfigInfoId',textField:'productName',data:products}}
		}, {
			field : 'priceConfigInfoId',
			hidden:true,
			editor:'textbox'
		},  {
			field : 'sellingPrice',
			width : '10%',
			align : 'center',
			title : '售价',
			editor : {
				type : 'numberbox',
				options : {
					precision : 2
				}
			}
		}, {
			field : 'porductNumber',
			width : '20%',
			align : 'center',
			title : '件数',
			editor : 'numberbox'
		},
		 {
				field : 'password',
				width : '10%',
				align : "center",
				title : '操作',
				formatter:function(value,row,index){
					return "<a href='javascript:void(0)' onclick='removeitAddRow(this)' style='text-decoration:none'>删除</a>";
				}
			}
		] ],
		singleSelect: false,
		selectOnCheck: true,
		checkOnSelect: true,
		toolbar : '#tb_addEstimateIncome'
	});
	$("#opration").val("add");
	// 加载下拉框 弹出窗口
	$("#addRealIncomeDiv").show();
	$('#addRealIncomeDiv').window({
		title : '新增实际收入',
		width : '90%',
		height : '80%',
		modal : true,
		minimizable : false,
		collapsible : false,
		onBeforeClose:function(){
			editIndex=undefined;
			$("#add_month").datebox('setValue','');
			$("#realIncomeExplain").textbox("setValue","");
		}
	});
	var time1 = new Date().format("yyyy-MM");
	 $("#add_month").datebox("setValue",time1);
	 $("#handlingSuggestion").textbox("textbox").attr("placeholder","1000个字以内！");
	 loadDepartment("auditDept","auditPerson");
}

function loadDepartment(dept,person){
	$('#'+dept).combobox({
	    url:contextPath+"/actualIncome/queryAllDep.do",
	    valueField:'deptCode',
	    textField:'deptName',
	    editable:false,
	    onChange: function (n,o) {
			var deptId = $('#'+dept).combobox('getValue');
			$('#'+person).combobox('setValue','');
			 $.ajax({  
				    type: "POST",  
				    url: contextPath+"/actualIncome/queryDepPersonByRole.do",  
				    data: {deptId: deptId},  
				    dataType: "json",  
				    success: function(response){// 调用成功
				    var options=$("#"+person).combobox('options');  
				    options.textField="userName";// 必须要和数据库查询的字段一样(大小写敏感)
				    options.valueField="userId";  
				    options.editable = false;
				// 加载数据
				$("#"+person).combobox("loadData",response);
				    },  
				 }); 
			
		}
	});
	$("#"+person).combobox({
		onShowPanel:function(){
			var deptId = $('#'+dept).combobox('getValue');
			if(isNull(deptId)){
				$.messager.alert("提示","请先选择部门！");
				$('#'+person).combo("hidePanel")
			}
		}
	});
}


function fomartAddMonthType(id){
	var q_month = $('#'+id);
	q_month.datebox({
		closeText:'close',
        onShowPanel: function () {// 显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
            span.trigger('click'); // 触发click事件弹出月份层
            // fix 1.3.x不选择日期点击其他地方隐藏在弹出日期框显示日期面板
            if (p.find('div.calendar-menu').is(':hidden')) p.find('div.calendar-menu').show();
            if (!tds){// 延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                tds = p.find('div.calendar-menu-month-inner td');
                tds.click(function (e) {
                    e.stopPropagation(); // 禁止冒泡执行easyui给月份绑定的事件
                    var year = /\d{4}/.exec(span.html())[0]// 得到年份
                    , month = parseInt($(this).attr('abbr'), 10); // 月份，这里不需要+1
                    q_month.datebox('hidePanel')// 隐藏日期对象
                    .datebox('setValue', year + '-' + month); // 设置日期的值
                });
            }
            yearIpt.unbind().hide();// 解绑年份输入框中任何事件
            yearSpan.hide();
        },
        parser: function (s) {
            if (!s) return new Date();
            var arr = s.split('-');
            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
        },
        formatter: function (d) {
        	if(lastDate!=undefined
        			&&lastDate!=(d.getFullYear() + '-' + (d.getMonth() + 1))){
        				onChangeDate();
        	}
        		lastDate=d.getFullYear() + '-' + (d.getMonth() + 1);
        		checkUnque(d.getFullYear() + '-' + (d.getMonth() + 1),selectedProjectId);
        		return d.getFullYear() + '-' + (d.getMonth() + 1);
        	}
	});
    var p = q_month.datebox('panel'), // 日期选择对象
        tds = false, // 日期选择对象中月份
        aToday = p.find('a.datebox-current'),
       yearIpt = p.find('input.calendar-menu-year'),// 年份输入框
        // 显示月份层的触发控件
        span = aToday.length ? p.find('div.calendar-title span') :// 1.3.x版本
       p.find('span.calendar-text'),
       yearSpan=p.find("div.calendar-menu-year-inner span"); // 1.4.x版本
    if (aToday.length) {// 1.3.x版本，取消Today按钮的click事件，重新绑定新事件设置日期框为今天，防止弹出日期选择面板
        aToday.unbind('click').click(function () {
            var now=new Date();
            q_month.datebox('hidePanel').datebox('setValue', now.getFullYear() + '-' + (now.getMonth() + 1));
        });
    }
    
}

function onChangeDate(){
	$("#add_realIncome").numberbox('setValue',"")
	$('#add_realIncomeTax').combobox('setValue','');
	$("#add_realExclusiveTax").numberbox('setValue',"")
	$("#add_realAmount").numberbox('setValue',"")
	
	  $("#addRealIncomeTable").datagrid("reload",{incomeManagerId : '0'});
	  editIndex = undefined;
}

function checkUnque(cycle,projectId){
	$.ajax({
		url:contextPath + '/incomeManager/selectRealIncomeByUnique.do',
		type : "post",
		data : {"cycle":cycle,"projectId":projectId},
		success:function(data){
			if(!isNull(data)){
			// $.messager.alert("提示","该项目当前月份已新增实际收入！");
				$("#add_month").next().css("border","1px solid red")
				isSaved=true;
				return ;
			}else{
				isSaved=false;	
				$("#add_month").next().css("border","1px solid #95B8E7")
			}
		}
	})
}
function addRow() {
	var option= $("#opration").val();
	var cycle=undefined;
	var tbId =undefined;
	if(option=='update'){
		cycle= $("#edit_month").textbox("getValue");
		tbId="editEstimateIncomeTable"
	}else{
		cycle= $("#add_month").datebox("getValue");
		tbId="addRealIncomeTable";
	}
	if(isNull(cycle)){
		$.messager.alert("提示","请选择业务账期！","warning");
		return ;
	}
	if (endEditing(tbId)) {
		$('#'+tbId).datagrid('appendRow', {
			state : 'P'
		});
		editIndex = $('#'+tbId).datagrid('getRows').length - 1;
		$('#'+tbId).datagrid('selectRow', editIndex).datagrid(
				'beginEdit', editIndex);
		var ed=$('#'+tbId).datagrid("getEditor",{index:editIndex,field:"productName"})
		if(ed){
			$(ed.target).combobox("reload",contextPath + '/priceConfig/selectProduct.do?projectId='+selectedProjectId+"&cycle="+cycle)
			$(ed.target).combobox({
				editable:false,
				onSelect:function(){
				var value =	$(ed.target).combobox('getValue');
				var text = $(ed.target).combobox('getText');
				$(ed.target).combobox('setValue',text);
				var edit = $('#'+tbId).datagrid("getEditor",{index:editIndex,field:"priceConfigInfoId"});
				if(edit){
					$(edit.target).textbox("setValue",value);
				}
				}
			});
		}
		var sellPriceEdit=$('#'+tbId).datagrid("getEditor",{index:editIndex,field:"sellingPrice"});
		if(sellPriceEdit){
			$(sellPriceEdit.target).numberbox({
				onChange:function(newValue,oldValue){
					nowSellPrice=newValue;
					$(sellPriceEdit.target).numberbox('setValue',newValue);
					var rows = $('#'+tbId).datagrid('getRows');
					var totalPrice=0.0;
					for(var i =0;i<rows.length;i++){
						if(i!=editIndex){
							totalPrice +=(parseFloat(rows[i].sellingPrice * rows[i].porductNumber));
						}
					}
					totalPrice+=(newValue * $(porductNumberEdit.target).numberbox('getValue'));
					$("#add_realIncome").numberbox("setValue",totalPrice.toFixed(2));
				}
			});
		}
		
		var porductNumberEdit=$('#'+tbId).datagrid("getEditor",{index:editIndex,field:"porductNumber"});
		if(porductNumberEdit){
			$(porductNumberEdit.target).numberbox({
				onChange:function(newValue,oldValue){
					noeProductNumber=newValue;
					$(porductNumberEdit.target).numberbox('setValue',newValue);
					var rows = $('#'+tbId).datagrid('getRows');
					var totalPrice=0.0;
					for(var i =0;i<rows.length;i++){
						if(i!=editIndex){
							totalPrice +=(parseFloat(rows[i].sellingPrice * rows[i].porductNumber));
						}
					}
					totalPrice+=(newValue * $(sellPriceEdit.target).numberbox('getValue'));
					$("#add_realIncome").numberbox("setValue",totalPrice.toFixed(2));
				}
			});
		}
	}
}
//保存
function saveRealIncome(val) {
	
		var ed = $('#addRealIncomeTable').datagrid('getEditor', {
			index : editIndex,
			field : 'productName'
		});
		if(ed){
			var text = $(ed.target).combobox('getText');
			$(ed.target).combobox('setValue',text);
		}
		$('#addRealIncomeTable').datagrid('endEdit', editIndex);
		
		var formData = new FormData($("#addRealIncomeForm")[0]);
		var addRealIncome =$("#add_realIncome").val();
		var cycle = $("#add_month").datebox("getValue");
		var file = $("#add_real_file").filebox("getValue");
		var auditDepartment = $('#auditDept').combobox("getValue");
		var auditPerson=$('#auditPerson').combobox("getValue");
		var realIncomeExplain = $("#realIncomeExplain").textbox("getValue");
		var add_realIncomeTax=$('#add_realIncomeTax').combobox("getValue");
		var billFlag= $("input[name='billFlag']:checked").val();
		var offset="0";
		if($("#toback").prop('checked')){
			 offset=$("#toback").val();
		} 
		if(isSaved){
			$.messager.alert("提示","该项目当前业务账期已新增实际收入或存在未审核通过的预估收入！");
			return ;
		}
		if(isNull(cycle)){
			$.messager.alert("提示","业务账期不能为空！")
			return;
		}
		if(isNull(add_realIncomeTax)){
			$.messager.alert("提示","税率不能为空！")
			return;
		}
		if(isNull(addRealIncome)){
			$.messager.alert("提示","实际总收入不能为空！")
			return;
		}
		if(isNull(file)){
			$.messager.alert("提示","请选择附件！")
			return;
		}
		if(isNull(billFlag)){
			$.messager.alert("提示","请选择是否需要开票！")
			return;
		}
		if(val==1){
			if(isNull(auditDepartment)){
				$.messager.alert("提示","部门不能为空！");
				return;
			}
			if(isNull(auditPerson)){
				$.messager.alert("提示","审核人不能为空！");
				return ;
			}
		}
		var rows = $('#addRealIncomeTable').datagrid('getData').rows;
		if(rows.length<1){
			$.messager.alert("提示","请添加实际收入明细！")
			return;
		}
		if(realIncomeExplain.length>1000){
			$.messager.alert("提示","实际收入说明不能超过1000个字！")
			return;
		}
		var datas = new Array();
		var total = 0;
		for(var i = 0;i<rows.length;i++){
			var item={
					sellingPrice:null,
					priceConfigInfoId:null,
					porductNumber:null,
					type:"R",
					incomeManagerId:null,
					cycle:cycle
			}
			if(isNull(rows[i].priceConfigInfoId)){
				$.messager.alert("提示","产品不能为空！")
				return;
			}
			if(isNull(rows[i].sellingPrice)){
				$.messager.alert("提示","售价不能为空！")
				return;
			}else if((""+rows[i].sellingPrice).indexOf(".")>16){
				$.messager.alert("提示","售价不能大于16位数！")
				return;
			}
			if(isNull(rows[i].porductNumber)){
				$.messager.alert("提示","件数不能为空！")
				return;
			}else if(rows[i].porductNumber<=0){
				$.messager.alert("提示","件数必须大于0！")
				return;
			}else if(rows[i].porductNumber.length>16){
				$.messager.alert("提示","件数不能大于16位数！")
				return;
			}
			total+=parseFloat(rows[i].sellingPrice * rows[i].porductNumber);
			item.priceConfigInfoId=rows[i].priceConfigInfoId;
			item.sellingPrice=rows[i].sellingPrice;
			item.porductNumber=rows[i].porductNumber;
		
			datas.push(item);
		}
		total=total.toFixed(2);
		if(total!=addRealIncome){
			$.messager.alert("提示","实际收入明细总额不等于实际总收入！")
			return;
		}
		formData.append("list",JSON.stringify(datas));
		formData.append("status",val);
		formData.append("offset",offset);
		formData.append("billFlag",billFlag);
		$("#btn-item-add button").attr("disabled","disabled");
		$.ajax({
			url : contextPath + '/incomeManager/addRealIncome.do',
			type : "post",
			data : formData,
			processData : false,
			contentType : false,
			success : function(data, status) {
				if (data.status == 1) {
						$.messager.alert('提示', "操作成功！");
						$("#add_real_file").filebox('setValue','');
						$("#addRealIncomeDiv").window('close');
						$("#btn-item-add button").removeAttr("disabled");
						searchSubmit();
						return
				}else{
					$("#add_real_file").filebox('setValue','');
					$.messager.alert('提示', data.msg);
					$("#btn-item-add button").removeAttr("disabled");
				}		
			},
			error : function() {
				$("#add_real_file").filebox('setValue','');
					$.messager.alert('告警', '新增实际收入失败！', 'warning');
					$("#btn-item-add button").removeAttr("disabled");
			}
		});
	
};


var lastDate= undefined;

//审核历史查询
function showHis(incomeManagerId) {  
    $("#hisData").datagrid({
    	url:contextPath+'/incomeManager/showHis.do?incomeManagerId='+incomeManagerId+"&type=R",  
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
	if(!isNull(incomeManagerId)){
		$("#prt").attr("href",contextPath+"/incomeManager/printRealPage.do?incomeManagerId="+incomeManagerId);
	}else{
		$("#prt").attr("href","javascript:void(0)");
	}

}; 
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

/*************start 导入产品信息**************/
function importProduct(){
	var ed = $('#addRealIncomeTable').datagrid('getEditor', {
		index : editIndex,
		field : 'productName'
	});
	if(ed){
		var text = $(ed.target).combobox('getText');
		$(ed.target).combobox('setValue',text);
	}
	$('#addRealIncomeTable').datagrid('endEdit', editIndex);
	$("#import-product").show();
	$('#import-product').window({
		title : '导入产品信息',
		width : '30%',
		height : '40%',
		modal : true,
		minimizable : false,
		collapsible : false
	});
}

function editImportProduct(){
	var ed = $('#refreshRealIncomeTable').datagrid('getEditor', {
		index : editIndex,
		field : 'productName'
	});
	if(ed){
		var text = $(ed.target).combobox('getText');
		$(ed.target).combobox('setValue',text);
	}
	$('#refreshRealIncomeTable').datagrid('endEdit', editIndex);
	$("#import-product-edit").show();
	$('#import-product-edit').window({
		title : '导入产品信息',
		width : '30%',
		height : '40%',
		modal : true,
		minimizable : false,
		collapsible : false
	});
}


function cancel(){
	$("#product-file").filebox('setValue','');
	$('#import-product').window("close");
	return true;
}
function cancelEdit(){
	$("#product-file-edit").filebox('setValue','');
	$('#import-product-edit').window("close");
	return true;
}
//确认导入文件
function submitProductData(){
	var projectId=$("#projectId").val();
	var fileName = $("#product-file").filebox("getValue");
	var oldData = $("#addRealIncomeTable").datagrid('getData').rows;
	var add_month =$("#add_month").datebox("getValue");
	if(isNull(add_month)){
		$.messager.alert("提示","请选择业务账期！","warning");
		return ;
	}
	if(isNull(fileName)){
		$.messager.alert("提示","请选择文件！","warning");
		return ;
	}
	var endstr =fileName.substr(fileName.lastIndexOf("."))
	
	if(".xlsx"!=endstr &&".xls"!=endstr){
		$.messager.alert("提示","文件格式不正确！请选择Excel格式的文件！","warning");
		return ;
	}
	var formData =  new FormData($("#import-product-form")[0]);
	formData.append("projectId",projectId);
	formData.append("month",add_month);
	cancel();
	if(oldData.length>0){
		$.messager.defaults={ok:"保留",cancel:"覆盖"};
		$.messager.confirm('确认导入','是否保留原有产品信息?',function(r){
		    if (r){
		    	formData.append("oldData",JSON.stringify(oldData));

		    		loadData(formData,projectId,oldData.length,"addRealIncomeTable","add_realIncome");
		    }else{
		    		loadData(formData,projectId,0,"addRealIncomeTable","add_realIncome");
		    }
		});
	}else{
		loadData(formData,projectId,0,"addRealIncomeTable","add_realIncome");
	}
	
}

function submitProductInfo(){
	var projectId=$("#refresh_projectId").val();
	var fileName = $("#product-file-edit").filebox("getValue");
	var oldData = $("#refreshRealIncomeTable").datagrid('getData').rows;
	var add_month =$("#refresh_real_month").html();
	if(isNull(add_month)){
		$.messager.alert("提示","请选择业务账期！","warning");
		return ;
	}
	if(isNull(fileName)){
		$.messager.alert("提示","请选择文件！","warning");
		return ;
	}
	var endstr =fileName.substr(fileName.lastIndexOf("."))
	
	if(".xlsx"!=endstr &&".xls"!=endstr){
		$.messager.alert("提示","文件格式不正确！请选择Excel格式的文件！","warning");
		return ;
	}
	var formData =  new FormData($("#import-product-edit-form")[0]);
	formData.append("projectId",projectId);
	formData.append("month",add_month);
	 cancelEdit()
	if(oldData.length>0){
		 formData.append("oldData",JSON.stringify(oldData));
		
		   loadData(formData,projectId,oldData.length,"refreshRealIncomeTable","refresh_Real_Income");
	}else{
			loadData(formData,projectId,0,"refreshRealIncomeTable","refresh_Real_Income");
		 
	}
	
}

function loadData(formData,projectId,rows,id,pid){
	$.ajax({
		url : contextPath + '/income/importIncomeDetails.do',
		type : "post",
		data : formData,
		processData : false,
		contentType : false,
		async: false,
		success : function(data, status) {
			//cancel();
			if(data.error!=null){
				$.messager.alert("提示",data.error,"error");
				return ;
			}
			if(data!=null){
				if(data.failData!=null&&data.failData.length>0){
					
					var msg = data.failData.substr(0,data.failData.lastIndexOf(","))
					$.messager.alert("提示","成功导入"+(data.list != null?(data.list.length-rows):0)+"条产品信息，产品编号（"+msg+"）的数据导入失败！<br>在项目"+projectId+"下未找到相应的产品信息!","warning"); 
				}else if(data.list!=null&&data.list.length>0){
					$.messager.alert("提示","导入成功！共导入"+(data.list.length-rows)+"条产品信息。");
				}
				if(data.list!=null&&data.list.length>0){
					$('#'+id).datagrid('loadData',data.list);
					var totalprice = 0.0;
					for(var i=0;i<data.list.length;i++){
						totalprice+=parseFloat(data.list[i].sellingPrice) * parseFloat(data.list[i].porductNumber)
					}
					$("#"+pid).numberbox("setValue",totalprice.toFixed(2));
					//$("#"+pid).numberbox("setValue",totalprice.toFixed(2));
				}
			}else{
				$.messager.alert("提示","导入失败！","error");
			}
			return ;
		},
		error: function(e){
			$.messager.alert("提示","导入失败！","error");
		}
	})
}

function downLoadExcel(type){
	var projectId="";
	var month="";
	
	if(type=='update'){
		projectId=$("#refresh_projectId").val();
		month=$("#refresh_real_month").html();
	}else{
		projectId=$("#projectId").val();
		month=$("#add_month").datebox('getValue');
	}
	var queryParam={
			'projectId':projectId,'today':month	
	}
		  $.ajax({    
		    		dataType: "json",
		    	    data: JSON.stringify(queryParam),
		    	    type: "post",
		    	    url: contextPath+"/income/downLoadExcel.do",
		    	    contentType:"application/json"
		    	}) .done(function(data){
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

function downLoad(){
	$("#exportform2").form("submit", {
        url : contextPath+"/income/downLoad.do",
        onSubmit : function(param) {
            return this;
        }
    });
}

//删除实际收入
function dellRealManager(incomeManagerId,index)
{
	var row = $('#list_data').datagrid('getData').rows[index];
	var projectName = row.projectName;
	var projectId = row.projectId;
	var cycle = row.cycle
	
	$.messager.confirm('提示',"确认删除"+projectName+"项目"+cycle+"的实际收入吗?",function(r){
	    if (r)
	    {
			$.ajax({
			    dataType: "json",
			    data: {"incomeManagerId":incomeManagerId,"projectName":projectName,"cycle":cycle,"projectId":projectId},
			    method: "post",
			    url: contextPath+"/income/dellRealManager.do"
			}).done(function(data){
			    if(data.reCode=="100"){
			    	$.messager.alert('提示','操作成功！');
			    	$("#list_data").datagrid('clearSelections').datagrid('clearChecked');
			    	searchSubmit();
			    }else{
			    	$.messager.alert('提示',data.reStr,'warning');
			    	searchSubmit();
			    }
		    }).fail(function(){
		    	$.messager.alert('提示',"本次操作失败，请重新操作",'error');
				return false;
		    });
	    }
    });
}


/**************end 导入产品信息********************/

//导出实际收入
function exportFile(){
	
	var selRows = $('#list_data').datagrid('getChecked'); 
	
	var incomeManagerIds = [];
	for (var i = 0; i < selRows.length; i++){
		var incomeManagerId = selRows[i].incomeManagerId;
		incomeManagerIds.push(incomeManagerId);
	}
	
	var q_month = $('#q_month').datebox('getValue');
	var q_projectId = $('#q_projectId').val();
	var q_projectName = $('#q_projectName').val();
	var realIncomeStatus = $('#realIncomeStatus').combobox('getValue');
	var q_incomeClassId = $("#q_incomeClassId").combobox('getValue');
	var q_incomeSectionId = $("#q_incomeSectionId").combobox('getValue');
	var q_dept =$("#q_dept").combobox('getValue');
	var q_userName =$("#q_userName").val();
	var est_income =$("#est_income").val();
	var est_exclusive_tax = $("#est_exclusive_tax").val();
	var q_cx = $("#q_cx").combobox('getValue','');
	var q_bill = $("#q_bill").combobox('getValue','');
	var q_merge = $("#q_merge").combobox('getValue','');
	var bzCycleReal = $("#bzCycleReal").combobox('getValue','');
	$.messager.confirm('提示',"你确定要导出实际收入吗？",function(r){
	    if (r)
	    {
	    	$.ajax({
	    	    dataType: "json",
	    	    data: {q_month:q_month,
	    	    	q_projectId:q_projectId,
	    	    	q_projectName:q_projectName,
	    	    	realIncomeStatus:realIncomeStatus,
	    	    	incomeManagerIds:incomeManagerIds.toString(),
	    	    	q_incomeClassId:q_incomeClassId,
	    			q_incomeSectionId:q_incomeSectionId,
	    			q_dept:q_dept,
	    			q_userName:q_userName,
	    			est_income:est_income,
	    			est_exclusive_tax:est_exclusive_tax,
	    			q_cx:q_cx,
	    			q_bill:q_bill,
	    			q_merge:q_merge,
	    			bzCycleReal:bzCycleReal
	    	    },
	    	    method: "post",
	    	    url: contextPath+"/income/realIncomeExportFile.do"
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

function viewMergeInfo(id){
	$('#merge_Info_data').datagrid({
		width: '100%',  
		height: 'auto',  
		nowrap: false,  
		striped: true,
		border: true, 
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的  
		fit: true,//自动大小  
		url:contextPath + '/mergeIncome/viewMergeInfo.do',
		queryParams:{id:id},
		remoteSort:false,   
		checkOnSelect :true,
		columns:[[
					{ field:'cycle',width : '10%',align : "center",title:'业务账期' },
					{ field:'projectId',width : '10%',align : "center",title:'项目号' },
					{ field:'projectName',width : '25%',align : "center",title:'项目名称' },
					{ field:'realIncome',width : '15%',align : "center",title:'实际收入(含税)',
						 formatter:function(val,rowData,rowIndex){
		            	        if(val!=null)
		            	            return val.toFixed(2);
		            	   }},
		            { field:'realIncomeTax',width : '10%',align : "center",title:'税率' },
		            { field:'realAmount',width : '10%',align : "center",title:'税额',
		            	 formatter:function(val,rowData,rowIndex){
		            	        if(val!=null)
		            	            return val.toFixed(2);
		            	   }},
		   
		            { field:'realExclusiveTax',width : '18%',align : "center",title:'实际收入(不含税)',
		            	 formatter:function(val,rowData,rowIndex){
		            	        if(val!=null)
		            	            return val.toFixed(2);
		            	   }},
				]]
	}); 
	$("#mergeInfo").show();
	$('#mergeInfo').window({
		title : '合并后',
		width : '70%',
		height : '40%',
		modal : true,
		minimizable : false,
		collapsible : false
	});
}
