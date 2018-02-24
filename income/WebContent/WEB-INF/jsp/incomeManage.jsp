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
<title>实际收款管理</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/manage/baseEasyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/manage/main.css" />
<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/js/backspace.js"></script>
<script type="text/javascript" src="${ctx}/static/js/manage/systemManage/incomeManage.js"></script>
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
		<form id="downloadForm" action="export" method="post">
		<input type="text" name="fileURL" id="old_file"/>
		</form>
	</div>
	<div style="display: none;">
		<form id="downloadAttach" action="export" method="post"></form>
	</div>
	<!-- 默认加载列表 -->
	<table id="list_data">
	</table>

	<div id="tb" style="padding:4px 5px;">
        	&nbsp;业务账期
        	<input class="easyui-datebox" name="q_month" id="q_month" style="width: 135px;" data-options="editable:false"/>
        	&nbsp;项目号
        	<input class="easyui-textbox" type="text" name="q_projectId" id="q_projectId" style="width: 135px;" data-options="required:false"/>
        	&nbsp;项目名称
        	<input class="easyui-textbox" type="text" name="q_projectName" id="q_projectName" style="width: 135px;" data-options="required:false"/>
        	&nbsp;实际收款状态
        	<select name="q_incomeState" id="q_incomeState" class="easyui-combobox" style="width: 135px;" data-options="editable:false">
        		<option value="1">审核中</option>
        		<option value="2">审核通过</option>
        		<option value="3">驳回</option>
        	</select>
        <span id="btn-q">
        	<a href='javascript:void(0)'  onclick='searchSubmit();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a>
        	<a href='javascript:void(0)'  onclick='reset();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-no l-btn-icon-left'>重置</span></span></a>
<%-- 	        <%if(!currentUserId.equals("0")){%>	 --%>
	        	<a href='javascript:void(0)'  onclick='searchProjectSearch();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-m_edit l-btn-icon-left'>新增实际收款</span></span></a>
	        	<a href='javascript:void(0)'  onclick='selected();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-m_edit l-btn-icon-left'>更新实际收款</span></span></a>
<%-- 	    	<%}%> --%>
			<a href='javascript:void(0)'  onclick='exportFile();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-undo l-btn-icon-left'>导出</span></span></a>
    		<a href='javascript:void(0)'   class='l-btn l-btn-plain smartSearch'><span class='l-btn-left'><span  class='l-btn-text icon-m-angle-down l-btn-icon-left'>高级查询</span></span></a>
    	</span>
    	<div id="smartbr" style="display:none;height: 45px;padding: 8px 5px;">
   	<table>
   		<tr>
	   		<td>报账月份</td>
	   		<td><input class="easyui-datebox" name="q_record_month" id="q_record_month" style="width: 135px;"
               data-options="editable:false"/></td>
	   		<td>收入大类</td>
	   		<td><select name="q_incomeClassId" id="q_incomeClassId"  class="easyui-combobox"
						style="width: 135px;" data-options="editable:false"></select>
		</td>
	   		<td>收入小类</td>
	   		<td><select name="q_incomeSectionId" id="q_incomeSectionId"  class="easyui-combobox"
						style="width: 135px;" data-options="editable:false"></select></td>
	   		<td>责任部门</td>
	   		<td><select name="q_dept" id="q_dept" class="easyui-combobox"
						style="width: 135px;" data-options="editable:false">
					</select>
		</td>
		<td>责任人 </td>
	   		<td><input class="easyui-textbox" type="text" id="q_userName"
				name="q_userName" style="width: 135px;"
				data-options="required:false" /></td>
		
   		</tr>
   		<tr>
	   		<td>开票申请编号</td>
	   		<td><input class="easyui-textbox" type="text" id="q_bill_num"
				name="q_bill_num" style="width: 135px;"
				data-options="required:false" /></td>
	   		<td>开票总金额</td>
	   		<td><input class="easyui-textbox" type="text" id="q_bill_total"
				name="q_bill_total" style="width: 135px;"
				data-options="required:false,min:0,precision:2" /></td>
				<td>实际收款</td>
				<td><input class="easyui-numberbox" name="q_income" id="q_income" style="width: 135px;"
               data-options="required:false,min:0,precision:2"/></td>
				<td>实际收款日期</td>
				<td> <input class="easyui-datebox" name="income_date" id="income_date" style="width: 135px;"
               data-options="editable:false"/></td>
   		</tr>
   		
   	</table>
   </div>
   </br>
    	<span id="btn-nin">
        	<a href='javascript:void(0)'  onclick='searchSubmit();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a>
        	<a href='javascript:void(0)'  onclick='reset();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-no l-btn-icon-left'>重置</span></span></a>
<%-- 	        <%if(!currentUserId.equals("0")){%>	 --%>
	        	<a href='javascript:void(0)'  onclick='searchProjectSearch();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-m_edit l-btn-icon-left'>新增实际收款</span></span></a>
	        	<a href='javascript:void(0)'  onclick='selected();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-m_edit l-btn-icon-left'>更新实际收款</span></span></a>
<%-- 	    	<%}%> --%>
			<a href='javascript:void(0)'  onclick='exportFile();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-undo l-btn-icon-left'>导出</span></span></a>
    		<a href='javascript:void(0)'   class='l-btn l-btn-plain smartSearch'><span class='l-btn-left'><span  class='l-btn-text icon-m-angle-down l-btn-icon-left'>高级查询</span></span></a>
    	</span>
    	
    	
    	
    	
    </div>
	
	<!-- 展示审核历史 -->
    <div id="hisDiv" style="width: 80%; height: auto;">  
        <table id="hisData" class="easyui-datagrid" style="width:100%;height:300px">  
        </table>  
        <div id="pt">
        	<a style="margin-right:90%" id="prt" target="_Blank"
				class='l-btn l-btn-plain'><span class='l-btn-left'><span
					class='l-btn-text icon-m-print l-btn-icon-left'>打印</span></span></a>
        </div>
    </div> 
    
    <!-- 更新实际收款 -->
	<div id="editIncomeDiv">
		<div style="padding:4px 5px;text-align: center;margin-top: 5px;"  >
			<table cellpadding="10" style="margin:auto" width="600px">
				<tr>
					<td style="text-align:right;font-weight:bold" width="30px">业务账期:</td>
					<td id="month_act" width="180px" style="text-align:left"></td>
					<td style="text-align:right;font-weight:bold" width="30px">项目号:</td>
					<td id="projectId_act" width="180px" style="text-align:left"></td>
				</tr>
				<tr>
					<td style="text-align:right;font-weight:bold" width="50px">项目名称:</td>
					<td id="projectName_act" width="100px" style="text-align:left"></td>
					<td style="text-align:right;font-weight:bold" width="82px">开票总金额(元):</td>
					<td id="billingIncome" width="100px" style="text-align:left"></td>
				</tr>
			</table>
		</div>
		<form id="editIncomeForm">
		<div style="padding:4px 5px;margin-top: 5px;"  >
				<input type="hidden" name="incomeManagerId" id="incomeManagerId"/>
				<input type="hidden" name="billingKey" id="billingKey"/>
				<table cellpadding="10" style="margin:auto">
							<tr>
								<td><span style="color: red">*</span>实际收款:</td>
								<td><input name="income" id="edit_income" class="easyui-numberbox" style="width: 150px;" data-options="precision:2"></input>(单位：元，精确到小数点后两位)</td>
							</tr>
							<tr>
								<td><span style="color: red">*</span>实际收款日期:</td>
								<td><input class="easyui-datebox" name="q_actualMonth" id="q_actualMonth" style="width: 150px;" data-options="editable:false"/></input></td>
							</tr>	
							<tr>
								<td><span style="color: red">*</span>收款证明附件:</td>
								<td><a href="javascript:void(0);" onclick="downloadFile();" id="fileURL"></a>&nbsp;&nbsp;&nbsp;<input class="easyui-filebox" id="edit_file" name="edit_file" data-options="buttonText:'选择',prompt:'请选择文件...'" /></td>
							</tr>
							<tr>
								<td><span style="color: red">*</span>审核部门:</td>
								<td><select name="Audit_dept" id="Audit_dept" class="easyui-combobox" style="width:150px;"></select>
								</td>
							</tr>
							<tr>
								<td><span style="color: red">*</span>审核人:</td>
								<td><select name="Audit_person" id="Audit_person" class="easyui-combobox" style="width:150px;" data-options="editable:false"></select>
								</td>
							</tr>
				</table>
			</div>
	    	<div style="text-align: center;margin-top: 5px;"  >
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitActual();">提交审核</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="dismisActual();">取消</a> 
			</div>
		</form>
	</div>
	
	<!-- 新增不需开票实际收款框 -->
	<div id="searchProject">
    <table id="list_data_searchProject">
    </table>
    <div id="tb_searchProject" style="padding: 4px 5px;">
        &nbsp;项目号 <input class="easyui-textbox" type="text"
                          name="searchProject_projectId" id="searchProject_projectId"
                          style="width: 135px;" data-options="required:false"/> &nbsp;项目名称 <input
            class="easyui-textbox" type="text" name="searchProject_projectName"
            id="searchProject_projectName" style="width: 135px;"
            data-options="required:false"/> <a href='javascript:void(0)'
                                               onclick='searchProjectSubmit();' class='l-btn l-btn-plain'><span
            class='l-btn-left'><span
            class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a> <a
            href='javascript:void(0)' onclick='searchProjectReset();'
            class='l-btn l-btn-plain'><span class='l-btn-left'><span
            class='l-btn-text icon-no l-btn-icon-left'>重置</span></span></a> <a
            href='javascript:void(0)' onclick='searchProjectSelected();'
            class='l-btn l-btn-plain'><span class='l-btn-left'><span
            class='l-btn-text icon-ok l-btn-icon-left'>确定</span></span></a>
    </div>
	</div>
	
	<!-- 新增实际收款 -->
	<div id="addIncomeDiv">
		<div style="padding:4px 5px;text-align: center;margin-top: 5px;"  >
			<table cellpadding="10" style="margin:auto" width="900px">
				<tr>
					<td style="text-align:left;font-weight:bold" width="30px">业务账期:</td>
					<td id="month_act_add" width="120px" style="text-align:left"></td>
					<td style="text-align:left;font-weight:bold" width="30px">项目号:</td>
					<td id="projectId_act_add" width="120px" style="text-align:left"></td>
					<td style="text-align:left;font-weight:bold" width="40px">项目名称:</td>
					<td id="projectName_act_add" width="110px" style="text-align:left"></td>
				</tr>
			</table>
		</div>
		<form id="addIncomeForm">
		<div style="padding:4px 5px;margin-top: 5px;"  >
				<table cellpadding="10" style="margin:auto">
							<tr>
								<td><span style="color: red">*</span>实际收款:</td>
								<td><input name="income_add" id="edit_income_add" class="easyui-numberbox" style="width: 150px;" data-options="precision:2"></input>(单位：元，精确到小数点后两位)</td>
							</tr>
							<tr>
								<td><span style="color: red">*</span>实际收款日期:</td>
								<td><input class="easyui-datebox" name="q_actualMonth_add" id="q_actualMonth_add" style="width: 150px;" data-options="editable:false"/></input></td>
							</tr>	
							<tr>
								<td><span style="color: red">*</span>收款证明附件:</td>
								<td><input class="easyui-filebox" id="edit_file_add" name="edit_file_add" data-options="buttonText:'选择',prompt:'请选择文件...'" /></td>
							</tr>
							<tr>
								<td><span style="color: red">*</span>审核部门:</td>
								<td><select name="Audit_dept_add" id="Audit_dept_add" class="easyui-combobox" style="width:150px;"></select>
								</td>
							</tr>
							<tr>
								<td><span style="color: red">*</span>审核人:</td>
								<td><select name="Audit_person_add" id="Audit_person_add" class="easyui-combobox" style="width:150px;" data-options="editable:false"></select>
								</td>
							</tr>
				</table>
			</div>
	    	<div style="text-align: center;margin-top: 5px;"  >
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitAddActual();">提交审核</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="dismisActual();">取消</a> 
			</div>
		</form>
	</div>
	
	<!-- 查询发票明细 -->
	<div id="dlg" style="width: 80%; height: auto;">  
        <table id="billDetail" class="easyui-datagrid" style="width:100%;height:300px">  
        </table>  
    </div>
	<input type="hidden" name="Status" id="opration"/>
</body>
</html>
