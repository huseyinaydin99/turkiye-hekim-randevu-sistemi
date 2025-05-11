DROP SEQUENCE IF EXISTS account_confirmation_token_seq;

CREATE SEQUENCE account_confirmation_token_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE account_confirmation_token (
    id BIGINT NOT NULL DEFAULT nextval('account_confirmation_token_seq'),
    token VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE
);