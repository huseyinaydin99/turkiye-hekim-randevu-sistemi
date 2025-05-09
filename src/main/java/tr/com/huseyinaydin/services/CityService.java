package tr.com.huseyinaydin.services;

import tr.com.huseyinaydin.entities.City;
import tr.com.huseyinaydin.services.impls.base.BaseService;

import java.util.Optional;

public interface CityService extends BaseService<City, Long> {
    Optional<City> findByName(String name);
}