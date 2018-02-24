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
		<title>系统管理-部门管理</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/manage/baseEasyui.css"/>
		<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/manage/systemManage/depManage.js"></script>
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
    	<a href='javascript:void(0)'  onclick='addDep();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-add l-btn-icon-left'>新增</span></span></a>
        	&nbsp;&nbsp; 部门名称: 
        	&nbsp;&nbsp;<input class="easyui-textbox" type="text" name="deptName" id="deptName" prompt="" data-options="required:false"/>
        <a href='javascript:void(0)'  onclick='searchSubmit();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a>
    </div>
	<!-- 修改用户 -->
	<div id="editDep">
		<div style="width:405px;" >
	        <div style="padding-left:60px;padding-top:10px;">
				<form >
				<input type="hidden" name="userManage_optType" id="" value=""/>
				<input type="hidden" name="mod_userId" id="mod_userId" value=""/>
		        <table cellpadding="3">
		        	<tr>
	                    <td>部门编号:</td>
	                    <td>
	                    	<input id="edit_deptCode" class="easyui-textbox" type="text" data-options="required:true" missingMessage="部门编号必填"/>
	                    </td>
	                </tr>
	                <tr>
	                    <td>部门名称:</td>
	                    <td>
	                    	<input id="edit_deptName" class="easyui-textbox" type="text" data-options="required:true" missingMessage="部门名称必填"/>
	                    </td>
	                </tr>
	                <tr>
	                    <td>部门描述:</td>
	                    <td>
	                    	<input id="edit_deptDescribe" class="easyui-textbox" data-options="multiline:true" type="text" style="height:60px"/>
	                    </td>
	                </tr>
	            </table>
		        </form>
	        </div>
		    <div style="text-align:center;">
	         	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="modDep();">提交</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWindow('editDep');">关闭</a>
	        </div>
		</div>
	</div>
	<!-- 添加用户 -->
	<div id="addDep">
		<div style="width:405px;" >
	        <div style="padding-left:60px;padding-top:10px;">
				<form >
				<input type="hidden" name="userManage_optType" id="" value=""/>
				<input type="hidden" name="mod_userId" id="" value=""/>
		        <table cellpadding="3">
		        	<tr>
	                    <td>部门编号:</td>
	                    <td>
	                    	<input id="add_deptCode" class="easyui-textbox" type="text" data-options="required:true" missingMessage="部门编号必填"/>
	                    </td>
	                </tr>
	                <tr>
	                    <td>部门名称:</td>
	                    <td>
	                    	<input id="add_deptName" class="easyui-textbox" type="text" data-options="required:true" missingMessage="部门名称必填"/>
	                    </td>
	                </tr>
	                <tr>
	                    <td>部门描述:</td>
	                    <td>
	                    	<input id="add_deptDescribe" class="easyui-textbox" data-options="multiline:true" type="text" style="height:60px"/>
	                    </td>
	                </tr>
	            </table>
		        </form>
	        </div>
		    <div style="text-align:center;">
	         	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveDep();">提交</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWindow('addDep');">关闭</a>
	        </div>
		</div>
	</div>
	</body>
</html>
