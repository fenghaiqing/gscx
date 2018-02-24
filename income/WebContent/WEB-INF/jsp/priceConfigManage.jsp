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
		<title>定价配置管理</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/manage/baseEasyui.css"/>
		<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/manage/systemManage/priceConfigManage.js"></script>
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
	<div style="display: none;">
		<form id="downloadForm" action="export" method="post"></form>
	</div>
	<!-- 默认加载列表 -->
    <table id="list_data">
    </table>
    <div id="tb" style="padding:4px 5px;">
        <%if(!currentUserId.equals("0")){%>	
        <a href='javascript:void(0)'  onclick='addPriceConfig();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-add l-btn-icon-left'>新增</span></span></a>
        <a href='javascript:void(0)'  onclick='importPriceConfig();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text  icon-redo l-btn-icon-left'>导入</span></span></a>
        &nbsp;&nbsp;
    	<%}%>
    </div>
    
    <!-- 导入定价配置 -->
	<div id="importPriceConfigDiv" style="width: 42%; height: 42%;">
		<div style="padding: 30px 30px;">
			<form id="exportPricCfg">
				<input type="hidden" name="priceConfig_projectid" id="priceConfig_projectid" />
				<table >
					<tr>
						<td><span style="color: red">*</span>定价文件：</td>
						<td><input id="exportExcel" name="pricCfgExcel" type="file"></input></td>
						<td>
						<a href='javascript:void(0)' onclick='downloadFile();'
							class='l-btn l-btn-plain'><span class='l-btn-left'><span
							class='l-btn-text icon-download l-btn-icon-left'>模板下载</span></span></a>
						</td>
					</tr>
					<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
					
					<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
					<tr>
						<td><span style="color: red">*</span>审核人：</td>
						<td><select id="priceCfgAuditUser" class="easyui-combobox" name="priceCfgAuditUser" style="width: 165px;"></select></td>
					</tr>
					<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
					<tr >
						<td colspan="3" align="center">
							<a href="javascript:void(0)" class="easyui-linkbutton" onclick="importExcl()">提交</a>
							&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWindow('importPriceConfigDiv')">取消</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<!-- 手动新增定价配置 -->
	<div id="addPriceConfigDiv">
		<table id="addPriceConfigTable" class="easyui-datagrid"
			data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				onClickRow: onClickRow
			">
			</table>
			<input type="hidden" id="opration"/>
			<input type="hidden" id="priceConfigId"/>
		<div id="tb_addPriceConfig" style="padding:4px 5px;">
		<span id="tb_btn_toolbar">
			&nbsp;<span style="color: red">*</span>审核人
			<select id="priceConfigAuditUser" class="easyui-combobox" name="priceConfigAuditUser" style="width: 165px;"></select>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" 
				data-options="iconCls:'icon-m_save',plain:true"
				onclick="getChanges('0')">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-m_exzam',plain:true"
				onclick="getChanges('1')">提交</a>
			</span>
			<span id="viewPriceConfigInfo">&nbsp;<span style="color: red">*</span>审核人:
				<span id="viewAuditUser"></span></span>
		</div>
		
			
		
	</div>
	
	</body>
</html>
