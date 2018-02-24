<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="cn.migu.income.pojo.MiguUsers"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String ctx = request.getContextPath();

	session.setAttribute("remuTemplate",
			this.getServletConfig().getServletContext().getRealPath("/reportTemplate"));
	MiguUsers user = (MiguUsers) session.getAttribute("user");
	String currentUserDeptId = user.getDeptId();
	String currentUserId = user.getUserId();
%>
<head>
<title>两非收入明细报表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/manage/baseEasyui.css" />
<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/js/backspace.js"></script>
<script type="text/javascript" src="${ctx}/static/js/manage/systemManage/incomeDetailReport.js"></script>
<script type="text/javascript">
	var contextPath = "${ctx}";
	var lock = 0;//防止重复提交
	$(document).ready(function() {
		document.onkeydown = banBackSpace;
	});
</script>
</head>
<body>
	<input type="hidden" name="currentUserDeptId" id="currentUserDeptId" value="<%=currentUserDeptId%>" />
	<input type="hidden" name="currentUserId" id="currentUserId" value="<%=currentUserId%>" />
	
	<div style="display: none;">
		<form id="exportform1" action="export" method="post"></form>
	</div>
	<div style="display: none;">
		<form id="downloadForm" action="export" method="post"></form>
	</div>
	<!-- 默认加载列表 -->
	<table id="list_data" style="width:100%;">
	</table>

	<div id="tb" style="padding:4px 5px;">
	        &nbsp;业务账期
	        <input class="easyui-datebox" name="q_month_begin" id="q_month_begin" style="width: 135px;"
	               data-options="editable:false"/>
	        &nbsp;-
	        <input class="easyui-datebox" name="q_month_end" id="q_month_end" style="width: 135px;"
	               data-options="editable:false"/>
	        &nbsp;项目号
	        <input class="easyui-textbox" type="text" name="q_projectId" id="q_projectId" style="width: 135px;"
	               data-options="required:false"/>
	        &nbsp;项目名称
	        <input class="easyui-textbox" type="text" name="q_projectName" id="q_projectName" style="width: 135px;"
	               data-options="required:false"/>
        	
        	<a href='javascript:void(0)'  onclick='searchSubmit();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a>
        	<a href='javascript:void(0)'  onclick='reset();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-no l-btn-icon-left'>重置</span></span></a>
	        <a href='javascript:void(0)'  onclick='exportExc();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-download l-btn-icon-left'>导出</span></span></a>
    </div>
</body>
</html>
