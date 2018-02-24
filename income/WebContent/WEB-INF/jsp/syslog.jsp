<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String ctx = request.getContextPath();
%>
	<head>
		<title>系统管理-操作日志查询</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/manage/baseEasyui.css"/>
		<!-- <script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.min.js"></script> -->
		<script type="text/javascript" src="${ctx}/static/js/manage/jquery2.0/jquery-2.2.1.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/manage/systemManage/syslogManage.js"></script>
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
    	        用户姓名： <input class="easyui-textbox" type="text" name="userName" id="userName" style="width: 100px;"  data-options="required:false"/>&nbsp;&nbsp;
        	时间：<input id="startDate" name="startDate" type="text" class="easyui-datebox"  data-options="editable:false" />至
        	    <input id="endDate" name="endDate" type="text" class="easyui-datebox"  data-options="editable:false" />&nbsp;&nbsp;
        	操作内容：<input class="easyui-textbox" type="text" name="operation" id="operation" style="width: 150px;"  data-options="required:false"/>&nbsp;&nbsp;
        	<a href='javascript:void(0)'  onclick='searchSubmit();' class='l-btn l-btn-plain'><span class='l-btn-left'>
                  <span class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a>		
		
    </div>

    
   
	</body>
</html>
