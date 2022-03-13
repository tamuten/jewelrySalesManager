INSERT INTO shozoku (name) VALUES ('所属1');
INSERT INTO shozoku (name) VALUES ('所属2');
INSERT INTO shozoku (name) VALUES ('所属3');
INSERT INTO shozoku (name) VALUES ('婦人服');
INSERT INTO shozoku (name) VALUES ('家庭用品');
INSERT INTO shozoku (name) VALUES ('食品');

INSERT INTO tantosha (name, shozoku_id, role) VALUES ('admin', 1, 'ROLE_ADMIN');
INSERT INTO tantosha (name, shozoku_id, role) VALUES ('user', 4, 'ROLE_GENERAL');
INSERT INTO tantosha (name, shozoku_id, role) VALUES ('test', 6, 'ROLE_GENERAL');
