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
        <h3 class="h3 text-center">实际收款出账单</h3>
        <h5 class="h5 col-sm-offset-8"><label style="color: #2e6da4">实际收款编号：</label>${income.billingKey}</h5>
        <h4 class="h4 text-left col-sm-offset-1" style="color: #2e6da4"><b>实际收款详情</b></h4>
        <div class="col-sm-1"></div>
        <div class="col-sm-10">
        <table style="border-collapse:separate; border-spacing:0px 20px;">
            <tr>
            	<td style="text-align:left;font-weight:bold" width="140px">项目号：</td> 
            	<td style="text-align:left" width="250px" >${income.projectId }</td>
            	<td style="text-align:left" width="150px" ></td>
                <td style="text-align:left;font-weight:bold" width="140px">项目名称：</td> 
                <td style="text-align:left" width="250px" >${income.projectName }</td>
              
            </tr>
            <tr>
	            	<td style="text-align:left;font-weight:bold" width="140px">责任人：</td> 
	            	<td style="text-align:left" width="250px" >${miguPrint.projectUser }</td>
	            	<td style="text-align:left" width="150px" ></td>
	                <td style="text-align:left;font-weight:bold" width="140px">所属大类：</td> 
	                <td style="text-align:left" width="250px" >${miguPrint.incomeClass }</td>
	            </tr>
	             <tr>
	            	<td style="text-align:left;font-weight:bold" width="140px">所属部门：</td> 
	            	<td style="text-align:left" width="250px" >${miguPrint.projectDept }</td>
	            	<td style="text-align:left" width="150px" ></td>
	                <td style="text-align:left;font-weight:bold" width="140px">所属小类：</td> 
	                <td style="text-align:left" width="250px" >${miguPrint.incomeSection }</td>
	            </tr>
            <tr>
              <td style="text-align:left;font-weight:bold" width="140px">业务账期：</td>
                <td style="text-align:left" width="250px" >${income.cycle }</td>
                <td style="text-align:left" width="150px" ></td>
                <td style="text-align:left;font-weight:bold" width="140px">开票总金额(元)：</td>
                <td style="text-align:left" width="250px" >${income.billingIncome }</td>
               
        	</tr>
        	<tr>
        	 <td style="text-align:left;font-weight:bold" width="140px">收款日期：</td>
                <td style="text-align:left" width="250px" >${income.incomeDate }</td>
                 <td style="text-align:left" width="150px" ></td>
                <td style="text-align:left;font-weight:bold" width="140px">实际收款(元)：</td>
                <td style="text-align:left" width="250px" >${income.income }</td>
           		
        	</tr>
        	<tr>
        		<td style="text-align:left;font-weight:bold" width="140px">收款凭证：</td>
           		<td style="text-align:left" width="250px" >${income.fileName }</td>
           		 <td style="text-align:left" width="150px" ></td>
                <td style="text-align:left;font-weight:bold" width="140px">请购单或呈批件文件名：</td>
                <td style="text-align:left" width="250px" >${miguProject.requisitionFileName }</td>
              
        	</tr>
        	<tr>  <td style="text-align:left;font-weight:bold" width="140px">OA审批截图文件名：</td> 
            	<td style="text-align:left" width="250px" >${miguProject.oaScreenFileName }</td></tr>
        </table>
        </div>
        <div class="col-sm-1"></div>
    </div>
    
   	<div class="row">
	        <h4 class="h4 text-left col-sm-offset-1" style="color: #2e6da4"><b>发票信息</b></h4>
	        <div class="col-sm-1"></div>
	        <div class="col-sm-10">  <table class="table table-bordered">
	            <thead>
	            <tr>
	                <th class="text-center">序号</th>
	                <th class="text-center">发票代码</th>
	                <th class="text-center">发票号码</th>
	                <th class="text-center">发票金额</th>
	            </tr>
	            </thead>
	            <tbody  class="text-center">
		         <c:forEach var="item" items="${billingList }"  varStatus="status">
		          	<tr>
		                <td>${status.index + 1 }</td>
		                <td>${item.invoiceCode }</td>
		                <td>${item.invoiceNumber }</td>
		                <td><fmt:formatNumber type="number"
												value="${item.total }"
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
             	<c:if test="${item.dealResult==0 }">
				<td>提交审核</td>
				</c:if>
				<c:if test="${item.dealResult==1 }">
				<td>审核通过</td>
				</c:if>
				<c:if test="${item.dealResult==2 }">
				<td>审核通过，流程关闭</td>
				</c:if>
				<c:if test="${item.dealResult==3 }">
				<td>驳回</td>
				</c:if>
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