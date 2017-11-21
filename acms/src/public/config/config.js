/**
 * Created by Administrator on 2017/11/6 0006.
 */
app.config(['$stateProvider', '$urlRouterProvider','$httpProvider','USER_ROLES', function($stateProvider, $urlRouterProvider,$httpProvider,USER_ROLES) {
    $httpProvider.interceptors.push('HttpRequestInterceptor');
    $stateProvider.state("index", {
            url: "/index",
            templateUrl:'./view/index.html',
            controller:'indexCtrl'
        })
        .state('Advertisement',{
            url:'/Advertisement',
            templateUrl:'./view/advList.html',
            controller:'advertisementCtrl'
        })
        .state('project',{
            url:'/project',
            templateUrl:'./view/projectList.html',
            controller:'projectManagerCtrl'
        })
        .state('assessment',{
            url:'/assessment',
            templateUrl:'./view/assessment.html',
            controller:'assessmentCtrl',
            data: {
                authorizedRoles: [1]
            }
        })
        .state('user',{
            url:'/user',
            templateUrl:'./view/users.html',
            controller:'userCtrl'
        })
        .state('login',{
            url:'/login',
            templateUrl:'./view/login.html',
            controller:'loginCtrl'
        })
        .state('editProject',{
        url:'/editProject',
        templateUrl:'./view/projectManager.html',
        controller:'projectManagerCtrl',
        data: {
                authorizedRoles: [3]
            }
    })
        .state('editAdvertisement',{
        url:'/editAdvertisement',
        templateUrl:'./view/addAdv.html',
        controller:'advertisementCtrl',
            data: {
                authorizedRoles: [3]
            }
    })
    .state('advDetail',{
        url:'/advDetail/:id',
        templateUrl:'./view/advDetail.html',
        controller:'viewAdvertisementCtrl'
    })
    .state('expenses',{
        url:'/expenses',
        templateUrl:'./view/expenses.html',
        controller:'expensesCtrl',
        data: {
            authorizedRoles: [1,2,5]
        }
    })
    .state('purchase',{
    url:'/purchase',
    templateUrl:'./view/purchase.html',
    controller:'purchaseCtrl',
    data: {
            authorizedRoles: [0,1,2]
        }
    });

    $urlRouterProvider.otherwise("index");
}])