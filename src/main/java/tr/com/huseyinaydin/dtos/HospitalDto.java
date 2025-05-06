package tr.com.huseyinaydin.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.huseyinaydin.entities.City;
import tr.com.huseyinaydin.entities.Clinic;
import tr.com.huseyinaydin.entities.District;
import tr.com.huseyinaydin.entities.Doctor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDto {
    private Long id;
    private String name;
    /*private CityDto city;
    private DistrictDto district;
    private List<ClinicDto> clinics;
    private List<DoctorDto> doctors;*/
}