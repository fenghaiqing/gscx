/**
 * Created by Administrator on 2018/1/25 0025.
 */
/**
 * Created by Administrator on 2018/1/25 0025.
 */
app.controller('addCtrl',function($scope,$state,$http){

    $scope.events={
        date:null,
        title:null,
        place:null,
        remark:null,
        id:null
    };
    $scope.saveEvents = function(){
        if(!$scope.events){
            return ;
        }
        $http.post('/events',$scope.events).success(function(result){
            if(result!=null){
                $state.go("events");
            }
        }).error(function(){
            alert("操作失败！");
        });
    }

});

