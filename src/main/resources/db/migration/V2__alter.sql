CREATE UNIQUE INDEX idx_login ON users (login);
CREATE UNIQUE INDEX idx_login_rating ON users (login, rating);