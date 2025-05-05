-- ========================================
-- Appointment Tablosuna Yeni İlişkisel Alanların Eklenmesi
-- ========================================
ALTER TABLE appointment
ADD COLUMN clinic_id BIGINT,
ADD COLUMN hospital_id BIGINT,
ADD COLUMN district_id BIGINT,
ADD COLUMN city_id BIGINT;

-- Yeni eklenen sütunlara foreign key constraint'lerinin eklenmesi
ALTER TABLE appointment
ADD CONSTRAINT fk_appointment_clinic FOREIGN KEY (clinic_id) REFERENCES clinic(id),
ADD CONSTRAINT fk_appointment_hospital FOREIGN KEY (hospital_id) REFERENCES hospital(id),
ADD CONSTRAINT fk_appointment_district FOREIGN KEY (district_id) REFERENCES district(id),
ADD CONSTRAINT fk_appointment_city FOREIGN KEY (city_id) REFERENCES city(id);

-- ========================================
-- Doctor Tablosuna Yeni İlişkisel Alanların Eklenmesi
-- ========================================
ALTER TABLE doctor
ADD COLUMN district_id BIGINT,
ADD COLUMN city_id BIGINT;

-- Yeni eklenen sütunlara foreign key constraint'lerinin eklenmesi
ALTER TABLE doctor
ADD CONSTRAINT fk_doctor_district FOREIGN KEY (district_id) REFERENCES district(id),
ADD CONSTRAINT fk_doctor_city FOREIGN KEY (city_id) REFERENCES city(id);

-- ========================================
-- Clinic Tablosuna Yeni İlişkisel Alanların Eklenmesi
-- ========================================
ALTER TABLE clinic
ADD COLUMN district_id BIGINT,
ADD COLUMN city_id BIGINT;

-- Yeni eklenen sütunlara foreign key constraint'lerinin eklenmesi
ALTER TABLE clinic
ADD CONSTRAINT fk_clinic_district FOREIGN KEY (district_id) REFERENCES district(id),
ADD CONSTRAINT fk_clinic_city FOREIGN KEY (city_id) REFERENCES city(id);

-- ========================================
-- Mevcut Randevuların İlişkisel Verilerle Güncellenmesi
-- ========================================
UPDATE appointment a
SET
    clinic_id = d.clinic_id,
    hospital_id = d.hospital_id,
    city_id = h.city_id,
    district_id = h.district_id
FROM doctor d
JOIN hospital h ON d.hospital_id = h.id
WHERE a.doctor_id = d.id;

-- ========================================
-- Doktorların İl ve İlçe Bilgilerinin Güncellenmesi
-- ========================================
UPDATE doctor d
SET
    city_id = h.city_id,
    district_id = h.district_id
FROM hospital h
WHERE d.hospital_id = h.id;

-- ========================================
-- Kliniklerin İl ve İlçe Bilgilerinin Güncellenmesi
-- ========================================
UPDATE clinic c
SET
    city_id = h.city_id,
    district_id = h.district_id
FROM hospital h
WHERE c.hospital_id = h.id;

-- ========================================
-- Yeni Sütunların NOT NULL Olarak Ayarlanması
-- (Önce tüm veriler güncellendiği için güvenle yapılabilir)
-- ========================================
ALTER TABLE appointment
ALTER COLUMN clinic_id SET NOT NULL,
ALTER COLUMN hospital_id SET NOT NULL,
ALTER COLUMN district_id SET NOT NULL,
ALTER COLUMN city_id SET NOT NULL;

ALTER TABLE doctor
ALTER COLUMN district_id SET NOT NULL,
ALTER COLUMN city_id SET NOT NULL;

ALTER TABLE clinic
ALTER COLUMN district_id SET NOT NULL,
ALTER COLUMN city_id SET NOT NULL;