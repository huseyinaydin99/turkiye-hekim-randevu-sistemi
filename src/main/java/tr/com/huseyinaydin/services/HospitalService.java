package tr.com.huseyinaydin.services;

import tr.com.huseyinaydin.entities.Hospital;
import tr.com.huseyinaydin.services.impls.base.BaseService;

import java.util.List;

public interface HospitalService extends BaseService<Hospital, Long> {
    List<Hospital> findByCityId(Long cityId);
    List<Hospital> findByDistrictId(Long districtId);
}