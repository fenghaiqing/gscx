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
<title>用户注册</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="js/jquery-2.2.2.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
						//alert("check");
						$("#username").blur(
										function() {
											var url = 'http://localhost:8080/mybatis/register/check.do';
											var params = {
												username : $("#username").val(),
												password1 : $("#password1")
														.val()
											};
											$.post(url,params,function callback(data) {
																if (data.reg == "allowed") {
																	$('#checkinfo').html("可以注册!");
																	$('#checkinfo').attr("style","color: blue");
																} else if (data.reg == "username_existed") {
																	$('#checkinfo').html("用户已存在，请用其它名字注册!");
																	$('#checkinfo').attr("style","color: red");
																} else if (data.reg == "username_needed") {
																	$('#checkinfo').html("用户名不能为空!");
																	$('#checkinfo').attr("style","color: red");
																}
															}, 'json');
});
						$('#regform').submit(function() {
											//alert("asd");
											$.ajax({
														url : "http://localhost:8080/mybatis/register/ajax.do",
														type : "POST",
														dataType : 'json',
														data : $('#regform').serialize(),
														success : function(data) {
															if (data.reg == "username_needed") {
																$('#reginfo').html("错误：用户名为空，无法注册!");
																$('#reginfo').attr("style","color: red");
															} else if (data.reg == "username_existed") {
																$('#reginfo').html("错误：用户名已存在，注册失败!");
																$('#reginfo').attr("style","color: red");
															} else {
																$('#reginfo').html("提示：用户["+ data.username+ "] "+ " 注册成功!");
																$('#reginfo').attr("style","color: blue");
																$('#regform')[0].reset();
																$('#checkinfo').html("");
																$('#age').val("");
															}
														},
														error : function() {
															alert("网络连接出错！");
														}
													});
											return false;
										});
					});
</script>
</head>
<body>

	<form id="regform" name="regform">
		<div class="main" style="margin-top: 100px;">
		<div class="main_contect">
		<div class="main_detail">
			<span id="checkinfo" style="color: red"></span><br /> <span>用&nbsp;&nbsp;户：</span>
			</div>
			<div class="main_detail">
			<input type="text" id="username" name="username" /><br /> <br /> <span>密&nbsp;&nbsp;码：</span>
			</div>
				<input type="password" id="password" name="password" size="12"
				maxlength="12" /><br /> <br /> <span>确认密码：</span>
				<input type="password" name="password2" size="12" maxlength="12" /><br />
			<br />
			 <span>姓&nbsp;&nbsp;名：</span>
			<input type="text" id="fullname"
				name="fullname" /><br /> <br /> <span>年&nbsp;&nbsp;龄：</span>
				<input type="text" id="age" name="age" value="18" /><br /> <br /> <span>住&nbsp;&nbsp;址：</span>
				<input type="text" id="address" name="address" size="40" value="" /><br />
			<br />
		
		
		<div class="main_detail">
			<input type="submit" value=" 注册 " /><span id="reginfo"
				style="color: red"></span>
		</div>
		</div>
			</div>
	</form>
</body>
</html>