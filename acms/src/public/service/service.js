/**
 * Created by Administrator on 2017/11/4 0004.
 */
app.service('Session',function($cookieStore){


            this.user=$cookieStore.get('user');


        this.destroy=function(){
            $cookieStore.remove('user');
            this.user=null;
        }

    }).service('DateFormat',function(){
        Date.prototype.Format = function (fmt) {
            var o = {
                "M+": this.getMonth() + 1, //月份
                "d+": this.getDate(), //日
                "h+": this.getHours(), //小时
                "m+": this.getMinutes(), //分
                "s+": this.getSeconds(), //秒
                "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                "S": this.getMilliseconds() //毫秒
            };
            if (/(y+)/.test(fmt))
                fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o){
                if (new RegExp("(" + k + ")").test(fmt)) {
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                }
            }
            return fmt;
        }
    this.format=function(date){
       return date.Format('yyyy-MM-dd');
    };


}) .service('StringUtile',function(){

        this.isNull=function(str){
            return str=="" || str==null || str==undefined;
        };


    }).service('$messager',function(){

        this.alert={
            success:function(type,msg){
                if($("#mymodal")){
                    $("#mymodal").remove();
                }
                var modal='<div class="modal" id="mymodal">'+
                    '<div class="modal-dialog modal-sm">'+
                    '<div class="modal-content">'+
                    '<div class="modal-header" style="background-color:#6FC146">'+
                    '<button type="button" class="close" data-dismiss="modal">'+
                    '<span aria-hidden="true">×</span><span class="sr-only">'+
                    'Close</span></button>'+
                    '<h4 class="modal-title">'+type+'</h4>'+
                    '</div><div class="modal-body">'+
                    '<p  style="text-align: center;">'+msg+'</p></div>'+
                    '<div class="modal-footer">'+
                    '<button type="button" class="btn btn-default" data-dismiss="modal">close</button>'+
                    '</div></div></div></div>';
                $("body").append(modal);

                $("#mymodal").modal({show:true,backdrop:"static"})
                    .css({
                        "margin-top": function () {
                            return ($(this).height() / 3);
                        }
                    });
            },
            error:function(type,msg){
                if($("#mymodal")){
                    $("#mymodal").remove();
                }
                var modal='<div class="modal" id="mymodal">'+
                    '<div class="modal-dialog modal-sm">'+
                    '<div class="modal-content">'+
                    '<div class="modal-header" style="background-color:#F2DEDE">'+
                    '<button type="button" class="close" data-dismiss="modal">'+
                    '<span aria-hidden="true">×</span><span class="sr-only">'+
                    'Close</span></button>'+
                    '<h4 class="modal-title">'+type+'</h4>'+
                    '</div><div class="modal-body">'+
                    '<p  style="text-align: center;">'+msg+'</p></div>'+
                    '<div class="modal-footer">'+
                    '<button type="button" class="btn btn-default" data-dismiss="modal">close</button>'+
                    '</div></div></div></div>';
                $("body").append(modal);

                $("#mymodal").modal({show:true,backdrop:"static"})
                    .css({
                        "margin-top": function () {
                            return ($(this).height() / 3);
                        }
                    });
            },
            warnning:function(type,msg){
                if($("#mymodal")){
                    $("#mymodal").remove();
                }
                var modal='<div class="modal" id="mymodal">'+
                    '<div class="modal-dialog modal-sm">'+
                    '<div class="modal-content">'+
                    '<div class="modal-header" style="background-color:#FCF8E3">'+
                    '<button type="button" class="close" data-dismiss="modal">'+
                    '<span aria-hidden="true">×</span><span class="sr-only">'+
                    'Close</span></button>'+
                    '<h4 class="modal-title">'+type+'</h4>'+
                    '</div><div class="modal-body">'+
                    '<p  style="text-align: center;">'+msg+'</p></div>'+
                    '<div class="modal-footer">'+
                    '<button type="button" class="btn btn-default" data-dismiss="modal">close</button>'+
                    '</div></div></div></div>';
                $("body").append(modal);

                $("#mymodal").modal({show:true,backdrop:"static"})
                    .css({
                        "margin-top": function () {
                            return ($(this).height() / 3);
                        }
                    });
            },
        }


    })
    .factory('AuthService',function($http,Session,$cookieStore){

    var authService={};

    authService.login=function(user,callBack){
        $http.post("/user/login",user).success(function(data,status){
                if(data.code=='200'){
                  //  Session.create(data.data.account,data.data.name,data.data.role);
                    callBack(data.data,data.code);
                }else{
                    callBack(data.data,data.code);
                }
        }).error(function(err){
            callBack(err,data.code);
        });

    }

    authService.isAuthenticated = function () {
        return !!$cookieStore.get('user');
    };

        authService.isAuthorized = function (authorizedRoles) {
        if (!angular.isArray(authorizedRoles)) {
            authorizedRoles = [authorizedRoles];
        }
        for(var i=0;i<authorizedRoles.length;i++){
            if(authorizedRoles[i]==Session.user.role){
                return true;
            }
        }
        return false;
    };

    return authService;
}) .factory('HttpRequestInterceptor', ['$q', '$injector','$messager', function($q, $injector, $messager) {
    return {
        request: function(config) {

            return config;
        },
        requestError: function(rejection) {
            $messager.alert.error('ERROR','发送请求失败，请检查网络');
            return $q.reject(rejection);
        },
        response: function(resp) {
            if (resp.data!=undefined && resp.data.code !== undefined && resp.data.code !== 0) {
                if (resp.data.code === 5003) {
                    var stateService = $injector.get('$state');
                    stateService.go('login', {}, {
                        reload: true
                    });
                    resp.data.code=200;
                    return resp;
                }
            }
            return resp;
        },
        responseError: function(rejection) {
            console.log(rejection);
            if (rejection.status === 0) {
                $messager.alert.error('ERROR','请求响应错误，请检查网络');
            } else if (rejection.status === 500) {
                $messager.alert.error('ERROR','服务器出错');
            } else {
                $messager.alert.error('ERROR','请求失败，请检查网络');
            }
            return $q.reject(rejection);
        }
    };
}])
    .run(function ($rootScope, $state,AUTH_EVENTS, AuthService,$messager) {
        $rootScope.$on('$stateChangeStart', function (event, toState) {

            if(toState.name=='login')return;// 如果是进入登录界面则允许

                console.log('run...');
            if(!AuthService.isAuthenticated()){
                event.preventDefault();// 取消默认跳转行为
                $state.go("login");//跳转到登录界面
            }else{
                if(toState.data!=undefined){
                    var authorizedRoles = toState.data.authorizedRoles;
                    if(authorizedRoles){
                       if(!AuthService.isAuthorized(authorizedRoles)){
                           event.preventDefault();// 取消默认跳转行为
                           $messager.alert.warnning('Information','没有权限，请联系管理员');
                       }
                    }
                }
            }




        });
    })