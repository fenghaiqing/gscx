
var Sequelize = require("sequelize");
var Event=require('../model/events.js');
var sequelize=require('../model/db.js');
class Events{
    find(params){

        return  Event.findAll();
    }
    get(id,params){
        return   Event.findAll({
            'where':{
                'id':id
            }
        })
    }
    create(data,params){
        return Event.create(data);
    }

    update(id,data,params){
        return   Event.update(data,{
            'where':{id:id}
        });
    }
}
class QueryEnd{
    find(params){
        return  sequelize.query("select*from (select*from events order by date DESC) limit 1",
            { type: sequelize.QueryTypes.SELECT})

    }
}
class QueryCount{
    find(params){
        return   sequelize.query("select COUNT(1) total from events",
            { type: sequelize.QueryTypes.SELECT});
    }
}
class QueryStart{
    find(params){
        return sequelize.query("select*from (select*from events order by date asc) limit 1",
            { type: sequelize.QueryTypes.SELECT});
    }
}

module.exports={events:new Events(),
   queryEnd:new QueryEnd(),
    queryCount:new QueryCount(),
    queryStart:new QueryStart()
};
