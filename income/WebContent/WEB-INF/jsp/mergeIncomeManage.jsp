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
<title>实际收入管理</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/manage/baseEasyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/manage/baseManage.css" />
<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/js/backspace.js"></script>
<script type="text/javascript" src="${ctx}/static/js/manage/systemManage/mergeIncomeManager.js"></script>
<script type="text/javascript">
	var contextPath = "${ctx}";
	var lock = 0;//防止重复提交
	$(document).ready(function() {
		document.onkeydown = banBackSpace;
	});
</script>
</head>
<body>
	<div id="confirm">
		    <div>
		    	<table id="data_info"></table>
		    </div>
		    <div style="text-align: center;margin-top: 30px;margin-bottom: 20px;">
		    	<a href='javascript:void(0)'  onclick='merge();' class='l-btn l-btn-plain'>
			    	<span class='l-btn-left'>
			    		<span class='l-btn-text icon-ok l-btn-icon-left'>确定</span>
			    	</span>
		    	</a>
		        <a href='javascript:void(0)'  onclick="dismiss('confirm');" class='l-btn l-btn-plain'>
			        <span class='l-btn-left'>
			        	<span class='l-btn-text icon-no l-btn-icon-left'>取消</span>
			        </span>
		        </a>
		    </div>
    </div>
	<input type="hidden" name="currentUserDeptId" id="currentUserDeptId" value="<%=currentUserDeptId%>" />
	<input type="hidden" name="currentUserId" id="currentUserId" value="<%=currentUserId%>" />
	
	<div style="display: none;">
		<form id="exportform1" action="export" method="post"></form>
	</div>
		<div style="display: none;">
		<form id="exportform2" action="export" method="post"></form>
	</div>
	<div style="display: none;">
		<form id="downloadAttach" action="export" method="post"></form>
	</div>
	<!-- 默认加载列表 -->
	<table id="list_data" class="easyui-datagrid">
	</table>
	
	<div id="tb" style="padding:4px 5px;">
			        	&nbsp;业务账期
        	<input class="easyui-datebox" name="q_month" id="q_month" style="width: 135px;" data-options="editable:false"/>
        	&nbsp;项目号
        	<input class="easyui-textbox" type="text" name="q_projectId" id="q_projectId" style="width: 135px;" data-options="required:false"/>
        	&nbsp;项目名称
        	<input class="easyui-textbox" type="text" name="q_projectName" id="q_projectName" style="width: 135px;" data-options="required:false"/>
        	<a href='javascript:void(0)'  onclick='searchSubmit();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a>
        	<a href='javascript:void(0)'  onclick='reset();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-no l-btn-icon-left'>重置</span></span></a>
	        <%if(!currentUserId.equals("0")){%>
	        <a href='javascript:void(0)'  onclick='doSearch();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-m-merge l-btn-icon-left'>合并</span></span></a>	
	        	<a href='javascript:void(0)'  onclick='cancelMerge();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-m-un-merge l-btn-icon-left'>取消合并</span></span></a>
	    	<%}%>
	    	<a href='javascript:void(0)'   class='l-btn l-btn-plain smartSearch'><span class='l-btn-left'><span  class='l-btn-text icon-m-angle-down l-btn-icon-left'>高级查询</span></span></a>
	    	<!-- <a href='javascript:void(0)'  onclick='exportFile();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-undo l-btn-icon-left'>导出</span></span></a>
 -->    
 			<div id="smartbr" style="display:none;height: 55px;padding: 8px 5px;">
 			
 			<table>
 				<tr>
			   		<td>报账月份</td>
			   		<td><input class="easyui-datebox" name="bzCycleReal" id="bzCycleReal" style="width: 135px;" data-options="editable:false"/></td>
			   		<td>收入大类</td>
			   		<td><select name="q_incomeClassId" id="q_incomeClassId"  class="easyui-combobox"
						style="width: 135px;" data-options="editable:false"></select></td>
			   		<td>收入小类</td>
			   		<td><select name="q_incomeSectionId" id="q_incomeSectionId"  class="easyui-combobox"
						style="width: 135px;" data-options="editable:false"></select></td>
			   		<td>责任部门</td>
			   		<td><select name="q_dept" id="q_dept" class="easyui-combobox"
						style="width: 135px;" data-options="editable:false"></select></td>
					<td>责任人 </td>
			   		<td><input class="easyui-textbox" type="text" id="q_userName" name="q_userName" style="width: 135px;" data-options="required:false" /></td>
		   		</tr>
		   		<tr>
		   			<td>实际收入（含税）</td>
			   		<td><input class="easyui-numberbox" id="est_income" type="text"
				name="est_income" style="width: 135px;"
				data-options="required:false,min:0,precision:2" /></td>
			   		<td>实际收入（不含税）</td>
			   		<td><input class="easyui-numberbox" id="est_exclusive_tax" type="text"
				name="est_exclusive_tax" style="width: 135px;"
				data-options="required:false,min:0,precision:2" /></td>
			   		<td>是否冲销：</td>
			   		<td><select name="q_cx" id="q_cx" class="easyui-combobox"
						style="width: 135px;" data-options="editable:false">
						<option value="">   &nbsp;</option>
						<option value="1">已冲销</option>
						<option value="0">未冲销</option>
					</select></td>
		   		</tr>
 			
 			</table>
		</div>
	
 </div>
    
  
    <!-- 合并明细 -->
    <div id="hisDiv" >  
        <table id="hisData" class="easyui-datagrid" >  
        </table>  
    </div> 
   

    
    <div id="searchMergeRealIncome">
		<table id="list_data_search" class="easyui-datagrid">
		</table>
		<div id="tb_searchMergeRealIncome" style="padding: 4px 5px;">
			&nbsp;部门 <input class="easyui-combobox" id="dept_id"
				style="width: 135px;" data-options="required:false,editable:false" /> 
				  &nbsp;业务账期：
	        	 <input class="easyui-datebox" name="startDate" id="startDate" style="width: 120px;" validType="start" data-options="editable:false"/>
	        	&nbsp;-&nbsp;
	        	<input class="easyui-datebox" name="endDate" id="endDate" style="width: 120px;" validType="end" data-options="editable:false"/>
				 <a href='javascript:void(0)'
				onclick='searchProjectSubmit();' class='l-btn l-btn-plain'><span
				class='l-btn-left'><span
					class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a> 
					<a href='javascript:void(0)' onclick='searchProjectReset();'
				class='l-btn l-btn-plain'><span class='l-btn-left'><span
					class='l-btn-text icon-no l-btn-icon-left'>重置</span></span></a> <a
				href='javascript:void(0)' onclick='selected();'
				class='l-btn l-btn-plain'><span class='l-btn-left'><span
					class='l-btn-text icon-ok l-btn-icon-left'>确定</span></span></a>
		</div>
	</div>
    
	<div id="selectProject">
			<table id="list_data_project" class="easyui-datagrid"
			</table>
			<div id="tb_searchProject" style="padding: 4px 5px;">
			&nbsp;项目号 <input class="easyui-textbox" id="projectId"
				style="width: 135px;" data-options="required:false" /> 
				&nbsp;项目名称 <input class="easyui-textbox" id="projectName"
				style="width: 135px;" data-options="required:false" /> 
				 <a href='javascript:void(0)'
				onclick='searchProject();' class='l-btn l-btn-plain'><span
				class='l-btn-left'><span
					class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a> 
					<a href='javascript:void(0)' onclick='resetProjectSearch();'
				class='l-btn l-btn-plain'><span class='l-btn-left'><span
					class='l-btn-text icon-no l-btn-icon-left'>重置</span></span></a> <a
				href='javascript:void(0)' onclick='doMerge();'
				class='l-btn l-btn-plain'><span class='l-btn-left'><span
					class='l-btn-text icon-ok l-btn-icon-left'>确定</span></span></a>
		</div>
	</div>
    
  
</body>
</html>
