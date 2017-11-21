/**
 * Created by Administrator on 2017/11/8 0008.
 */
app.controller('userCtrl',function($scope,$http,$messager){

    $scope.user=null;
    $scope.query=function(){
        $http.post('editUser/queryByAccount',null)
            .success(function(data){
                if(data.code=='200'){
                    $scope.user=data.result;
                }else{
                    $messager.alert.error('ERROR','操作失败！');
                }
            }).error(function(err){
            $messager.alert.error('ERROR',err);
            });
    }

    $scope.query();

    $scope.update=function(){
        var file=$("#file")[0];
        if($("#file").val()==""||$("#file").val()==null){
            return ;
        }
        var formData = new FormData("#userForm");
        formData.append('hread',file.files[0]);
        formData.append('account',$scope.user.account);
        $.ajax({
            url:'editUser/updateUser',
            type : "post",
            data : formData,
            processData : false,
            contentType : false,
            success : function(data, status) {
                if(data.code=='200'){
                    $messager.alert.success('TIP','操作成功！');
                    $scope.query();
                }
                $scope.query();
            },
            error : function(err) {
                $messager.alert.error('ERROR',err);
            }
        })
    }

    $scope.updateUserInfo=function(){
        $http.post('editUser/updateUserInfo',$scope.user)
            .success(function(data){
                if(data.code=='200'){
                    $scope.query();
                    $messager.alert.success('TIP','操作成功！');
                }
            }).error(function(err){
            $messager.alert.error('ERROR',err);
        });
    }
});