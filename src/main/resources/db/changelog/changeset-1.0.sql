create table clients (
    id serial primary key,
    name varchar(100) not null,
    email varchar(100) not null,
    password varchar(255) not null
);
