/**
 * Created by Administrator on 2017/11/7 0007.
 */
var express = require('express');
var Expenses_router =express.Router();
var bodyParser = require('body-parser');
var Project=require('../models/project.js');
var Expenses=require('../models/expenses.js');
var sequelize=require('../models/db.js');
//Expenses.belongsTo(Project, {foreignKey: 'projectId'});

// create application/json parser
var jsonParser = bodyParser.json();

// create application/x-www-form-urlencoded parser
var urlencodedParser = bodyParser.urlencoded({ extended: false });


Expenses_router.post('/addExpenses', jsonParser, function(req, res, next){
        console.log(req.body);
        req.body.createUser= req.session.user.id;
        var projectId=req.body.projectId;
        var amount = req.body.amount;
    Expenses.create(req.body).then(function(result){

        Project.findAll({
            'where':{
                'id':projectId
            }
        })
            .then(function(result){
                amount= result[0].expenses+amount
                var param={id:projectId,expenses:amount}
                Project.update(param,{
                    'where':{
                        'id':param.id
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

});

Expenses_router.post('/query',jsonParser,function(req,res) {
   /* Expenses.findAll({
        'where':req.body
    }).then(function(result){
        var map={code:'200',result:result};
        res.json(map);
    }).catch(function(err){
        var map={code:'400',msg:err};
        res.json(map);
    });*/
    var sql =  'SELECT e.id, e.title, e.project_id AS `projectId`,p.project_name as projectName , '
        +'e.amount,e.create_date AS `createDate`, e.seller, e.invoice,e.reimbursement,'
        + 'e.create_user AS `createUser` FROM acms_project_expenses e '
        +'left JOIN acms_project p on e.project_id=p.id';

    if(req.body.projectId!=null &&req.body.projectId!=undefined){
        sql+=' where e.project_id='+req.body.projectId;
    }
    sequelize.query(sql, { type:sequelize.QueryTypes.SELECT})
        .then(function(result){
            var map={code:'200',result:result};
            res.json(map);
        }).catch(function(err){
        var map={code:'400',msg:err};
        res.json(map);
    });
});

Expenses_router.post('/updateExpenses',jsonParser,function(req,res) {
    var projectId=req.body.projectId;
    var amount = req.body.amount;
    Expenses.update(req.body,{
        'where':{id:req.body.id}
    }).then(function(result){
        var param={id:projectId,expenses:amount}
        Project.update(param,{
            'where':{
                'id':param.id
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
    }).catch(function(err){
        var map={code:'400',msg:err};
        res.json(map);
    });
});

Expenses_router.post('/delete',jsonParser,function(req,res) {
    var items = req.body;

    for(var i=0;i<items.length;i++){
        var amount = items[i].amount;
        var projectId=items[i].projectId;
console.log('-----------'+items[i].id);
        Expenses.destroy(
            {
                'where':{
                    id:items[i].id
                }
            })
            .then(function(result){
                Project.findAll(
                    {
                        'where':{
                            id:projectId
                        }
                    })
                    .then(function(result){

                        var expenses = result[0].expenses-amount;
                        var param={'id':result[0].id,'expenses':expenses}
                        Project.update(param,
                            {
                                'where':{
                                    id:param.id
                                }
                            })
                            .then(function(result){
                                console.log(' update ok');
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
module.exports = Expenses_router;