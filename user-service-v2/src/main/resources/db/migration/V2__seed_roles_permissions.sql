SET search_path TO user_schema;

-- ================================
-- ROLES
-- ================================
INSERT INTO roles (name, description) VALUES
('USER', 'Regular platform user'),
('ADMIN', 'Platform administrator')
ON CONFLICT (name) DO NOTHING;

-- ================================
-- PERMISSIONS
-- ================================
INSERT INTO permissions (name, description) VALUES
('ORDER_CREATE', 'Create order'),
('ORDER_READ', 'Read order'),
('ORDER_CANCEL', 'Cancel order')
ON CONFLICT (name) DO NOTHING;

-- ================================
-- USER ROLE PERMISSIONS
-- ================================
-- USER
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p
  ON p.name IN ('ORDER_CREATE', 'ORDER_READ')
WHERE r.name = 'USER'
ON CONFLICT DO NOTHING;

-- ADMIN (ALL permissions)
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
CROSS JOIN permissions p
WHERE r.name = 'ADMIN'
ON CONFLICT DO NOTHING;
