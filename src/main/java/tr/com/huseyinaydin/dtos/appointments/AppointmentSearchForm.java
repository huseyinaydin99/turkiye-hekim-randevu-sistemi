package tr.com.huseyinaydin.dtos.appointments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentSearchForm {
    private String city;
    private String district;
    private String hospital;
    private String clinic;
    private String doctor;
}