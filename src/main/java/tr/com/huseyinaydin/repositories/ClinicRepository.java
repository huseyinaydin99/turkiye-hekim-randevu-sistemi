package tr.com.huseyinaydin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.huseyinaydin.entities.Clinic;
import tr.com.huseyinaydin.entities.Hospital;

import java.util.List;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    // Hospital'a göre Clinic'leri bul
    List<Clinic> findByHospital(Hospital hospital);
    List<Clinic> findByHospitalId(Long hospitalId);
}