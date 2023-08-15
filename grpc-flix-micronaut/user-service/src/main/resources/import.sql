DROP TABLE IF EXISTS tb_user;
CREATE TABLE tb_user AS SELECT * FROM CSVREAD('classpath:user.csv');