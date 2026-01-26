-- Tables for Smart Home Device Management

CREATE TABLE IF NOT EXISTS users (
    user_id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(120),
    created_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    modified_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted_date TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS houses (
    plot_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    address VARCHAR(255),
    admin_user_id BIGINT NOT NULL REFERENCES users(user_id),
    version BIGINT NOT NULL DEFAULT 0,
    created_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    modified_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted_date TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS house_members (
    member_id BIGSERIAL PRIMARY KEY,
    plot_id BIGINT NOT NULL REFERENCES houses(plot_id),
    user_id BIGINT NOT NULL REFERENCES users(user_id),
    role VARCHAR(20) NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    created_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    modified_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted_date TIMESTAMPTZ,
    CONSTRAINT uq_house_member UNIQUE (plot_id, user_id),
    CONSTRAINT chk_house_member_role CHECK (role IN ('ADMIN', 'MEMBER'))
);

CREATE TABLE IF NOT EXISTS rooms (
    room_id BIGSERIAL PRIMARY KEY,
    plot_id BIGINT NOT NULL REFERENCES houses(plot_id),
    name VARCHAR(120) NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    created_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    modified_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted_date TIMESTAMPTZ,
    CONSTRAINT uq_room_name_per_house UNIQUE (plot_id, name)
);

CREATE TABLE IF NOT EXISTS device_inventory (
    kickston_id VARCHAR(6) PRIMARY KEY,
    device_username VARCHAR(120) NOT NULL,
    device_password VARCHAR(120) NOT NULL,
    manufacture_date_time TIMESTAMPTZ NOT NULL,
    manufacture_factory_place VARCHAR(120) NOT NULL,
    created_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    modified_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted_date TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS devices (
    device_id BIGSERIAL PRIMARY KEY,
    kickston_id VARCHAR(6) NOT NULL REFERENCES device_inventory(kickston_id),
    plot_id BIGINT NOT NULL REFERENCES houses(plot_id),
    room_id BIGINT REFERENCES rooms(room_id),
    version BIGINT NOT NULL DEFAULT 0,
    created_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    modified_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted_date TIMESTAMPTZ
);
