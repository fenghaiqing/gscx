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
<title>定价配置审核管理</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/js/base/jquery-easyui-1.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/js/base/jquery-easyui-1.4/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/manage/baseEasyui.css" />
<script type="text/javascript"
	src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/manage/systemManage/priceConfigExamine.js"></script>
<script type="text/javascript" src="${ctx}/static/js/backspace.js"></script>
<script type="text/javascript">
	var contextPath = "${ctx}";
	var lock = 0;//防止重复提交
	$(document).ready(function() {
		document.onkeydown = banBackSpace;
	});
</script>
</head>
<body>
	<input type="hidden" name="currentUserDeptId" id="currentUserDeptId"
		value="<%=currentUserDeptId%>" />
	<input type="hidden" name="currentUserId" id="currentUserId"
		value="<%=currentUserId%>" />
	<div style="display: none;">
		<form id="exportform1" action="export" method="post"></form>
	</div>
	<div style="display: none;">
		<form id="downloadForm" action="export" method="post"></form>
	</div>
	<!-- 默认加载列表 -->
	<table id="list_data">
	</table>
	<div id="tb">
        	&nbsp;项目号
        	<input class="easyui-textbox" type="text" name="q_projectId" id="q_projectId" style="width: 135px;" data-options="required:false"/>
        	&nbsp;责任部门
        	<select id="q_projectDeptId" name="q_projectDeptId" class="easyui-combobox" style="width: 135px;"></select>
        	&nbsp;责任人
        	<input class="easyui-textbox" type="text" name="q_projectUserName" id="q_projectUserName" style="width: 135px;" data-options="required:false"/>
        	&nbsp;审核结果
        	<select id="q_priceConfigAuditResult" name="q_priceConfigAuditResult" class="easyui-combobox" style="width: 135px;" editable="false">
        		<option value="1" selected="selected">待审核</option>
        		<option value="2">审核通过</option>
        		<option value="3">驳回</option>
        	</select>
        	<a href='javascript:void(0)'  onclick='searchSubmit();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a>
        	<a href='javascript:void(0)'  onclick='reset();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-no l-btn-icon-left'>重置</span></span></a>
    </div>

	<!-- 查看待审核的定价配置列表 -->
	<div id="viewPriceConfigInfo" style="width: 95%; height: 80%; padding:4px 5px;">
		<input type="hidden" name="viewPriceConfigInfo_priceConfigId" id="viewPriceConfigInfo_priceConfigId"/>
		<table id="list_data_viewPriceConfigInfo"></table>
	    <div style="padding:4px 5px;">
	    	<span style="color: red">*</span>处理意见:
	    	<br />
	    	<input name="handlingSuggestion" id="handlingSuggestion"
								class="easyui-textbox" data-options="multiline:true" type="text"
								style="height: 60px; width: 100%" />
	    </div>
	    <div style="text-align: center;margin-top: 5px;"  >
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitExamine('2');">审核通过</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitExamine('3');">驳回</a> 
		</div>
    </div>  
    
    <!-- 查看审核历史列表 -->
	<div id="viewPriceConfigInfoHis" style="width: 95%; height: 80%; padding:4px 5px;">
		<table id="list_data_viewPriceConfigInfoHis"></table>
	    <div style="text-align: center; margin-top: 5px;">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWindow('viewPriceConfigInfoHis');">关闭</a> 
		</div>
    </div>  

</body>
</html>
