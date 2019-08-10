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
