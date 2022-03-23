INSERT INTO shozoku (name) VALUES ('所属1');
INSERT INTO shozoku (name) VALUES ('所属2');
INSERT INTO shozoku (name) VALUES ('所属3');
INSERT INTO shozoku (name) VALUES ('婦人服');
INSERT INTO shozoku (name) VALUES ('家庭用品');
INSERT INTO shozoku (name) VALUES ('食品');

INSERT INTO tantosha (name, shozoku_id, role) VALUES ('admin', 1, 'ROLE_ADMIN');
INSERT INTO tantosha (name, shozoku_id, role) VALUES ('user', 4, 'ROLE_GENERAL');
INSERT INTO tantosha (name, shozoku_id, role) VALUES ('test', 6, 'ROLE_GENERAL');

INSERT INTO customer(name, name_kana,birthday, gender, blood_type, address, memo, tantosha_id, signup_date) VALUES ('鈴木一郎', 'スズキイチロウ', '1970-1-1', 'male', 'A', '埼玉県さいたま市北区〇〇町1-1-1', 'メモです。1', 1, '2022-1-1');

INSERT INTO customer_phone (id, customer_id, phone_number, memo) VALUES (1,1,'07000000000','メモ');
INSERT INTO customer_phone (id, customer_id, phone_number, memo) VALUES (2,1,'07000000001','メモ');
INSERT INTO customer_phone (id, customer_id, phone_number, memo) VALUES (3,1,'07000000002','メモ');

INSERT INTO customer_mail (id, customer_id, mail_address, memo) VALUES (1,1,'sample1@example.com','メモ');
INSERT INTO customer_mail (id, customer_id, mail_address, memo) VALUES (2,1,'sample2@example.com','メモ');
INSERT INTO customer_mail (id, customer_id, mail_address, memo) VALUES (3,1,'sample3@example.com','メモ');