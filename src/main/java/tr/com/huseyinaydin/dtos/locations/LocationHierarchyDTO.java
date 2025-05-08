package tr.com.huseyinaydin.dtos.locations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.huseyinaydin.dtos.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationHierarchyDTO {
    private List<CityDTO> cities;
    private List<DistrictDTO> districts;
    private List<HospitalDTO> hospitals;
    private List<ClinicDTO> clinics;
    private List<DoctorDTO> doctors;
}