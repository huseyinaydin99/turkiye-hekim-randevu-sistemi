package tr.com.huseyinaydin.repositories;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tr.com.huseyinaydin.dtos.appointments.AppointmentSearchForm;
import tr.com.huseyinaydin.entities.Appointment;
import tr.com.huseyinaydin.entities.Doctor;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByUserId(Long userId);

    boolean existsByDoctorIdAndAppointmentDateTime(Long doctorId, LocalDateTime dateTime);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Appointment a where a.id = :id")
    Appointment findByIdWithPessimisticLock(Long id);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select a from Appointment a where a.id = :id")
    Appointment findByIdWithOptimisticLock(Long id);

    List<Appointment> findByAppointmentDateTime(LocalDateTime appointmentDateTime);

    List<Appointment> findByDoctor(Doctor doctor);

    List<Appointment> findByDoctorAndAppointmentDateTimeBetween(Doctor doctor, LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM Appointment a " +
            "WHERE a.doctor.clinic.city = :#{#searchForm.city} " +
            "AND a.doctor.clinic.district = :#{#searchForm.district} " +
            "AND a.hospital = :#{#searchForm.hospital} " +
            "AND a.clinic = :#{#searchForm.clinic} " +
            "AND a.doctor.fullName LIKE %:#{#searchForm.doctor}%")
    List<Appointment> findAppointmentsByCriteria(@Param("searchForm") AppointmentSearchForm searchForm);
}