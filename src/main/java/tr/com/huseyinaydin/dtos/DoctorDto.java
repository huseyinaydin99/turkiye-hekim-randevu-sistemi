package tr.com.huseyinaydin.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.huseyinaydin.entities.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {
    private Long id;
    private String fullName;
    private String title;
    /*private ClinicDto clinic;
    private HospitalDto hospital;
    private DistrictDto district;
    private CityDto city;
    private List<AppointmentDto> appointments;*/
}