--
-- 由SQLiteStudio v3.1.1 产生的文件 周五 1月 26 14:37:02 2018
--
-- 文本编码：System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- 表：events
CREATE TABLE events (id INTEGER PRIMARY KEY AUTOINCREMENT, date STRING, title STRING, place STRING, remark STRING);
INSERT INTO events (id, date, title, place, remark) VALUES (4, '2018-7-25', 'sdfs', 'asdasda', 'asasas');
INSERT INTO events (id, date, title, place, remark) VALUES (5, '2017-01-12', 345, 3243, 2342);
INSERT INTO events (id, date, title, place, remark) VALUES (6, '2018-01-16', 123, 121, 1212);
INSERT INTO events (id, date, title, place, remark) VALUES (7, '2018-01-16', 1212, 121, 333);
INSERT INTO events (id, date, title, place, remark) VALUES (8, '2017-05-31', 23, 12, 121);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
--
-- 由SQLiteStudio v3.1.1 产生的文件 周五 1月 26 14:38:36 2018
--
-- 文本编码：System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- 表：user
CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name STRING, neptun_id STRING, email STRING);
INSERT INTO user (id, name, neptun_id, email) VALUES (1, 'HOU Ruizhe', 'EIFLE6', 'rayzhe0823@gmail.com');

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;