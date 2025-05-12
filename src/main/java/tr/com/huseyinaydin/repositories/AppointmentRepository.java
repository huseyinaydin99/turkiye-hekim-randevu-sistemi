package tr.com.huseyinaydin.repositories;

import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tr.com.huseyinaydin.dtos.appointments.AppointmentSearchForm;
import tr.com.huseyinaydin.entities.AppUser;
import tr.com.huseyinaydin.entities.Appointment;
import tr.com.huseyinaydin.entities.Doctor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByUserId(Long userId);

    //boolean existsByDoctorIdAndAppointmentDateTimeBetween(Long doctorId, LocalDateTime dateTime);
    boolean existsByDoctorIdAndAppointmentDateTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Appointment a where a.id = :id")
    Appointment findByIdWithPessimisticLock(Long id);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select a from Appointment a where a.id = :id")
    Appointment findByIdWithOptimisticLock(Long id);

    List<Appointment> findByAppointmentDateTime(LocalDateTime appointmentDateTime);

    List<Appointment> findByDoctor(Doctor doctor);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.appointmentDateTime BETWEEN :start AND :end")
    List<Appointment> findByDoctorIdAndAppointmentDateTimeBetween(@Param("doctorId") Long doctorId,
                                                                  @Param("start") LocalDateTime start,
                                                                  @Param("end") LocalDateTime end);

    @Query("SELECT a FROM Appointment a " +
            "WHERE a.doctor.clinic.city = :#{#searchForm.city} " +
            "AND a.doctor.clinic.district = :#{#searchForm.district} " +
            "AND a.hospital = :#{#searchForm.hospital} " +
            "AND a.clinic = :#{#searchForm.clinic} " +
            "AND a.doctor.fullName LIKE %:#{#searchForm.doctor}%")
    List<Appointment> findAppointmentsByCriteria(@Param("searchForm") AppointmentSearchForm searchForm);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND DATE(a.appointmentDateTime) = :date")
    List<Appointment> findByDoctorAndDate(@Param("doctorId") Long doctorId, @Param("date") LocalDate date);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM Appointment a " +
            "WHERE a.user = :user AND a.doctor = :doctor " +
            "AND FUNCTION('DATE', a.appointmentDateTime) = :date")
    boolean existsByUserAndDoctorAndDate(@Param("user") AppUser user,
                                         @Param("doctor") Doctor doctor,
                                         @Param("date") LocalDate date);

    List<Appointment> findByUser(AppUser user);

    Page<Appointment> findByUserId(Long userId, Pageable pageable);

    Page<Appointment> findByUserAndAppointmentDateTimeAfter(
            AppUser user,
            LocalDateTime date,
            Pageable pageable);

    Page<Appointment> findByUserAndAppointmentDateTimeBefore(
            AppUser user,
            LocalDateTime date,
            Pageable pageable);

    Page<Appointment> findByUserAndDoctorFullNameContainingIgnoreCase(
            AppUser user,
            String doctorName,
            Pageable pageable);

    Page<Appointment> findByUserAndAppointmentDateTimeBetween(
            AppUser user,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable);

    Page<Appointment> findByUserAndAttended(
            AppUser user,
            boolean attended,
            Pageable pageable);

    Page<Appointment> findByUserAndHospitalIdAndClinicId(
            AppUser user,
            Long hospitalId,
            Long clinicId,
            Pageable pageable);
}