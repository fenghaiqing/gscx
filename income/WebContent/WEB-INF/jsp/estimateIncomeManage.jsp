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
<title>预估收入管理</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/js/base/jquery-easyui-1.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/js/base/jquery-easyui-1.4/themes/icon.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/manage/baseEasyui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/manage/main.css" />
<script type="text/javascript"
	src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/base/jquery-easyui-1.4/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/js/backspace.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/manage/systemManage/estimateIncomeManage.js"></script>
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
		<form id="exportform2" action="export" method="post"></form>
	</div>
	<div style="display: none;">
		<form id="downloadAttach" action="export" method="post"></form>
	</div>
	<!-- 默认加载列表 -->
	<table id="list_data">
	</table>

	<div id="tb" style="padding: 4px 5px;">
		&nbsp;业务账期 <input class="easyui-datebox" name="q_month" id="q_month"
			style="width: 135px;" data-options="editable:false" /> &nbsp;项目号 <input
			class="easyui-textbox" type="text" name="q_projectId"
			id="q_projectId" style="width: 135px;" data-options="required:false" />
		&nbsp;项目名称 <input class="easyui-textbox" type="text"
			name="q_projectName" id="q_projectName" style="width: 135px;"
			data-options="required:false" /> &nbsp;预估收入状态 <select
			name="q_estimateState" id="q_estimateState" class="easyui-combobox"
			style="width: 135px;" data-options="editable:false">
			<option value="0">草稿</option>
			<option value="1">审核中</option>
			<option value="2">审核通过</option>
			<option value="3">驳回</option>
			<option value="K">&nbsp;</option>
		</select> <a href='javascript:void(0)' onclick='searchSubmit();'
			class='l-btn l-btn-plain'><span class='l-btn-left'><span
				class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a> <a
			href='javascript:void(0)' onclick='reset();'
			class='l-btn l-btn-plain'><span class='l-btn-left'><span
				class='l-btn-text icon-no l-btn-icon-left'>重置</span></span></a>
				<span id="btn-q">
				<%
			if (!currentUserId.equals("0")) {
		%>
		<a href='javascript:void(0)' onclick='doSearch();'
			class='l-btn l-btn-plain'><span class='l-btn-left'><span
				class='l-btn-text icon-work-add l-btn-icon-left'>新增预估收入</span></span></a> <a
			href='javascript:void(0)' onclick='editEstimateIncome();'
			class='l-btn l-btn-plain'><span class='l-btn-left'><span
				class='l-btn-text icon-m_edit l-btn-icon-left'>修改预估收入</span></span></a>
		<%
			}
		%>
		<a href='javascript:void(0)'  onclick='exportFile();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-undo l-btn-icon-left'>导出</span></span></a>
		<a href='javascript:void(0)'   class='l-btn l-btn-plain smartSearch'><span class='l-btn-left'><span  class='l-btn-text icon-m-angle-up l-btn-icon-left'>高级查询</span></span></a>
			</span>	
		<div id="btn-nin" style="padding:auto 10px">
		<%
			if (!currentUserId.equals("0")) {
		%>
		
		<a href='javascript:void(0)' onclick='doSearch();'
			class='l-btn l-btn-plain'><span class='l-btn-left'><span
				class='l-btn-text icon-work-add l-btn-icon-left'>新增预估收入</span></span></a> <a
			href='javascript:void(0)' onclick='editEstimateIncome();'
			class='l-btn l-btn-plain'><span class='l-btn-left'><span
				class='l-btn-text icon-m_edit l-btn-icon-left'>修改预估收入</span></span></a>
		<%
			}
		%>
		<a href='javascript:void(0)'  onclick='exportFile();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-undo l-btn-icon-left'>导出</span></span></a>
		<a href='javascript:void(0)'   class='l-btn l-btn-plain smartSearch'><span class='l-btn-left'><span  class='l-btn-text icon-m-angle-up l-btn-icon-left'>高级查询</span></span></a>
		</div>
		<div id="smartbr" style="display:none;height: 50px;padding: 8px 5px;">
			<table>
				<tr>
					<td>报账月份</td>
					<td><input class="easyui-datebox" name="bzCycle" id="bzCycle"
			style="width: 135px;" data-options="editable:false" /></td>
					<td>收入大类</td>
					<td><select name="q_incomeClassId" id="q_incomeClassId"  class="easyui-combobox"
						style="width: 135px;" data-options="editable:false"></select></td>
					<td>收入小类</td>
					<td><select name="q_incomeSectionId" id="q_incomeSectionId"  class="easyui-combobox"
						style="width: 135px;" data-options="editable:false"></select></td>
					<td>责任部门</td>
					<td><select name="q_dept" id="q_dept" class="easyui-combobox"
						style="width: 135px;" data-options="editable:false">
					</select></td>
				</tr>
				
				<tr>
					<td>责任人</td>
					<td><input class="easyui-textbox" type="text" id="q_userName"
				name="q_userName" style="width: 135px;"
				data-options="required:false" /></td>
					<td>预估收入(含税)</td>
					<td><input class="easyui-numberbox" id="est_income" type="text"
				name="est_income" style="width: 135px;"
				data-options="required:false,min:0,precision:2" /></td>
					<td>预估收入(不含税)</td>
					<td><input class="easyui-numberbox" id="est_exclusive_tax" type="text"
				name="est_exclusive_tax" style="width: 135px;"
				data-options="required:false,min:0,precision:2" /></td>
				</tr>
			</table>
		</div>
	</div>

	<div id="dlg" style="width: 40%; height: auto;">
		<table id="dataDetail" class="easyui-datagrid"
			style="width: 100%; height: 300px">
		</table>
	</div>

	<!-- 新增预估收入 -->
	<div id="addEstimateIncomeDiv">
		<form id="addEstimateIncomeForm">
			<div style="padding: 4px 5px;">
				<table cellpadding="10">
					<tr>
						<td><span style="color: red">*</span>业务账期 :</td>
						<td><input name="cycle" id="add_month" class="easyui-datebox"
							data-options="editable:false" style="width: 100px;"/></td>
						<td><span style="color: red">*</span>预估总收入(含税):</td>
						<td><input name="estimateIncome" id="add_GeneralIncome"
							class="easyui-numberbox" style="width: 160px;"
							data-options="precision:2" readonly="true"></input></td>
						<td ><span style="color: red">*</span>税率:</td> 
						<td>
							<select name="estimateIncomeTax" id="add_estimateIncomeTax" class="easyui-combobox" 
							data-options="editable:false,onChange: function ( id , value ){ addChangeTax ( id , value );} " 
							style="width: 80px;">
								<option value="" selected="selected"></option>
								<option value="0">0</option>
								<option value="0.03">0.03</option>
								<option value="0.06">0.06</option>
								<option value="0.11">0.11</option>
								<option value="0.17">0.17</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td><span style="color: red">*</span>预估总收入(不含税):</td>
						<td><input name="estimateExclusiveTax" id="add_estimateExclusiveTax"
							class="easyui-numberbox" style="width: 160px;"
							data-options="precision:2" readonly="true"></input></td>
							
						<td><span style="color: red">*</span>税额:</td>
						<td><input name="estimateAmount" id="add_estimateAmount"
							class="easyui-numberbox" style="width: 160px;"
							data-options="precision:2" readonly="true"></input></td>	
							
						<td><span style="color: red">*</span>附件:</td>
						<td><input class="easyui-filebox" id="add_file" name="add_file" data-options="buttonText:'选择',prompt:'请选择文件...'" /></td>
					</tr>
				</table>
			</div>
			<input type="hidden" name="projectName" id="projectName" /> <input
				type="hidden" name="projectId" id="projectId" />
			<div style="padding: 4px 5px;">
				<span style="color: red">*</span><span>预估收入明细:</span>
			</div>
			<table id="addEstimateIncomeTable" class="easyui-datagrid"
				data-options="
					iconCls: 'icon-edit',
					singleSelect: true,
					onClickRow: onClickRow">
			</table>
			<input type="hidden" name="eastimateStatus" id="opration" />
			<div id="tb_addEstimateIncome" style="padding: 4px 5px;">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-m_import',plain:true" onclick="importProduct()">导入</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-download',plain:true" onclick="downLoad()">空白模板下载</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-download',plain:true" onclick="downLoadExcel('add')">含产品信息模板下载</a>
			</div>

			<div style="padding: 4px 5px;">
				预估说明: <br /> <input name="estimateExplain" id="handlingSuggestion"
					class="easyui-textbox" data-options="multiline:true" type="text"
					style="height: 60px; width: 100%" />
			</div>
			<div style="padding-left:30%; padding-top: 2%; padding-bottom: 2%;">
				<span>审核部门:</span> <select name="auditDepartment"
					id="Audit_dept" class="easyui-combobox" style="width: 150px;"></select>&nbsp;
				<span>审核人:</span> <select name="auditPerson" id="Audit_person"
					class="easyui-combobox" onclick="checkDept();"
					style="width: 150px;" data-options="editable:false"></select>
			</div>
			<div style="text-align: center; margin-top: 5px;" id="btn-item-add">
				<button  class="easyui-linkbutton" type="button"
					onclick="doSubmit('1');">提交审核</button> <button type="button"
					class="easyui-linkbutton" onclick="doSubmit('0');">保存</button> <button type="button"
					 class="easyui-linkbutton"
					onclick="dismisExamine();">取消</button>
			</div>
		</form>
	</div>

	<!-- start 导入产品信息 -->
	<div id="import-product" style="width: 20%; height: auto; padding: 10px 10px;">
		<form action="" id="import-product-form">
			<div style="margin-left: 5%; margin-top: 20px;">
				<label>产品信息：</label>
				<input class="easyui-filebox" id="product-file" name="product" data-options="buttonText:'选择',prompt:'请选择文件...'"/>
			</div>
			<div style="margin-left: 30%;margin-top: 20%;">
			<button type="button" onclick="submitProductData();">提交</button>
			&nbsp;
			<button type="button" onclick="cancel();">取消</button>
			</div>
		</form>
	</div>
	<div id="import-product-edit" style="width: 20%; height: auto; padding: 10px 10px;">
		<form action="" id="import-product-edit-form">
			<div style="margin-left: 5%; margin-top: 20px;">
				<label>产品信息：</label>
				<input class="easyui-filebox"  id="product-file-edit" name="product" data-options="buttonText:'选择',prompt:'请选择文件...'"/>
			</div>
			<div style="margin-left: 30%;margin-top: 20%;">
			<button type="button" onclick="submitProductInfo();">提交</button>
			&nbsp;
			<button type="button" onclick="cancelEdit();">取消</button>
			</div>
		</form>
	</div>
	<!-- end 导入产品信息 -->

	<!-- 修改预估收入 -->
	<div id="editEstimateIncomeDiv">
		<form id="editEstimateIncomeForm">
			<div style="padding: 4px 5px;">
				<table cellpadding="10">
					<tr>
						<td><span style="color: red">*</span>业务账期 :&nbsp;&nbsp;</td>
						<td><input name="cycle" id="edit_month"
							class="easyui-textbox" readonly="true"
							data-options="editable:false" style="width: 160px;" /></td>
						<td><span style="color: red">*</span>预估总收入:<br/>&nbsp;(含税)</td>
						<td><input name="estimateIncome" id="edit_GeneralIncome"
							class="easyui-numberbox" style="width: 160px;"
							data-options="precision:2" readonly="true"></input></td>
						<td colspan="3"><span style="color: red">*</span>税率:
						
							<select name="estimateIncomeTax" id="edit_estimateIncomeTax" class="easyui-combobox" 
							data-options="editable:false,onChange: function ( id , value ){ editChangeTax ( id , value );} " style="width: 80px;">
								<option value="" selected="selected"></option>
								<option value="0.00">0</option>
								<option value="0.03">0.03</option>
								<option value="0.06">0.06</option>
								<option value="0.11">0.11</option>
								<option value="0.17">0.17</option>
							</select>
						</td>
					</tr>
					
					<tr>
							<td><span style="color: red">*</span>预估总收入:<br/>&nbsp;(不含税)</td>
							<td><input name="estimateExclusiveTax" id="edit_estimateExclusiveTax"
								class="easyui-numberbox" style="width: 160px;"
								data-options="precision:2" readonly="true"></input></td>
								
							<td><span style="color: red">*</span>税额:</td>
							<td><input name="estimateAmount" id="edit_estimateAmount"
								class="easyui-numberbox" style="width: 160px;"
								data-options="precision:2" readonly="true"></input></td>	
								
							<td colspan="3"><span style="color: red">*</span>附件:<a href="javascript:void(0);" onclick="downloadFile();" id="fileURL"></a>
							<input class="easyui-filebox" id="edit_file" name="edit_file" data-options="buttonText:'选择',prompt:'请选择文件...'" /></td>
					</tr>		
				</table>
			</div>
			<input type="hidden" name="projectName" id="edit_projectName" /> <input
				type="hidden" name="incomeManagerId" id="incomeManagerId" />
			<div style="padding: 4px 5px;">
				<span style="color: red">*</span><span>预估收入明细:</span>
			</div>
			<table id="editEstimateIncomeTable" class="easyui-datagrid"
				data-options="
					iconCls: 'icon-edit',
					singleSelect: true,
					onClickRow: onClickRow">
			</table>
			<div id="tb_editEstimateIncome" style="padding: 4px 5px;">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-m_import',plain:true" onclick="editImportProduct()">导入</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-download',plain:true" onclick="downLoad()">空白模板下载</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-download',plain:true" onclick="downLoadExcel('update')">含产品信息模板下载</a>
			</div>

			<div style="padding: 4px 5px;">
				预估说明: <br /> <input name="estimateExplain" id="estimate_explain"
					class="easyui-textbox" data-options="multiline:true" type="text"
					style="height: 60px; width: 100%" />
			</div>
			<div style="padding-left:30%; padding-top: 2%; padding-bottom: 2%;">
				<span>审核部门:</span> <select name="auditDepartment"
					id="edit_Audit_dept" class="easyui-combobox" style="width: 150px;"></select>&nbsp;
				<span>审核人:</span> <select name="auditPerson" id="edit_Audit_person"
					class="easyui-combobox" onclick="checkDept();"
					style="width: 150px;" data-options="editable:false"></select>
			</div>
			<div style="text-align: center; margin-top: 5px;" id="btn-item-edit">
				<button type="button" class="easyui-linkbutton"
					onclick="doSubmitforEdit('1');">提交审核</button> <button
					type="button" class="easyui-linkbutton"
					onclick="doSubmitforEdit('0');">保存</button> <button type="button"
					class="easyui-linkbutton" onclick="dismisExamine();">取消</button>
			</div>
		</form>
		<div style="display: none;">
			<form id="downloadForm" action="export" method="post">
				<input type="text" name="fileURL" id="old_file" />
			</form>
		</div>
	</div>

<!-- 展示审核历史 -->
    <div id="hisDiv" style="width: 80%; height: auto;">  
        <table id="hisData" class="easyui-datagrid" style="width:100%;height:300px">  
        </table>
        <div id="pt" > <a id="prt" target="_Blank"
				class='l-btn l-btn-plain'><span class='l-btn-left'><span
					class='l-btn-text icon-m-print l-btn-icon-left'>打印</span></span></a></div>  
    </div> 
	<div id="searchProject">
		<table id="list_data_searchProject">
		</table>
		<div id="tb_searchProject" style="padding: 4px 5px;">
			&nbsp;项目号 <input class="easyui-textbox" type="text"
				name="searchProject_projectId" id="searchProject_projectId"
				style="width: 135px;" data-options="required:false" /> &nbsp;项目名称 <input
				class="easyui-textbox" type="text" name="searchProject_projectName"
				id="searchProject_projectName" style="width: 135px;"
				data-options="required:false" /> <a href='javascript:void(0)'
				onclick='searchProjectSubmit();' class='l-btn l-btn-plain'><span
				class='l-btn-left'><span
					class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a> <a
				href='javascript:void(0)' onclick='searchProjectReset();'
				class='l-btn l-btn-plain'><span class='l-btn-left'><span
					class='l-btn-text icon-no l-btn-icon-left'>重置</span></span></a> <a
				href='javascript:void(0)' onclick='selected();'
				class='l-btn l-btn-plain'><span class='l-btn-left'><span
					class='l-btn-text icon-ok l-btn-icon-left'>确定</span></span></a>
		</div>
	</div>
</body>
</html>
