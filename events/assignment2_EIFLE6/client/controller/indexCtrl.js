/**
 * Created by Administrator on 2018/1/25 0025.
 */
app.controller('indexCtrl',function($scope,$state,$http){

    $scope.startDate="-- -- --";
    $scope.endDate="-- -- --";
    $scope.count=0;
    $scope.queryStart=function(){
        $http.get("/queryStart").success(function(result){
            if(result.length>0){
                $scope.startDate=result[0].date;
            }
        })
    }
    $scope.queryStart();
    $scope.queryEnd=function(){
        $http.get("/queryEnd").success(function(result){
            if(result.length>0){
                $scope.endDate=result[0].date;
            }
        })
    }
    $scope.queryEnd();
    $scope.queryCount=function(){
        $http.get("/queryCount").success(function(result){
            if(result.length>0){
                $scope.count=result[0].total;
            }
        })
    }
    $scope.queryCount();
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


    $scope.getOneEvent=function(id){
                $state.go('edit',{id:id});
    }

});