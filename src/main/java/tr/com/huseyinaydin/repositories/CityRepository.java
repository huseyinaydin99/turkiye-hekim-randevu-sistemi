package tr.com.huseyinaydin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.huseyinaydin.entities.City;

public interface CityRepository extends JpaRepository<City, Long> {
    boolean existsByNameIgnoreCase(String name);
}