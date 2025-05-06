package tr.com.huseyinaydin.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.huseyinaydin.entities.City;
import tr.com.huseyinaydin.entities.District;
import tr.com.huseyinaydin.entities.Hospital;
import tr.com.huseyinaydin.repositories.CityRepository;
import tr.com.huseyinaydin.repositories.DistrictRepository;
import tr.com.huseyinaydin.repositories.HospitalRepository;
import tr.com.huseyinaydin.services.LocationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final HospitalRepository hospitalRepository;

    @Override
    @Transactional(readOnly = true)
    public List<City> findAllCities() {
        return cityRepository.findAll();
    }

    /*@Override
    @Transactional(readOnly = true)
    public List<City> findAllCities() {
        return cityRepository.findAll().stream()
                .map(city -> {
                    city.setName(cleanString(city.getName()));
                    return city;
                })
                .collect(Collectors.toList());
    }*/

    /*private String cleanString(String input) {
        return input.replaceAll("[^\\x20-\\x7e]", "").trim();
    }*/

    @Override
    @Transactional(readOnly = true)
    public List<District> findDistrictsByCityId(Long cityId) {
        if (cityId == null) {
            throw new IllegalArgumentException("Şehir Id null olamaz!");
        }
        return districtRepository.findByCityId(cityId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Hospital> findHospitalsByDistrictId(Long districtId) {
        if (districtId == null) {
            throw new IllegalArgumentException("İlçe Id null olamaz!");
        }
        return hospitalRepository.findByDistrictId(districtId);
    }
}