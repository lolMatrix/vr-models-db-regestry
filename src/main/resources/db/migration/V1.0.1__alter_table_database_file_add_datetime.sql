ALTER TABLE database_file
    ADD COLUMN datetime timestamptz not null default now();