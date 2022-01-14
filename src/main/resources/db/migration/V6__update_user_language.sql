ALTER TABLE language
    ADD COLUMN IF NOT EXISTS user_id uuid references users;