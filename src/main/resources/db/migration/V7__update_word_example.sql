ALTER TABLE word_examples ADD COLUMN IF NOT EXISTS pronunciation varchar(100);
ALTER TABLE word ADD COLUMN IF NOT EXISTS pronunciation varchar(100);