DROP TABLE IF EXISTS tb_movie;
CREATE TABLE tb_movie AS SELECT * FROM CSVREAD('classpath:movie.csv');