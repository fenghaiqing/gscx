app.controller('aboutCtrl',function($scope,$state,$http) {

    $scope.user={
        id:null,
        name:null,
        neptunId:null,
        email:null
    }
    $scope.getUserInfo=function(){
        $http.get('/user').success(function(result){
            if(result){
                $scope.user =result;
            }

        });
    }
    $scope.getUserInfo();
});