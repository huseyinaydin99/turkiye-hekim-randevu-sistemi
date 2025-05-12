ALTER TABLE appointment
ADD COLUMN statu BOOLEAN NOT NULL DEFAULT TRUE;

COMMENT ON COLUMN appointment.statu IS 'Randevunun aktif/pasif durumunu tutar (true: aktif, false: pasif)';