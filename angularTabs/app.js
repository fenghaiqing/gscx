/**
 * Created by Administrator on 2018/1/8 0008.
 */


    var app =angular.module('myApp',['ui.router','ui.bootstrap','ct.ui.router.extras']);

    app.config(['$stateProvider','$urlRouterProvider','$httpProvider','$stickyStateProvider',
        function($stateProvider,$urlRouterProvider,$httpProvider,$stickyStateProvider){
        $urlRouterProvider.when('','windows/intro');
        $stateProvider.state('windows',{
            url:'/windows',
            views:{
                'content':{
                    templateUrl:'./template/windows.html'
                }
            }
        })
            .state('windows.intro',{
                url:'/intro',
              
                views:{
                    'windows':{
                        templateUrl:'./template/intro.html'
                    }
                }
            })
            .state('windows.setting',{
                url:'/setting',
                sticky: true,
                dsr: true,
                views:{
                    'setting':{
                        templateUrl:'./template/setting.html',
                        controller:'settingCtrl'
                    }
                }
            })
            .state('windows.tables',{
                url:'/tables',
                sticky: true,
                dsr: true,
                views:{
                    'tables':{
                        templateUrl:'./template/tables.html'
                    }
                }
            });
    }])
        .factory('locals', ['$window', function ($window) {
            return {        //存储单个属性
                set: function (key, value) {
                    $window.localStorage[key] = value;
                },        //读取单个属性
                get: function (key, defaultValue) {
                    return $window.localStorage[key] || null;
                },        //存储对象，以JSON格式存储
                setObject: function (key, value) {
                    $window.localStorage[key] = JSON.stringify(value);//将对象以字符串保存
                },        //读取对象
                getObject: function (key) {
                    return JSON.parse($window.localStorage[key] || null);//获取字符串并解析成对象
                }

            }
    }])
        .run(['$rootScope', '$state', '$stateParams',
            function($rootScope, $state, $stateParams) {
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
            }
        ]);
