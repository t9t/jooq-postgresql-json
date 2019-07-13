create table jooq_json_test
(
    id    bigserial primary key,
    name  text  not null,
    data  json  null,
    datab jsonb null
);

insert into jooq_json_test (name, data, datab)
values ('both', '{"json": {"int": 100, "str": "Hello, JSON world!", "object": {"v":  200}, "n": null}}', '{"jsonb": {"int": 100, "str": "Hello, JSON world!", "object": {"v": 200}, "n": null}}'),
       ('empty', '{}', '{}'),
       ('null', null, null);
