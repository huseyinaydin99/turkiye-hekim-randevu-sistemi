package tr.com.huseyinaydin.repositories;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import tr.com.huseyinaydin.entities.Appointment;

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
}