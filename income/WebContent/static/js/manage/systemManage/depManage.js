$(function(){
	$('#editDep').hide();
	$('#addDep').hide();
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	//datagrid初始化  
	$('#list_data').datagrid({
		title:'部门维护列表',  
		iconCls:'icon-edit',//图标  
		width: 770,  
		height: 'auto',  
		nowrap: false,  
		striped: true,
		border: true, 
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的  
		fit: true,//自动大小  
		url:contextPath + '/manage/queryAllDepList.do',  
		remoteSort:false,   
		idField:'deptCode', 
		checkOnSelect :false,
		singleSelect:false,//是否单选  
		pagination:true,//分页控件  
		rownumbers:true,//行号
		pageSize: 20,//每页显示的记录条数，默认为10
		pageList: [5,10,15,20],//可以设置每页记录条数的列表
		columns:[[
			{ field:'deptCode',width : 200,align : "center",title:'部门编号' },
			{ field:'deptName',width : 200,align : "center",title:'部门名称' },
			{ field:'deptDescribe',width : 400,align : "center",title:'部门描述' },
			{field : 'opt',width : 200,align : "center",title : '操作',
				formatter : function(value, rec, index) {
					if(rec.deptCode=='1'){
						return " ";
					}else{
						var a = "<a href='javascript:void(0)'  onclick='edit("
							+ "\"" + rec.deptCode +"\""+",\"" + rec.deptName + "\"," +"\"" +rec.deptDescribe+ "\""  
							+ ")' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit l-btn-icon-left'>修改</span></span></a>";
						var b = "<a href='javascript:void(0)'  onclick='dell("
							+ "\""+rec.deptCode+"\""
							+ ")' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-remove l-btn-icon-left'>删除</span></span></a>";
						return a + b;
					}
				}
			}
		]],
		toolbar: '#tb'
	});  
	
	//设置分页控件  
	var p = $('#list_data').datagrid('getPager');  
	$(p).pagination({  
		//pageSize: 10,//每页显示的记录条数，默认为10  
		//pageList: [5,10,15,20],//可以设置每页记录条数的列表  
		beforePageText: '第',//页数文本框前显示的汉字  
		afterPageText: '页    共 {pages} 页',  
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
		onBeforeRefresh:function(){ 
			$(this).pagination('loading'); 
			// alert('before refresh'); 
			$(this).pagination('loaded'); 
		}
	});
	
});

function addDep(){	
	$("#add_deptCode").textbox('setValue','');
	$("#add_deptName").textbox('setValue','');
	$("#add_deptDescribe").textbox('setValue','');
	$('#addDep').show();
	$('#addDep').window({
		title:'新增部门',
    	width:420,
		height:250,
		modal:true,
		minimizable:false,
		collapsible:false
	});
}

/**
 * 修改部门
 * @param deptCode
 * @param deptName
 * @param deptDescribe
 */
function edit(deptCode,deptName,deptDescribe){
	$("#edit_deptCode").textbox('setValue',deptCode);
	$('#edit_deptCode').combobox('disable');//不可编辑
	$("#edit_deptName").textbox('setValue',deptName);
	if(deptDescribe=="null"){
		deptDescribe = "";
	}
	$("#edit_deptDescribe").textbox('setValue',deptDescribe);
	$('#editDep').show();
	$('#editDep').window({
		title:'修改部门',
    	width:420,
		height:250,
		modal:true,
		minimizable:false,
		collapsible:false
	});
}

// 单个删除
function dell(deptCode)
{
	$.messager.confirm('提示',"你确定要删除选择的部门吗？",function(r){
	    if (r)
	    {
			$.ajax({
			    dataType: "json",
			    data: {"deptCode":deptCode},
			    method: "post",
			    url: contextPath+"/manage/deleteDep.do"
			}).done(function(data){
			    if(data.reCode=="100"){
			    	$.messager.alert('提示','操作成功！');
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

/**
 * 新增部门
 */
function saveDep()
{	
	var reg = /.*\..*/;
	var deptCode = $('#add_deptCode').val();
	var deptName = $('#add_deptName').val();
	var deptDescribe = $('#add_deptDescribe').val();
	if ($.trim(deptCode) == ""){
		$.messager.alert('告警','请输入部门编号','warning');
		return false;
	}else if(isNaN(deptCode)){
		$.messager.alert('告警','部门编号只允许数字','warning');
    	return false;
    }else if(reg.test(deptCode)){
		$.messager.alert('告警','部门编号不允许存在小数点','warning');
    	return false;
    }else if($.trim(deptCode).length>10){
		$.messager.alert('告警','部门编号不能超过10位','warning');
    	return false;
    }
	if($.trim(deptName) == ""){
		$.messager.alert('告警','请输入部门名称','warning');
		return false;
	}
	if (deptName.length>100){
		$.messager.alert('告警','部门名称超过长度','warning');
		return false;
	}
	if(deptName.indexOf('<') != -1 ||deptName.indexOf('>') != -1 ||deptName.indexOf('%') != -1 || deptName.indexOf('_') != -1){
		$.messager.alert('告警','部门名称不允许出现 %、_、<、> 等字符，请重新输入','warning');
		return false;
	}
	
	lock ++;
	if(lock > 1){
		alert('保存中，请稍后...');
		return;
	}
	
	// 请求后台	
	$.ajax({
	    dataType: "json",
	    data: {"deptCode":deptCode,"deptName":deptName,"deptDescribe":deptDescribe},
	    method: "post",
	    url: contextPath+"/manage/addDep.do"
	}).done(function(data){
		lock = 0;
	    if(data.reCode=="100"){
	    	$.messager.alert('提示',data.reStr);
	    	$('#addDep').window('close');
	    	$('#list_data').datagrid('load',{});
	    }else{
	    	$.messager.alert('提示',data.reStr,'warning');
	    }
    }).fail(function(){
    	lock = 0;
    	$.messager.alert('告警',"本次操作失败，请重新操作",'error');
		return false;
    });
}

/**
 * 修改部门
 */
function modDep(){
	var deptCode = $('#edit_deptCode').val();
	var deptName = $('#edit_deptName').val();
	var deptDescribe = $('#edit_deptDescribe').val();
	if ($.trim(deptName) == ""){
		$.messager.alert('告警','请输入部门名称','warning');
		return false;
	}
	if (deptName.length>100){
		$.messager.alert('告警','部门名称超过长度','warning');
		return false;
	}
	if(deptName.indexOf('<') != -1 ||deptName.indexOf('>') != -1 ||deptName.indexOf('%') != -1 || deptName.indexOf('_') != -1){
		$.messager.alert('告警','用户名称不允许出现 %、_、<、> 等字符，请重新输入','warning');
		return false;
	}
	lock ++;
	if(lock > 1){
		alert('保存中，请稍后...');
		return;
	}
	$.ajax({
	    dataType: "json",
	    data: {"deptCode":deptCode,"deptName":deptName,"deptDescribe":deptDescribe},
	    method: "post",
	    url: contextPath+"/manage/updateDep.do"
	}).done(function(data){
		lock = 0;
	    if(data.reCode=="100"){
	    	$.messager.alert('提示',data.reStr);
	    	$('#editDep').window('close');
	    	$('#list_data').datagrid('load',{});
	    }else{
	    	$.messager.alert('提示',data.reStr,'warning');
	    }
    }).fail(function(){
    	lock = 0;
    	$.messager.alert('提示',"本次操作失败，请重新操作",'error');
		return false;
    });
}

function closeWindow(win)
{
	$("#" + win+ "").window('close');
}

// 查询
function searchSubmit(){
	var deptName = $('#deptName').val();
	$('#list_data').datagrid('load', {
		deptName : deptName,
	});
}
