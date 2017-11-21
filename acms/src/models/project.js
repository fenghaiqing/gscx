/**
 * Created by Administrator on 2017/11/6 0006.
 */
var Sequelize = require("sequelize");
var sequelize=require('./db.js');

var Project = sequelize.define('acms_project',{
    id:{
        type:Sequelize.INTEGER,
        field:'id',
        primaryKey:true
    },
    code:{
        type:Sequelize.STRING,
        field:'code'
    },
    projectName:{
        type:Sequelize.STRING,
        field:'project_name'
    },
    content:{
        type:Sequelize.STRING,
        field:'content'
    },
    budget:{
        type:Sequelize.DOUBLE,
        field:'budget'
    },
    estStart:{
        type:Sequelize.STRING,
        field:'est_start'
    },
    estEnd:{
        type:Sequelize.STRING,
        field:'est_end'
    },
    relStart:{
        type:Sequelize.STRING,
        field:'rel_start'
    },
    relEnd:{
        type:Sequelize.STRING,
        field:'rel_end'
    },
    expenses:{
        type:Sequelize.DOUBLE,
        field:'expenses'
    },
    schedule:{
        type:Sequelize.STRING,
        field:'schedule'
    },
    createDate:{
        type:Sequelize.STRING,
        field:'create_date'
    },
    cpCode:{
        type:Sequelize.STRING,
        field:'cp_code'
    },
    belongTo:{
        type:Sequelize.STRING,
        field:'belong_to'
    },
    createUser:{
        type:Sequelize.STRING,
        field:'create_user'
    }
},{
    freezeTableName: true, // Model 对应的表名将与model名相同
    timestamps: false,//不自动加更新时间
    tableName: 'acms_project'
});

module.exports=Project;