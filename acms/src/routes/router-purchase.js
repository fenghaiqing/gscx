/**
 * Created by Administrator on 2017/11/7 0007.
 */
var express = require('express');
var Purchase_router =express.Router();
var bodyParser = require('body-parser');
var Purchase=require('../models/purchase.js');

//Expenses.belongsTo(Project, {foreignKey: 'projectId'});

// create application/json parser
var jsonParser = bodyParser.json();

// create application/x-www-form-urlencoded parser
var urlencodedParser = bodyParser.urlencoded({ extended: false });


Purchase_router.post('/addPurchase', jsonParser, function(req, res, next){
        console.log(req.body);
    Purchase.create(req.body).then(function(result){

            console.log('inserted result ok');
            res.send({code:'200',msg:'操作成功！'});

        }).catch(function(err){

            console.log(err);
            var map={code:'400',msg:err};
            res.json(map);

        });

});

Purchase_router.post('/query',jsonParser,function(req,res) {
    Purchase.findAll({
        'where':req.body
    }).then(function(result){
        var map={code:'200',result:result};
        res.json(map);
    }).catch(function(err){
        var map={code:'400',msg:err};
        res.json(map);
    });
});

Purchase_router.post('/updatePurchase',jsonParser,function(req,res) {

    Purchase.update(req.body,{
        'where':{id:req.body.id}
    }).then(function(result){
        var map={code:'200',result:result};
        res.json(map);
    }).catch(function(err){
        var map={code:'400',msg:err};
        res.json(map);
    });
});

Purchase_router.post('/delete',jsonParser,function(req,res) {
    var items = req.body;
    for(var i=0;i<items.length;i++){
        Purchase.destroy(
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

module.exports = Purchase_router;