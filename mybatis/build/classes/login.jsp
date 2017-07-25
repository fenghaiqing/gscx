<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>用户登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
.main {
	width: 1200px;
	margin: 150px auto;
}

.main_title {
	width: 350px;
	margin: 0 auto;
}

.main_title p {
	font-size: 20px;
}

.main_contect {
	width: 350px;
	margin: 0 auto;
}

.main_contect input {
	display: block;
	width: 320px;
	height: 40px;
	margin: 10px auto;
	padding-left: 15px;
}

input::-webkit-input-placeholder {
	font-size: 16px !important;
	color: #666666;
	line-height: 16px;
}

input[type="text"] {
	border: 1px solid #d2d2d2;
	display: inline;
}

input[type="password"] {
	border: 1px solid #d2d2d2;
	display: inline;
}

.btn {
	width: 340px;
	height: 45px;
	background-color: #fff;
	margin: 30px auto;
	border: 1px solid #017e66;
	text-align: center;
	color: #017e66;
	font-size: 20px;
	cursor: pointer;
	border-radius: 10px;
}

.btn:hover {
	background: #017e66;
	color: #fff;
}
</style>
</head>
<a href="<%=basePath%>toAddUser.do">新用户注册</a>
<p />
<form action="<%=basePath%>Login.do" method="POST">
<div class="main_contect">
				<div class="main_detail">
		<span>用户名：</span><input type="text" name="username" /><br /> <br />
		</div>
		<div class="main_detail">
		<span>密&nbsp;码：</span><input type="password" name="password" />
	</div>
	<div class="main_detail">
		<input type="submit" value=" 登录 " />
		</div>
	</div>
</form>
</body>
</html>