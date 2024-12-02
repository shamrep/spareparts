-- roles table
create table roles (
    id serial primary key,
    name varchar(100) not null unique, -- role name (e.g., admin, trainer, member)
    creation_date timestamptz not null default now(),
    created_by varchar(100) -- optional: user who created this role
);

-- permissions table
create table permissions (
    id serial primary key,
    name varchar(100) not null unique, -- permission name (e.g., manage_users, view_members)
    creation_date timestamptz not null default now()
);

-- role-permissions table
create table role_permissions (
    id serial primary key,
    role_id bigint not null, -- links to roles.id
    permission_id bigint not null, -- links to permissions.id
    creation_date timestamptz not null default now(), -- tracks when this mapping was created
    foreign key (role_id) references roles (id) on delete cascade,
    foreign key (permission_id) references permissions (id) on delete cascade,
    unique (role_id, permission_id) -- ensures a role cannot have duplicate permissions
);
