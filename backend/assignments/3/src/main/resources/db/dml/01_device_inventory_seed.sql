-- Seed device inventory data (idempotent)

INSERT INTO device_inventory (
    kickston_id,
    device_username,
    device_password,
    manufacture_date_time,
    manufacture_factory_place,
    created_date,
    modified_date
) VALUES
    ('000001', 'device_user_1', 'device_pass_1', now(), 'China Hub 1', now(), now()),
    ('0000A1', 'device_user_2', 'device_pass_2', now(), 'China Hub 2', now(), now()),
    ('00FF10', 'device_user_3', 'device_pass_3', now(), 'India Hub 1', now(), now()),
    ('00FFAA', 'device_user_4', 'device_pass_4', now(), 'India Hub 2', now(), now()),
    ('0ABC10', 'device_user_5', 'device_pass_5', now(), 'USA Hub 1', now(), now())
ON CONFLICT (kickston_id) DO NOTHING;
