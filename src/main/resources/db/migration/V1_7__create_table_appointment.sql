CREATE TABLE appointment (
    id SERIAL PRIMARY KEY,
    appointment_date TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    note TEXT,
    user_id INT NOT NULL REFERENCES app_user(id),
    doctor_id INT NOT NULL REFERENCES doctor(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);