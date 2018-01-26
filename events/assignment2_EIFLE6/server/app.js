const feathers = require('@feathersjs/feathers');
const express = require('@feathersjs/express');
const Events=require('./service/eventservice.js');
const Users=require('./service/userService.js');

const app = express(feathers());

app.use(express.json())

app.use(express.urlencoded({ extended: true }));

app.configure(express.rest());

app.use(express.static('../client'));

app.use('events',  Events.events);
app.use('user',  Users);
app.use('queryStart',  Events.queryStart);
app.use('queryEnd',  Events.queryEnd);
app.use('queryCount',  Events.queryCount);
app.use(express.errorHandler());
app.listen(3030);

