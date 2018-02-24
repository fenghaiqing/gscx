// JavaScript Document
$(function(){
	$('#newRole').hide();
	$('#funcRole').hide();
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	//datagrid初始化  
	$('#list_role').datagrid({
		title:'角色维护列表',  
		iconCls:'icon-edit',//图标  
		width: 700,  
		height: 'auto',
		nowrap: false, 
		striped: true,
		border: true,
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的
		fit: true,//自动大小
		url:contextPath+'/manage/queryAllRoles.do',
		remoteSort:false,
		idField:'userId',
		singleSelect:false,//是否单选
		pagination:true,//分页控件
		rownumbers:true,//行号
		pageSize: 20,//每页显示的记录条数，默认为10
		pageList: [5,10,15,20],//可以设置每页记录条数的列表
		columns:[[
			{ field:'roleName',width : 150,align : "center",title:'角色名称' },
			{ field:'crtateDate',width : 150,align : "center",title:'创建时间' },
			{ field : 'opt',width : 250,align : "center",title : '操作',
				formatter : function(value, rec, index) {
					if(rec.isAdmin == 1){//如果是默认角色，不能权限分配和删除
						var a = "<a href='javascript:void(0)'  onclick='edit("
								+ rec.roleId + ",\"" + rec.roleName
								+ "\")' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit l-btn-icon-left'>修改</span></span></a>";
						return a;
					}else{
						var a = "<a href='javascript:void(0)'  onclick='addFunc("
								+ rec.roleId
								+ ")' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-add l-btn-icon-left'>权限分配</span></span></a>";
						var b = "<a href='javascript:void(0)'  onclick='edit("
								+ rec.roleId + ",\"" + rec.roleName
								+ "\")' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit l-btn-icon-left'>修改</span></span></a>";
						var c = "<a href='javascript:void(0)'  onclick='dell("
								+ rec.roleId
								+ ")' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-remove l-btn-icon-left'>删除</span></span></a>";
						return a + b + c;
					}
				}
			}
		]],
		toolbar: '#tb'
	});
	//设置分页控件  
	var p = $('#list_role').datagrid('getPager');
	$(p).pagination({
		//pageSize: 10,//每页显示的记录条数，默认为10
		//pageList: [5,10,15,20],//可以设置每页记录条数的列表
		beforePageText: '第',//页数文本框前显示的汉字 
		afterPageText: '页    共 {pages} 页',
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh:function(){
			$(this).pagination('loading');
			$(this).pagination('loaded');
		}
	});
});
function addUser(){
	//$('#add_roleName').val('');
	$("#add_roleName").textbox('setValue',"");
	//$("#add_roleName").css("border-color","#95B8E7");
	$('#userManage_optType').val('add');
	$("#add_userType").attr("disabled",false);
	$("#add_userType").val("0");
	$('#newRole').show();
	$('#newRole').window({
		title:'新增角色',
    	width:420,
		height:180,
		modal:true,
		minimizable:false,
		collapsible:false
	});
}

function edit(roleId,roleName){
	$('#mod_roleId').val(roleId);
	$('#add_roleName_old').roleName;
	$("#add_roleName_old").css("border-color","#95B8E7");
	$('#userManage_optType').val('edit');
	$("#add_roleName").textbox('setValue',roleName);
	$('#newRole').show();
	$('#newRole').window({
		title:'修改角色',
    	width:420,
		height:180,
		modal:true,
		minimizable:false,
		collapsible:false
	});
}

// 单个删除
function dell(roleId)
{
	$.messager.confirm('提示',"你确定要删除该角色吗？",function(r){
	    if (r)
	    {
	    	$.ajax({
	    	    dataType: "json",
	    	    data: {"roleId":roleId},
	    	    method: "post",
	    	    url: contextPath+"/manage/deleteRole.do"
	    	}).done(function(data){
	    	    if(data.reCode=="100"){
	    	    	$.messager.alert('提示','操作成功！');
	    	    	$('#list_role').datagrid('reload',{});
	    	    }else{
	    	    	$.messager.alert('告警',data.reStr,'warning');
	    	    	$('#list_role').datagrid('reload',{});
	    	    }
	        }).fail(function(){
	        	$.messager.alert('告警',"本次操作失败，请重新操作",'error');
	    		return false;
	        });
	    }
    });
}

function optType()
{
    var optType = $('#userManage_optType').val();
	if (optType == 'add'){
		saveRole();
	}else{
		modRole();
	}
}

function saveRole(){
	var roleName = $('#add_roleName').val();
	// 判断角色名
	if (roleName == ""){
		$.messager.alert('告警','请输入角色名称','warning');
		return false;
	}
	if(roleName.indexOf('<') != -1 ||roleName.indexOf('>') != -1 ||roleName.indexOf('%') != -1 || roleName.indexOf('_') != -1){
		$.messager.alert('告警','角色名称不允许出现 %、_、<、> 等字符，请重新输入','warning');
		return false;
	}
	// 请求后台	
	$.ajax({
	    dataType: "json",
	    data: {"roleName":roleName},
	    method: "post",
	    url: contextPath+"/manage/addRole.do"
	}).done(function(data){
	    if(data.reCode=="100"){
	    	$.messager.alert('提示',data.reStr);
	    	$('#newRole').window('close');
	    	$('#list_role').datagrid('reload',{});
	    }else{
	    	$.messager.alert('告警',data.reStr,'warning');
	    }
    }).fail(function(){
    	$.messager.alert('告警',"本次操作失败，请重新操作",'error');
		return false;
    });
}

function modRole(){
	var roleId = $('#mod_roleId').val();
	var roleName = $('#add_roleName').val();
	var roleName_old = $('#add_roleName_old').val();
	
	// 判断用户名
	if (roleName == ""){
		$.messager.alert('告警','请输入角色名称','warning');
		return false;
	}
	if(roleName.indexOf('<') != -1 ||roleName.indexOf('>') != -1 ||roleName.indexOf('%') != -1 || roleName.indexOf('_') != -1){
		$.messager.alert('告警','角色名称不允许出现 %、_、<、> 等字符，请重新输入','warning');
		return false;
	}
	
	if (roleName == roleName_old){
		$.messager.alert('告警','修改前后没有任何变化，请确认','warning');
		return false;
	}
	
	// 请求后台	
	$.ajax({
	    dataType: "json",
	    data: {"roleName":roleName,"roleId":roleId},
	    method: "post",
	    url: contextPath+"/manage/updateRole.do"
	}).done(function(data){
	    if(data.reCode=="100"){
	    	$.messager.alert('提示',data.reStr);
	    	$('#newRole').window('close');
	    	$('#list_role').datagrid('reload',{});
	    }else{
	    	$.messager.alert('告警',data.reStr,'warning');
	    }
    }).fail(function(){
    	$.messager.alert('提示',"本次操作失败，请重新操作",'error');
		return false;
    });
}

function addFunc(roleId){
	$('#mod_roleId').val(roleId);
	// 请求后台	
	$.ajax({
	    dataType: "json",
	    data: {"roleId":roleId},
	    method: "post",
	    url: contextPath+"/manage/queryAllFunc.do"
	}).done(function(data){
	    if(data.reCode=="100"){
	        var str = data.reStr;
	    	var json = $.parseJSON(str);
	    	$('#tt').tree({checkbox:true,data: json});
	    	$('#funcRole').show();
			$('#funcRole').window({
				title:'角色赋权',
		    	width:420,
				height:380,
				modal:true,
				minimizable:false,
				collapsible:false
			});
	    }else{
	    	$.messager.alert('告警',date.reStr,'warning');
	    }
    }).fail(function(){
    	$.messager.alert('提示',"本次操作失败，请重新操作",'error');
		return false;
    });
}

function getChecked(){
	var roleId = $('#mod_roleId').val();
	var nodes = $('#tt').tree('getChecked');
    var s = '';
    for (var i = 0; i < nodes.length; i++){
       if (s != ''){
           s += ',';
       }
       s += nodes[i].id;
    }
    // 请求后台	
	$.ajax({
	    dataType: "json",
	    data: {"roleId":roleId, "funcIds":s},
	    method: "post",
	    url: contextPath+"/manage/saveRoleFunc.do"
	}).done(function(date){
	    if(date.reCode=="100"){
	    	$.messager.alert('提示',"权限分配成功");
			$('#funcRole').window('close');
	    }else{
	    	$.messager.alert('告警',date.reStr,'warning');
	    }
    }).fail(function(){
    	$.messager.alert('告警',"本次操作失败，请重新操作",'error');
		return false;
    });
}


function closeWindow(win)
{
	$("#" + win+ "").window('close');
}

// 查询
function searchSubmit(){
	var roleName = $('#roleName').val();
	$('#list_role').datagrid('load', {
		roleName : roleName,
		chaxun : "tiaojian"
	});
}
