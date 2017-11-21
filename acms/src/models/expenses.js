/**
 * Created by Administrator on 2017/11/6 0006.
 */
var Sequelize = require("sequelize");
var sequelize=require('./db.js');

var ProjectExpenses=sequelize.define('acms_project_expenses',{
    id:{
        field:'id',
        type:Sequelize.INTEGER,
        primaryKey:true
    },
    title:{
        type:Sequelize.STRING,
        field:'title'
    },
    projectId:{
        type:Sequelize.STRING,
        field:'project_id'
    },
    amount:{
        type:Sequelize.DOUBLE,
        field:'amount'
    },
    createDate:{
        type:Sequelize.STRING,
        field:'create_date'
    },
    seller:{
        type:Sequelize.STRING,
        field:'seller'
    },
    invoice:{
        type:Sequelize.STRING,
        field:'invoice'
    },
    reimbursement:{
        type:Sequelize.STRING,
        field:'reimbursement'
    },
    createUser:{
        type:Sequelize.INTEGER,
        field:'create_user'
    }
},{
    freezeTableName: true, // Model 对应的表名将与model名相同
    timestamps: false,//不自动加更新时间
    tableName: 'acms_project_expenses'
});

module.exports=ProjectExpenses;