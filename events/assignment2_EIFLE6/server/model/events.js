
var Sequelize = require("sequelize");
var sequelize=require('./db.js');

var Event = sequelize.define('events', {
    id: {
        type: Sequelize.INTEGER,
        field: 'id',
        primaryKey:true
    },
    date: {
        type: Sequelize.STRING,
        field: 'date'
    },
    title: {
        type: Sequelize.STRING,
        field: 'title'
    },
    place: {
        type: Sequelize.STRING,
        field: 'place'
    },
    remark: {
        type: Sequelize.STRING,
        field: 'remark'
    }
}, {
    freezeTableName: true,
    timestamps: false,
    tableName: 'events'
});

module.exports=Event;