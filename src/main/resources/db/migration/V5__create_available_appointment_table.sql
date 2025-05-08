CREATE SEQUENCE available_appointment_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE available_appointment (
    id BIGINT PRIMARY KEY DEFAULT nextval('available_appointment_seq'),
    available_appointment_date_time_start TIMESTAMP NOT NULL,
    available_appointment_date_time_end TIMESTAMP NOT NULL,
    doctor_id BIGINT,
    clinic_id BIGINT NOT NULL,
    hospital_id BIGINT NOT NULL,
    district_id BIGINT NOT NULL,
    city_id BIGINT NOT NULL,

    CONSTRAINT fk_available_appointment_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id),
    CONSTRAINT fk_available_appointment_clinic FOREIGN KEY (clinic_id) REFERENCES clinic(id),
    CONSTRAINT fk_available_appointment_hospital FOREIGN KEY (hospital_id) REFERENCES hospital(id),
    CONSTRAINT fk_available_appointment_district FOREIGN KEY (district_id) REFERENCES district(id),
    CONSTRAINT fk_available_appointment_city FOREIGN KEY (city_id) REFERENCES city(id)
);