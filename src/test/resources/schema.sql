DROP TABLE IF EXISTS shozoku CASCADE;
CREATE TABLE shozoku (
  id serial PRIMARY KEY,
  name varchar(50) NOT NULL
);

DROP TABLE IF EXISTS tantosha CASCADE;
CREATE TABLE tantosha (
  id serial PRIMARY KEY,
  name varchar(50) NOT NULL,
  shozoku_id integer NOT NULL,
  role varchar(50) NOT NULL
);

DROP TABLE IF EXISTS customer CASCADE;
CREATE TABLE customer (
  id serial PRIMARY KEY,
  name varchar(50) NOT NULL,
  name_kana varchar(100),
  birthday date,
  gender varchar(10),
  blood_type varchar(5),
  phone_no1 varchar(20),
  phone_no2 varchar(20),
  phone_no3 varchar(20),
  mail_address varchar(100),
  address varchar(100),
  memo varchar(200),
  tantosha_id integer,
  signup_date date NOT NULL
);

DROP TABLE IF EXISTS orders CASCADE;
CREATE TABLE orders (
  id serial PRIMARY KEY,
  jutyu_date date NOT NULL,
  keijo_date date,
  uriba varchar(20),
  ks_tanto varchar(50),
  hinmei varchar(50),
  number1 varchar(10),
  number2 varchar(10),
  jodai integer,
  gedai integer,
  shikiri_date date,
  shikiri_no varchar(10),
  customer_id integer NOT NULL
);
