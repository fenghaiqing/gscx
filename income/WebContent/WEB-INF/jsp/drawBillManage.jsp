<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="cn.migu.income.pojo.MiguUsers" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
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
    <title>开票管理</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/icon.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/manage/baseEasyui.css"/>
    <script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/backspace.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/manage/systemManage/drawBillManage.js"></script>
    <script type="text/javascript">
        var contextPath = "${ctx}";
        var lock = 0;//防止重复提交
        $(document).ready(function () {
            document.onkeydown = banBackSpace;
        });
    </script>
</head>
<body>
<input type="hidden" name="currentUserDeptId" id="currentUserDeptId" value="<%=currentUserDeptId%>"/>
<input type="hidden" name="currentUserId" id="currentUserId" value="<%=currentUserId%>"/>

<div style="display: none;">
    <form id="exportform1" action="export" method="post"></form>
</div>

<!-- 默认加载列表 -->
<table id="list_data">
</table>

<div id="tb" style="padding:4px 5px;">
    <div>
        &nbsp;业务账期
        <input class="easyui-datebox" name="q_month_begin" id="q_month_begin" style="width: 135px;"
               data-options="editable:false"/>
        &nbsp;-
        <input class="easyui-datebox" name="q_month_end" id="q_month_end" style="width: 135px;"
               data-options="editable:false"/>
        &nbsp;项目号
        <input class="easyui-textbox" type="text" name="q_projectId" id="q_projectId" style="width: 135px;"
               data-options="required:false"/>
        &nbsp;项目名称
        <input class="easyui-textbox" type="text" name="q_projectName" id="q_projectName" style="width: 135px;"
               data-options="required:false"/>
        &nbsp;开票审核状态
        <select name="q_billState" id="q_billState" editable="false" class="easyui-combobox"
                data-options="valueField:'id', textField:'text' ,panelHeight:'auto'" style="width: 172px;"></select>
        </select>
        <div id="smartbr" style="display:none;height: 45px;padding: 8px 5px;">
   	<table>
   		<tr>
	   		<td>报账月份</td>
	   		<td>  <input class="easyui-datebox" name="q_month_bill" id="q_month_bill" style="width: 135px;"
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
   		</tr>
   		<tr>
   			<td>责任人 </td>
	   		<td><input class="easyui-textbox" type="text" id="q_userName"
				name="q_userName" style="width: 135px;"
				data-options="required:false" /></td>
	   		<td>开票申请编号</td>
	   		<td><input class="easyui-textbox" type="text" id="q_bill_num"
				name="q_bill_num" style="width: 135px;"
				data-options="required:false" /></td>
	   		<td>开票总金额</td>
	   		<td><input class="easyui-numberbox" type="text" id="q_bill_total"
				name="q_bill_total" style="width: 135px;"
				data-options="required:false,min:0,precision:2" /></td>
   		</tr>
   	</table>
   </div>
   </br>
        <span>
        <a href='javascript:void(0)' onclick='searchSubmit();' class='l-btn l-btn-plain'><span class='l-btn-left'><span
                class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a>
        <a href='javascript:void(0)' onclick='reset();' class='l-btn l-btn-plain'><span class='l-btn-left'><span
                class='l-btn-text icon-no l-btn-icon-left'>重置</span></span></a>
    	<a href='javascript:void(0)'  onclick='exportFile();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-undo l-btn-icon-left'>导出</span></span></a>
    	 <a href='javascript:void(0)' onclick='askSubmit(0);' class='l-btn l-btn-plain'><span class='l-btn-left'><span
                class='l-btn-text icon-add l-btn-icon-left'>专票申请</span></span></a>
        <a href='javascript:void(0)' onclick='askSubmit(1);' class='l-btn l-btn-plain'><span class='l-btn-left'><span
                class='l-btn-text icon-app-push-send l-btn-icon-left'>普票申请</span></span></a>
    	<a href='javascript:void(0)'   class='l-btn l-btn-plain smartSearch'><span class='l-btn-left'><span  class='l-btn-text icon-m-angle-down l-btn-icon-left'>高级查询</span></span></a>
    </span>
    </div>
   
</div>

<div id="dlg" style="width: 40%; height: auto;">
    <table id="dataDetail" class="easyui-datagrid" style="width:100%;height:300px">
    </table>
</div>

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
            href='javascript:void(0)' onclick='selected();'
            class='l-btn l-btn-plain'><span class='l-btn-left'><span
            class='l-btn-text icon-ok l-btn-icon-left'>确定</span></span></a>
    </div>
</div>

<div style="display: none;">
    <form id="downloadForm" action="export" method="post">
        <input type="text" name="fileURL" id="old_file"/>
    </form>
</div>

<div id="billingDataDetailDiv" style="width: 80%; height: 80%;">
	<table id="billingDataDetail" class="easyui-datagrid"
			style="width: 100%; height: 100%">
	</table>
</div>

</body>
</html>
