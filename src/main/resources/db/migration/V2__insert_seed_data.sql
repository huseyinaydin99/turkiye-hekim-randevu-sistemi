-- Şehirler
INSERT INTO city (id, name) VALUES (1, 'İstanbul');
INSERT INTO city (id, name) VALUES (2, 'Ankara');
INSERT INTO city (id, name) VALUES (3, 'İzmir');

-- İlçeler
INSERT INTO district (id, name, city_id) VALUES (1, 'Kadıköy', 1);
INSERT INTO district (id, name, city_id) VALUES (2, 'Çankaya', 2);
INSERT INTO district (id, name, city_id) VALUES (3, 'Konak', 3);

-- Hastaneler
INSERT INTO hospital (id, name, city_id, district_id) VALUES (1, 'Kadıköy Devlet Hastanesi', 1, 1);
INSERT INTO hospital (id, name, city_id, district_id) VALUES (2, 'Ankara Şehir Hastanesi', 2, 2);
INSERT INTO hospital (id, name, city_id, district_id) VALUES (3, 'İzmir Atatürk Eğitim ve Araştırma Hastanesi', 3, 3);

-- Klinikler
INSERT INTO clinic (id, name, hospital_id) VALUES (1, 'Kardiyoloji', 1);
INSERT INTO clinic (id, name, hospital_id) VALUES (2, 'Ortopedi', 2);
INSERT INTO clinic (id, name, hospital_id) VALUES (3, 'Nöroloji', 3);

-- Doktorlar
INSERT INTO doctor (id, full_name, title, clinic_id, hospital_id) VALUES (1, 'Dr. Ahmet Yılmaz', 'Kardiyolog', 1, 1);
INSERT INTO doctor (id, full_name, title, clinic_id, hospital_id) VALUES (2, 'Dr. Ayşe Demir', 'Ortopedist', 2, 2);
INSERT INTO doctor (id, full_name, title, clinic_id, hospital_id) VALUES (3, 'Dr. Mehmet Kaya', 'Nörolog', 3, 3);

-- Kullanıcılar
INSERT INTO app_user (id, full_name, email, national_id, password, role) VALUES (1, 'Ali Veli', 'ali@example.com', '12345678901', 'toor', 'PATIENT');
INSERT INTO app_user (id, full_name, email, national_id, password, role) VALUES (2, 'Ayşe Fatma', 'ayse@example.com', '23456789012', 'toor', 'PATIENT');

-- Randevular
INSERT INTO appointment (id, appointment_date_time, attended, note_to_doctor, user_id, doctor_id) VALUES (1, '2025-05-01 10:00:00', false, 'Kalp çarpıntısı şikayeti', 1, 1);
INSERT INTO appointment (id, appointment_date_time, attended, note_to_doctor, user_id, doctor_id) VALUES (2, '2025-05-02 14:30:00', false, 'Diz ağrısı', 2, 2);