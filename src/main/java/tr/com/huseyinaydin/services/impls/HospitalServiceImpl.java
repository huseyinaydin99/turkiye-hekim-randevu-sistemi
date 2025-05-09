package tr.com.huseyinaydin.services.impls;

import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.entities.Hospital;
import tr.com.huseyinaydin.repositories.HospitalRepository;
import tr.com.huseyinaydin.services.HospitalService;
import tr.com.huseyinaydin.services.impls.abstracts.BaseServiceImpl;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class HospitalServiceImpl extends BaseServiceImpl<Hospital, Long> implements HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalServiceImpl(HospitalRepository hospitalRepository) {
        super(hospitalRepository);
        this.hospitalRepository = hospitalRepository;
    }

    @Override
    public List<Hospital> findByCityId(Long cityId) {
        return hospitalRepository.findByCityId(cityId);
    }

    @Override
    public List<Hospital> findByDistrictId(Long districtId) {
        return hospitalRepository.findByDistrictId(districtId);
    }
}