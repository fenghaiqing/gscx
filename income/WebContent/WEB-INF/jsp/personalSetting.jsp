<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/manage/change.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/manage/baseEasyui.css"/>
		<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/manage/systemManage/personalSetting.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/backspace.js"></script>
		<title>个人设置</title>
		<script type="text/javascript">
			$(document).ready(function() {
				document.onkeydown=banBackSpace; 
			});
			var contextPath = "${ctx}";
        </script>
</head>
<body>
	<div class="content_wp1">
		<h2>密码修改</h2>
		<div class="content_box1">
				<table>
					<tbody>
						<tr>
							<th ><font color="red">*</font>原密码</th>
							<td><input id="old_pwd" type="password" class="easyui-textbox" data-options="required:true" missingMessage="请填写原密码" ><input id="userId" value="${personalSetting}" type="hidden" /></td>
						</tr>
						<tr>
							<th><font color="red">*</font>新密码</th>
							<td> <input id="password" name="password" validType="length[6,20]" invalidMessage="密码长度不能低于8位大于20位" class="easyui-textbox" missingMessage="新密码长度不得小于8位，必须含有小写字母或者大写字母、数字、特殊字符（!,@,#,$,&,*）" required="true" type="password" value=""/></td>
						</tr>
						<tr>
							<th><font color="red">*</font>确认密码</th>
							<td><input type="password" name="repassword" id="repassword" validType="length[6,20]" invalidMessage="密码长度不能低于8位大于20位" class="easyui-textbox" missingMessage="请再次输入新密码" required="true" value=""/></td>
						</tr>
						<tr class="table_btn">
							<td colspan="2">
								<input type="button" value="提交修改"  onclick="modPassword();">
								<input type="button" value="重置"  onclick="reset();">
							</td>
						</tr>
					</tbody>
				</table>
		</div>
	</div>
</body>
</html>