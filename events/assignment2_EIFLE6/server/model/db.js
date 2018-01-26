
const sqlite3 = require("sqlite3");
const Sequelize = require("sequelize");

var sequelize = new Sequelize('', '', '', {
    host: 'localhost',
    dialect: 'sqlite',

    pool: {
        max: 5,
        min: 0,
        idle: 10000
    },

    storage: 'd:/test.db'
});
module.exports=sequelize;