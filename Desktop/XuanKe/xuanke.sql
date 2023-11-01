DROP DATABASE IF EXISTS xuanke;
CREATE DATABASE xuanke;
USE xuanke;








-- 课程表
DROP TABLE IF EXISTS course;
CREATE TABLE  course(
cid INT PRIMARY KEY AUTO_INCREMENT, -- ID
uid INT, -- 主讲老师ID
uid2 INT, -- 主管老师ID
NAME VARCHAR(20), -- 课程名称
num VARCHAR(20), -- 学生数量
TIME VARCHAR(100)  -- 计划学时
);
INSERT INTO course(NAME,num,TIME,uid,uid2) VALUES ('语文','80','90',1,7);
INSERT INTO course(NAME,num,TIME,uid,uid2) VALUES ('高数','70','80',2,7);
INSERT INTO course(NAME,num,TIME,uid,uid2) VALUES ('英语','60','70',3,7);
INSERT INTO course(NAME,num,TIME,uid,uid2) VALUES ('物理','50','60',4,7);
INSERT INTO course(NAME,num,TIME,uid,uid2) VALUES ('化学','40','50',5,7);
INSERT INTO course(NAME,num,TIME,uid,uid2) VALUES ('生物','30','40',6,7);
SELECT * FROM course;


-- 学生申请表
DROP TABLE IF EXISTS apply;
CREATE TABLE  apply(
aid INT PRIMARY KEY AUTO_INCREMENT, -- ID
uid INT,  -- 学生id
cid INT,  -- 课程id
content1 TEXT, -- 申请原因
content2 TEXT, -- 驳回原因1
content3 TEXT, -- 驳回原因2
statu INT -- 1. 申请中  2.主讲老师同意  3.主管老师同意  4.主讲老师驳回  5.主管老师驳回 6. 学生点击确定完成申请
);
SELECT * FROM apply;


