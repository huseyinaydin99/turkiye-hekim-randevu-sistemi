package tr.com.huseyinaydin.services;

import tr.com.huseyinaydin.entities.City;
import tr.com.huseyinaydin.entities.District;
import tr.com.huseyinaydin.entities.Hospital;

import java.util.List;

public interface LocationService {
    List<City> findAllCities();
    List<District> findDistrictsByCityId(Long cityId);
    List<Hospital> findHospitalsByDistrictId(Long districtId);
}