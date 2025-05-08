package tr.com.huseyinaydin.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
    private Long id;
    private LocalDateTime appointmentDateTime;
    private boolean attended;
    private String noteToDoctor;
    /*private AppUserDto user;
    private DoctorDto doctor;
    private ClinicDto clinic;
    private HospitalDto hospital;
    private DistrictDto district;
    private CityDto city;*/
}