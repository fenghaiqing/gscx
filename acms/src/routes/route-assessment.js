/**
 * Created by Administrator on 2017/11/8 0008.
 */
var express = require('express');
var Assessment_router =express.Router();
var bodyParser = require('body-parser');
var Assessment=require('../models/assessment.js');

var upload=require('./multerUtil.js');


// create application/json parser
var jsonParser = bodyParser.json();

// create application/x-www-form-urlencoded parser
var urlencodedParser = bodyParser.urlencoded({ extended: false });

Assessment_router.post('/createAssessment', jsonParser, function(req, res, next){
        console.log(req.body);
        Assessment.create(req.body).then(function(result){
            console.log('inserted result ok');
            res.send({code:'200',msg:'操作成功！'});
        }).catch(function(err){
            console.log(err);
            var map={code:'400',msg:err};
            res.json(map);
        });
});

Assessment_router.post('/updateAssessment', jsonParser, function(req, res, next){
    console.log(req.body);
    Assessment.update(req.body,{
        'where':{id:req.body.id}
    }).then(function(result){
        res.send({code:'200',msg:'操作成功！'});
    }).catch(function(err){
        console.log(err);
        var map={code:'400',msg:err};
        res.json(map);
    });
});



Assessment_router.post('/queryAll',jsonParser,function(req,res) {
    console.log("session-----"+req.session.user)

    Assessment.findAll({
        'where':req.body
    }).then(function(result){
        var map={code:'200',result:result};
        res.json(map);
    }).catch(function(err){
        var map={code:'400',msg:err};
        res.json(map);
    });

});

Assessment_router.post('/delete',jsonParser,function(req,res) {
    var items = req.body;
    for(var i=0;i<items.length;i++){
        Assessment.destroy(
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

module.exports = Assessment_router;