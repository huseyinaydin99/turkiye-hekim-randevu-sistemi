package tr.com.huseyinaydin.dtos.locations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.huseyinaydin.dtos.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationHierarchyDto {
    private List<CityDto> cities;
    private List<DistrictDto> districts;
    private List<HospitalDto> hospitals;
    private List<ClinicDto> clinics;
    private List<DoctorDto> doctors;
}