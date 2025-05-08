package tr.com.huseyinaydin.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDTO {
    private Long id;
    private String name;
    /*private CityDto city;
    private DistrictDto district;
    private List<ClinicDto> clinics;
    private List<DoctorDto> doctors;*/
}