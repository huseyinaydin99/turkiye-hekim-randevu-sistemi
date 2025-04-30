package tr.com.huseyinaydin.dtos.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllFieldLoginResponse {
    private Long id;
    private String fullName;
    private String email;
    private String nationalId;
    private String password;
    private String role;
}