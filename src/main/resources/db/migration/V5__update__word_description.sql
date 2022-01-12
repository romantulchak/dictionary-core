ALTER TABLE word
    ADD COLUMN IF NOT EXISTS description varchar(500);

CREATE TABLE IF NOT EXISTS word_examples
(
    word_id bigint  not null references word,
    example varchar not null
);