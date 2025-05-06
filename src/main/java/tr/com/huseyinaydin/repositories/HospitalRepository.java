package tr.com.huseyinaydin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.huseyinaydin.entities.City;
import tr.com.huseyinaydin.entities.Hospital;

import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    // City'ye göre hastaneleri bul
    List<Hospital> findByCity(City city);
    List<Hospital> findByDistrictId(Long districtId);
}