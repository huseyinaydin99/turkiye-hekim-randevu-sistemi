package tr.com.huseyinaydin.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.entities.City;
import tr.com.huseyinaydin.repositories.CityRepository;
import tr.com.huseyinaydin.services.CityService;
import tr.com.huseyinaydin.services.impls.abstracts.BaseServiceImpl;

import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class CityServiceImpl extends BaseServiceImpl<City, Long> implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        super(cityRepository);
        this.cityRepository = cityRepository;
    }

    @Override
    public Optional<City> findByName(String name) {
        return cityRepository.findByName(name);
    }
}