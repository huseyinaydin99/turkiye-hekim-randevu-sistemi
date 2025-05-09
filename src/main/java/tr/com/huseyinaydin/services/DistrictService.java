package tr.com.huseyinaydin.services;

import tr.com.huseyinaydin.entities.District;
import tr.com.huseyinaydin.services.impls.base.BaseService;

import java.util.List;

public interface DistrictService extends BaseService<District, Long> {
    List<District> findByCityId(Long cityId);
}