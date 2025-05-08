package tr.com.huseyinaydin.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClinicDTO {
    private Long id;
    private String name;
    //private HospitalDto hospital;
    //private DistrictDto district;
    //private CityDto city;
    //private List<DoctorDto> doctors;
}