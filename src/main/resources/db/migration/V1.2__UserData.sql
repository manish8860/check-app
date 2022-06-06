INSERT INTO security_user (id, username, password) VALUES
(1,  'admin', '$2a$12$ZhGS.zcWt1gnZ9xRNp7inOvo5hIT0ngN7N.pN939cShxKvaQYHnnu'),
(2,  'user_jane', '$2a$12$ZhGS.zcWt1gnZ9xRNp7inOvo5hIT0ngN7N.pN939cShxKvaQYHnnu'),
(3,  'user_mark', '$2a$12$ZhGS.zcWt1gnZ9xRNp7inOvo5hIT0ngN7N.pN939cShxKvaQYHnnu'),
(4,  'wally', '$2a$12$ZhGS.zcWt1gnZ9xRNp7inOvo5hIT0ngN7N.pN939cShxKvaQYHnnu');

INSERT INTO security_role (id, role_name, description) VALUES (1, 'ROLE_ADMIN', 'Administrator');
INSERT INTO security_role (id, role_name, description) VALUES (2, 'ROLE_USER', 'User');

INSERT INTO user_role(user_id, role_id) VALUES
 (1, 1), -- give admin ROLE_ADMIN
 (2, 2),  -- give Jane ROLE_USER
 (3, 2),  -- give Mark ROLE_USER
 (4, 1),  -- give Wally ROLE_ADMIN
 (4, 2);  -- give Wally ROLE_USER