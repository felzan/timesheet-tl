INSERT INTO USERS (username, password, enabled)
  values (
    'admin@email.com',
    '$2b$10$9WMEdriJ17huo7EVgXetyudjJ7V7Vk6hnCSlvDgnn4uQHUbT2SviK',
    1
    ),(
    'p1@email.com',
    '$2b$10$BbJ5nQOiZO52nyltOADo/.VpXc30g2k9lrdx.qXLsfu6UhoVnCwt2',
    1
    ),(
    'p2@email.com',
    '$2b$10$oyCo853PoXyGl8s4fZlLNO8ZsTzvEULVE3GJ./NE7OuQr2AnHFXc2',
    1);

INSERT INTO AUTHORITIES (username, authority)
  values ('admin@email.com', 'ROLE_ADMIN'),
         ('p1@email.com', 'ROLE_USER'),
         ('p2@email.com', 'ROLE_USER');

INSERT INTO projects (name) VALUES
('Projeto Cliente A'),
('Projeto Cliente B');

INSERT INTO allocation VALUES
(1, 2),
(2, 2),
(2, 3);

INSERT INTO hours (hours, `day`, project_id, user_id) VALUES ('08:30:00', '2020-12-01', 1, 2);
INSERT INTO hours (hours, `day`, project_id, user_id) VALUES ('08:30:00', '2020-12-01', 1, 2);
INSERT INTO hours (hours, `day`, project_id, user_id) VALUES ('08:40:00', '2020-12-01', 1, 2);
INSERT INTO hours (hours, `day`, project_id, user_id) VALUES ('08:20:00', '2020-12-01', 1, 2);