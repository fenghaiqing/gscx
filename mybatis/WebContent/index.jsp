<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="./css/bootstrap.min.css">
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="./js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="./js/index.js"></script>
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<div class="row">&nbsp;</div>
		<div class="row">
			<div class="form-group">
				<div class="col-md-8 "></div>
				<div class="col-md-4 ">
					
					<button type="button" onclick="showPritcutr();"
						class="btn btn-default">图形</button>
					<button type="button" onclick="showTable();"
						class="btn btn-primary">报表</button>
				</div>
			</div>
			
		</div>
		
		<div class="row" id="Pictur">
			<div class="col-md-12 ">
				<div class="pfl" id="main-csbhgb"
					style="width: 100%; height: 230px; padding-top: 1%;"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-5 "></div>
			<div class="col-md-2 "></div>
		</div>
		<div class="row"><div class="col-md-8 ">结论：<span id="result"></span></div></div>
		<div class="row" id="tables" style="display: none">
			<div class="col-md-2 "></div>
			<div class="col-md-8 ">
				<table class="table table-bordered">
					<caption>投诉处理即时率 趋势分析</caption>
					<thead>
						<tr>
							<th></th>
							<th>1月</th>
							<th>2月</th>
							<th>3月</th>
							<th>4月</th>
							<th>5月</th>
							<th>6月</th>
							<th>7月</th>
							<th>8月</th>
							<th>9月</th>
							<th>10月</th>
							<th>11月</th>
							<th>12月</th>
						</tr>
					</thead>
					<tbody>
						<tr id="thisyear">

						</tr>
						<tr id="preyear">

						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<script src="./js/echart/build/dist/echarts.js"></script>
	<!--注状图js-->
</body>
</html>