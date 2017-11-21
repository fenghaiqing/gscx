/**
 * Created by Administrator on 2017/11/6 0006.
 */
var Sequelize = require("sequelize");
var sequelize=require('./db.js');

var Advertisement=sequelize.define('acms_advertisement',{
    id:{
        type:Sequelize.INTEGER,
        field:'id',
        primaryKey:true
    },
    name:{
        type:Sequelize.STRING,
        field:'name'
    },
    type:{
        type:Sequelize.STRING,
        field:'type'
    },
    projectId:{
        type:Sequelize.STRING,
        field:'project_id'
    },
    advContent:{
        type:Sequelize.STRING,
        field:'adv_content'
    },
    createDate:{
        type:Sequelize.STRING,
        field:'create_date'
    },
        createUser:{
            type:Sequelize.INTEGER,
            field:'create_user'
        }
}  ,{
        freezeTableName: true, // Model 对应的表名将与model名相同
        timestamps: false,//不自动加更新时间
        tableName: 'acms_advertisement'
    }
);

module.exports = Advertisement;