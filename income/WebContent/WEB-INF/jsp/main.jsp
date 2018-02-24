<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
.tab-panel {
	border: none;
	padding: 0 !important; 
	position: relative;
	width: 100%;
	height: 100%;
}

.north-nav {
	width: 250px;
	height: 25px;
	position: absolute;
	right: 0;
	bottom:15px;
}
</style>
<title>两非收入管理系统</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/manage/baseManage.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/manage/baseEasyui.css" />
<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/easyloader.js"></script>
<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/backspace.js"></script>
<script type="text/javascript">
var ctx = "${ctx}";

function openTab(projectId,title,href){
	var iframeName = title+projectId;
	var iframeId = projectId+title;
    var e = $('#home-center-tabs').tabs('exists',iframeName);
    if(e){
        $("#home-center-tabs").tabs('select',iframeName);
        var tab = $("#home-center-tabs").tabs('getSelected');
    }else{
        $('#home-center-tabs').tabs('add',{
            title:iframeName,
            content: "<iframe id=\""+iframeId+"\" class=\"tab-panel\" frameborder=\"0\" scrolling=\"auto\" src=\"\"></iframe>",
            closable:true
        });
        $("#"+iframeId).attr("src",ctx+href);
    }
}

var funcList = ${funcList};
//产生一级和二级菜单对象
function funcSet(funcList){
	var secondFuncData = new Object();
	var firstFuncArr = new Array();
	for(var i=0;i<funcList.length;i++){
		var funcObj = funcList[i];
		var id = funcObj.funcId;
		var text = funcObj.funcName;
		var bossId = funcObj.funcPid;
		var iconCls = funcObj.funcIconClass;
		var funcPageSrc = funcObj.funcPageSrc;
		var obj = {
				"id":id,
				"text":text,
				"bossId":bossId,
				"iconCls":iconCls,
				"url":funcPageSrc,
				"funcIframe":true
			};
		if(bossId==0){
			firstFuncArr.push(obj);
		}else{
			if(secondFuncData[bossId]){
				secondFuncData[bossId].push(obj);
			}else{
				secondFuncData[bossId] = new Array();
				secondFuncData[bossId].push(obj);
			};
		};
	};
	var returnObj = {"firstFuncArr":firstFuncArr,"secondFuncData":secondFuncData};
	return returnObj;
};

$(document).ready(function(){ 
	document.onkeydown=banBackSpace; 
	var westPanel = $('#home-west');
	//首页西面accordion面板
	var homeWestAccPanel = $('<div id="home-west-acc" class="easyui-accordion" data-options="fit:true,border:false"></div>');
	    westPanel.append(homeWestAccPanel);
	if(funcList != null){
		var funcData = funcSet(funcList);
	var firstFuncArr = funcData.firstFuncArr;
	var secondFuncData = funcData.secondFuncData;
	    for(var i=0;i<firstFuncArr.length;i++){
			var funcObj = firstFuncArr[i];
			var id = funcObj.id;
			var text = funcObj.text;
			var bossId = funcObj.bossId;
			var secondFuncArr = secondFuncData[id];
				var firstFunc = $('<div id="home-west-acc-'+id+'" title="'+text+'" iconCls="'+funcObj.iconCls+'"  style="padding-left:-10px;"></div>');
				homeWestAccPanel.append(firstFunc);
				var treeObj = $('<ul id="home-west-acc-tree-'+id+'"></ul>');
				firstFunc.append(treeObj);
				$.ajax({
					dataType: "json",
				    method: "post",
				    data: {"pid":id,"secondFuncData":secondFuncData},
				    url: ctx+"/Users/querySubmenu.do",
					async:false,
					success: function(data, textStatus){
						var str = data.reStr;
				    	var json = $.parseJSON(str);
				    	treeObj.tree({
						    data: json,
							onClick: function(node){
								var funcIframe = node.funcIframe;
								if(funcIframe){
									var funcId = node.id;
									var funcName = node.text;
									var funcPageUrl = node.url;
									
									var tabIdPrefix = 'tab-';
									var tabPanelId = tabIdPrefix+funcId;
								    var homeCenterTabs = $('#home-center-tabs');
									//判断是否存在tab
									var currentTab = null;
									var allTabs = homeCenterTabs.tabs('tabs');
								    for(var i=0; i<allTabs.length; i++){
										var tab = allTabs[i];
										if(tab.panel('options').id==funcId){
											currentTab = tab;
											break;
										};
									};
									if(currentTab==null){//tab不存在
										var tabOpt = {
											id: funcId,
										    title: funcName,
										    content:'',
										    closable:true
										};
										if(funcIframe){//以iframe方式打开页面
											var iframeId = "iframe-"+funcId;
											tabOpt.content = "<iframe id=\""+iframeId+"\" class=\"tab-panel\" frameborder=\"0\" scrolling=\"auto\" src=\"\"></iframe>";
											//创建tab面板
											homeCenterTabs.tabs("add",tabOpt);
											//防止src加载2次
											$("#"+iframeId).attr("src",ctx + funcPageUrl);
										};
									}else{//tab存在
										var index = homeCenterTabs.tabs('getTabIndex',currentTab);
										//将tab设置为选中
										homeCenterTabs.tabs('select', index);
									};
								};
							}
						});
					},
					error: function(){}
					});
				
				/* treeObj.tree({
				    data: secondFuncArr,
					onClick: function(node){
						var funcIframe = node.funcIframe;
						if(funcIframe){
							var funcId = node.id;
							var funcName = node.text;
							var funcPageUrl = node.url;
							
							var tabIdPrefix = 'tab-';
							var tabPanelId = tabIdPrefix+funcId;
						    var homeCenterTabs = $('#home-center-tabs');
							//判断是否存在tab
							var currentTab = null;
							var allTabs = homeCenterTabs.tabs('tabs');
						    for(var i=0; i<allTabs.length; i++){
								var tab = allTabs[i];
								if(tab.panel('options').id==funcId){
									currentTab = tab;
									break;
								};
							};
							if(currentTab==null){//tab不存在
								var tabOpt = {
									id: funcId,
								    title: funcName,
								    content:'',
								    closable:true
								};
								if(funcIframe){//以iframe方式打开页面
									var iframeId = "iframe-"+funcId;
									tabOpt.content = "<iframe id=\""+iframeId+"\" class=\"tab-panel\" frameborder=\"0\" scrolling=\"auto\" src=\"\"></iframe>";
									//创建tab面板
									homeCenterTabs.tabs("add",tabOpt);
									//防止src加载2次
									$("#"+iframeId).attr("src",ctx + funcPageUrl);
								};
							}else{//tab存在
								var index = homeCenterTabs.tabs('getTabIndex',currentTab);
								//将tab设置为选中
								homeCenterTabs.tabs('select', index);
							};
						};
					}
				}); */
		};
	}

	homeWestAccPanel.accordion({'fit':true,'animate':false});
	
	//退出按钮
	var loginOutObj = $('#loginOut');
	loginOutObj.bind('click', function(){
		$.messager.defaults = { ok: "确定", cancel: "取消" };
    	$.messager.confirm('确认操作','您确定要退出吗?',function(r){
    	    if(r){
    	    	window.location.href=ctx+"/Users/logout.do";
    	    };
    	});
    });
    //当前登录人员
    var curLoginUserObj = $('#curLoginUser');
    curLoginUserObj.tooltip({
		position:'top',
	    content:'当前登录人员:${user.userName}'
	});
    

	    
});
</script>
</head>
<body class="easyui-layout">
	<div id="home-north" data-options="region:'north',border:false,height:100">
		<div class="easyui-panel head_north" data-options="fit:true,border:false" style="padding: 15px;">
			<div class="north-nav">
				<div>
				<span style="color:#CCFF33">${user.userName}：欢迎您</span>
					<span><a id="loginOut" href="#" class="easyui-linkbutton" data-options="plain:false,iconCls:'icon-back'">退出</a></span>
					<span><a id="help" href="${ctx}/reportTemplate/help.docx" class="easyui-linkbutton" data-options="plain:false,iconCls:'icon-help'">帮助</a></span>
				</div>
			</div>
		</div>
	</div>
	<div id="home-west" data-options="region:'west',split:true,title:'导航菜单'" style="width: 200px; padding: 0px;"></div>
	<div id="home-south" data-options="region:'south',border:false" class="footer">©咪咕互动娱乐有限公司</div>
	<div id="home-center" data-options="region:'center'">
		<div id="home-center-tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
			<div title="欢迎使用" style="padding: 20px; overflow: hidden;" id="home">
				<span>欢迎您使用中国移动咪咕两非收入管理系统</span>
			</div>
		</div>
	</div>
	<div>
		<input type="hidden" id="sessionUserId" value="${user.userId}" />
	</div>
</body>
</html>