package tr.com.huseyinaydin.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {
    private Long id;
    private String fullName;
    private String email;
    private String nationalId;
    private String password;
    private String role;
    //private List<AppointmentDto> appointments;
}