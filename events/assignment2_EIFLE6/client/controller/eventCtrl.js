/**
 * Created by Administrator on 2018/1/25 0025.
 */
/**
 * Created by Administrator on 2018/1/25 0025.
 */
app.controller('eventCtrl',function($scope,$state,$http){



    $scope.data=[];
    $scope.querAllEvents=function(){
        $http.get("/events").success(function(result){
            if(result!=null){
                $scope.data=result
            }
        }).error(function(e){
            alert("操作失败！");
        })
    }
    $scope.querAllEvents();

});
