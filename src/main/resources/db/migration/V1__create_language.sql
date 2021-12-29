CREATE TABLE IF NOT EXISTS language
(
    id        bigserial   not null primary key,
    name      varchar(30) not null,
    code      varchar(2)  not null,
    create_at timestamp   not null default now(),
    update_at timestamp
);