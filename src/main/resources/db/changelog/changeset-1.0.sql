-- changeset shamrep:1
CREATE TABLE parts (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price NUMERIC(10, 2)
);

create table customers (
    id serial primary key,
    email varchar(100) not null
);
