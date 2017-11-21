/**
 * Created by Administrator on 2017/11/7 0007.
 */
var express = require('express');
var project_router =express.Router();
var bodyParser = require('body-parser');
var Project=require('../models/project.js');
var Advertisement=require('../models/advertisement.js');
var Expenses=require('../models/expenses.js');
// create application/json parser
var jsonParser = bodyParser.json();

// create application/x-www-form-urlencoded parser
var urlencodedParser = bodyParser.urlencoded({ extended: false });

project_router.post('/add',jsonParser,function(req,res) {

    var user =req.session.user;
    req.body.createUser=user.id;
    Project.create(req.body).then(function(result){
        console.log('inserted result ok');
        var map={code:'200',msg:'操作成功！'};
        res.json(map);
    }).catch(function(err){
        console.log('inserted XiaoMing error');
        var map={code:'400',msg:'操作成功！'};
        res.json(map);
    });


});

project_router.post('/selectByExmaple',urlencodedParser,function(req,res){

    if(req.session.user.role=='3' &&req.session.user.role!='5'){
        req.body.belongTo=req.session.user.id;
    }else if(req.session.user.role=='5'){
        req.body.cpCode= req.session.user.company
    }
    Project.findAll({
        'where':req.body
    }).then(function(result){
        var map={code:'200',result:result};
        res.json(map);
    }).catch(function(err){
        var map={code:'400',msg:err};
        res.json(map);
    });
});

project_router.post('/update',jsonParser,function(req,res) {

    Project.update(req.body,{
        'where':{
            'id':req.body.id
        }
    }).then(function(result){
        console.log('update ok');
        var map={code:'200',msg:'操作成功！'};
        res.json(map);
    }).catch(function(err){
        console.log(err);
        var map={code:'400',msg:err};
        res.json(map);
    });


});
project_router.post('/delete',jsonParser,function(req,res) {
    var items = req.body;
    for(var i=0;i<items.length;i++){
        var id=items[i].id;
        Project.destroy(
            {
                'where':{
                    'id':id
                }
            })
            .then(function(result){
                Advertisement.destroy(
                    {
                        'where':{
                            'projectId':id
                        }
                    })
                    .then(function(result){
                        Expenses.destroy(
                            {
                                'where':{
                                    'projectId':id
                                }
                            })
                            .then(function(result){
                                console.log(' delete ok');
                                var map={code:'200',msg:'操作成功！'};
                                res.json(map);
                            }).catch(function(err){
                            console.log(err);
                            var map={code:'400',msg:err};
                            res.json(map);
                        });
                    }).catch(function(err){
                    console.log(err);
                    var map={code:'400',msg:err};
                    res.json(map);
                });
            }).catch(function(err){
            console.log(err);
            var map={code:'400',msg:err};
            res.json(map);
        });
    }
});

module.exports = project_router;