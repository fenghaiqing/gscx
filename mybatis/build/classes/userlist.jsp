<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<script type="text/javascript" src="js/jquery-2.2.2.min.js"></script>
<title>用户列表</title>
<script type="text/javascript">
function del(id){
$.get("<%=basePath%>/delUser?id=" + id, function(data) {
			if ("success" == data.result) {
				alert("删除成功");
				window.location.reload();
			} else {
				alert("删除失败");
			}
		});
	}
</script>
</head>
<body>
	<table border="1">
		<tbody>
			<tr>
				<th>序号</th>
				<th>账户名</th>
				<th>密码</th>
				<th>姓名</th>
				<th>年龄</th>
				<th>住址</th>
				<th>注册时间</th>
				<th>锁定状态</th>
				<th>状态</th>
			</tr>
			<c:if test="${!empty userList }">
				<c:forEach items="${userList}" var="user" varStatus="status">
					<tr>
						<td>${status.count }</td>
						<td>${user.username}</td>
						<td>${user.password}</td>
						<td>${user.fullname}</td>
						<td>${user.age}</td>
						<td>${user.address}</td>
						<td>${user.created_time}</td>
						<td>${user.locked}</td>
						<td><a href="<%=basePath%>getUser.do?id=${user.id}">编辑</a>
							<a href="javascript:del('${user.id }')">删除</a></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</body>
</html>