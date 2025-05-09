package tr.com.huseyinaydin.services;

import tr.com.huseyinaydin.dtos.DoctorDTO;
import tr.com.huseyinaydin.entities.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    DoctorDTO getDoctorById(Long id);
    List<DoctorDTO> searchDoctors(String query);
    List<DoctorDTO> getDoctorsByClinicAndHospital(Long clinicId, Long hospitalId);
    Optional<Doctor> findByDoctorId(Long cityId);
}