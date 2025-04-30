package tr.com.huseyinaydin.dtos.register;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private Long id;
    private String fullName;
    private String email;
    private String nationalId;
    private String password;
    private String role;
}