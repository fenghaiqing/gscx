/**
 * Created by Administrator on 2017/11/7 0007.
 */
app.controller('projectManagerCtrl',function($scope,$http,$state,DateFormat,StringUtile,$messager){

    $scope.project=null;

    $scope.users=null;

    $scope.queryParam={};

    $scope.data=null;

    $scope.createProject=function(){
        $scope.project.estStart= DateFormat.format($scope.start);
        $scope.project.estEnd = DateFormat.format($scope.end);
        $http.post("project/add", $scope.project)
            .success(function(data){
                  if(data.code==200){
                      $messager.alert.success('TIP','操作成功！');
                      $state.go('project');
                  }else{
                      $messager.alert.error('ERROR','操作失败！');
                  }
            })
            .error(function(e){
                alert(e);
            });
    };
    $scope.code=null;
    $scope.relStart=null;
    $scope.projectName=null;
    $scope.query=function(){
        if(!StringUtile.isNull($scope.code)){
            $scope.queryParam.code=$scope.code
        }
        if(!StringUtile.isNull($scope.projectName)){
            $scope.queryParam.projectName=$scope.projectName
        }
        if($scope.relStart!=null&&!StringUtile.isNull(DateFormat.format($scope.relStart))){
            $scope.queryParam.relStart= DateFormat.format($scope.relStart);
        }
        $http.post('project/selectByExmaple',$scope.queryParam)
            .success(function(data){
                if(data.code=='200'){
                    $scope.data=data.result;
                    $scope.queryParam={};
                }else{
                    $messager.alert.error('ERROR','操作失败！');
                    $scope.queryParam={};
                }
            })
            .error(function(err){
                $messager.alert.error('ERROR',err);
                $scope.queryParam={};
            });
    };
    $scope.query();

    $scope.getUser=function(){
        $http.post('editUser/queryAllUser', null)
            .success(function(data){
                if(data.code=='200'){
                    $scope.users=data.result;
                }else{

                    $messager.alert.error('ERROR','操作失败!');
                }
            })
            .error(function(err){
                $messager.alert.error('ERROR',err);
            });
    };
    $scope.getUser();

    //查看广告
    $scope.viewAdvertisement=function(item){
        $state.go('advDetail',{id:item.id})
    }

    $scope.update=function(item){
        $scope.project=item;
        $('#myModal').modal('show');
    }

    $scope.updateProject=function(){

        if(StringUtile.isNull($scope.project.projectName)){
            $messager.alert.error('WARNNING','项目名不能为空!');
            return
        }
        if(StringUtile.isNull($scope.project.code)){
            $messager.alert.error('WARNNING','项目代码不能为空!');
            return
        }
        if($scope.project.relStart!=null &&$scope.project.relStart!=undefined){
            $scope.project.relStart=DateFormat.format($scope.project.relStart);
        }
        if($scope.project.relEnd!=null &&$scope.project.relEnd!=undefined){
            $scope.project.relEnd=DateFormat.format($scope.project.relEnd);
        }
        $http.post('project/update',$scope.project).success(function(data){
            if(data.code!=undefined&&data.code=='200'){
                $scope.dismiss();
                $messager.alert.success('Infomation','操作成功!');
            }
        }).error(function(error){

        });
    }

    $scope.dismiss=function(){
        $('#myModal').modal('hide');
        $scope.project={};
    }
    $scope.delete=function(){
        var items =new Array();
        angular.forEach($scope.data,function(item,index){

            if(item.select){
                items.push(item);
            }
        })
        if(items.length>0){
            BootstrapDialog.confirm('删除项目将会删除该项目及项目下的广告和开销记录,确定要删除吗?', function(result){
                if(result) {
                    $http.post('project/delete',items)
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