package tr.com.huseyinaydin.controllers.apis;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.huseyinaydin.dtos.*;
import tr.com.huseyinaydin.dtos.appointments.AppointmentSearchCriteria;
import tr.com.huseyinaydin.dtos.locations.LocationHierarchyDto;
import tr.com.huseyinaydin.entities.*;
import tr.com.huseyinaydin.services.AppointmentSearchService;
import tr.com.huseyinaydin.services.LocationService;
import tr.com.huseyinaydin.services.MedicalService;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/appointments/search")
@RequiredArgsConstructor
public class AppointmentSearchApiController {
    private final LocationService locationService;
    private final MedicalService medicalService;
    private final AppointmentSearchService appointmentSearchService;
    private final ModelMapper modelMapper;

    @GetMapping("/hierarchy")
    public ResponseEntity<LocationHierarchyDto> getInitialHierarchy() {
        LocationHierarchyDto hierarchy = new LocationHierarchyDto();
        Type listType = new TypeToken<List<CityDto>>() {}.getType();
        List<CityDto> cityDtos = modelMapper.map(locationService.findAllCities(), listType);
        hierarchy.setCities(cityDtos);
        return ResponseEntity.ok(hierarchy);
    }

    /*@GetMapping("/hierarchy")
    public ResponseEntity<LocationHierarchyDto> getInitialHierarchy() {
        LocationHierarchyDto hierarchy = new LocationHierarchyDto();
        hierarchy.setCities(
                locationService.findAllCities()
                        .stream()
                        .map(CityDto::new)
                        .collect(Collectors.toList())
        );
        return ResponseEntity.ok(hierarchy);
    }*/

    /*@GetMapping("/hierarchy")
    public ResponseEntity<List<CityDto>> getInitialHierarchy() {
        Type listType = new TypeToken<List<CityDto>>() {}.getType();
        List<CityDto> cityDtos = modelMapper.map(locationService.findAllCities(), listType);
        return ResponseEntity.ok(cityDtos);
    }*/

    @GetMapping("/districts/{cityId}")
    public ResponseEntity<List<DistrictDto>> getDistrictsByCity(@PathVariable Long cityId) {
        Type listType = new TypeToken<List<DistrictDto>>() {}.getType();
        List<DistrictDto> districtDtos = modelMapper.map(locationService.findDistrictsByCityId(cityId), listType);
        return ResponseEntity.ok(districtDtos);
    }

    @GetMapping("/hospitals/{districtId}")
    public ResponseEntity<List<HospitalDto>> getHospitalsByDistrict(@PathVariable Long districtId) {
        Type listType = new TypeToken<List<HospitalDto>>() {}.getType();
        List<HospitalDto> hospitalDtos = modelMapper.map(medicalService.findHospitalsByDistrictId(districtId), listType);
        return ResponseEntity.ok(hospitalDtos);
    }

    @GetMapping("/clinics/{hospitalId}")
    public ResponseEntity<List<ClinicDto>> getClinicsByHospital(@PathVariable Long hospitalId) {
        Type listType = new TypeToken<List<ClinicDto>>() {}.getType();
        List<ClinicDto> clinicDtos = modelMapper.map(medicalService.findClinicsByHospitalId(hospitalId), listType);
        return ResponseEntity.ok(clinicDtos);
    }

    @GetMapping("/doctors/{clinicId}")
    public ResponseEntity<List<DoctorDto>> getDoctorsByClinic(@PathVariable Long clinicId) {
        Type listType = new TypeToken<List<DoctorDto>>() {}.getType();
        List<DoctorDto> doctorDtos = modelMapper.map(medicalService.findDoctorsByClinicId(clinicId), listType);
        return ResponseEntity.ok(doctorDtos);
    }

    @PostMapping("/results")
    public ResponseEntity<List<AppointmentDto>> searchAppointments(
            @RequestBody AppointmentSearchCriteria criteria) {
        Type listType = new TypeToken<List<AppointmentDto>>() {}.getType();
        List<AppointmentDto> appointmentDtos = modelMapper.map(appointmentSearchService.findAvailableAppointments(criteria), listType);
        return ResponseEntity.ok(appointmentDtos);
    }
}