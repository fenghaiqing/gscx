//禁用后退键
function banBackSpace(e){ 
	var ev = e || window.event;//获取event对象 
	var obj = ev.target || ev.srcElement;//获取事件源 
	var t = obj.type || obj.getAttribute('type');//获取事件源类型 
	//获取作为判断条件的事件类型 
	var vReadOnly = obj.getAttribute('readonly'); 
	//处理null值情况 
	vReadOnly = (vReadOnly == null) ? false : true; 
	if(ev.keyCode == 8){
		if(t=="password" || t=="text" || t=="textarea"){
			console.log(vReadOnly);
			if(!vReadOnly){
				return true;
			}
		}
	}else{
		return true;
	}
	return false; 
} 
Date.prototype.format = function(fmt) { 
    var o = { 
       "M+" : this.getMonth()+1,                 //月份 
       "d+" : this.getDate(),                    //日 
       "h+" : this.getHours(),                   //小时 
       "m+" : this.getMinutes(),                 //分 
       "s+" : this.getSeconds(),                 //秒 
       "q+" : Math.floor((this.getMonth()+3)/3), //季度 
       "S"  : this.getMilliseconds()             //毫秒 
   }; 
   if(/(y+)/.test(fmt)) {
           fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
   }
    for(var k in o) {
       if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
   return fmt; 
}
//判断是否为空
function isNull(mixed_var) {
	var key;

	if (mixed_var === ""
			|| mixed_var === null || mixed_var === false
			|| typeof mixed_var === 'undefined') {
		return true;
	}

	if (typeof mixed_var == 'object') {
		for (key in mixed_var) {
			return false;
		}
		return true;
	}
	return false;
}

//改变时间控件类型
function changeMonthType(id){
	var q_month = $('#'+id);
	q_month.datebox({
        onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
            span.trigger('click'); //触发click事件弹出月份层
            //fix 1.3.x不选择日期点击其他地方隐藏在弹出日期框显示日期面板
            if (p.find('div.calendar-menu').is(':hidden')) p.find('div.calendar-menu').show();
            if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                tds = p.find('div.calendar-menu-month-inner td');
                tds.click(function (e) {
                    e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                    var year = /\d{4}/.exec(span.html())[0];//得到年份
                    var month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                    q_month.datebox('hidePanel')//隐藏日期对象
                    .datebox('setValue', year + '-' + month); //设置日期的值
                });
            }, 0);
            yearIpt.unbind().hide();//解绑年份输入框中任何事件
            yearSpan.hide();
        },
        parser: function (s) {
            if (!s) return new Date();
            var arr = s.split('-');
            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
        },
        formatter: function (d) {
        		return d.getFullYear() + '-' + (d.getMonth() + 1);
        	},
	});
    var p = q_month.datebox('panel'), //日期选择对象
        tds = false, //日期选择对象中月份
        aToday = p.find('a.datebox-current'),
        yearIpt = p.find('input.calendar-menu-year'),//年份输入框
        //显示月份层的触发控件
        span = aToday.length ? p.find('div.calendar-title span') ://1.3.x版本
        p.find('span.calendar-text'),
        yearSpan=p.find("div.calendar-menu-year-inner span"); //1.4.x版本
    if (aToday.length) {//1.3.x版本，取消Today按钮的click事件，重新绑定新事件设置日期框为今天，防止弹出日期选择面板
        aToday.unbind('click').click(function () {
            var now=new Date();
            q_month.datebox('hidePanel').datebox('setValue', now.getFullYear() + '-' + (now.getMonth() + 1));
        });
    }
}
var $message={
	alert:function(type,msg){
		if($("#mymodal")){
			$("#mymodal").remove();
		}
		var modal='<div class="modal" id="mymodal">'+
		 '<div class="modal-dialog modal-sm">'+
		 '<div class="modal-content">'+
		 '<div class="modal-header">'+
		 '<button type="button" class="close" data-dismiss="modal">'+
		 '<span aria-hidden="true">×</span><span class="sr-only">'+
		 'Close</span></button>'+
		 '<h4 class="modal-title">'+type+'</h4>'+
		 '</div><div class="modal-body">'+
		 '<p  style="text-align: center;">'+msg+'</p></div>'+
		 '<div class="modal-footer">'+
		 '<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>'+
		 '</div></div></div></div>';
		
		$("body").append(modal);
		
     $("#mymodal").modal({show:true,backdrop:"static"})
   .css({
         "margin-top": function () {
             return ($(this).height() / 3);
         }
     });
	},
	close:function(){
		if($("#mymodal")){
			$("#mymodal").remove();
		}
		var modal='<div class="modal" id="mymodal">'+
		 '<div class="modal-dialog modal-sm">'+
		 '<div class="modal-content">'+
		 '<div class="modal-header">'+
		 '<h4 class="modal-title">提示</h4>'+
		 '</div><div class="modal-body">'+
		 '<p  style="text-align: center;">审核成功！</p></div>'+
		 '<div class="modal-footer">'+
		 '<button type="button" id="closeWindow" class="btn btn-primary" >确定</button>'+
		 '</div></div></div></div>';
		
		$("body").append(modal);
		
     $("#mymodal").modal({show:true,backdrop:"static"})
   .css({
         "margin-top": function () {
             return ($(this).height() / 3);
         }
     });
     $("#closeWindow").click(function(){
    	 window.close();
     });
	}
}
