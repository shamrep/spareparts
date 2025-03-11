create table notifications (
    id serial primary key,
    client_id bigint not null,
    message varchar(1000) not null,
    send_date timestamptz not null,
    is_read boolean not null default false
);

create type membership_type as enum ('MONTHLY', 'ANNUAL');

create table memberships (
    id serial primary key,
    client_id bigint not null references clients(id) on delete cascade,
    type membership_type not null,
    start_date timestamptz not null,
    end_date timestamptz not null,
    price numeric(10, 2) not null
);
