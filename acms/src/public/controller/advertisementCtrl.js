/**
 * Created by Administrator on 2017/11/7 0007.
 */
/**
 * Created by Administrator on 2017/11/7 0007.
 */
app.controller('advertisementCtrl',function($scope,$http,$state,DateFormat,StringUtile,$messager){

    $scope.project=null;

    $scope.queryParam={};

    $scope.data=null;
    $scope.projects=null;
    $scope.addAdevertisement=function(){
        var file=$("#content")[0];
        if($("#content").val()==""||$("#content").val()==null){
            return ;
        }
        var formData = new FormData("#avdForm");
        formData.append('content',file.files[0]);
        formData.append('name',$scope.adv.name);
        formData.append('type',$scope.adv.type);
        formData.append('projectId',$scope.adv.projectId);
        formData.append('advContent',$("#content").val());
        formData.append('createDate',DateFormat.format(new Date()));

        $.ajax({
            url:'advertisement/createAdv',
            type : "post",
            data : formData,
            processData : false,
            contentType : false,
            success : function(data, status) {
                if(data.code=='200'){
                    $messager.alert.success('TIP','操作成功！');
                    $state.go('Advertisement');
                }
            },
            error : function(err) {
                $messager.alert.error('ERROR',err);
            }
        })
    }


    $scope.query=function(){
        $scope.projectName={};
        if(!StringUtile.isNull($scope.name)){
            $scope.queryParam.name=$scope.name;
        }
        $http.post('advertisement/queryAllAdv', $scope.queryParam)
            .success(function(data){
                if(data.code=='200'){
                    $scope.data=data.result;
                }else{
                    $messager.alert.error('ERROR','操作失败！');
                }
            })
            .error(function(err){
                $messager.alert.error('ERROR',err);
            });
    }
    $scope.query();

    $scope.queryPoject=function(){

        $http.post('project/selectByExmaple',{})
            .success(function(data){
                if(data.code=='200'){
                    $scope.projects=data.result;

                }else{
                    $messager.alert.error('ERROR','操作失败！');

                }
            })
            .error(function(err){
                $messager.alert.error('ERROR',err);
                $scope.queryParam={};
            });
    };
    $scope.queryPoject();

    $scope.delete=function(){
        var items =new Array();
        angular.forEach($scope.data,function(item,index){

            if(item.select){
                items.push(item);
            }

        })
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
                    $http.post('advertisement/delete',items)
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