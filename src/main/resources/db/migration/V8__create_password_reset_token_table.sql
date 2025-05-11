CREATE SEQUENCE password_reset_token_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE password_reset_token (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_password_reset_token_user
        FOREIGN KEY (user_id)
        REFERENCES app_user(id)
        ON DELETE CASCADE
);

COMMENT ON TABLE password_reset_token IS 'Kullanıcılar için parola sıfırlama belirteçlerini(tokenlerini) depolar';
COMMENT ON COLUMN password_reset_token.token IS 'Benzersiz belirteç(token) dizesi';
COMMENT ON COLUMN password_reset_token.user_id IS 'Bu token-ın ait olduğu kullanıcıya referans';
COMMENT ON COLUMN password_reset_token.expiry_date IS 'Bu token-ın sona ereceği tarih';