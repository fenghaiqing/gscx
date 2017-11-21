/**
 * Created by Administrator on 2017/11/4 0004.
 */
var app =angular.module("myApp",['ngCookies','ui.router']);

app.constant('AUTH_EVENTS', { //记录登录状态
        loginSuccess: 'auth-login-success',
        loginFailed: 'auth-login-failed',
        logoutSuccess: 'auth-logout-success',
        sessionTimeout: 'auth-session-timeout',
        notAuthenticated: 'auth-not-authenticated',
        notAuthorized: 'auth-not-authorized'
    })
    //用户权限
    .constant('USER_ROLES', {
        all: '*',
        admin: 'admin',
        editor: 'editor',
        guest: 'guest'
    })

