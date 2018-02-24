<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>两非收入管理系统-登录</title>
<link rel="stylesheet" href="${ctx}/static/css/manage/main.css">

<script type="text/javascript">
	var ctx= "${ctx}";
</script>
</head>
<body class="easyui-layout">
	<%
	    String message = (String) request.getAttribute("message");
		if (message != null && !"".equals(message)) {
	%>
	<script type="text/javascript">
	alert("<%=message%>");
	</script>
	<%
	    }
	%>
	<div style="height: 30px;"></div>
	<div class="header"></div>
	<div class="center">
	 	<div class="container">
			<div class="col-1">
				<label>用户：</label><input type="text" id="accounts" maxlength="32" placeholder="请输入登录账号" class="input-1" />
			</div>
			<div class="col-1">
				<label>密码：</label><input type="password" id="passwd" placeholder="请输入登录密码" class="input-1" />
			</div>
			<div class="col-1" style="padding-left:44px;">
				<label>验证码：</label>
				<input type="text" style="width:100px;" class="input-1" placeholder="请输入验证码" onkeydown="keyDown(event)" id="checkCodeValue" name="checkCodeValue" />  
                <img style="vertical-align: middle;width:80px;height:44px;" id="img-checkcode" alt="点击更换！" title="点击更换！" src="${ctx}/static/images/manage/loading.gif" style=" cursor:pointer" />  
			</div>
			<div class="form-error"></div>
			<div class="code-error"></div>
			<div class="col-2"  id="btn_log">
				<input type="button" id="login" value="登录" class="btn-login" />
			</div>
			<div class="col-2" id="isLoging" style="display: none">
			<div class="spinner"><div class="rect1"></div>
				 <div class="rect2"></div>
				 <div class="rect3"></div>
				 <div class="rect4"></div>
				 <div class="rect5"></div></div>
				 
			</div>
		</div>
	</div>
	<div id="home-south" data-options="region:'south',border:false" class="footer">©咪咕互动娱乐有限公司</div>
	<script src="${ctx}/static/js/jquery.min.js"></script>
	<script src="${ctx}/static/js/manage/login.js"></script>
</body>
</html>