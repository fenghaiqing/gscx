
var Sequelize = require("sequelize");
var User=require('../model/users.js')
class Users {
    find(params) {
        return  User.findOne();
    }

}
module.exports=new Users();