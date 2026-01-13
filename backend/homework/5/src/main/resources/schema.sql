CREATE TABLE IF NOT EXISTS shift_type (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    active BOOLEAN NOT NULL,
    tenant_id BIGINT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE IF NOT EXISTS shift (
    id BIGSERIAL PRIMARY KEY,
    shift_type_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    tenant_id BIGINT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    logged_in BOOLEAN DEFAULT false,
    timezone VARCHAR(50),
    tenant_id BIGINT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE IF NOT EXISTS shift_user (
    id BIGSERIAL PRIMARY KEY,
    shift_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    tenant_id BIGINT NOT NULL
);
