--
create table clients (
    id serial primary key,
    name varchar(100) not null,
    email varchar(100) not null,
    password varchar(255) not null
);

create table roles (
    id serial primary key,
    name varchar(20) not null
)

create table clients_roles (
    client_id bigint not null,
    role_id bigint not null,
    primary key (client_id, role_id),
    foreign key (client_id) references clients(id) on delete cascade,
    foreign key (role_id) references roles(id) on delete cascade
);

