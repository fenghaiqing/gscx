<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String ctx = request.getContextPath();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../static/css/bootstrap.min.css">
<script type="text/javascript" src="../static/js/jquery.min.js"></script>
<script type="text/javascript" src="../static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../static/js/backspace.js"></script>
<script type="text/javascript"
	src="../static/js/base/jquery-easyui-1.4/jquery.easyui.min.js"></script>
  <style type="text/css">
        body {
            background-color: #d5d5d5;
            padding: 10px 10px;
        }

        .container {
            background-color: white;
        }
    </style>
</head>
<body>

<div class="container" >
<div><h1><img src="${ctx}/static/images/manage/migu_index.jpg"></h1></div>
    <div class="row">
        <h3 class="h3 text-center">预估收入出账单</h3>
        <h5 class="h5   col-sm-offset-9"><label style=" color: #2e6da4">预估收入编号：</label>${incomeManager.incomeManagerId}</h5>
        <h4 class="h4 text-left col-sm-offset-1" style="color: #2e6da4"><b>预估收入详情</b></h4>
        <div class="col-sm-1"></div>
        <div class="col-sm-10">
	        <table style="border-collapse:separate; border-spacing:0px 20px;">
	            <tr>
	            	<td style="text-align:left;font-weight:bold" width="100px">项目号：</td> 
	            	<td style="text-align:left" width="250px" >${incomeManager.projectId }</td>
	            	<td style="text-align:left" width="150px" ></td>
	                <td style="text-align:left;font-weight:bold" width="100px">项目名称：</td> 
	                <td style="text-align:left" width="250px" >${incomeManager.projectName }</td>
	            </tr>
	            <tr>
	            	<td style="text-align:left;font-weight:bold" width="100px">业务账期：</td>
	                <td style="text-align:left" width="250px" >${incomeManager.cycle }</td>
	                <td style="text-align:left" width="150px" ></td>
	            	<td style="text-align:left;font-weight:bold" width="100px">预估收入<br>报账月份：</td>
	                <td style="text-align:left" width="250px" >${incomeManager.bzCycle }</td>
	            </tr>
            	<tr>
	            	<td style="text-align:left;font-weight:bold" width="100px">责任人：</td> 
	            	<td style="text-align:left" width="250px" >${miguPrint.projectUser }</td>
	            	<td style="text-align:left" width="150px" ></td>
	                <td style="text-align:left;font-weight:bold" width="100px">所属大类：</td> 
	                <td style="text-align:left" width="250px" >${miguPrint.incomeClass }</td>
	            </tr>
	            <tr>
	            	<td style="text-align:left;font-weight:bold" width="100px">所属部门：</td> 
	            	<td style="text-align:left" width="250px" >${miguPrint.projectDept }</td>
	            	<td style="text-align:left" width="150px" ></td>
	                <td style="text-align:left;font-weight:bold" width="100px">所属小类：</td> 
	                <td style="text-align:left" width="250px" >${miguPrint.incomeSection }</td>
	            </tr>
	            <tr>
	            	<td style="text-align:left;font-weight:bold" width="100px">预估收入<br>(含税)：</td> 
	            	<td style="text-align:left" width="250px" >${estimateIncome }</td>
	                <td style="text-align:left" width="150px" ></td>	            	
	            	<td style="text-align:left;font-weight:bold" width="100px">预估收入<br>(不含税)：</td> 
	            	<td style="text-align:left" width="250px" >${estimateExclusiveTax }</td>
	            </tr>    
	            <tr>
	            	<td style="text-align:left;font-weight:bold" width="100px">税率：</td> 
	            	<td style="text-align:left" width="250px" >${incomeManager.estimateIncomeTax }</td>
	            	<td style="text-align:left" width="150px" ></td>
	            	<td style="text-align:left;font-weight:bold" width="100px">税额：</td> 
	            	<td style="text-align:left" width="250px" >${estimateAmount }</td>
	            </tr>
	            <tr>
	            	<td style="text-align:left;font-weight:bold" width="100px">请购单或<br>呈批件文件名：</td>
	                <td style="text-align:left" width="250px" >${miguProject.requisitionFileName }</td>
	                <td style="text-align:left" width="150px" ></td>
	            	<td style="text-align:left;font-weight:bold" width="100px">OA审批<br>截图文件名：</td> 
	            	<td style="text-align:left" width="250px" >${miguProject.oaScreenFileName }</td>
	            </tr>
	            <tr>
	            	<td style="text-align:left;font-weight:bold" width="100px">收入凭证：</td> 
	                <td style="text-align:left" width="250px" >${incomeManager.fileName }</td>
	            </tr>
	        </table>
            <p style="padding-top: 20px"><label >说明：</label>${incomeManager.estimateExplain }</p>
        </div>
        <div class="col-sm-1"></div>
    </div>
    <div class="row">
        <div class="col-sm-1"></div>
        <div class="col-sm-10">  <table class="table table-bordered">
            <thead>
            <tr>
                <th  class="text-center">产品</th>
                <th class="text-center">售价</th>
                <th class="text-center">件数</th>
                <th class="text-center">合计</th>
            </tr>
            </thead>
            <tbody  class="text-center">
         <c:forEach var="item" items="${deatailList }">
          <tr>
                <td>${item.productName }</td>
                <td>${item.sellingPrice }</td>
                <td><fmt:formatNumber type="number"
										value="${item.porductNumber }"
										pattern="0" maxFractionDigits="0" /></td>
                <td><fmt:formatNumber type="number"
										value="${item.sellingPrice * item.porductNumber }"
										pattern="0.00" maxFractionDigits="2" /></td>
            </tr>
         </c:forEach>
            </tbody>
        </table></div>
        <div class="col-sm-1"></div>

    </div>
    <div class="row">
        <h4 class="h4 text-left col-sm-offset-1" style="color: #2e6da4"><b>处理信息</b></h4>
        <div class="col-sm-1"></div>
        <div class="col-sm-10">  <table class="table table-bordered">
            <thead>
            <tr>
                <th  class="text-center">处理人</th>
                <th class="text-center">处理部门</th>
                <th class="text-center">处理时间</th>
                <th class="text-center">处理结果</th>
                <th class="text-center">处理意见</th>
            </tr>
            </thead>
            <tbody  class="text-center">
            <c:forEach var="item" items="${hisList }">
             <tr>
                <td>${item.auditPerson }</td>
                <td>${item.auditDept }</td>
                <td>${item.createDate }</td>
                <td>${item.dealResult }</td>
                <td>${item.dealOptions }</td>
            </tr>
            </c:forEach>
            </tbody>
        </table></div>
        <div class="col-sm-1"></div>

    </div>
    <div  style="margin-bottom: 40%"></div>
</div>

</body>
</html>