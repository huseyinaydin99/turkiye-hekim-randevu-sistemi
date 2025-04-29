package tr.com.huseyinaydin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.huseyinaydin.entities.District;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findByCityId(Long cityId);
}