/**
 * Created by Administrator on 2017/11/7 0007.
 */
var express = require('express');
var Advertisement_router =express.Router();
var bodyParser = require('body-parser');
var Advertisement=require('../models/advertisement.js');

var upload=require('./multerUtil.js');

// create application/json parser
var jsonParser = bodyParser.json();

// create application/x-www-form-urlencoded parser
var urlencodedParser = bodyParser.urlencoded({ extended: false });

Advertisement_router.post('/createAdv', upload.single('content'), function(req, res, next){
    console.log(req.file.path);
    var path ="assets/img/"+req.file.filename;
    var createUser = req.session.user.id;
    if(path){
        req.body.advContent=path;
        req.body.createUser=createUser;
        Advertisement.create(req.body).then(function(result){
            console.log('inserted result ok');
            res.send({code:'200',msg:'操作成功！'});
        }).catch(function(err){
            console.log(err);
            var map={code:'400',msg:err};
            res.json(map);
        });
    }
});

Advertisement_router.post('/queryAllAdv',jsonParser,function(req,res) {
    var user = req.session.user;
    if(user.role=='3'){
        req.body.createUser=user.id;
    }
    Advertisement.findAll({
        'where':req.body
    }).then(function(result){
        var map={code:'200',result:result};
        res.json(map);
    }).catch(function(err){
        var map={code:'400',msg:err};
        res.json(map);
    });


});

Advertisement_router.post('/delete',jsonParser,function(req,res) {
    var items = req.body;
    for(var i=0;i<items.length;i++){
        Advertisement.destroy(
            {
                'where':{
                    'id':items[i].id
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
    }
});


module.exports = Advertisement_router;