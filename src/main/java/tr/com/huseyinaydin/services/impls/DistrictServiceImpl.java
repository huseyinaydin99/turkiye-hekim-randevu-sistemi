package tr.com.huseyinaydin.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.entities.District;
import tr.com.huseyinaydin.repositories.DistrictRepository;
import tr.com.huseyinaydin.services.DistrictService;
import tr.com.huseyinaydin.services.impls.abstracts.BaseServiceImpl;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class DistrictServiceImpl extends BaseServiceImpl<District, Long> implements DistrictService {

    private final DistrictRepository districtRepository;

    public DistrictServiceImpl(DistrictRepository districtRepository) {
        super(districtRepository);
        this.districtRepository = districtRepository;
    }

    @Override
    public List<District> findByCityId(Long cityId) {
        return districtRepository.findByCityId(cityId);
    }
}