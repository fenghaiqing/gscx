
var Sequelize = require("sequelize");
var sequelize=require('./db.js');
var User = sequelize.define('user', {
    id: {
        type: Sequelize.INTEGER,
        field: 'id',
        primaryKey:true
    },
    name: {
        type: Sequelize.STRING,
        field: 'name'
    },
    neptunId: {
        type: Sequelize.STRING,
        field: 'neptun_id'
    },
    email: {
        type: Sequelize.STRING,
        field: 'email'
    }
}, {
    freezeTableName: true,
    timestamps: false,
    tableName: 'user'
});

module.exports=User;