var Sequelize = require("sequelize");
var sequelize=require('./db.js');

var User = sequelize.define('acms_users', {
		id: {
			type:Sequelize.INTEGER,
			field: 'id',
			primaryKey:true
		},
	account: {
		type:Sequelize.STRING,
		field: 'account'
		},
	password: {
		type:Sequelize.STRING,
		field: 'password'
	},

	nickname:{
		type:Sequelize.STRING,
		field: 'nickname'
	},
	desc:{
		type:Sequelize.STRING,
		field: 'desc'
	},
	hread:{
		type:Sequelize.STRING,
		field: 'hread'
	},
	name:{
		type:Sequelize.STRING,
		field: 'name'
	},
	sex:{
		type:Sequelize.STRING,
		field: 'sex'
	},
	dept:{
		type:Sequelize.STRING,
		field: 'dept'
	},
	role:{
		type:Sequelize.STRING,
		field: 'role'
	},
	company:{
		type:Sequelize.STRING,
		field: 'company'
	}
	,
	address:{
		type:Sequelize.STRING,
		field: 'address'
	}
	,
	tel:{
		type:Sequelize.STRING,
		field: 'tel'
	}
},{
		freezeTableName: true, // Model 对应的表名将与model名相同
		timestamps: false,//不自动加更新时间
		tableName: 'acms_users'
	}
);

module.exports = User;