package tr.com.huseyinaydin.repositories;


import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import tr.com.huseyinaydin.entities.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByClinicId(Long clinicId);
    List<Doctor> findByHospitalId(Long hospitalId);
    List<Doctor> findByClinicIdAndHospitalId(Long clinicId, Long hospitalId);

    // Doktor adında arama (büyük/küçük harf duyarsız)
    List<Doctor> findByFullNameContainingIgnoreCase(String namePart);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select d from Doctor d where d.id = :id")
    Optional<Doctor> findByIdWithPessimisticLock(Long id);
}