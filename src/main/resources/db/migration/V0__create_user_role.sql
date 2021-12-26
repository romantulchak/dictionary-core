CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START 1;

CREATE TABLE IF NOT EXISTS users
(
    id         uuid        not null primary key,
    first_name varchar(25) not null,
    last_name  varchar(30) not null,
    username   varchar(15) not null unique,
    email      varchar(40) not null unique,
    password   varchar(90) not null
);

CREATE TABLE IF NOT EXISTS role
(
    id   bigserial    not null primary key,
    name varchar(255) not null
);

CREATE TABLE IF NOT EXISTS users_role
(
    user_id uuid   not null references users,
    role_id bigint not null references role
);

INSERT INTO users
VALUES ('9db4a7ba-6642-11ec-90d6-0242ac120003', 'Test', 'Test', 'test', 'test@gmail.com',
        '$2a$12$RVt9KNoleQJE1A0to9aTqu3H59eik2NdVw6/Ixg.p9bILurRFrmKe');

INSERT INTO role
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_MODERATOR'),
       (3, 'ROLE_ADMIN');

INSERT INTO users_role
VALUES ('9db4a7ba-6642-11ec-90d6-0242ac120003', 1),
       ('9db4a7ba-6642-11ec-90d6-0242ac120003', 2),
       ('9db4a7ba-6642-11ec-90d6-0242ac120003', 3);