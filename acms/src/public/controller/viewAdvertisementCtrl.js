/**
 * Created by Administrator on 2017/11/7 0007.
 */
/**
 * Created by Administrator on 2017/11/7 0007.
 */
app.controller('viewAdvertisementCtrl',function($scope,$http,$state,$stateParams,DateFormat,StringUtile,$messager){

    $scope.project=null;

    $scope.queryParam={};

    $scope.data=null;
    $scope.advertisement=null;

    //查询所有广告
    $scope.query=function(){
        $scope.projectName={};
        if(!StringUtile.isNull($stateParams.id)){
            $scope.queryParam.projectId=$stateParams.id;
        }
        $http.post('advertisement/queryAllAdv', $scope.queryParam)
            .success(function(data){
                if(data.code=='200'){
                    $scope.data=data.result;
                    $scope.advertisement=data.result[0];
                }else{
                    $messager.alert.error('ERROR','操作失败！');
                }
            })
            .error(function(err){
                $messager.alert.error('ERROR',err);
            });
    }
    $scope.query();

    $scope.viewDetail=function(item){
        $scope.advertisement=item;
    }
});