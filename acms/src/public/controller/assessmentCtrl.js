/**
 * Created by Administrator on 2017/11/8 0008.
 */
app.controller('assessmentCtrl',function($scope,$http,StringUtile,$messager){
    $scope.isAdd=false;
    $scope.data=null;
    $scope.queryParam={};
    $scope.assessment=null;
    $scope.query=function(){
        $scope.queryParam={};
        if(!StringUtile.isNull($scope.name)){
            $scope.queryParam.name=$scope.name;
        }
        if(!StringUtile.isNull($scope.month)){
            $scope.queryParam.month=$scope.month;
        }
        if(!StringUtile.isNull($scope.flag)){
            $scope.queryParam.flag=$scope.flag;
        }
        $http.post('assessment/queryAll',  $scope.queryParam)
            .success(function(data){
                if(data.code=='200'){
                    $scope.data=data.result;
                }else{
                    $messager.alert.error('ERROR','操作失败！');
                }
            }).error(function(err){
            $messager.alert.error('ERROR',err);
        });
    }

    $scope.query();

    $scope.AddAssessment=function(){
        $scope.assessment.salary=$scope.assessment.achievements+$scope.assessment.baseSalary;
        $http.post('assessment/createAssessment',  $scope.assessment)
            .success(function(data){
                if(data.code=='200'){
                    $scope.user=data.result;
                    $scope.dismiss();
                }else{
                    $messager.alert.error('ERROR','操作失败！');
                }
            }).error(function(err){
            $messager.alert.error('ERROR',err);
        });
    }

    $scope.updateAssessment=function(){
        $scope.assessment.salary=$scope.assessment.achievements+$scope.assessment.baseSalary;
        $http.post('assessment/updateAssessment',  $scope.assessment)
            .success(function(data){
                if(data.code=='200'){
                    $scope.user=data.result;
                    $scope.dismiss();
                }else{
                    $messager.alert.error('ERROR','操作失败！');
                }
            }).error(function(err){
            $messager.alert.error('ERROR','操作失败！');
        });
    }


    $scope.dismiss=function(){
        $('#myModal').modal('hide');
        $scope.assessment={};
    }


    $scope.update=function(item){
        $scope.assessment=item;
        $scope.isAdd=false;
    }

    $scope.add=function(){
        $scope.isAdd=true;
        $scope.assessment={};
        $('#myModal').modal('show');
    }


    $scope.delete=function(){
        var items =new Array();
        angular.forEach($scope.data,function(item,index){

            if(item.select){
                items.push(item);
            }
        })
        if(items.length>0){
            BootstrapDialog.confirm('确定要删除吗?', function(result){
                if(result) {
                    $http.post('assessment/delete',items)
                        .success(function(data){
                            if(data.code!=undefined&&data.code=='200'){
                                $scope.query();
                                $messager.alert.success('Infomation','操作成功!');
                            }
                        })
                        .error(function(err){
                            $messager.alert.error('WARNNING',err);
                        });
                }else {
                    return ;
                }
            });
        }else{
            $messager.alert.warning('TIP','请选择要删除的记录！')
        }
    }

});