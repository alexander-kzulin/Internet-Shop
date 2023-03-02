INSERT INTO users(id, archive, email, name, password, role)
VALUES(1, false, 'mail@mail.ru', 'admin', '$2a$10$8j70HQws8lLnyTY5TXiQqu6DSTuA54dQQ1p/wkR8L5DqiGJDCqCV6', 'ADMIN');

ALTER SEQUENCE user_seq RESTART WITH 2;