INSERT INTO available_appointment (
    id,
    available_appointment_date_time_start,
    available_appointment_date_time_end,
    doctor_id,
    clinic_id,
    hospital_id,
    district_id,
    city_id
)
VALUES
    (nextval('available_appointment_seq'), '2025-05-09 09:00:00', '2025-05-09 09:15:00', 1, 1, 1, 1, 1),
    (nextval('available_appointment_seq'), '2025-05-09 09:15:00', '2025-05-09 09:30:00', 1, 1, 1, 1, 1),
    (nextval('available_appointment_seq'), '2025-05-09 09:30:00', '2025-05-09 09:45:00', 1, 1, 1, 1, 1),
    (nextval('available_appointment_seq'), '2025-05-09 09:45:00', '2025-05-09 10:00:00', 1, 1, 1, 1, 1),
    (nextval('available_appointment_seq'), '2025-05-09 10:00:00', '2025-05-09 10:15:00', 1, 1, 1, 1, 1),
    (nextval('available_appointment_seq'), '2025-05-09 10:15:00', '2025-05-09 10:30:00', 1, 1, 1, 1, 1),
    (nextval('available_appointment_seq'), '2025-05-09 10:30:00', '2025-05-09 10:45:00', 1, 1, 1, 1, 1),
    (nextval('available_appointment_seq'), '2025-05-09 10:45:00', '2025-05-09 11:00:00', 1, 1, 1, 1, 1),
    (nextval('available_appointment_seq'), '2025-05-09 11:00:00', '2025-05-09 11:15:00', 1, 1, 1, 1, 1),
    (nextval('available_appointment_seq'), '2025-05-09 11:15:00', '2025-05-09 11:30:00', 1, 1, 1, 1, 1);