package tr.com.huseyinaydin.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.huseyinaydin.entities.Clinic;
import tr.com.huseyinaydin.entities.Doctor;
import tr.com.huseyinaydin.entities.Hospital;
import tr.com.huseyinaydin.repositories.ClinicRepository;
import tr.com.huseyinaydin.repositories.DistrictRepository;
import tr.com.huseyinaydin.repositories.DoctorRepository;
import tr.com.huseyinaydin.repositories.HospitalRepository;
import tr.com.huseyinaydin.services.MedicalService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalServiceImpl implements MedicalService {
    private final ClinicRepository clinicRepository;
    private final DoctorRepository doctorRepository;
    private final DistrictRepository districtRepository;
    private final HospitalRepository hospitalRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Clinic> findClinicsByHospitalId(Long hospitalId) {
        if (hospitalId == null) {
            throw new IllegalArgumentException("Hastane Id null olamaz!");
        }
        Hospital hospital = new Hospital();
        hospital.setId(hospitalId);
        return clinicRepository.findByHospital(hospital);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Doctor> findDoctorsByClinicId(Long clinicId) {
        if (clinicId == null) {
            throw new IllegalArgumentException("Klinik Id null olamaz!");
        }
        return doctorRepository.findByClinicId(clinicId);
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