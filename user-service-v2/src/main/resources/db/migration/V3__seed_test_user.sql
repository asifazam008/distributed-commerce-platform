SET search_path TO user_schema;

-- Test user (JWT sub)
INSERT INTO users (user_id, username)
VALUES ('asif_test_01', 'asif_test_01')
ON CONFLICT (user_id) DO NOTHING;

-- Assign USER role
INSERT INTO user_roles (user_id, role_id)
SELECT 'asif_test_01', id
FROM roles
WHERE name = 'USER'
ON CONFLICT DO NOTHING;
