<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="cn.migu.income.pojo.MiguUsers"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String ctx = request.getContextPath();
   
    session.setAttribute("remuTemplate", this.getServletConfig().getServletContext().getRealPath("/reportTemplate"));
    MiguUsers user = (MiguUsers) session.getAttribute("user");
    String currentUserDeptId = user.getDeptId();
    String currentUserId = user.getUserId();
%>
	<head>
		<title>合同管理</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/manage/baseEasyui.css"/>
		<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.easyui.min.js"></script>
		<script type="text/javascript"
	src="${ctx}/static/js/base/jquery-easyui-1.4/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/manage/systemManage/contractManage.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/backspace.js"></script>
		<script type="text/javascript">
			var contextPath = "${ctx}";
			var lock = 0;//防止重复提交
			$(document).ready(function(){
				document.onkeydown=banBackSpace; 
			}); 
        </script>
	</head>
	<body >
	<!-- 默认加载列表 -->
    <table id="list_data">
    </table>
    <div id="tb" style="padding:4px 5px;">
        	&nbsp;合同流水号
        	<input class="easyui-textbox" type="text" name="q_conNumber" id="q_conNumber"  data-options="required:false"/>
        	&nbsp;合同编号
        	<input class="easyui-textbox" type="text" name="q_conNo" id="q_conNo" data-options="required:false" style="width:250px;"/>
        	&nbsp;同步开始时间
        	<input id="q_conStartTime" name="q_conStartTime" type="text" class="easyui-datetimebox"  data-options="editable:false" />
        	&nbsp;同步结束时间
        	<input id="q_conEndTime" name="q_conEndTime" type="text" class="easyui-datetimebox" data-options="editable:false" />
        </br>
        <div align="right" style="padding-right: 80px; padding-top: 5px;">
        	<a href='javascript:void(0)'  onclick='searchSubmit();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a>
        	<a href='javascript:void(0)'  onclick='reset();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-no l-btn-icon-left'>重置</span></span></a>
        </div>
        <%if(!currentUserId.equals("0")){%>	
        <a href='javascript:void(0)'  onclick='doAssociatedContract();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-work-add l-btn-icon-left'>关联</span></span></a>
        &nbsp;&nbsp;
    	<%}%>
    </div>
	</body>
</html>
