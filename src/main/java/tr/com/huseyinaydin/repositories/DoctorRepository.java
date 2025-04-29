package tr.com.huseyinaydin.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.huseyinaydin.entities.Doctor;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByClinicId(Long clinicId);
    List<Doctor> findByHospitalId(Long hospitalId);
}