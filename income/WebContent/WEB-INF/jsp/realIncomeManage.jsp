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
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/manage/main.css" />
<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/js/backspace.js"></script>
<script type="text/javascript" src="${ctx}/static/js/manage/systemManage/realIncomeManage.js"></script>
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
		<form id="exportform2" action="export" method="post"></form>
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
        	&nbsp;实际收入状态
        	<select name="realIncomeStatus" id="realIncomeStatus" class="easyui-combobox" style="width: 135px;" data-options="editable:false">
        		<option value="0">草稿</option>
        		<option value="1">审核中</option>
        		<option value="2">审核通过</option>
        		<option value="3">驳回</option>
        		<option value="K">&nbsp;</option>
        	</select>
        	<a href='javascript:void(0)'  onclick='searchSubmit();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a>
        	<a href='javascript:void(0)'  onclick='reset();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-no l-btn-icon-left'>重置</span></span></a>
	        <span id="btn-q">
	        <%if(!currentUserId.equals("0")){%>
	        <a href='javascript:void(0)'  onclick='doSearch();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-m_edit l-btn-icon-left'>新增实际收入</span></span></a>	
	        	<a href='javascript:void(0)'  onclick='refreshRealIncome();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-m_edit l-btn-icon-left'>更新实际收入</span></span></a>
	    	<%}%>
	    	<a href='javascript:void(0)'  onclick='exportFile();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-undo l-btn-icon-left'>导出</span></span></a>
	    	<a href='javascript:void(0)'   class='l-btn l-btn-plain smartSearch'><span class='l-btn-left'><span  class='l-btn-text icon-m-angle-up l-btn-icon-left'>高级查询</span></span></a>
		</span>
		<div id="btn-nin" style="padding:auto 10px">
		  <%if(!currentUserId.equals("0")){%>
	        <a href='javascript:void(0)'  onclick='doSearch();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-m_edit l-btn-icon-left'>新增实际收入</span></span></a>	
	        	<a href='javascript:void(0)'  onclick='refreshRealIncome();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-m_edit l-btn-icon-left'>更新实际收入</span></span></a>
	    	<%}%>
	    	<a href='javascript:void(0)'  onclick='exportFile();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-undo l-btn-icon-left'>导出</span></span></a>
	    	<a href='javascript:void(0)'   class='l-btn l-btn-plain smartSearch'><span class='l-btn-left'><span  class='l-btn-text icon-m-angle-up  l-btn-icon-left'>高级查询</span></span></a>
		
		</div>
		<div id="smartbr" style="display:none;height: 55px;padding: 8px 5px;">
		<table>
			<tr>
				<td>报账月份</td>
				<td> <input class="easyui-datebox" name="bzCycleReal" id="bzCycleReal" style="width: 135px;" data-options="editable:false"/></td>
				<td>收入大类</td>
				<td><select name="q_incomeClassId" id="q_incomeClassId"  class="easyui-combobox"
						style="width: 135px;" data-options="editable:false"></select></td>
				<td>收入小类 </td>
				<td><select name="q_incomeSectionId" id="q_incomeSectionId"  class="easyui-combobox"
						style="width: 135px;" data-options="editable:false"></select></td>
				<td>责任部门</td>
				<td><select name="q_dept" id="q_dept" class="easyui-combobox"
						style="width: 135px;" data-options="editable:false">
					</select></td>
				<td>责任人</td>
				<td><input class="easyui-textbox" type="text" id="q_userName"
				name="q_userName" style="width: 135px;"
				data-options="required:false" /></td>
			</tr>
			<tr>
				<td>实际收入</br>(含税)</td>
				<td> <input class="easyui-numberbox" id="est_income" type="text"
				name="est_income" style="width: 135px;"
				data-options="required:false,min:0,precision:2" /></td>
				<td>实际收入</br>(不含税)</td>
				<td><input class="easyui-numberbox" id="est_exclusive_tax" type="text"
				name="est_exclusive_tax" style="width: 135px;"
				data-options="required:false,min:0,precision:2" /></td>
				<td>是否冲销</td>
				<td> <select name="q_cx" id="q_cx" class="easyui-combobox"
						style="width: 135px;" data-options="editable:false">
						<option value="">   &nbsp;</option>
				<option value="1">已冲销</option>
				<option value="0">未冲销</option>
					</select></td>
				<td>是否开票</td>
				<td><select name="q_bill" id="q_bill" class="easyui-combobox"
				style="width: 135px;" data-options="editable:false">
				<option value="">   &nbsp;</option>
				<option value="1">是</option>
				<option value="0">否</option>
			</select></td>
				<td>是否合并</td>
				<td><select name="q_merge" id="q_merge" class="easyui-combobox"
				style="width: 135px;" data-options="editable:false">
				<option value="">   &nbsp;</option>
				<option value="1">已合并</option>
				<option value="0">未合并</option>
			</select></td>
				
			</tr>
		</table>
		</div>
	
    </div>
    
    <div id="dlg" style="width: 40%; height: auto;">  
        <table id="dataDetail" class="easyui-datagrid" style="width:100%;height:300px">  
        </table>  
    </div> 
    <!-- 展示审核历史 -->
    <div id="hisDiv" style="width: 80%; height: auto;">  
        <table id="hisData" class="easyui-datagrid" style="width:100%;height:300px">  
        </table>  
        <div id="pt">
        	<a id="prt" target="_Blank"
				class='l-btn l-btn-plain'><span class='l-btn-left'><span
					class='l-btn-text icon-m-print l-btn-icon-left'>打印</span></span></a>
        </div>
    </div> 
     <div id="mergeInfo" style="width: 50%; height: auto;">  
        <table id="merge_Info_data" class="easyui-datagrid" style="width:100%;height:300px">  
        </table>  
    </div> 
    <!-- 刷新实际收入 -->
	<div id="refreshRealIncomeDiv">
		<form id="refreshRealIncomeForm">
			<div style="padding:4px 5px;">
				<table cellpadding="10" >
							<tr align="left">
							
								<td><b>业务账期:</b></td>
								<td><span id="refresh_real_month"></span></td>
								<td colspan="2"><b>项目号:&nbsp;</b>
								<span id="refresh_real_project_Id"></span></td>
								<td><b>项目名称:</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="refresh_real_project_Name"></span>
								</td>
								<td>
									<b id="cxtitle">冲销预估收入</b>&nbsp;&nbsp;<span id="cxIpt"><input type="checkbox" id="updBack" value="1"></span>
								</td>
							</tr>
							<tr>
								<td><span style="color: red">*</span>实际总收入:<br/>&nbsp;(含税)</td>
								<td><input name="realIncome" id="refresh_Real_Income" class="easyui-numberbox" style="width: 160px;" data-options="precision:2" readonly="true"></input></td>
								
								<td colspan="2">
								<span style="color: red">*</span>税率:
									<select name="realIncomeTax" id="refresh_Real_IncomeTax" class="easyui-combobox" 
									data-options="editable:false,onChange: function ( id , value ){ editChangeTax ( id , value );} "
									style="width: 80px;">
										<option value="" selected="selected"></option>
										<option value="0.00">0</option>
										<option value="0.03">0.03</option>
										<option value="0.06">0.06</option>
										<option value="0.11">0.11</option>
										<option value="0.17">0.17</option>
									</select>
								</td>
								
					       		<td colspan="2"><span style="color: red">*</span>是否需要开票：
					                <input type="radio" name="billFlag2" value="1">需开票</input>
					                <input type="radio" name="billFlag2" value="0">不需开票</input>
					        	</td>
							</tr>
							
							<tr>
									<td><span style="color: red">*</span>实际总收入:<br/>&nbsp;(不含税)</td>
									<td><input name="realExclusiveTax" id="refresh_Real_ExclusiveTax"
										class="easyui-numberbox" style="width: 160px;"
										data-options="precision:2" readonly="true"></input></td>
										
									<td colspan="2"><span style="color: red">*</span>税额:
									<input name="realAmount" id="refresh_Real_Amount"
										class="easyui-numberbox" style="width: 160px;"
										data-options="precision:2" readonly="true"></input></td>	
									<td colspan="2">
									<span style="color: red">*</span>附件:<a href="javascript:void(0)" 
									onclick="downloadFile();" id="fileURL"></a>
									<input class="easyui-filebox" id="add_file" name="add_file" data-options="buttonText:'选择',prompt:'请选择文件...'" /></td>
							</tr>							
				</table>
			</div>
			
			<div style="padding:4px 5px;"><span style="color: red">*</span><span>实际收入明细:</span></div>
			<table id="refreshRealIncomeTable" class="easyui-datagrid" 
				data-options="
					iconCls: 'icon-edit',
					singleSelect: true,
					onClickRow: onClickRow">
			</table>
			<div id="tb_refreshRealIncome" style="padding:4px 5px;">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-m_import',plain:true" onclick="editImportProduct()">导入</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-download',plain:true" onclick="downLoad()">空白模板下载</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-download',plain:true" onclick="downLoadExcel('update')">含产品信息模板下载</a>
			</div >
		
			<div style="padding:4px 5px;">
		    	说明:
		    	<br />
		    	<input name="realExplain" id="handlingSuggestion"
									class="easyui-textbox" data-options="multiline:true"  type="text"
									style="height: 60px; width: 100%" />
		    </div>
		    <table cellpadding="10" align="center">
   				<tr>
					<td>审核部门:</td>
					<td colspan="4"><select name="Audit_dept" id="Audit_dept" class="easyui-combobox" style="width:150px;"></select></td>
					<td >审核人:</td>
					<td><select name="Audit_person" id="Audit_person" class="easyui-combobox" style="width:150px;" data-options="editable:false"></select></td>
				</tr>
			</table>
	   		<input type="hidden" name="incomeManagerId" id="incomeManagerId"/>
	   		<input type="hidden" name="projectId" id="refresh_projectId"/>
	    	<div style="text-align: center;margin-top: 5px;" id="btn-item-edit" >
				<button  class="easyui-linkbutton" type="button" onclick="doSubmit('1');">提交审核</button> 
				<button class="easyui-linkbutton" type="button" onclick="doSubmit('0');">保存</button> 
				<button  class="easyui-linkbutton" type="button" onclick="dismisExamine('refreshRealIncomeDiv');">取消</button> 
			</div>
		</form>
	</div>
    
		<div style="display: none;">
		<form id="downloadForm" action="export" method="post">
		<input type="text" name="fileURL" id="old_file"/>
		</form>
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
    
    <!-- 新增实际收入 -->
	<div id="addRealIncomeDiv">
		<form id="addRealIncomeForm">
			<div style="padding: 4px 5px;">
				<table cellpadding="10">
					<tr>
						<td><span style="color: red">*</span>业务账期:</td>
						<td><input name="cycle" id="add_month" class="easyui-datebox"
							data-options="editable:false" /></td>
						<td><span style="color: red">*</span>实际总收入(含税):</td>
						<td><input name="addRealIncome" type="text" id="add_realIncome"
							class="easyui-numberbox" style="width: 160px;"
							data-options="precision:2" readonly="true"></input></td>
						<td><span style="color: red">*</span>税率:</td> 
						<td>
							<select name="realIncomeTax" id="add_realIncomeTax" class="easyui-combobox"
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
						<td><span style="color: red">*</span>实际总收入(不含税):</td>
						<td><input name="realExclusiveTax" id="add_realExclusiveTax"
							class="easyui-numberbox" style="width: 160px;"
							data-options="precision:2" readonly="true"></input></td>
							
						<td><span style="color: red">*</span>税额:</td>
						<td><input name="realAmount" id="add_realAmount"
							class="easyui-numberbox" style="width: 160px;"
							data-options="precision:2" readonly="true"></input></td>	
							
						<td><span style="color: red">*</span>附件:</td>
						<td><input class="easyui-filebox" id="add_real_file" name="add_real_file" data-options="buttonText:'选择',prompt:'请选择文件...'" /></td>
					</tr>	
					<tr>
			       		<td><span style="color: red">*</span>是否需要开票：</td>
			       		<td style="text-align:left">
			            <span>
			                <input type="radio" name="billFlag" value="1" checked>需开票</input>
			                <input type="radio" name="billFlag" value="0">不需开票</input>
			            </span>
			        	</td>
 				   </tr>
				</table>
			</div>
			<input type="hidden" name="projectName" id="projectName" /> <input
				type="hidden" name="projectId" id="projectId" />
			<div style="padding: 4px 5px;">
				<span style="color: red">*</span><span>实际收入明细:</span>
			</div>
			<table id="addRealIncomeTable" class="easyui-datagrid"
				data-options="
					iconCls: 'icon-edit',
					singleSelect: true,
					onClickRow: onClickAddRow">
			</table>
			<div id="tb_addEstimateIncome" style="padding: 4px 5px;">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:true" onclick="addRow()">新增</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-m_import',plain:true" onclick="importProduct()">导入</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-download',plain:true" onclick="downLoad()">空白模板下载</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-download',plain:true" onclick="downLoadExcel('add')">含产品信息模板下载</a>
			</div>

			<div style="padding: 4px 5px;">
				说明: <br /> <input name="realIncomeExplain" id="realIncomeExplain"
					class="easyui-textbox" data-options="multiline:true" type="text"
					style="height: 60px; width: 100%" />
			</div>
			<div style="padding-left:30%; padding-top: 2%; padding-bottom: 2%;">
					<span>审核部门:</span> <select name="auditDept"
					id="auditDept" class="easyui-combobox" style="width: 150px;"></select>&nbsp;
				<span>审核人:</span> <select name="auditPerson" id="auditPerson"
					class="easyui-combobox" onclick="checkDept();"
					style="width: 150px;" data-options="editable:false"></select>
			</div>
			<div style="text-align: center; margin-top: 5px;" id="btn-item-add"  >
				<button class="easyui-linkbutton"  type="button"  onclick="saveRealIncome('1');" >提交审核</button> 
				<button class="easyui-linkbutton"  type="button"   onclick="saveRealIncome('0');" >保存</button> 
				<button class="easyui-linkbutton"  type="button"  onclick="dismisExamine('addRealIncomeDiv');">取消</button>
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
				<input class="easyui-filebox" id="product-file-edit" name="product" data-options="buttonText:'选择',prompt:'请选择文件...'"/>
			</div>
			<div style="margin-left: 30%;margin-top: 20%;">
			<button type="button" onclick="submitProductInfo();">提交</button>
			&nbsp;
			<button type="button" onclick="cancelEdit();">取消</button>
			</div>
		</form>
	</div>
	<!-- end 导入产品信息 -->
</body>
</html>
