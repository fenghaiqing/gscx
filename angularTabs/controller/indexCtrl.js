/**
 * Created by Administrator on 2018/1/8 0008.
 */
angular.module('myApp')
    .controller('indexCtrl',function($scope,$state,$stickyState,$rootScope,$timeout,locals){
        $scope.tabs=[];
        var modules=new Array();

        //读取已经打开的tab
        modules=locals.getObject('module');

        if(modules){
            $scope.tabs=modules;
            angular.forEach( $scope.tabs,function(tab){
                if(tab.disable){
                    $state.go(tab.route);
                }
            })
        }else {
            $scope.tabs = [
                {
                    coin: ' fa-home',
                    heading: '首页',
                    times: 'none',
                    disable: '',
                    route: 'windows.intro',

                }
            ];
        }

        //增加tab
        $scope.addTable=function(heading,route){
            var isContent =false;
            angular.forEach($scope.tabs,function(tab,index){
                if(tab.heading===heading){
                    isContent=true;
                    tab.disable=true;
                    $scope.changeTable(tab);
                }else{
                    tab.disable=false;
                }
            });

            if(!isContent){
                var tab={  coin:' fa-home',
                    heading:heading,
                    times:'',
                    disable: true,
                    route:route
                }
                $scope.tabs.push(tab);
                $scope.changeTable(tab);
            }

        }

        //切换tab
        $scope.changeTable=function(tab){
            for(var i=0;i<$scope.tabs.length;i++) {
                if(tab.route==$scope.tabs[i].route){
                    $scope.tabs[i].disable=true;
                    break;
                }
                $scope.tabs[i].disable=false;
            };
            $state.go(tab.route);

        }

        //移除Tab
        $scope.removeTab= function($index){
            $scope.tabs.splice($index,1);

           for(var i=0;i<$scope.tabs.length;i++) {
                if(i==$scope.tabs.length-1){
                    $scope.tabs[i].disable=true;
                    break;
                }
               $scope.tabs[i].disable=false;
            };

            $timeout(function(){
                var tablength=$scope.tabs.length
                if(tablength>0){
                    $state.go($scope.tabs[tablength-1].route);
                }else{
                    $scope.tabData.push({
                        coin: ' fa-home',
                        heading: '首页',
                        times: 'none',
                        disable: true,
                        route: 'windows.intro',
                    });

                    $state.go($scope.tabs[0].route);
                }
                $timeout(function(){ $stickyState.reset($scope.tabs[tablength-1].route);},100);

            },150);
        }


        $scope.$on('$stateChangeSuccess',function(event, toState, toParams, fromState, fromParams){

            //将tab缓存到本地
            locals.setObject("module",$scope.tabs);

       });
    });