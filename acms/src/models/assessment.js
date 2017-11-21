/**
 * Created by Administrator on 2017/11/6 0006.
 */
var Sequelize = require("sequelize");
var sequelize=require('./db.js');

var Assessment=sequelize.define('acms_assessment',{
    id:{
        field:'id',
        type:Sequelize.INTEGER,
        primaryKey:true
    } ,
    name:{
        field:'name',
        type:Sequelize.STRING
    },
    baseSalary:{
        field:'base_salary',
        type:Sequelize.DOUBLE
    },
    achievements:{
        field:'achievements',
        type:Sequelize.DOUBLE
    },
    day:{
        field:'day',
        type:Sequelize.INTEGER
    },
    month:{
        field:'month',
        type:Sequelize.STRING
    },
    salary:{
        field:'salary',
        type:Sequelize.DOUBLE
    },
    flag:{
        field:'flag',
        type:Sequelize.STRING
    },
    remark:{
        field:'remark',
        type:Sequelize.STRING
    },
    createDate:{
        field:'create_date',
        type:Sequelize.STRING
    }
} ,{
    freezeTableName: true, // Model 对应的表名将与model名相同
        timestamps: false,//不自动加更新时间
        tableName: 'acms_assessment'
});

module.exports=Assessment;