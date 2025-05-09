package tr.com.huseyinaydin.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.dtos.DoctorDTO;
import tr.com.huseyinaydin.entities.Doctor;
import tr.com.huseyinaydin.entities.Hospital;
import tr.com.huseyinaydin.exceptions.ResourceNotFoundException;
import tr.com.huseyinaydin.mappers.DoctorMapper;
import tr.com.huseyinaydin.repositories.DoctorRepository;
import tr.com.huseyinaydin.repositories.HospitalRepository;
import tr.com.huseyinaydin.services.DoctorService;
import tr.com.huseyinaydin.services.impls.abstracts.BaseServiceImpl;

import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class DoctorServiceImpl extends BaseServiceImpl<Doctor, Long> implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorMapper doctorMapper) {
        super(doctorRepository);
        this.doctorMapper = doctorMapper;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Optional<Doctor> findByDoctorId(Long cityId) {
        return doctorRepository.findById(cityId);
    }

    @Override
    public DoctorDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doktor bulunamadı: " + id));
        return doctorMapper.toDto(doctor);
    }

    @Override
    public List<DoctorDTO> searchDoctors(String query) {
        List<Doctor> doctors = doctorRepository.findByFullNameContainingIgnoreCase(query);
        return doctorMapper.toDtoList(doctors);
    }

    @Override
    public List<DoctorDTO> getDoctorsByClinicAndHospital(Long clinicId, Long hospitalId) {
        List<Doctor> doctors = doctorRepository.findByClinicIdAndHospitalId(clinicId, hospitalId);
        return doctorMapper.toDtoList(doctors);
    }
}
