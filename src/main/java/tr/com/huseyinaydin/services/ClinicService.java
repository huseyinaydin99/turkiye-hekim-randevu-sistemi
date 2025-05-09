package tr.com.huseyinaydin.services;

import tr.com.huseyinaydin.entities.Clinic;
import tr.com.huseyinaydin.services.impls.base.BaseService;

import java.util.List;

public interface ClinicService extends BaseService<Clinic, Long> {
    List<Clinic> findByHospitalId(Long hospitalId);
}