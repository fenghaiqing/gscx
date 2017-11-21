var express = require('express');
var router = express.Router();

var multer  = require('multer');

var User =require('../models/user.js');

var bodyParser = require('body-parser');

var upload=require('./multerUtil.js');

// create application/json parser
var jsonParser = bodyParser.json();

// create application/x-www-form-urlencoded parser
var urlencodedParser = bodyParser.urlencoded({ extended: false });




router.post('/updateUser', upload.single('hread'), function(req, res, next){
    console.log(req.file.path)
    var path ="assets/img/"+req.file.filename;
    if(path){
        req.body.hread=path;
        req.body.sex= req.body.optionsRadios;
        console.log(req.body);
        User.update(req.body,{
            'where':{
                'account':req.body.account
            }
        }).then(function(result){
            res.send({code:'200',msg:'操作成功！'});
        });
    }
   // res.send(req.file);
});

router.post('/queryByAccount',jsonParser,function(req,res){
    var id= req.session.user.id;
    User.findAll({
        where:{id:id}
    }).then(function(result){
        var map ={};
        if(result){
            map.code='200';
            map.result=result[0]
            res.json(map);
        }
    }).catch(function(err){
        var map ={};
        map.code='400';
        map.msg=err;
        res.json(map);
    });
});

router.post('/updateUserInfo',jsonParser,function(req,res){

    User.update(req.body,{
        'where':{account:req.body.account}
    }).then(function(result){
        var map ={};
        console.log(result)
        if(result){
            map.code='200';
            map.result=result[0]
            res.json(map);
        }
    }).catch(function(err){
        var map ={};
        map.code='400';
        map.msg=err;
        res.json(map);
    });
});

router.post('/queryAllUser',jsonParser , function(req, res, next){
    User.findAll({}).then(function(result){
        var map ={};
        if(result){
            map.code='200';
            map.result=result;
            res.json(map);
        }
    }).catch(function(err){
        var map ={};
        map.code='400';
        map.msg=err;
        res.json(map);
    });
});



module.exports = router;