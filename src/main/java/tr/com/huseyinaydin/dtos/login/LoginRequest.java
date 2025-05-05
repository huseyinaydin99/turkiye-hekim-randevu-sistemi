package tr.com.huseyinaydin.dtos.login;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Email boş olamaz")
    @Email(message = "Geçersiz email formatı")
    private String email;

    @NotBlank(message = "Şifre boş olamaz")
    @Size(message = "Şifre en az 9, en fazla 20 karakter olmalıdır", min = 9, max = 20)
    private String password;

    // Constructor, getter ve setter'lar Lombok'dan
}