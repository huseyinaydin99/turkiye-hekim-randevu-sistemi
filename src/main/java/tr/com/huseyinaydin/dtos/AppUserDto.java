package tr.com.huseyinaydin.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.huseyinaydin.entities.Appointment;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {
    private Long id;
    private String fullName;
    private String email;
    private String nationalId;
    private String password;
    private String role;
    //private List<AppointmentDto> appointments;
}