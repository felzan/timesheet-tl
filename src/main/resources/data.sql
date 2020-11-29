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
