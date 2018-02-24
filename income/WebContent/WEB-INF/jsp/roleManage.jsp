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
		<title>系统管理-角色管理</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/manage/baseEasyui.css"/>
		<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/manage/systemManage/roleManage.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/backspace.js"></script>
		<script type="text/javascript">
			var contextPath = "${ctx}";
			$(document).ready(function(){
				document.onkeydown=banBackSpace; 
			}); 
			
        </script>
	</head>
	<body>
		<!-- 初始化加载信息列表 -->
		<table id="list_role" class="list_data">  
		</table>  
		<!-- 角色查询 -->
		<div id="tb" style="padding:4px 5px;">
    	<a href='javascript:void(0)'  onclick='addUser();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-add l-btn-icon-left'>新增</span></span></a>
        	角色名称: <input class="easyui-textbox" type="text" name="roleName" id="roleName"/>
        <a href='javascript:void(0)'  onclick='searchSubmit();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a>
        
    	</div>
		<!-- 添加角色 -->
		<div id="newRole">
		<div style="width:405px;" >
	        <div style="padding-left:60px;padding-top:10px;">
				<form >
				<input type="hidden" name="userManage_optType" id="userManage_optType" value=""/>
			    <input type="hidden" name="mod_roleId" id="mod_roleId" value=""/>
			    <input type="hidden" name="add_roleName_old" id="add_roleName_old" value=""/>
			    <input type="hidden" name="add_userType_old" id="add_userType_old" value=""/>
		        <table cellpadding="5">
	                <tr>
	                    <td>角色名称：</td>
	                    <td>
	                    	<input id="add_roleName" class="easyui-textbox" data-options="required:true" missingMessage="角色名称必填" validType="length[1,15]" invalidMessage="不能超过15个字符！">
	                    </td>
	                </tr>
	            </table>
		        </form>
	        </div>
		    <div style="text-align:center;">
	         	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="optType();">提交</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWindow('newRole');">关闭</a>
	        </div>
		</div>
	</div>
	<div id="funcRole">
		<div style="width:405px;" >
	        <div style="padding-left:60px;padding-top:10px;">
				<form >
					<table cellpadding="5">
		                <tr>
		                    <td>功能菜单：</td>
		                    <td>
		                    	<ul id="tt"></ul>
		                    </td>
		                </tr>
		                </table>
		        </form>
			</div>
		    <div style="text-align:center;">
	         	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="getChecked();">提交</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWindow('funcRole');">关闭</a>
	        </div>
		</div>
	</div>
	</body>
</html>
