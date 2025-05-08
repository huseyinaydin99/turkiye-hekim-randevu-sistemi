package tr.com.huseyinaydin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tr.com.huseyinaydin.dtos.appointments.AppointmentSearchCriteria;
import tr.com.huseyinaydin.entities.AvailableAppointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AvailableAppointmentRepository extends JpaRepository<AvailableAppointment, Long> {
    /*@Query("""
    SELECT a FROM AvailableAppointment a
    WHERE (:cityId IS NULL OR a.city.id = :cityId)
      AND (:districtId IS NULL OR a.district.id = :districtId)
      AND (:hospitalId IS NULL OR a.hospital.id = :hospitalId)
      AND (:clinicId IS NULL OR a.clinic.id = :clinicId)
      AND (:doctorId IS NULL OR a.doctor.id = :doctorId)
      AND (a.appointmentDateTimeStart >= TO_TIMESTAMP('2025-05-09 09:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    ORDER BY a.appointmentDateTimeStart ASC
""")
    List<AvailableAppointment> searchByCriteria(
            @Param("cityId") Long cityId,
            @Param("districtId") Long districtId,
            @Param("hospitalId") Long hospitalId,
            @Param("clinicId") Long clinicId,
            @Param("doctorId") Long doctorId
            @Param("startDate") LocalDateTime startDate
    );*/

    @Query(value = """
    SELECT * FROM available_appointment a
    WHERE (:cityId IS NULL OR a.city_id = :cityId)
      AND (:districtId IS NULL OR a.district_id = :districtId)
      AND (:hospitalId IS NULL OR a.hospital_id = :hospitalId)
      AND (:clinicId IS NULL OR a.clinic_id = :clinicId)
      AND (:doctorId IS NULL OR a.doctor_id = :doctorId)
      AND (a.available_appointment_date_time_start >= TO_TIMESTAMP('2025-05-09 09:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    ORDER BY a.available_appointment_date_time_start ASC
    """, nativeQuery = true)
    List<AvailableAppointment> searchByCriteria(
            @Param("cityId") Long cityId,
            @Param("districtId") Long districtId,
            @Param("hospitalId") Long hospitalId,
            @Param("clinicId") Long clinicId,
            @Param("doctorId") Long doctorId
            /*@Param("startDate") LocalDateTime startDate*/
    );
}