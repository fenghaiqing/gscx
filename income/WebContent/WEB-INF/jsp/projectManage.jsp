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
<title>项目管理</title>
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
	src="${ctx}/static/js/base/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/manage/systemManage/projectManage.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/manage/systemManage/priceConfigManage.js"></script>
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
		<div style="display: none;">
		<form id="priceConfigdownloadForm" action="export" method="post"></form>
	</div>
	<!-- 默认加载列表 -->
	<table id="list_data">
	</table>
	<div id="tb" style="padding: 4px 5px;">
		&nbsp;
		
		项目号 <input class="easyui-textbox" type="text" name="q_projectId" id="q_projectId" style="width: 120px;" data-options="required:false" />
		&nbsp;项目名称 <input class="easyui-textbox" type="text" name="q_projectName" id="q_projectName" style="width: 120px;" data-options="required:false" /> 
		&nbsp;收入大类 <select name="q_incomeClassId" id="q_incomeClassId" class="easyui-combobox" style="width: 120px;"></select> 
		&nbsp;收入小类 <select name="q_incomeSectionId" id="q_incomeSectionId" class="easyui-combobox" style="width: 120px;"></select> 
		&nbsp;责任人 <input class="easyui-textbox" type="text" name="q_projectUserName" id="q_projectUserName" style="width: 120px;" data-options="required:false" />
		<%
			if ((currentUserDeptId != null && currentUserDeptId.equals("1")) || currentUserId.equals("0")) {
		%>
		&nbsp;责任部门 <select name="q_projectDeptId" id="q_projectDeptId"
			class="easyui-combobox" style="width: 120px;"></select>
		<%
			}
		%>

		<a href='javascript:void(0)' onclick='searchSubmit();'
			class='l-btn l-btn-plain'><span class='l-btn-left'><span
				class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a> <a
			href='javascript:void(0)' onclick='reset();'
			class='l-btn l-btn-plain'><span class='l-btn-left'><span
				class='l-btn-text icon-no l-btn-icon-left'>重置</span></span></a>
		<br /> <br />
		<%
			if (!currentUserId.equals("0")) {
		%>
		<a href='javascript:void(0)' onclick='addProject();'
			class='l-btn l-btn-plain'><span class='l-btn-left'><span
				class='l-btn-text icon-work-add l-btn-icon-left'>新增项目</span></span></a>
		&nbsp;&nbsp; <a href='javascript:void(0)'
			onclick='associatedContract();' class='l-btn l-btn-plain'><span
			class='l-btn-left'><span
				class='l-btn-text icon-m_add l-btn-icon-left'>关联合同</span></span></a>
				&nbsp;&nbsp; <a href='javascript:void(0)'
			onclick='associatedEstimate();' class='l-btn l-btn-plain'><span
			class='l-btn-left'><span
				class='l-btn-text icon-app-push-send l-btn-icon-left'>关联预算</span></span></a>
		&nbsp;&nbsp; <a href='javascript:void(0)' onclick='addPriceConfig();'
			class='l-btn l-btn-plain'><span class='l-btn-left'><span
				class='l-btn-text icon-m_edit l-btn-icon-left'>新增定价配置</span></span></a>
		&nbsp;&nbsp; <a href='javascript:void(0)'
			onclick='importPriceConfig();' class='l-btn l-btn-plain'><span
			class='l-btn-left'><span
				class='l-btn-text icon-m_import l-btn-icon-left'>导入定价配置</span></span></a>
		&nbsp;&nbsp;
		<%
			}
		%>
	</div>

	<!-- 新增项目 -->
	<div id="addProjectDiv">
		<div style="width: 440px;">
			<div style="padding-left: 60px; padding-top: 10px;">
				<form id="addProjectForm">
					<table cellpadding="5">
						<tr>
							<td><span style="color: red">*</span>项目名称:</td>
							<td><input name="add_projectName" id="add_projectName"
								class="easyui-textbox" type="text" data-options="required:true"
								missingMessage="项目名称必填" style="width: 160px;"/></td>
						</tr>
						<tr>
							<td><span style="color: red">*</span>收入大类:</td>
							<td><select name="add_incomeClassId" id="add_incomeClassId"
								class="easyui-combobox" style="width: 160px;"></select></td>
						</tr>
						<tr>
							<td><span style="color: red">*</span>收入小类:</td>
							<td><select name="add_incomeSectionId"
								id="add_incomeSectionId" class="easyui-combobox"
								style="width: 160px;"></select></td>
						</tr>
						<tr>
							<td><span style="color: red">*</span>责任部门:</td>
							<td><select id="add_projectDeptId" class="easyui-combobox"
								style="width: 160px;"></select></td>
						</tr>
						<tr>
							<td><span style="color: red">*</span>责任人:</td>
							<td><input class="easyui-textbox" type="text"
								disabled="disabled" value="<%=user.getUserName()%>" style="width: 160px;"/></td>
						</tr>
						<tr>
							<td><span style="color: red">*</span>请购单或呈批件:</td>
							<td><input class="easyui-filebox" id="add_requisitionFile"
								name="add_requisitionFile" style="width: 160px;" data-options="buttonText:'选择',prompt:'请选择文件...'" /></td>
						</tr>

						<tr>
							<td><span style="color: red">*</span>OA审批截图:</td>
							<td><input class="easyui-filebox" id="add_oaScreenFile"
								name="add_oaScreenFile" style="width: 160px;" data-options="buttonText:'选择',prompt:'请选择文件...'" /></td>
						</tr>
						<tr>
							<td>项目附件:</td>
							<td><input class="easyui-filebox" id="add_proFileFile"
								name="add_proFileFile" style="width: 160px;" data-options="buttonText:'选择',prompt:'请选择文件...'" /></td>
						</tr>
						<tr>
							<td>说明:</td>
							<td><input name="add_comments" id="add_comments"
								class="easyui-textbox" data-options="multiline:true" type="text"
								style="height: 60px" /></td>
						</tr>
					</table>
				</form>
			</div>
			<div style="text-align: center;">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="submitProject();">提交</a> <a href="javascript:void(0)"
					class="easyui-linkbutton" onclick="closeWindow('addProjectDiv');">关闭</a>
			</div>
		</div>
	</div>

	<!-- 查看项目 -->
	<div id="viewProjectDiv">
		<div style="width: 440px;">
			<div style="padding-left: 60px; padding-top: 10px;">
				<table cellpadding="5">
					<tr>
						<td>项目号:</td>
						<td><input name="view_projectId" id="view_projectId"
							class="easyui-textbox" type="text" readonly="true" /></td>
					</tr>
					<tr>
						<td>项目名称:</td>
						<td><input name="view_projectName" id="view_projectName"
							class="easyui-textbox" type="text" readonly="true" /></td>
					</tr>
					<tr>
						<td>收入大类:</td>
						<td><input name="view_incomeClassName"
							id="view_incomeClassName" class="easyui-textbox" type="text"
							readonly="true" /></td>
					</tr>
					<tr>
						<td>收入小类:</td>
						<td><input name="view_incomeSectionName"
							id="view_incomeSectionName" class="easyui-textbox" type="text"
							readonly="true" /></td>
					</tr>
					<tr>
						<td>责任部门:</td>
						<td><input name="view_projectDeptName"
							id="view_projectDeptName" class="easyui-textbox" type="text"
							readonly="true" /></td>
					</tr>
					<tr>
						<td>责任人:</td>
						<td><input name="view_projectUserName"
							id="view_projectUserName" class="easyui-textbox" type="text"
							readonly="true" /></td>
					</tr>
					<tr>
						<td>请购单或呈批件:</td>
						<td><span><a id="view_requisitionFile"
								href="javascript:void(0)"
								onclick="downloadFile('view_requisitionFile')"></a></span></td>
					</tr>
					<tr>
						<td>OA审批截图:</td>
						<td><span><a id="view_oaScreenFile"
								href="javascript:void(0)"
								onclick="downloadFile('view_oaScreenFile')"></a></span></td>
					</tr>
					<tr>
						<td>项目附件:</td>
						<td><span><a id="view_proFileFile"
								href="javascript:void(0)"
								onclick="downloadFile('view_proFileFile')"></a></span></td>
					</tr>
					<tr>
						<td>说明:</td>
						<td><input name="view_comments" id="view_comments"
							class="easyui-textbox" data-options="multiline:true" type="text"
							style="height: 60px" readonly="true" /></td>
					</tr>
					<tr>
						<td>申请人:</td>
						<td><input name="view_projectApplyUserName"
							id="view_projectApplyUserName" class="easyui-textbox" type="text"
							readonly="true" /></td>
					</tr>
					<tr>
						<td>申请时间:</td>
						<td><input name="view_updateDate" id="view_updateDate"
							class="easyui-textbox" type="text" readonly="true" /></td>
					</tr>
				</table>
			</div>
			<div style="text-align: center;">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="closeWindow('viewProjectDiv');">关闭</a>
			</div>
		</div>
	</div>
	
	
	<!-- 修改项目 -->
	<div id="updateProjectDiv">
		<div style="width: 700px;">
			<div style="padding-left: 60px; padding-top: 10px;">
			  <form id="updateProjectForm">
				<table cellpadding="5">
					<tr>
						<td>项目号:</td>
						<td><input name="update_projectId2" id="update_projectId2"
							class="easyui-textbox" type="text" disabled="disabled" style="width: 160px;"/>
							<input type="hidden" name="update_projectId" id="update_projectId"/>
						</td>
					</tr>
					<tr>
						<td><span style="color: red">*</span>项目名称:</td>
						<td><input name="update_projectName" id="update_projectName"
							class="easyui-textbox" type="text" style="width: 160px;"/></td>
					</tr>
					<tr>
						<td><span style="color: red">*</span>收入大类:</td>
						<td><select name="update_incomeClassId" id="update_incomeClassId"
								class="easyui-combobox" style="width: 160px;"></select></td>
					</tr>
					<tr>
						<td><span style="color: red">*</span>收入小类:</td>
						<td><select name="update_incomeSectionId"
								id="update_incomeSectionId" class="easyui-combobox"
								style="width: 160px;"></select></td>
					</tr>
					<tr>
						<td><span style="color: red">*</span>责任部门:</td>
						<td><input name="update_projectDeptName"
							id="update_projectDeptName" class="easyui-textbox" type="text" disabled="disabled" style="width: 160px;"/></td>
					</tr>
					<tr>
						<td><span style="color: red">*</span>责任人:</td>
						<td><select name="update_projectUserId"
								id="update_projectUserId" class="easyui-combobox"
								style="width: 160px;"></select></td>
					</tr>
					<tr>
						<td><span style="color: red">*</span>请购单或呈批件:</td>
						<td><span><a id="update_requisitionFile2"
								href="javascript:void(0)"
								onclick="downloadFile('update_requisitionFile2')"></a></span></td>
						<td><input class="easyui-filebox" id="update_requisitionFile"
								name="update_requisitionFile" style="width: 160px;" data-options="buttonText:'选择',prompt:'请选择文件...'" /></td>
					</tr>
					<tr>
						<td><span style="color: red">*</span>OA审批截图:</td>
						<td><span><a id="update_oaScreenFile2"
								href="javascript:void(0)"
								onclick="downloadFile('update_oaScreenFile2')"></a></span></td>
						<td><input class="easyui-filebox" id="update_oaScreenFile"
								name="update_oaScreenFile" style="width: 160px;" data-options="buttonText:'选择',prompt:'请选择文件...'" /></td>
					</tr>
					<tr>
						<td>项目附件:</td>
						<td><span><a id="update_proFileFile2"
								href="javascript:void(0)"
								onclick="downloadFile('update_proFileFile2')"></a></span></td>
						<td><input class="easyui-filebox" id="update_proFileFile"
								name="update_proFileFile" style="width: 160px;"  data-options="buttonText:'选择',prompt:'请选择文件...'" /></td>
					</tr>
					<tr>
						<td>说明:</td>
						<td><input name="update_comments" id="update_comments"
							class="easyui-textbox" data-options="multiline:true" type="text"
							style="height: 60px" /></td>
					</tr>
					<tr>
						<td>申请人:</td>
						<td><input name="update_projectApplyUserName"
							id="update_projectApplyUserName" class="easyui-textbox" type="text" disabled="disabled" style="width: 160px;"/></td>
					</tr>
					<tr>
						<td>申请时间:</td>
						<td><input name="update_updateDate" id="update_updateDate"
							class="easyui-textbox" type="text" disabled="disabled" style="width: 160px;"/></td>
					</tr>
				</table>
			 </form>
			</div>
			<div style="text-align: center;">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="doUpdateProject();">提交</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="closeWindow('updateProjectDiv');">关闭</a>
			</div>
		</div>
	</div>

	<!-- 查看关联合同 -->
	<div id="viewAssociatedDiv" style="width: 90%; height: 80%;">
		<table id="list_data_viewAssociated"></table>
		<div id="tb_viewAssociated" style="padding: 4px 5px;">
			<input type="hidden" name="viewAssociated_projectId"
				id="viewAssociated_projectId" /> &nbsp;合同流水号 <input
				class="easyui-textbox" type="text" name="q_conNumber"
				id="q_conNumber" data-options="required:false" /> &nbsp;合同编号 <input
				class="easyui-textbox" type="text" name="q_conNo" id="q_conNo"
				data-options="required:false" /> <a href='javascript:void(0)'
				onclick='search_viewAssociated();' class='l-btn l-btn-plain'><span
				class='l-btn-left'><span
					class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a> <a
				href='javascript:void(0)' onclick='reset_viewAssociated();'
				class='l-btn l-btn-plain'><span class='l-btn-left'><span
					class='l-btn-text icon-no l-btn-icon-left'>重置</span></span></a> <a
				href='javascript:void(0)' onclick='cancelAssociatedContract();'
				class='l-btn l-btn-plain'><span class='l-btn-left'><span
					class='l-btn-text icon-m_add l-btn-icon-left'>取消关联</span></span></a>
		</div>
	</div>

	<!-- 查看关联预算 -->
	<div id="viewAssociatedBudgetDiv" style="width: 90%; height: 80%;">
		<table id="list_data_viewAssociatedBudget"></table>
		<div id="tb_viewAssociatedBudget" style="padding: 4px 5px;">
			<input type="hidden" name="viewAssociatedBudget_projectId"
				id="viewAssociatedBudget_projectId" /> &nbsp;项目预算编号 <input
				class="easyui-textbox" type="text" name="budgetProjectNumber"
				id="budgetProjectNumber" data-options="required:false" />
			&nbsp;项目预算名称 <input class="easyui-textbox" type="text"
				name="budgetProjectName" id="budgetProjectName"
				data-options="required:false" /> <a href='javascript:void(0)'
				onclick='search_viewAssociatedBudget();' class='l-btn l-btn-plain'><span
				class='l-btn-left'><span
					class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a> <a
				href='javascript:void(0)' onclick='reset_viewAssociatedBudget();'
				class='l-btn l-btn-plain'><span class='l-btn-left'><span
					class='l-btn-text icon-no l-btn-icon-left'>重置</span></span></a> <a
				href='javascript:void(0)' onclick='cancelAssociatedBudget();'
				class='l-btn l-btn-plain'><span class='l-btn-left'><span
					class='l-btn-text icon-resourceBatch l-btn-icon-left'>取消关联</span></span></a>
		</div>
	</div>


	<!-- 导入定价配置 -->
	<div id="importPriceConfigDiv" style="width: 42%; height: 42%;">
		<div style="padding: 30px 30px;">
			<form id="exportPricCfg">
				<input type="hidden" name="priceConfig_projectid"
					id="priceConfig_projectid" />
				<table>
					<tr>
						<td><span style="color: red">*</span>定价文件：</td>
						<td><input id="exportExcel" name="pricCfgExcel" class="easyui-filebox" data-options="buttonText:'选择',prompt:'请选择文件...'" ></input></td>
						<td><a href='javascript:void(0)' onclick='downloadPriceConfigFile();'
							class='l-btn l-btn-plain'><span class='l-btn-left'><span
									class='l-btn-text icon-download l-btn-icon-left'>模板下载</span></span></a></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>

					<tr >
						
						<td  colspan="3" align="center">&nbsp;<!-- 增量导入：<input type="radio" id="plus" name="type" >&nbsp;全量导入：<input type="radio" id="all" name="type" > --></td>
						
					</tr>
				
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td colspan="3" align="center"><a href="javascript:void(0)"
							class="easyui-linkbutton" onclick="importExcl()">提交</a>
							&nbsp;&nbsp;&nbsp; <a href="javascript:void(0)"
							class="easyui-linkbutton"
							onclick="closeWindow('importPriceConfigDiv')">取消</a></td>
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
		<input type="hidden" id="opration" /> <input type="hidden"
			id="projectId" />
		<div id="tb_addPriceConfig" style="padding: 4px 5px;">
			<span id="tb_btn_toolbar"> &nbsp; <a href="javascript:void(0)"
				class="easyui-linkbutton"
				data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a>

				<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-m_exzam',plain:true"
				onclick="getChanges('1')">提交</a>
			</span> <span id="tb_btn_view_toolbar"> <input type="hidden"
				name="priceConfigWin_projectId" id="priceConfigWin_projectId" />
				&nbsp;产品编号 <input class="easyui-textbox" type="text"
				name="q_productId" id="q_productId" data-options="required:false" />
				&nbsp;产品名称 <input class="easyui-textbox" type="text"
				name="q_productName" id="q_productName"
				data-options="required:false" /> <a href='javascript:void(0)'
				onclick='search_priceConfigWin();' class='l-btn l-btn-plain'><span
					class='l-btn-left'><span
						class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a> <a
				href='javascript:void(0)' onclick='reset_priceConfigWin();'
				class='l-btn l-btn-plain'><span class='l-btn-left'><span
						class='l-btn-text icon-no l-btn-icon-left'>重置</span></span></a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-m_exzam',plain:true"
				onclick="updatePriceConfig('1')">提交</a>
				<a href='javascript:void(0)'  onclick='exportFile();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-undo l-btn-icon-left'>导出</span></span></a>
			</span>
		</div>
	</div>
</body>
</html>
