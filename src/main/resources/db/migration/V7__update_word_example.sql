ALTER TABLE word_examples ADD COLUMN IF NOT EXISTS pronunciation varchar;
ALTER TABLE word ADD COLUMN IF NOT EXISTS pronunciation varchar;