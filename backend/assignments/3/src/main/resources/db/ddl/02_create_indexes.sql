-- Indexes for common lookups and foreign keys

CREATE INDEX IF NOT EXISTS idx_houses_admin_user_id ON houses(admin_user_id);

CREATE INDEX IF NOT EXISTS idx_house_members_plot_id ON house_members(plot_id);
CREATE INDEX IF NOT EXISTS idx_house_members_user_id ON house_members(user_id);

CREATE INDEX IF NOT EXISTS idx_rooms_plot_id ON rooms(plot_id);

CREATE INDEX IF NOT EXISTS idx_devices_plot_id ON devices(plot_id);
CREATE INDEX IF NOT EXISTS idx_devices_room_id ON devices(room_id);

-- Allow re-registration of a device after soft delete
CREATE UNIQUE INDEX IF NOT EXISTS uq_device_kickston_active
    ON devices(kickston_id)
    WHERE deleted_date IS NULL;
