var express = require('express');
//var execut=require('../models/db.js');
var route =express.Router();
var bodyParser = require('body-parser');
var User=require('../models/user.js');

// create application/json parser
var jsonParser = bodyParser.json();

// create application/x-www-form-urlencoded parser
var urlencodedParser = bodyParser.urlencoded({ extended: false });


route.post('/login',jsonParser,function(req,res){
User.findAll({
		'where':req.body
	})
	.then(function(result){
		if(result!=null && result.length>0){
			map={
				data:result[0],
				code:'200'
			};
			req.session.user=result[0];
			res.json(map);
			//console.log(result[0])
		}else{
			map={code:'1000',msg:'用户名密码不存在！'};
			res.json(map);
		}
	}).catch(function(err){
		throw err;
	});

});
route.get('/add',jsonParser,function(req,res){
	User.create(req.body).then(function(result){
		console.log('inserted result ok');
		res.json(result);
	}).catch(function(err){
		console.log('inserted XiaoMing error');
		res.json(err);
	});

});


module.exports = route;