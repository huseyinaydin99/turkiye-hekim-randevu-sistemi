package tr.com.huseyinaydin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.huseyinaydin.entities.City;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    boolean existsByNameIgnoreCase(String name);
    Optional<City> findByName(String name);
}