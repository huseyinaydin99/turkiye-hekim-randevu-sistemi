package tr.com.huseyinaydin.services;

import tr.com.huseyinaydin.dtos.DoctorDto;

import java.util.List;

public interface DoctorService {
    DoctorDto getDoctorById(Long id);
    List<DoctorDto> searchDoctors(String query);
    List<DoctorDto> getDoctorsByClinicAndHospital(Long clinicId, Long hospitalId);
}