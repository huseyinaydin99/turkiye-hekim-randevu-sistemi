package tr.com.huseyinaydin.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.entities.Clinic;
import tr.com.huseyinaydin.repositories.ClinicRepository;
import tr.com.huseyinaydin.services.ClinicService;
import tr.com.huseyinaydin.services.impls.abstracts.BaseServiceImpl;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class ClinicServiceImpl extends BaseServiceImpl<Clinic, Long> implements ClinicService {

    private final ClinicRepository clinicRepository;

    public ClinicServiceImpl(ClinicRepository clinicRepository) {
        super(clinicRepository);
        this.clinicRepository = clinicRepository;
    }

    @Override
    public List<Clinic> findByHospitalId(Long hospitalId) {
        return clinicRepository.findByHospitalId(hospitalId);
    }
}