create table notifications (
    id serial primary key,
    client_id bigint not null,
    message varchar(1000) not null,
    send_date timestamptz not null,
    is_read boolean not null default false
);
