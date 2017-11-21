
var express = require('express');
var io = require('socket.io');
var parse =require("body-parser");
var user = require("./routes/route.js");
var Project = require("./routes/router-project.js");
var Advertisement=require("./routes/advertisement-router.js");
var User_router=require("./routes/router-user.js");
var Assessment=require("./routes/route-assessment.js");
var Expenses=require("./routes/router-expenses.js");
var Purchase=require("./routes/router-purchase.js");


var session = require('express-session');
var cookie = require("cookie-parser");
var app = express();
 
//静态化资源路径
app.use(express.static(__dirname+'/public'));

app.use(parse.json());

app.use(cookie());
app.use(session({
    name: "final",
    secret: "1234567",
    cookie: {maxAge: 600000000},  //过期时间 毫秒为单位
    resave: true,    //每次触发后保存时间
    rolling: true    // 最后一次触发后计时
}));

app.all('*',function (req, res, next) {
    var url = req.originalUrl;
    if (url.indexOf('login')==-1&&!req.session.user) {
        var map ={code:5003}
        return res.json(map);
    }
    next();
});


app.use('/user',user);
app.use('/project',Project);
app.use('/editUser',User_router);
app.use('/advertisement',Advertisement);
app.use('/assessment',Assessment);
app.use('/expenses',Expenses);
app.use('/purchase',Purchase);






app.get('/', function (req, res) {
   res.send('Hello World');
})








var server = app.listen(3000, function () {
 
  var host = server.address().address
  var port = server.address().port
 
  console.log("300服务器启动。。。", host, port)
 
})

var  ws = io.listen(server);

var users = {};


var numUsers = 0;

ws.on('connection', function (socket) {
    var addedUser = false;

    // when the client emits 'new message', this listens and executes
    socket.on('new message', function (from,to,data) {
        // we tell the client to execute 'new message'
        socket.broadcast.emit('new message'+from, {
            username: socket.username,
            message: data
        });
    });

    // when the client emits 'add user', this listens and executes
    socket.on('add user', function (username) {
        if (addedUser) return;

        // we store the username in the socket session for this client
        socket.username = username;
        ++numUsers;
        addedUser = true;
        socket.emit('login', {
            numUsers: numUsers
        });
        // echo globally (all clients) that a person has connected
        socket.broadcast.emit('user joined', {
            username: socket.username,
            numUsers: numUsers
        });
    });

    // when the client emits 'typing', we broadcast it to others
    socket.on('typing', function () {
        socket.broadcast.emit('typing', {
            username: socket.username
        });
    });

    // when the client emits 'stop typing', we broadcast it to others
    socket.on('stop typing', function () {
        socket.broadcast.emit('stop typing', {
            username: socket.username
        });
    });

    // when the user disconnects.. perform this
    socket.on('disconnect', function () {
        if (addedUser) {
            --numUsers;

            // echo globally that this client has left
            socket.broadcast.emit('user left', {
                username: socket.username,
                numUsers: numUsers
            });
        }
    });
});