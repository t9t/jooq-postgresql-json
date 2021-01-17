create table jooq.json_test
(
    id    bigserial primary key,
    name  text unique not null,
    data  json        null,
    datab jsonb       null
);

create table jooq.json_str_test
(
    id    bigserial primary key,
    name  text unique not null,
    data  json        null,
    datab jsonb       null
);

-- To see what the jOOQ Kotlin code generator does with not-nullable JSON fields
create table jooq.json_not_null
(
    id    bigserial primary key,
    name  text unique not null,
    data  json        not null,
    datab jsonb       not null
);
