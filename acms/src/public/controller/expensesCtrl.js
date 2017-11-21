/**
 * Created by Administrator on 2017/11/7 0007.
 */
/**
 * Created by Administrator on 2017/11/7 0007.
 */
app.controller('expensesCtrl',function($scope,$http,$state,DateFormat,StringUtile,$messager){

    $scope.expenses=null;

    $scope.queryParam={};

    $scope.data=null;


    $scope.addExpenses=function(){
        var createDate =DateFormat.format(new Date());
        if(StringUtile.isNull($scope.expenses.projectId)){
            $messager.alert.error('Warnning','项目不能为空！');
            return;
        }
        if(StringUtile.isNull($scope.expenses.invoice)){
            $messager.alert.error('Warnning','发票代码不能为空！');
            return;
        }
        if(StringUtile.isNull($scope.expenses.amount)){
            $messager.alert.error('Warnning','金额不能为空！');
            return;
        }
        $scope.expenses.createDate=createDate;
        $http.post('expenses/addExpenses', $scope.expenses)
            .success(function(data){

                if(data.code!=undefined&&data.code=='200'){
                    $scope.dismiss();
                    $scope.expenses={};
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

    $scope.updateExpenses=function(){
        var createDate =DateFormat.format(new Date());
        if(StringUtile.isNull($scope.expenses.projectId)){
            $messager.alert.error('Warnning','项目不能为空！');
            return;
        }
        if(StringUtile.isNull($scope.expenses.invoice)){
            $messager.alert.error('Warnning','发票代码不能为空！');
            return;
        }
        if(StringUtile.isNull($scope.expenses.amount)){
            $messager.alert.error('Warnning','金额不能为空！');
            return;
        }
        $scope.expenses.createDate=createDate;
        $http.post('expenses/updateExpenses', $scope.expenses)
            .success(function(data){

                if(data.code!=undefined&&data.code=='200'){
                    $scope.dismiss();
                    $scope.expenses={};
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
        if(!StringUtile.isNull($scope.projectId)){
            $scope.queryParam.projectId=$scope.projectId;
        }
        $http.post('expenses/query', $scope.queryParam)
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


    $scope.dismiss=function(){
        $('#expressModal').modal('hide');
        $scope.expenses={};
    }

    $scope.isAdd=true;
   // $scope.expenses.projectId="";
    $scope.update=function(item){
        $scope.expenses={
            projectId:null,
            seller:null,
            invoice:null,
            amount:null,
            reimbursement:null,
            title:null,
            id:null
        }
        $scope.expenses.projectId=item.projectId;
        $scope.expenses.seller=item.seller
        $scope.expenses.invoice=item.invoice
        $scope. expenses.amount=item.amount
        $scope.expenses.reimbursement=item.reimbursement
        $scope.expenses.title=item.title
        $scope.expenses.id=item.id;
        $scope.isAdd=false;
        $('#expressModal').modal('show');
    }

    $scope.add=function(){
        $scope.isAdd=true;
        $('#expressModal').modal('show');
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
                    $http.post('expenses/delete',items)
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