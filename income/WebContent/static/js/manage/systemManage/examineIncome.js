var contextPath = "${ctx}";
$(function(){
	//查询所有部门部门
	loadDepartment();
});

//查询部门
function loadDepartment() {
	$.ajax({
		url :  "/income/incomeOATask/queryAllDep.do",
		type:"post",
		success:function(data){
			var options="<option value=''>&nbsp;</option>";
			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					options+="<option value='"+data[i].deptCode+"'>"+data[i].deptName+"</option>";
				}
				$("#dept").append(options);
			}
		},
		error:function(){
			$message.alert("提示","操作失败！");
		}
	});
}

//查询人
function loadPerson(){
	var dept=$("#dept").val();
	var userId =$("#userId").val();
	$("#person").html("<option value=''>&nbsp;</option>");
	$("#person").val("");
	if(isNull(dept)){
		$("#person").html("");
		$("#person").attr("disabled","disabled");
		return ;
	}
	$.ajax({
		type : "POST",
		url : "/income/incomeOATask/queryDepPerson.do",
		data : {
			deptId : dept,
			userId:userId
		},
		dataType : "json",
		success : function(data) {//调用成功  
			var options="";
			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					options+="<option value='"+data[i].userId+"'>"+data[i].userName+"</option>";
				}
				$("#person").append(options);
				$("#person").removeAttr("disabled");
			}else{
				$("#person").attr("disabled","disabled");
			}
		},
	});
}

function doSubmitforExamine(val){
	var auditUser=$("#person").val();
	var auditDepartment = $("#dept").val();
	var auditOption=$("#explain").val();
	var billingKey=$("#billingKey").val();
	var code =$("#code").val();
	var userName=$("#userName").val();
	var userId =$("#userId").val();
	if(isNull(auditOption)){
		$message.alert("提示","处理意见不能为空!");
		return;
	}else if(auditOption.length>1000){
		$message.alert("提示","处理意见在1000个字以内!");
		return;
	}
	if(val=='1'){
		if(isNull(auditDepartment)){
			$message.alert("提示","部门不能为空!");
			return;
		}
		if(isNull(auditUser)){
			$message.alert("提示","审核人不能为空!")
			return ;
		}
	}
	disabled();
	var  data = {
				"auditUser":auditUser,
				"auditDept":auditDepartment,
				"auditOption":auditOption,
				"billingKey":billingKey,
				"eastimateStatus":val,
				"code":code,
				"userName":userName,
				"userId":userId
			};
	$.ajax({
		url: '/income/incomeOATask/doIncomeExamine.do',
		type:"post",
		dataType:"json",
		contentType: "application/json; charset=utf-8",
		data:JSON.stringify(data),
		success:function(data,status){
			if(data.status=='1'){
				$message.close();
				undisabled();
			}else{
				$message.alert("提示",data.msg);
				undisabled();
			}
		},
		error:function(){
			$message.alert("提示","审核失败！")
			undisabled();
		}
	});
}

function undisabled(){
	$("#pass1").removeAttr("disabled","disabled"); 
	if($("#pass2")){
		$("#pass2").removeAttr("disabled","disabled");
	}
	$("#fail").removeAttr("disabled","disabled");
}

function disabled(){
	$("#pass1").attr("disabled","disabled");  
	if($("#pass2")){
		$("#pass2").attr("disabled","disabled");
	}
	$("#fail").attr("disabled","disabled");
}



//下载模板文件
function downloadFile() {
	var fileURL=$("#old_file").val()

	$("#myform").submit();  
	$("#downloadForm").form("submit", {
		url : "/income/incomeOATask/downLoad.do",
		data:{"fileURL":fileURL},
		onSubmit : function(param) {
			return this;
		},
		success : function(result) {
			if (null != result && "" != result) {
				$message.alert('提示', "项目附件下载功能异常，文件不存在，请检查！");
			}
		}
	});
}

//下载项目附件
function downloadProjectFile(fileType){
	
	var filename = $('#'+fileType).html();
	var projectId = $('#view_projectId').val();
	
	$("#downloadProjectForm").form("submit", {
        url : "/income/incomeOATask/downLoadProjectFile.do",
        onSubmit : function(param) {
        	param.filename = filename,
        	param.projectId = projectId;
            return this;
        },
        success : function(result) {
        	if (null != result && "" != result)
        	{
        		$message.alert('提示', "项目附件下载功能异常，文件不存在，请检查！",'warning');
        	}
        }
    });
}