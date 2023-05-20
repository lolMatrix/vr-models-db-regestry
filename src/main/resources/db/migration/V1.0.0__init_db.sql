CREATE TABLE client (
    id uuid primary key not null,
    "name" varchar(255) not null,
    password varchar(255) not null
);

CREATE TABLE database_file (
    id uuid primary key not null,
    "name" varchar(255) not null,
    description text not null,
    created_by_user_id uuid not null references client(id),
    file bytea not null
);

CREATE TABLE database_file_user (
    user_id uuid not null references client(id),
    database_file_id uuid not null references database_file(id),

    constraint user_id_database_file_id_pkey primary key (user_id, database_file_id)
);