/**
 * Created by Administrator on 2017/11/3 0003.
 */
var mysql=require("mysql2");
var Sequelize = require("sequelize");

/*var pool =mysql.createPool({
    host:'localhost',
    user:'root',
    password:'root',
    database:'acms'
});

var execut =function(sql,options,callback){
    pool.getConnection(function(err,conn){
            if(err){
                callback(err,null,null);
            }else{
                conn.query(sql,options,function(err,results,fields){
                    conn.release();//释放连接
                    callback(err,results,fields);
                });
            }
    });
}*/


var sequelize = new Sequelize('sxc', 'root', '1234', {
    host: 'localhost',
    dialect: 'mysql',

    pool: {
        max: 15,
        min: 0,
        idle: 10000
    }
});


module.exports=sequelize;