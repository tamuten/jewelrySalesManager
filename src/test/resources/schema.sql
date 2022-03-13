DROP TABLE IF EXISTS tantosha CASCADE;
CREATE TABLE tantosha (
  id serial PRIMARY KEY,
  name varchar(60) NOT NULL CHECK name <> '',
  delete_flg boolean NOT NULL,
  signup_datetime timestamp NOT NULL,
  update_datetime timestamp NOT NULL,
  delete_datetime timestamp
);

DROP TABLE IF EXISTS customers CASCADE;
CREATE TABLE customers (
  id serial PRIMARY KEY,
  name varchar(60) NOT NULL,
  name_kana varchar(60),
  birthday date,
  gender varchar(10),
  phone_number1 varchar(20),
  phone_number2 varchar(20),
  address varchar(100),
  favorite_memo varchar(200),
  tantosha_id integer REFERENCES tantosha(id),
  delete_flg boolean NOT NULL,
  signup_datetime timestamp NOT NULL,
  update_datetime timestamp NOT NULL,
  delete_datetime timestamp
);

DROP TABLE IF EXISTS orders CASCADE;
CREATE TABLE orders (
  id serial PRIMARY KEY,
  jutyu_date date NOT NULL,
  keijo_date date,
  uriba varchar(20),
  ks_tanto varchar(60),
  hinmei varchar(60),
  number1 varchar(10),
  number2 varchar(10),
  jodai integer,
  gedai integer,
  shikiri_date date,
  shikiri_no varchar(10),
  shozoku varchar(20),
  customer_id integer NOT NULL REFERENCES customers(id),
  delete_flg boolean NOT NULL,
  signup_datetime timestamp NOT NULL,
  update_datetime timestamp NOT NULL,
  delete_datetime timestamp
);
