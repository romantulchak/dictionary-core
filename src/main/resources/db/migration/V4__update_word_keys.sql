CREATE TABLE IF NOT EXISTS word_keys
(
    word_id bigint  not null references word,
    key     varchar not null
);