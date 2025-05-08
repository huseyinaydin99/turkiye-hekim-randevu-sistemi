package tr.com.huseyinaydin.services;

import tr.com.huseyinaydin.dtos.DoctorDTO;

import java.util.List;

public interface DoctorService {
    DoctorDTO getDoctorById(Long id);
    List<DoctorDTO> searchDoctors(String query);
    List<DoctorDTO> getDoctorsByClinicAndHospital(Long clinicId, Long hospitalId);
}