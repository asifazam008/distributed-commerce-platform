SET search_path TO user_schema;

INSERT INTO roles (name, description)
VALUES
 ('USER', 'Regular platform user'),
 ('ADMIN', 'Platform administrator')
ON CONFLICT (name) DO NOTHING;

INSERT INTO permissions (code, method, path_pattern, description)
VALUES
 ('ORDER_CREATE', 'POST', '/orders/**', 'Create order'),
 ('ORDER_READ',   'GET',  '/orders/**', 'Read orders'),
 ('ORDER_CANCEL', 'POST', '/orders/**', 'Cancel order'),
 ('ADMIN_MANAGE_RBAC', 'POST', '/users/internal/admin/**', 'Manage RBAC')
ON CONFLICT (code) DO NOTHING;

-- USER permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON p.code IN ('ORDER_CREATE', 'ORDER_READ')
WHERE r.name = 'USER'
ON CONFLICT DO NOTHING;

-- ADMIN permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
CROSS JOIN permissions p
WHERE r.name = 'ADMIN'
ON CONFLICT DO NOTHING;
