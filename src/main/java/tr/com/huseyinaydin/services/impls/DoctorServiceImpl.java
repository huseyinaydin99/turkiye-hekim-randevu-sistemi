package tr.com.huseyinaydin.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.dtos.DoctorDTO;
import tr.com.huseyinaydin.entities.Doctor;
import tr.com.huseyinaydin.exceptions.ResourceNotFoundException;
import tr.com.huseyinaydin.mappers.DoctorMapper;
import tr.com.huseyinaydin.repositories.DoctorRepository;
import tr.com.huseyinaydin.services.DoctorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

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
