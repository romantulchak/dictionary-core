CREATE TABLE IF NOT EXISTS word
(
    id             bigserial    not null primary key,
    name           varchar(500) not null,
    capital_name   varchar(500) not null,
    lowercase_name varchar(500) not null,
    uppercase_name varchar(500) not null,
    create_at      timestamp    not null default now(),
    update_at      timestamp,
    user_id        uuid         not null references users,
    language_id    bigint       not null references language
);
