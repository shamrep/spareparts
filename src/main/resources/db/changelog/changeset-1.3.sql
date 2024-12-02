-- Roles Table
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE, -- Role name (e.g., admin, trainer, member)
    creation_date TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_by VARCHAR(100) -- Optional: User who created this role
);

-- Permissions Table
CREATE TABLE permissions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE, -- Permission name (e.g., manage_users, view_members)
    creation_date TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Role-Permissions Table
CREATE TABLE role_permissions (
    id SERIAL PRIMARY KEY,
    role_id BIGINT NOT NULL, -- Links to roles.id
    permission_id BIGINT NOT NULL, -- Links to permissions.id
    creation_date TIMESTAMPTZ NOT NULL DEFAULT NOW(), -- Tracks when this mapping was created
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions (id) ON DELETE CASCADE,
    UNIQUE (role_id, permission_id) -- Ensures a role cannot have duplicate permissions
);
