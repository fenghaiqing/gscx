/**
 * Created by Administrator on 2018/1/25 0025.
 */
var app =angular.module("myApp",['ui.router']);

app.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("home");
    $stateProvider.state("home", {
            url: "/home",
            templateUrl:'./view/home.html',
            controller:'indexCtrl'
        })
        .state('about',{
            url:'/about',
            templateUrl:'./view/about.html',
            controller:'aboutCtrl'
        })
        .state('edit',{
            url:'/edit/:id',
            templateUrl:'./view/edit.html',
            controller:'editCtrl',

        })
        .state('events',{
            url:'/events',
            templateUrl:'./view/events.html',
           controller:'eventCtrl',
            /*   data: {
                authorizedRoles: [1]
            }*/
        })
        .state('add',{
            url:'/add',
            templateUrl:'./view/add.html',
            controller:'addCtrl'
        })
}])