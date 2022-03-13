DROP TABLE IF EXISTS shozoku CASCADE;
CREATE TABLE shozoku (
  id serial PRIMARY KEY,
  name varchar(50) NOT NULL
);

DROP TABLE IF EXISTS tantosha CASCADE;
CREATE TABLE tantosha (
  id serial PRIMARY KEY,
  name varchar(50) NOT NULL,
  shozoku_id integer NOT NULL REFERENCES shozoku(id),
  role varchar(50) NOT NULL
);

DROP TABLE IF EXISTS customers CASCADE;
CREATE TABLE customers (
  id serial PRIMARY KEY,
  name varchar(50) NOT NULL,
  name_kana varchar(100),
  birthday date,
  gender varchar(10),
  blood_type varchar(5),
  phone_number1 varchar(20),
  phone_number2 varchar(20),
  phone_number3 varchar(20),
  mail_address varchar(100),
  address varchar(100),
  memo varchar(200),
  tantosha_id integer REFERENCES tantosha(id)
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
  customer_id integer NOT NULL REFERENCES customers(id)
);
