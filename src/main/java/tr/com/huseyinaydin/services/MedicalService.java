package tr.com.huseyinaydin.services;

import tr.com.huseyinaydin.entities.Clinic;
import tr.com.huseyinaydin.entities.Doctor;
import tr.com.huseyinaydin.entities.Hospital;

import java.util.List;

public interface MedicalService {
    List<Clinic> findClinicsByHospitalId(Long hospitalId);
    List<Doctor> findDoctorsByClinicId(Long clinicId);
    List<Hospital> findHospitalsByDistrictId(Long districtId);
}