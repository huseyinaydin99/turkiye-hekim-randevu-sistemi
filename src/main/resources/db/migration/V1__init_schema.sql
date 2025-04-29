-- ========================================
-- City
-- ========================================
CREATE SEQUENCE city_seq START 50;
CREATE TABLE city (
    id BIGINT PRIMARY KEY DEFAULT nextval('city_seq'),
    name VARCHAR(255) NOT NULL
);

-- ========================================
-- District
-- ========================================
CREATE SEQUENCE district_seq START 50;
CREATE TABLE district (
    id BIGINT PRIMARY KEY DEFAULT nextval('district_seq'),
    name VARCHAR(255) NOT NULL,
    city_id BIGINT REFERENCES city(id)
);

-- ========================================
-- Hospital
-- ========================================
CREATE SEQUENCE hospital_seq START 50;
CREATE TABLE hospital (
    id BIGINT PRIMARY KEY DEFAULT nextval('hospital_seq'),
    name VARCHAR(255) NOT NULL,
    city_id BIGINT REFERENCES city(id),
    district_id BIGINT REFERENCES district(id)
);

-- ========================================
-- Clinic
-- ========================================
CREATE SEQUENCE clinic_seq START 50;
CREATE TABLE clinic (
    id BIGINT PRIMARY KEY DEFAULT nextval('clinic_seq'),
    name VARCHAR(255) NOT NULL,
    hospital_id BIGINT REFERENCES hospital(id)
);

-- ========================================
-- AppUser
-- ========================================
CREATE SEQUENCE app_user_seq START 50;
CREATE TABLE app_user (
    id BIGINT PRIMARY KEY DEFAULT nextval('app_user_seq'),
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    national_id VARCHAR(20) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(50)
);

-- ========================================
-- OAuthIdentity
-- ========================================
CREATE SEQUENCE oauth_identity_seq START 50;
CREATE TABLE oauth_identity (
    id BIGINT PRIMARY KEY DEFAULT nextval('oauth_identity_seq'),
    provider VARCHAR(100),
    provider_id VARCHAR(255),
    user_id BIGINT UNIQUE REFERENCES app_user(id)
);

-- ========================================
-- Doctor
-- ========================================
CREATE SEQUENCE doctor_seq START 50;
CREATE TABLE doctor (
    id BIGINT PRIMARY KEY DEFAULT nextval('doctor_seq'),
    full_name VARCHAR(255) NOT NULL,
    title VARCHAR(100),
    clinic_id BIGINT REFERENCES clinic(id),
    hospital_id BIGINT REFERENCES hospital(id)
);

-- ========================================
-- Appointment
-- ========================================
CREATE SEQUENCE appointment_seq START 50;
CREATE TABLE appointment (
    id BIGINT PRIMARY KEY DEFAULT nextval('appointment_seq'),
    appointment_date_time TIMESTAMP NOT NULL,
    attended BOOLEAN DEFAULT FALSE,
    note_to_doctor TEXT,
    user_id BIGINT REFERENCES app_user(id),
    doctor_id BIGINT REFERENCES doctor(id)
);