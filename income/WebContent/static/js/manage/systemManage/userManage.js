// JavaScript Document
$(function(){
	$('#addRole').hide();
	$('#editUsers').hide();
	$('#addUser').hide();
	$.messager.defaults = { ok: "确定", cancel: "取消" };
	//datagrid初始化  
	$('#list_data').datagrid({
		title:'人员维护列表',  
		iconCls:'icon-edit',//图标  
		width: 770,  
		height: 'auto',  
		nowrap: false,  
		striped: true,
		border: true, 
		loadMsg: '正在加载',
		collapsible:false,//是否可折叠的  
		fit: true,//自动大小  
		url:contextPath + '/manage/queryAllUserList.do',  
		remoteSort:false,   
		idField:'userId', 
		checkOnSelect :false,
		singleSelect:false,//是否单选  
		pagination:true,//分页控件  
		rownumbers:true,//行号
		pageSize: 20,//每页显示的记录条数，默认为10
		pageList: [5,10,15,20],//可以设置每页记录条数的列表
		columns:[[
			//{ field:'ck',checkbox:true },
			{ field:'userId',width : 150,align : "center",title:'用户编号' },
			{ field:'userName',width : 150,align : "center",title:'用户名称' },
			{ field:'msisdn',width : 120,align : "center",title:'手机号码' },
			{ field:'email',width : 120,align : "center",title:'邮箱地址' },
			{ field:'deptId',hidden:'true'},
			{ field:'deptName',width : 150,align : "center",title:'所属部门' },
			/*{ field:'pricingCommittee',width : 130,align : "center",title:'是否为定价委员会成员',
			formatter : function(value, row, index) {
				if(value==1){
					return "是"
				}else{
					return "否"
				}
			}
			},*/
			{ field:'startDate',width : 120,align : "center",
				formatter:function(value,row,index){   
                return value.substr(0,10);  
                }  
           ,title:'启用时间' },
			{ field:'endDate',width : 120,align : "center",
        	   formatter:function(value,row,index){   
                return value.substr(0,10);  
            },title:'停用时间' },
//			{ field:'password',width : 90,align : "center",title:'登录密码', 
//				formatter : function(value, rec, index) {
//					var url = "<a href='javascript:void(0)'  onclick='resetPwd("
//						+ "\""+rec.userId+"\""
//						+ ")' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-reload l-btn-icon-left'>重置</span></span></a>";
//					return url;
//				}
//			},
			{field : 'opt',width : 220,align : "center",title : '操作',
				formatter : function(value, rec, index) {
					if(rec.userId == window.parent.document.getElementById("sessionUserId").value){
						var b = "<a href='javascript:void(0)'  onclick='edit("
							+"\""+ rec.userId +"\""+ ",\"" + rec.userName + "\"," + rec.msisdn + ",\"" + rec.email + "\","+"\"" + rec.deptId + "\","+"\"" +rec.startDate+ "\","+"\"" +rec.endDate+ "\","+"\""+rec.pricingCommittee+"\""  
							+ ")' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit l-btn-icon-left'>修改</span></span></a>";
						return b;
					} else if(rec.roleId == '0'){	//系统管理员
						return " ";
					} else {
						var a = "<a href='javascript:void(0)'  onclick='addRole("
							+ "\""+rec.userId+"\"" 
							+ ")' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-add l-btn-icon-left'>角色分配</span></span></a>";
						var b = "<a href='javascript:void(0)'  onclick='edit("
							+ "\"" + rec.userId +"\""+",\"" + rec.userName + "\"," + rec.msisdn + ",\"" + rec.email + "\","+"\"" + rec.deptId + "\","+"\"" +rec.startDate+ "\","+"\"" +rec.endDate+ "\","+"\""+rec.pricingCommittee+"\""  
							+ ")' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit l-btn-icon-left'>修改</span></span></a>";
						var d = "<a href='javascript:void(0)'  onclick='dell("
							+ "\""+rec.userId+"\""
							+ ")' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-remove l-btn-icon-left'>删除</span></span></a>";
						return a + b  + d;
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

/**
 * 选择人员类型时显示省、市、区县
 * @param userType
 */
function changeUserType(userType){
	if(userType == "0"){
		$('#tr_province').hide();
		$("#province").val("0");
		$("#city").html("<option value='0'>请选择</option>");
		$('#tr_city').hide();
		$("#cunty").html("<option value='0'>请选择</option>");
		$('#tr_cunty').hide();
	}else if(userType == '1'){//选择省移动人员用户类型时，显示省combo
		$('#tr_province').show();
		$("#city").html("<option value='0'>请选择</option>");
		$('#tr_city').hide();
		$("#cunty").html("<option value='0'>请选择</option>");
		$('#tr_cunty').hide();
	}else if(userType == '2'){
		$('#tr_province').show();
		$('#tr_city').show();
		$("#cunty").html("<option value='0'>请选择</option>");
		$('#tr_cunty').hide();
	}else if(userType == '3'){
		$('#tr_province').show();
		$('#tr_city').show();
		$('#tr_cunty').show();
	}
}	   

function changeProvince(provinceId,selectedCityId){
	$.ajax({
	    dataType: "json",
	    data: {"provinceId":provinceId},
	    method: "post",
	    url: contextPath+"/manage/system/getCityByProvinceId"
	}).done(function(data){
	    if(data.reCode=="100"){
			var cityHtml = "<option value='0'>请选择</option>";
			for(var i = 0; i < data.reStr.length; i++){
				if(selectedCityId == data.reStr[i].id){
					cityHtml += "<option value='" + data.reStr[i].id + "' selected>" + data.reStr[i].city + "</option>";
				}
				cityHtml += "<option value='" + data.reStr[i].id + "'>" + data.reStr[i].city + "</option>";
			}
			$("#city").html(cityHtml);
	    }else{
	    	$.messager.alert('提示',"地市获取失败！",'warning');
	    }
    });
}

function changeCity(cityId,selectedCuntyId){
	$.ajax({
	    dataType: "json",
	    data: {"cityId":cityId},
	    method: "post",
	    url: contextPath+"/manage/system/getCuntyByCityId"
	}).done(function(data){
	    if(data.reCode=="100"){
			var cuntyHtml = "<option value='0'>请选择</option>";
			for(var i = 0; i < data.reStr.length; i++){
				if(selectedCuntyId == data.reStr[i].id){
					cuntyHtml += "<option value='" + data.reStr[i].id + "' selected>" + data.reStr[i].cunty + "</option>";
				}
				cuntyHtml += "<option value='" + data.reStr[i].id + "'>" + data.reStr[i].cunty + "</option>";
			}
			$("#cunty").html(cuntyHtml);
	    }else{
	    	$.messager.alert('提示',"地市获取失败！",'warning');
	    }
    });
}

function addUser(){	
	$("#add_userName").textbox('setValue','');
	$("#add_userDn").textbox('setValue','');
	$("#add_email").textbox('setValue','');
	$("#add_userId").textbox('setValue','');
	$("#add_userId").attr("disabled",false);
	$("#startDate").datebox("setValue","");
	$("#endDate").datebox("setValue","");
	$('#add_dept').combobox({
	    url:contextPath+"/manage/queryAllDep.do",
	    valueField:'deptCode',
	    textField:'deptName',
	    editable:false
	});
	
	$(":radio[name='pricingCommittee'][value='0']").prop("checked", "checked");
		
	$('#add_dept').combobox({
		value:'请选择'
	});
	
	$('#addUser').show();
	$('#addUser').window({
		title:'新增用户',
    	width:420,
		height:380,
		modal:true,
		minimizable:false,
		collapsible:false
	});
}

function addRole(userId)
{
	// 查询用户已有角色
	$.ajax({
	    dataType: "json",
	    data: {"userId":userId},
	    method: "post",
	    url: contextPath +"/manage/queryRoleUser.do"
	}).done(function(data){
	    if(data.reCode=="100"){
	    	$("#role_user_tr").show();
			$('#role_user').text(data.reStr);
			$('#role_roleId').val(data.reRoleId);
	    }else {
	    	$("#role_user_tr").hide();
	    	$('#role_user').text('');
	    	$('#role_roleId').val('');
	    }
    });

	$('#mod_userId').val(userId);
	$('#add_userRole').combobox({
	    url:contextPath+"/manage/queryRoleByUserType.do",
	    valueField:'roleId',
	    textField:'roleName'
	});
	$('#addRole').show();
	$('#addRole').window({
		title:'角色指派',
		width:420,
		height:160,
		modal:true,
		minimizable:false,
		collapsible:false
	});
	
	$('#add_userRole').combobox({
		panelHeight:100,
		value:'请选择'
	});
}

function formatStr(str){
	var strs=str.split("-");
	var kk=strs[1]+"/"+strs[2]+"/"+strs[0];
	return kk;
}
function formatStrToDate(str){
	var strs=str.split("/");
	var kk=strs[2]+"/"+strs[0]+"/"+strs[1];
	return kk;
}

/**
 * 修改用户
 * @param userId
 * @param userName
 * @param msisdn
 * @param userType
 * @param provinceId
 * @param cityId
 * @param cuntyId
 */
function edit(userId,userName,msisdn,email,deptId,startDate,endDate,pricingCommittee){
	//$('#userManage_optType').val('edit');
	$('#edit_dept').combobox({
	    url:contextPath+"/manage/queryAllDep.do",
	    valueField:'deptCode',
	    textField:'deptName'
	});
	$("#mod_userId").val(userId);
	$("#userId").textbox('setValue',userId);
	$('#userId').combobox('disable');//不可编辑
	$("#userName").textbox('setValue',userName);	
	$("#email").textbox('setValue',email);
	$("#n_userDn").textbox('setValue',msisdn);
	$('#edit_dept').combobox('setValue',deptId);
	if(startDate=='null' ||startDate==""){
		$("#n_startDate").datebox("setValue","");
	}else{
		$("#n_startDate").datebox("setValue",formatStr(startDate));
	}
	if(endDate=='null' ||endDate==""){
		$("#n_endDate").datebox("setValue","");
	}else{
		$("#n_endDate").datebox("setValue",formatStr(endDate));
	}
	
	 $(":radio[name='edit_pricingCommittee'][value='" + pricingCommittee + "']").prop("checked", "checked");
	
	$('#editUsers').show();
	$('#editUsers').window({
		title:'修改用户',
		width:420,
		height:380,
		modal:true,
		minimizable:false,
		collapsible:false
	});
}


// 批量删除
function dellAll(){
	var selRow = $("#list_data").datagrid("getChecked");
	if (selRow.length == 0) {
		$.messager.alert('告警','请至少选择一行数据!','warning');
		return false;
	}
	var ids = [];
	for (var i = 0; i < selRow.length; i++){
		var id = selRow[i].userId;
		if(id==window.parent.document.getElementById("sessionUserId").value){
			$.messager.alert('告警','当前登陆者不能删除!','warning');
			return false;
		}
		ids.push(id);
	}
	$.messager.confirm('提示',"你确定要删除选择的用户吗？",function(r){
	if (r){
		$.ajax({
		    dataType: "json",
		    data: {"userIds":ids.toString()},
		    method: "post",
		    url: contextPath+"/manage/deleteUser.do"
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
	    }else{
	    	$("#list_data").datagrid("clearSelections");
	    }
    });
}

// 单个删除
function dell(userId)
{
	$.messager.confirm('提示',"你确定要删除选择的用户吗？",function(r){
	    if (r)
	    {
			$.ajax({
			    dataType: "json",
			    data: {"userIds":userId},
			    method: "post",
			    url: contextPath+"/manage/deleteUser.do"
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

// 删除已有角色
function dellUserRole(){
	var mod_userId = $('#mod_userId').val();
	var userRole = $('#role_roleId').val();
	$.ajax({
	    dataType: "json",
	    data: {"roleId":userRole,"userId":mod_userId,"type":"delete"},
	    method: "post",
	    url: contextPath + "/manage/saveUserRole.do"
	}).done(function(data){
	    if(data.reCode=="100"){
	    	$.messager.alert('提示',"角色删除成功");
	    	$("#role_user_tr").hide();
	    	$('#role_roleId').val('');
	    }else{
	    	$.messager.alert('提示',data.reStr,'warning');
	    }
    }).fail(function(){
    	$.messager.alert('提示',"本次操作失败，请重新操作",'error');
		return false;
    });
}

function optType(){
    var optType = $('#userManage_optType').val();
	if (optType == 'add'){
		saveUser();
	}else{
		modUser();
	}
}

/**
 * 新增用户
 * @returns {Boolean}
 */
function saveUser()
{	
	var rC = { 
	 		lW:'[a-z]', 
	 		uW:'[A-Z]', 
	 		nW:'[0-9]'}; 
	 	function Reg(str, rStr){ 
	 		var reg = new RegExp(rStr); 
	 		if(reg.test(str)) return true; 
	 		else return false; 
	 	}
	
	
	var userName = $('#add_userName').val();
	var userDn = $('#add_userDn').val();
	var userId = $('#add_userId').val();
	var email = $('#add_email').val();
	var startDate = $('#startDate').datebox('getValue');
	var endDate = $('#endDate').datebox('getValue');
	
	var deptId = $('#add_dept').combobox('getValue');
	
	var pricingCommittee=$("input[name='pricingCommittee']:checked").val();
	
	var tR = { l:Reg(userId, rC.lW), u:Reg(userId, rC.uW), n:Reg(userId, rC.nW)}
	
	if (userId == ""){
		$.messager.alert('告警','请输入用户编号','warning');
		return false;
	}
	
	
//	else if(!((tR.l && tR.n)||(tR.u && tR.n))){
//		$.messager.alert('告警','用户编号必须含有小写字母或者大写字母和数字','warning');
//		return false;
//	}else if($.trim(userId).length>10){
//		$.messager.alert('告警','用户编号不能超过10位','warning');
//    	return false;
//    }
	
	
	
	if(userName == ""){
		$.messager.alert('告警','请输入用户名称','warning');
		return false;
	}
	if (userName.length>50){
		$.messager.alert('告警','用户名超过长度','warning');
		return false;
	}
	if(userName.indexOf('<') != -1 ||userName.indexOf('>') != -1 ||userName.indexOf('%') != -1 || userName.indexOf('_') != -1){
		$.messager.alert('告警','用户名称不允许出现 %、_、<、> 等字符，请重新输入','warning');
		return false;
	}
	if (userDn == ""){		
			$.messager.alert('告警','请输入手机号码','warning');
	 		return false;		
	}else if (userDn != ""){
		if((!/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(userDn))){
			$.messager.alert('告警','请输入正确的11位手机号码','warning');
	 		return false;
		}
	}
	if(email == ""){		
		$.messager.alert('告警','请输入邮箱','warning');
		return false;
	}else if(!/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,4}$/.test(email)){
		$.messager.alert('告警','请输入正确的邮箱','warning');
 		return false;
	}
	if (deptId == "请选择"){
		$.messager.alert('告警','请输选择部门','warning');
		return false;
	}
	if (startDate == ""){		
		$.messager.alert('告警','请输入启用时间','warning');
 		return false;		
	}
	if (endDate == ""){		
		$.messager.alert('告警','请输入结束时间','warning');
 		return false;
	}
	if(new Date(startDate).getTime()>new Date(endDate).getTime()){
		$.messager.alert('告警','开始时间不能大于结束时间','warning');
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
	    data: {"userName":userName,"userId":userId,"msisdn":userDn,"email":email,"deptId":deptId,"startDate":startDate,"endDate":endDate,"pricingCommittee":pricingCommittee},
	    method: "post",
	    url: contextPath+"/manage/addUser.do"
	}).done(function(data){
		lock = 0;
	    if(data.reCode=="100"){
	    	$.messager.alert('提示',data.reStr);
	    	$('#addUser').window('close');
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
 * 修改用户
 * @returns {Boolean}
 */
function modUser(){
	var userId = $("#userId").val();
	var userName = $('#userName').val();
	var email = $('#email').val();
	var userDn = $('#n_userDn').val();
	//alert(userDn);
	var startDate = $('#n_startDate').datebox('getValue');
	var endDate = $('#n_endDate').datebox('getValue');
	
	var deptId = $('#edit_dept').combobox('getValue');
	
	var pricingCommittee=$("input[name='edit_pricingCommittee']:checked").val();
	// 判断用户名
	if (userName == ""){
		$.messager.alert('告警','请输入用户名称','warning');
		return false;
	}
	
	if (userName.length>50){
		$.messager.alert('告警','用户名超过长度','warning');
		return false;
	}
	if(userName.indexOf('<') != -1 ||userName.indexOf('>') != -1 ||userName.indexOf('%') != -1 || userName.indexOf('_') != -1){
		$.messager.alert('告警','用户名称不允许出现 %、_、<、> 等字符，请重新输入','warning');
		return false;
	}
	
	// 判断手机号码
	if (userDn != ""){
		if((!/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(userDn))){
			$.messager.alert('告警','请输入正确的11位手机号码','warning');
	 		return false;
		}
	}else{
		$.messager.alert('告警','请输入11位手机号码','warning');
 		return false;
	}
	if(email == ""){
		$.messager.alert('告警','请输入邮箱','warning');
		return false;
	}else if(!/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,4}$/.test(email)){
		$.messager.alert('告警','请输入正确的邮箱','warning');
 		return false;
	}
	
	if (deptId == "请选择"){
		$.messager.alert('告警','请输选择部门','warning');
		return false;
	}
	if (startDate == ""){		
		$.messager.alert('告警','请输入启用时间','warning');
 		return false;		
	}
	if (endDate == ""){		
		$.messager.alert('告警','请输入结束时间','warning');
 		return false;
	}
	if(new Date(startDate).getTime()>new Date(endDate).getTime()){
		$.messager.alert('告警','开始时间不能大于结束时间','warning');
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
	    data: {"userId":userId,"userName":userName,"msisdn":userDn,"email":email,"deptId":deptId,"startDate":startDate,"endDate":endDate,"pricingCommittee":pricingCommittee},
	    method: "post",
	    url: contextPath+"/manage/updateUser.do"
	}).done(function(data){
		lock = 0;
	    if(data.reCode=="100"){
	    	$.messager.alert('提示',data.reStr);
	    	$('#editUsers').window('close');
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

function saveRole()
{
	var mod_userId = $('#mod_userId').val();
	var role_roleId = $('#role_roleId').val();
	var userRole = $('#add_userRole').combobox('getValue');
	// 判断是否选择角色
	if (userRole == "请选择"){
		$.messager.alert('告警','请输选择角色','warning');
		return false;
	}
	
	// 判断角色是否修改
	if (role_roleId == userRole){
		$.messager.alert('告警','赋予的角色已经存在，请重新选择角色','warning');
		return false;
	}
	
	// 请求后台	
	$.ajax({
	    dataType: "json",
	    data: {"roleId":userRole,"userId":mod_userId,"type":"add"},
	    method: "post",
	    url: contextPath + "/manage/saveUserRole.do"
	}).done(function(data){
	    if(data.reCode=="100"){
	    	$.messager.alert('提示',data.reStr);
	    	$('#addRole').window('close');
	    }else{
	    	$.messager.alert('提示',data.reStr,'warning');
	    }
    }).fail(function(){
    	$.messager.alert('提示',"本次操作失败，请重新操作",'error');
		return false;
    });
}

// 重置密码
function resetPwd(userId){
	// 请求后台	
	$.ajax({
	    dataType: "json",
	    data: {"userId":userId},
	    method: "post",
	    url: contextPath+"/manage/resetPwd.do"
	}).done(function(data){
	    if(data.reCode=="100"){
	    	$.messager.alert('提示',data.reStr);
	    }else{
	    	$.messager.alert('提示',data.reStr,'warning');
	    }
    }).fail(function(){
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
	//var userType = $('#tb_userType').val();
	var userDn = $('#userDn').val();
	$('#list_data').datagrid('load', {
		//userType : userType,
		userName : userDn,
		chaxun : "tiaojian"
	});
}
