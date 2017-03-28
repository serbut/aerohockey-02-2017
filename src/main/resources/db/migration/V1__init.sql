CREATE TABLE users (
    id SERIAL NOT NULL PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(200) NOT NULL,
    rating INTEGER DEFAULT 0);
CREATE UNIQUE INDEX idx_login ON users (login);
CREATE UNIQUE INDEX idx_login_rating ON users (login, rating);