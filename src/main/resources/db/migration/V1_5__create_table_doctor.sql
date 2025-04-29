CREATE TABLE doctor (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    clinic_id INT NOT NULL REFERENCES clinic(id),
    hospital_id INT NOT NULL REFERENCES hospital(id)
);