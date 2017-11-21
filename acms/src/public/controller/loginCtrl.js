/**
 * Created by Administrator on 2017/11/4 0004.
 */
app.controller('loginCtrl',function($scope,$http,$rootScope,$state,$cookieStore, AUTH_EVENTS, AuthService,Session,$messager){
    $scope.user={
        account:'',
        password:''
    };
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() + 0.25);//设置cookie保存30天

    $scope.islogin=false;
    $scope.loginmsg="";
   $scope.login=function(){
       console.log("login user--"+ $scope.user);
      AuthService.login($scope.user,function(data,status){
           console.log("login user--"+ status);
           if(status=='200'){
               $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
               $cookieStore.put("user", {'account':data.account, 'name':data.name
               ,'role':data.role,'desc':data.desc,'tel':data.tel,'hread':data.hread
               }, {'expires': expireDate});
           //    Session.create();
               console.log("login success...");
               $scope.islogin=false;
               $scope.loginmsg="";
               $state.go("index");
           }else{
               $scope.islogin=true;
               $scope.loginmsg="用户名或密码错误，登录失败！"
               $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
           }

       });
   }


});