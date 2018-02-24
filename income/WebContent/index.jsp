<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="refresh" content="3;url=<%=basePath%>manage/toLogin.do">
<title>MIGU两非管理后台</title>
</head>
<script type="text/javascript">
	function redirect() {

	}
</script>
<body>加载中，请稍候...
</body>
</html>