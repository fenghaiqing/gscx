$(function(){
	$.messager.defaults = { ok: "确定", cancel: "取消" };
});
function reset()
{
	$("#old_pwd").textbox('setValue',"");
	$("#password").textbox('setValue',"");
	$("#repassword").textbox('setValue',"");
}

function modPassword()
{
	var userId = $('#userId').val();
	var old_pwd = $('#old_pwd').val();
	var password = $('#password').val();
	var repassword = $('#repassword').val();
	
	// 判断新密码的规则
	var rC = { 
 		lW:'[a-z]', 
 		uW:'[A-Z]', 
 		nW:'[0-9]',
 		wW:'[!,@,#,$,&,*]'}; 
 	function Reg(str, rStr){ 
 		var reg = new RegExp(rStr); 
 		if(reg.test(str)) return true; 
 		else return false; 
 	}
	
	// 对老密码进行非空判断
	if (old_pwd == '')
	{
		$.messager.alert('告警','请输入原密码','warning');
		return false;
	}
	// 新密码判断
	if (password == '')
	{
		$.messager.alert('告警','请输入新密码','warning');
		return false;
	}
	// 对确认密码进行判断
	if (repassword == '')
	{
		$.messager.alert('告警','请输入确认密码','warning');
		return false;
	}
	if(password.length < 8)
 	{ 
 		$.messager.alert('告警','密码长度不得低于8位，请重新输入','warning');
 		return false; 
 	}
	if(repassword.length < 8)
 	{ 
 		$.messager.alert('告警','确认密码长度不得低于8位，请重新输入','warning');
 		return false; 
 	}
	// 对确认密码进行判断
	if (repassword != password)
	{
		$.messager.alert('告警','两次输入的新密码不一致，请重新输入','warning');
		return false;
	}
	
//	// 判断新密码是否与前5次使用的密码一致
//	for(var i=0; i<old_pwd.length;i++){
//		 var oldPwd = old_pwd[i];
//		 if (password == oldPwd)
//		 {
//			$.messager.alert('告警','新密码在前5次中已经使用过，请重新输入','warning');
//		 	$("#password").focus();
//		 	return false;
//		 }
//	 }
	
	
	if (password == old_pwd)
	{
		$.messager.alert('告警','新密码和原密码相同，请重新输入','warning');
 		$("#password").focus();
 		return false;
	}
	var tR = { l:Reg(password, rC.lW), u:Reg(password, rC.uW), n:Reg(password, rC.nW),w:Reg(password, rC.wW)}
	
	if((tR.l && tR.n && tR.w) || (tR.u && tR.n && tR.w))
	{
//		$.messager.confirm('提示',"你确定要修改密码吗？",function(r){
//		    if (r)
//		    {
//		    	
//		    }
//	    });
		$.ajax({
    	    dataType: "json",
    	    data: {"password":password,"old_pwd":old_pwd,"userId":userId},
    	    method: "post",
    	    url: contextPath+"/manage/modUserPassword.do"
    	}).done(function(date){
    	    if(date.reCode=="100"){
    	    	$.messager.alert('提示',"修改密码成功,点击跳到登录页",'info',function(){
    	    		$('#password').val('');
    				$('#repassword').val('');
    				$("#old_pwd").val('');
    				window.location.href=contextPath+"/Users/logout.do";
    	    	});
    	    }else{
    	    	$.messager.alert('告警',date.reStr);
    	    }
        }).fail(function(){
        	$.messager.alert('告警',"本次操作失败，请重新操作",'error');
    		return false;
        });
	}
	else
	{
		$.messager.alert('告警','您的密码必须含有小写字母或者大写字母、数字、特殊字符(!,@,#,$,&,*)','warning');
		$("#password").focus();
		return false;
	}
}
