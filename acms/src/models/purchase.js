/**
 * Created by Administrator on 2017/11/6 0006.
 */
var Sequelize = require("sequelize");
var sequelize=require('./db.js');

var Purchase=sequelize.define('acms_purchase',{
    id:{
        type:Sequelize.INTEGER,
        field:'id',
        primaryKey:true
    },
        vendorName:{
        type:Sequelize.STRING,
        field:'vendor_name'
    },
    type:{
        type:Sequelize.STRING,
        field:'type'
    },
        address:{
        type:Sequelize.STRING,
        field:'address'
    },
        tel:{
        type:Sequelize.STRING,
        field:'tel'
    },
        link:{
        type:Sequelize.STRING,
        field:'link'
    },
        remark:{
            type:Sequelize.STRING,
            field:'remark'
        }
}  ,{
        freezeTableName: true, // Model 对应的表名将与model名相同
        timestamps: false,//不自动加更新时间
        tableName: 'acms_purchase'
    }
);

module.exports = Purchase;