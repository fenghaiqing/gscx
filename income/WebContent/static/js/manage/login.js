// JavaScript Document
$(function(){
	// 解决session失效重新跳转到登录页面的问题
	if (window != top)
	{
		top.location.href = location.href; 
	}
	
	var input = $("#accounts, #checkCodeValue, #passwd");
	var formerror = $(".form-error");
	var codeerror = $(".code-error");
	
	input.focus(function(){
		if(this.value == this.defaultValue){
		    this.value="";
		}
	});
	
	$(document).ready(function(){  
		$("#checkCodeValue").val("");
	    $('#img-checkcode').bind('click', function () { /**为验证码图片绑定更换验证码事件**/ 
	    	codeerror.html("");
	        $("#img-checkcode").attr("src",'loading.gif');  
	        var url = ctx+'/Users/init.do?method=createCheckCode';  
	        url += "&nowCode=" + (new Date()).getTime();  
	        this.src = url;  
	    });  
	      
	    setTimeout(function(){ /**初始化生成验证码，写个setTimeout是为了查看loading图片效果**/  
	        $("#img-checkcode").attr("src",ctx+'/Users/init.do?method=createCheckCode&nowCode=' + (new Date()).getTime());  
	    },1000);  
	      
	}); 
    $("#login").click(function(event){
        if(!checkcellphone($("#accounts"), formerror)){return false;}
        if(!checkpasswd($("#passwd"), formerror)){return false;}
        if(!checkcode($("#checkCodeValue"), codeerror)){return false;}
        $("#isLoging").show();
        $("#btn_log").hide();
        $.ajax({
        	dataType:"json",
            type:"post",
            url:ctx+"/Users/login.do",
            data:{"username": $("#accounts").val(), "password": $("#passwd").val(), "checkCodeValue": $("#checkCodeValue").val()}
        }).done(function(data){
            if(data.reCode == 100){
                window.location.href=ctx+"/Users/main.do";
            }else if(data.reCode == 101){ 
            	codeerror.html(data.reStr);
            	$("#checkCodeValue").val("");
            	$("#img-checkcode").attr("src",ctx+'/Users/init.do?method=createCheckCode&nowCode=' + (new Date()).getTime());
            	$("#isLoging").hide();
            	$("#btn_log").show();
            	return false;
            }else if(data.reCode == 102){ 
            	codeerror.html(data.reStr);
            	$("#checkCodeValue").val("");
            	$("#img-checkcode").attr("src",ctx+'/Users/init.do?method=createCheckCode&nowCode=' + (new Date()).getTime());
            	$("#isLoging").hide();
            	$("#btn_log").show();
            	return false;
            }else if(data.reCode == 1){ 
            	formerror.html(data.reStr);
            	$("#passwd").val("");
            	$("#checkCodeValue").val("");
            	$("#img-checkcode").attr("src",ctx+'/Users/init.do?method=createCheckCode&nowCode=' + (new Date()).getTime());
            	$("#isLoging").hide();
            	$("#btn_log").show();
            	return false;
            }else if(data.reCode == 9){    	
            	formerror.html(data.reStr);
            	$("#checkCodeValue").val("");
            	$("#img-checkcode").attr("src",ctx+'/Users/init.do?method=createCheckCode&nowCode=' + (new Date()).getTime());
            	$("#isLoging").hide();
            	$("#btn_log").show();
            	return false;
            }else if(data.reCode == 101){    	
            	formerror.html(data.reStr);
            	$("#isLoging").hide();
            	$("#btn_log").show();
            	return false;
            }else{
            	window.location.href=ctx+"/manage/pwdModify.do?userId="+$("#accounts").val();
            }
        });
    });
    
   
    
});
function keyDown(e) {
	var input = $("#accounts, #checkCodeValue, #passwd");
	var formerror = $(".form-error");
	var codeerror = $(".code-error");
	  var ev= window.event||e;
	  //13是键盘上面固定的回车键
	  if (ev.keyCode == 13) {
	        if(!checkcellphone($("#accounts"), formerror)){return false;}
	        if(!checkpasswd($("#passwd"), formerror)){return false;}
	        if(!checkcode($("#checkCodeValue"), codeerror)){return false;}
	        $("#isLoging").show();
	        $("#btn_log").hide();
	        $.ajax({
	        	dataType:"json",
	            type:"post",
	            url:ctx+"/Users/login.do",
	            data:{"username": $("#accounts").val(), "password": $("#passwd").val(), "checkCodeValue": $("#checkCodeValue").val()}
	        }).done(function(data){
	            if(data.reCode == 100){
	                window.location.href=ctx+"/Users/main.do";
	            }else if(data.reCode == 101){ 
	            	codeerror.html(data.reStr);
	            	$("#checkCodeValue").val("");
	            	$("#img-checkcode").attr("src",ctx+'/Users/init.do?method=createCheckCode&nowCode=' + (new Date()).getTime());
	            	$("#isLoging").hide();
	            	$("#btn_log").show();
	            	return false;
	            }else if(data.reCode == 102){ 
	            	codeerror.html(data.reStr);
	            	$("#checkCodeValue").val("");
	            	$("#img-checkcode").attr("src",ctx+'/Users/init.do?method=createCheckCode&nowCode=' + (new Date()).getTime());
	            	$("#isLoging").hide();
	            	$("#btn_log").show();
	            	return false;
	            }else if(data.reCode == 1){ 
	            	formerror.html(data.reStr);
	            	$("#passwd").val("");
	            	$("#checkCodeValue").val("");
	            	$("#img-checkcode").attr("src",ctx+'/Users/init.do?method=createCheckCode&nowCode=' + (new Date()).getTime());
	            	$("#isLoging").hide();
	            	$("#btn_log").show();
	            	return false;
	            }else if(data.reCode == 9){    	
	            	formerror.html(data.reStr);
	            	$("#checkCodeValue").val("");
	            	$("#img-checkcode").attr("src",ctx+'/Users/init.do?method=createCheckCode&nowCode=' + (new Date()).getTime());
	            	$("#isLoging").hide();
	            	$("#btn_log").show();
	            	return false;
	            }else if(data.reCode == 101){    	
	            	formerror.html(data.reStr);
	            	$("#isLoging").hide();
	            	$("#btn_log").show();
	            	return false;
	            }else{
	            	window.location.href=ctx+"/manage/pwdModify.do?userId="+$("#accounts").val();
	            }
	        });
	    
	  }
}
function checkcellphone(jo, joero){
    if($.trim(jo.val()) == "" || jo[0].value == jo[0].defaultValue){
        joero.html("工号不能为空!");
        return false;
    }
    joero.html("");
    return true;
}
function checkpasswd(jo, joero){
	var joVal = $.trim(jo.val());
    if(joVal == "" || jo[0].value == jo[0].defaultValue){
        joero.html("密码不能为空!");
        return false;
    }else if (joVal.length > 16){
    	joero.html("密码不能多于16位!");
        return false;
    }
    joero.html("");
    return true;
}
function checkcode(jo, joero){
    if($.trim(jo.val()) == "" || jo[0].value == jo[0].defaultValue){
        joero.html("验证码不能为空!");
        return false;
    }   
    joero.html("");
    return true;
}

