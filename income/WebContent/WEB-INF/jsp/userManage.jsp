<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String ctx = request.getContextPath();
%>
	<head>
		<title>系统管理-用户管理</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/default/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/js/base/jquery-easyui-1.4/themes/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/manage/baseEasyui.css"/>
		<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/base/jquery-easyui-1.4/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/manage/systemManage/userManage.js"></script>
		<script type="text/javascript" src="${ctx}/static/js/backspace.js"></script>
		<script type="text/javascript">
			var contextPath = "${ctx}";
			var lock = 0;//防止重复提交
			$(document).ready(function(){
				document.onkeydown=banBackSpace; 
			}); 
			
        </script>
	</head>
	<body >
	<!-- 默认加载列表 -->
    <table id="list_data">
    </table>
    <div id="tb" style="padding:4px 5px;">
    	<a href='javascript:void(0)'  onclick='addUser();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-add l-btn-icon-left'>新增</span></span></a>
    	
        	&nbsp;&nbsp; 用户名称: <!--<select id="tb_userType" >
		                    	<option value="0">请选择</option>
		                    	<option value="1">省移动人员</option>
		                    	<option value="2">地市移动人员</option>
		                    	<option value="3">区县移动人员</option>
		                    </select> -->
        	&nbsp;&nbsp;<input class="easyui-textbox" type="text" name="userDn" id="userDn" prompt="" data-options="required:false"/>
        <a href='javascript:void(0)'  onclick='searchSubmit();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-search l-btn-icon-left'>查询</span></span></a>
    </div>
    <!-- 角色指派 -->
	<div id="addRole" class="easyui-layout addRole">
		<div style="width:405px;" >
	        <div style="padding-left:90px;padding-top:10px;">
				<form >
					<input type="hidden" id="role_roleId" value=""/>
			        <table cellpadding="5">
		                <tr id="role_user_tr" style="display:none;">
		                    <td>已有角色:</td>
				    		<td ><span id="role_user"></span>&nbsp;&nbsp;<a href='javascript:void(0)'  onclick='dellUserRole();' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-m_delete l-btn-icon-left'>删除角色</span></span></a></td>
				    	</tr>
		                <tr>
		                    <td>角色名称:</td>
				    		<td >
					    		<select name="add_userRole" id="add_userRole" class="easyui-combobox" style="width:100px;">
								</select>
				    		</td>
				    	</tr>
					</table>
				</form>
			</div>
			<div style="text-align:center;">
		    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveRole();">提交</a>
		        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWindow('addRole');">关闭</a>
	        </div>
		</div>
	</div>
	<!-- 修改用户 -->
	<div id="editUsers">
		<div style="width:405px;" >
	        <div style="padding-left:60px;padding-top:10px;">
				<form >
				<input type="hidden" name="userManage_optType" id="" value=""/>
				<input type="hidden" name="mod_userId" id="mod_userId" value=""/>
		        <table cellpadding="5">
		        	<tr>
	                    <td>用户编号:</td>
	                    <td>
	                    	<input id="userId" class="easyui-textbox" type="text" data-options="required:true" missingMessage="用户编号必须含有小写字母或者大写字母、数字"/>
	                    </td>
	                </tr>
	                <tr>
	                    <td>用户名称:</td>
	                    <td>
	                    	<input id="userName" class="easyui-textbox" type="text" data-options="required:true" missingMessage="用户名称必填"/>
	                    </td>
	                </tr>
	                <tr>
	                    <td>手机号码：</td>
	                    <td>
	                    	<input class="easyui-textbox" type="text" id="n_userDn" data-options="required:true" missingMessage="手机号码必填"/>
	                    </td>
	                </tr>
	                
	                <tr>
	                    <td>邮箱地址:</td>
	                    <td>
	                    	<input id="email" class="easyui-textbox" type="text" data-options="required:true" missingMessage="邮箱地址必填"/>
	                    </td>
	                </tr>
	                <tr>
	                    <td>所属部门:</td>
	                    <td >
					    	<select name="edit_dept" id="edit_dept" class="easyui-combobox" style="width:174px;"></select>
				    	</td>
	                </tr>
	                <!--  <tr>
				        <td>定价委员会成员:</td>
				        <td>
				            <span >
				            	<input  type="radio" name="edit_pricingCommittee" value="1">是</input>
				                <input  type="radio" name="edit_pricingCommittee" value="0">否</input>
				            </span>
				        </td>
    				</tr> -->
	                <tr>
	                    <td>启用时间:</td>
	                    <td>
	                    	<input id="n_startDate" type="text" class="easyui-datebox" data-options="editable:false" required="required" missingMessage="启用时间必填" />
	                    </td>
	                </tr>
	                <tr>
	                    <td>停用时间:</td>
	                    <td>
	                    	<input id="n_endDate" type="text" class="easyui-datebox" data-options="editable:false" required="required" missingMessage="停用时间必填"/>
	                    </td>
	                </tr>
	                
	            </table>
		        </form>
	        </div>
		    <div style="text-align:center;">
	         	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="modUser();">提交</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWindow('editUsers');">关闭</a>
	        </div>
		</div>
	</div>
	<!-- 添加用户 -->
	<div id="addUser">
		<div style="width:405px;" >
	        <div style="padding-left:60px;padding-top:10px;">
				<form >
				<input type="hidden" name="userManage_optType" id="" value=""/>
				<input type="hidden" name="mod_userId" id="" value=""/>
		        <table cellpadding="5">
		        	<tr>
		        		<span style="color: red">*此账号需要和咪咕OA账号保持一致</span>
	                    <td>用户编号:</td>
	                    <td>
	                    	<input id="add_userId" class="easyui-textbox" type="text" data-options="required:true" missingMessage="用户编号必填"/>
	                    </td>
	                    
	                </tr>
	                <tr>
	                    <td>用户名称:</td>
	                    <td>
	                    	<input id="add_userName" class="easyui-textbox" type="text" data-options="required:true" missingMessage="用户名称必填"/>
	                    </td>
	                </tr>
	                <tr>
	                    <td>手机号码：</td>
	                    <td>
	                    	<input class="easyui-textbox" type="text" id="add_userDn" data-options="required:true" missingMessage="手机号码必填"/>
	                    </td>
	                </tr>
	                
	                <tr>
	                    <td>邮箱地址:</td>
	                    <td>
	                    	<input id="add_email" class="easyui-textbox" type="text" data-options="required:true" missingMessage="邮箱地址必填"/>
	                    </td>
	                </tr>
	                
	                <tr>
	                    <td>所属部门:</td>
	                    <td >
					    	<select name="add_dept" id="add_dept" class="easyui-combobox" style="width:160px;"></select>
				    	</td>
	                </tr>
	                <!--  <tr>
				        <td>定价委员会成员:</td>
				        <td>
				            <span>
				             <input id="add_pricingCommittee_y" type="radio" name="pricingCommittee" value="1">是</input>
				             <input id="add_pricingCommittee_n" type="radio" name="pricingCommittee" value="0">否</input>
				            </span>
				        </td>
    				</tr> -->
	                
	                <tr>
	                    <td>启用时间:</td>
	                    <td>
	                    	<input id="startDate" type="text" class="easyui-datebox" data-options="editable:false" required="required" missingMessage="启用时间必填" />
	                    </td>
	                </tr>
	                <tr>
	                    <td>停用时间:</td>
	                    <td>
	                    	<input id="endDate" type="text" class="easyui-datebox" data-options="editable:false" required="required" missingMessage="停用时间必填" />
	                    </td>
	                </tr>
	                
	            </table>
		        </form>
	        </div>
		    <div style="text-align:center;">
	         	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveUser();">提交</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWindow('addUser');">关闭</a>
	        </div>
		</div>
	</div>
	</body>
</html>
