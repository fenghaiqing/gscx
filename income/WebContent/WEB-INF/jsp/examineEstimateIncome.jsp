<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="cn.migu.income.pojo.MiguUsers"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String ctx = request.getContextPath();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css">
<script type="text/javascript" src="${ctx}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/backspace.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/manage/systemManage/examineEstimateIncome.js"></script>
<style type="text/css">
.col-sm-12{
background-color: #FFFFFF;
margin-top: 10px;
}
</style>
<title>预估收入审核</title>
</head>
<body style="background-color: #F2F2F2">
	<nav class="navbar navbar-default " role="navigation">
	<div class="container-fluid"
		style="padding-left: 0px; padding-right: 0px;">
		<div class="navbar-header">
			<img src="${ctx}/static/images/manage/head.png" style="width: 100%">
		</div>
	</div>
	</nav>
<div class="container">
	<div class="row">
		<div class="col-sm-12"  >
			<h4 class="h4 text-center"><b>预估收入审核</b></h4>
			<table class="table table-condensed">
					<caption style="color: #2e6da4">
						<b>待审核信息</b>
					</caption>
					<thead>
						<tr>
							<th>项目号</th>
							<th>项目名称</th>
							<th>业务账期</th>
							<th>预估收入报账月份</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${incomeManager.projectId }</td>
							<td>${incomeManager.projectName }</td>
							<td>${incomeManager.cycle }</td>
							<td>${incomeManager.bzCycle }</td>
							<td></td>
						</tr>
					</tbody>
					
					<thead>
						<tr>
							<th>预估收入(含税)</th>
							<th>预估收入(不含税)</th>
							<th>税率</th>
							<th>税额</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><fmt:formatNumber type="number"
									value="${incomeManager.estimateIncome }" pattern="0.00"
									maxFractionDigits="2" /></td>
							<td><fmt:formatNumber type="number"
									value="${incomeManager.estimateExclusiveTax }" pattern="0.00"
									maxFractionDigits="2" /></td>
							<td><fmt:formatNumber type="number"
									value="${incomeManager.estimateIncomeTax }" pattern="0.00"
									maxFractionDigits="2" /></td>
							<td><fmt:formatNumber type="number"
									value="${incomeManager.estimateAmount }" pattern="0.00"
									maxFractionDigits="2" /></td>
							<td></td>
						</tr>
					</tbody>
					<thead>
						<tr>
							<th>请购单或呈批件附件</th>
							<th>OA审批截图</th>
							<th>收入凭证</th>
							<th></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><span>
								<a id="view_requisitionFile"
								href="javascript:void(0)"
								onclick="downloadProjectFile('view_requisitionFile')">${miguProject.requisitionFileName }</a>
								</span>
							</td>
							<td><span><a id="view_oaScreenFile"
								href="javascript:void(0)"
								onclick="downloadProjectFile('view_oaScreenFile')">${miguProject.oaScreenFileName }</a>
								</span>
							</td>
							<td><a href="javascript:void(0)" onclick="downloadFile();"><span
									id="estimateOptionsUrl">${incomeManager.fileName }</span></a>
							</td>
							<td></td>
							<td></td>
						</tr>
					</tbody>
				</table>
				<br><label style="color: #2E6DA4">说明：</label>
					${incomeManager.estimateExplain}
					<br><br>
		</div>
		
			
		
		<div class="col-sm-12"  >
			<table class="table table-condensed">
					<caption style="color: #2e6da4">
						<b>产品明细信息</b>
					</caption>
					<thead>
						<tr>
							<th>产品编号</th>
							<th>产品名称</th>
							<th>售价</th>
							<th>数量</th>
							<th>合计</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${list }">
							<tr>
								<td>${item.productId }</td>
								<td>${item.productName }</td>
								<td><fmt:formatNumber type="number"
										value="${item.sellingPrice }" pattern="0.00"
										maxFractionDigits="2" /></td>
								<td><fmt:formatNumber type="number"
										value="${item.porductNumber }" pattern="0"
										maxFractionDigits="0" /></td>
								<td><fmt:formatNumber type="number"
										value="${item.sellingPrice * item.porductNumber }"
										pattern="0.00" maxFractionDigits="2" /></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
		</div>
		
			<form class="form-horizontal" role="form">
		
			<div class="col-sm-12" style="padding: 10px 5px;">
				<div class="form-group">
					<label for="name" class="col-sm-1 control-label"><span
						style="color: red">*</span>处理<br>意见</label>
					<div class="col-sm-11">
						<textarea class="form-control" id="explain" rows="3"
							placeholder="请输入1000字以内的字符！"></textarea>
					</div>

				</div>
				<div class="form-group">
					<label for="name" class="col-sm-1 control-label">部门</label>
					<div class=" col-sm-2">
						<select class="form-control" id="dept" onchange="loadPerson();"
							name="dept">

						</select>
					</div>
					<label for="name" class="col-sm-1 control-label">审核人</label>
					<div class="col-sm-2">
						<select class="form-control" id="person" name="person"
							disabled="disabled">

						</select>
					</div>
					<div class="col-sm-2"></div>
					<div class="col-sm-4">
						<button type="button" class="btn btn-primary"
							onclick="doSubmitforExamine('1');" id="pass1">审核通过</button>
						<c:if test="${incomeManager.auditDept==1}">
							<button type="button" class="btn btn-success"
								onclick="doSubmitforExamine('2');" id="pass2">审核通过,流程结束</button>
						</c:if>
						<button type="button" class="btn btn-danger"
							onclick="doSubmitforExamine('3');" id="fail">驳回</button>

					</div>
				</div>
			</div>
			
		</form>
		
		<div class="col-sm-12"
				 >
		<table class="table table-condensed">
				<caption style="color: #2e6da4">
					<b>处理信息</b>
				</caption>
				<thead>
					<tr>
						<th>处理人</th>
						<th>处理人部门</th>
						<th>处理时间</th>
						<th>处理结果</th>
						<th>处理意见</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${his }" varStatus="status">
						<tr>
							<td>${item.auditPerson }</td>
							<td>${item.auditDept }</td>
							<td>${item.createDate }</td>
							<c:if test="${item.dealResult==0 }">
								<td>提交审核</td>
							</c:if>
							<c:if test="${item.dealResult==1 }">
								<td>审核通过</td>
							</c:if>
							<c:if test="${item.dealResult==3 }">
								<td>驳回</td>
							</c:if>
							<td><a href="javascript:void(0);" class="tooltip-toggle"
								data-toggle="tooltip" data-placement="bottom"
								title="${item.dealOptions}">${item.options }</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="col-sm-12">
		<div style="display: none;">
			<form id="downloadForm" action="export" method="post">
				<input type="text" name="fileURL" id="old_file"
					value="${incomeManager.estimateOptionsUrl }" />
			</form>
		</div>
		<div style="display: none;">
			<form id="downloadProjectForm" action="export" method="post"></form>
		</div>
		
		<input type="hidden" id="view_projectId"
			value="${incomeManager.projectId }" /> 
		<input type="hidden" id="incomManagerId"
			value="${incomeManager.incomeManagerId}" /> 
		<input type="hidden"
			id="code" value="${code }" /> 
		<input type="hidden" id="userName"
			value="${user.userName }" /> 
		<input type="hidden" id="userId"
			value="${user.userId }" />
		</div>
		</div>
		
	</div>

</body>
</html>