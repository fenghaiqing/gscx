/**
 * Created by Administrator on 2017/11/7 0007.
 */
/**
 * Created by Administrator on 2017/11/7 0007.
 */
app.controller('purchaseCtrl',function($scope,$http,$state,DateFormat,StringUtile,$messager){

    $scope.purchase={};

    $scope.queryParam={};

    $scope.data=null;


    $scope.addPurchase=function(){

        if(StringUtile.isNull($scope.purchase.vendorName)){
            $messager.alert.error('Warnning','供应商名称不能为空！');
            return;
        }
        if(StringUtile.isNull($scope.purchase.address)){
            $messager.alert.error('Warnning','供应商地址不能为空！');
            return;
        }
        if(StringUtile.isNull($scope.purchase.tel)){
            $messager.alert.error('Warnning','联系电话不能为空！');
            return;
        }

        $http.post('purchase/addPurchase', $scope.purchase)
            .success(function(data){

                if(data.code!=undefined&&data.code=='200'){
                    $scope.dismiss();
                    $scope.purchase={};
                    $scope.query();
                    $messager.alert.success('Infomation','操作成功！');
                }else{
                    $messager.alert.error('ERROR','操作失败！');
                }
            })
            .error(function(err){
                $messager.alert.error('ERROR','err！');
            });

    }

    $scope.updatePurchase=function(){

        if(StringUtile.isNull($scope.purchase.vendorName)){
            $messager.alert.error('Warnning','供应商名称不能为空！');
            return;
        }
        if(StringUtile.isNull($scope.purchase.address)){
            $messager.alert.error('Warnning','供应商地址不能为空！');
            return;
        }
        if(StringUtile.isNull($scope.purchase.tel)){
            $messager.alert.error('Warnning','联系电话不能为空！');
            return;
        }
        $http.post('purchase/updatePurchase', $scope.purchase)
            .success(function(data){

                if(data.code!=undefined&&data.code=='200'){
                    $scope.dismiss();
                    $scope.purchase={};
                    $scope.query();
                    $messager.alert.success('Infomation','操作成功！');
                }else{
                    $messager.alert.error('ERROR','操作失败！');
                }
            })
            .error(function(err){
                $messager.alert.error('ERROR','err！');
            });

    }

    $scope.query=function(){
        $scope.queryParam={};

        if(!StringUtile.isNull($scope.vendorName)){
            $scope.queryParam.vendorName=$scope.vendorName;
        }

        if(!StringUtile.isNull($scope.type)){
            $scope.queryParam.type=$scope.type;
        }

        $http.post('purchase/query', $scope.queryParam)
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


    $scope.dismiss=function(){
        $('#myModal').modal('hide');
        $scope.purchase={};
    }

    $scope.isAdd=true;
    $scope.update=function(item){
        $('#myModal').modal('show');
        $scope.purchase={
            vendorName:null,
            address:null,
            tel:null,
            link:null,
            type:null,
            remark:null,
            id:null
        };
        $scope.purchase.vendorName=item.vendorName;
        $scope.purchase.address=item.address
        $scope.purchase.tel=item.tel
        $scope. purchase.link=item.link
        $scope.purchase.type=item.type
        $scope.purchase.remark=item.remark
        $scope.purchase.id=item.id;
        $scope.isAdd=false;

    }

    $scope.add=function(){
        $scope.isAdd=true;
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
                    $http.post('purchase/delete',items)
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
            $messager.alert.warnning('TIP','请选择要删除的记录！')
        }
    }

});