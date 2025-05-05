package tr.com.huseyinaydin.dtos.register;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    //@NotNull(message = "ID boş olamaz")
    private Long id;

    @NotBlank(message = "Ad soyad boş olamaz")
    @Size(min = 5, max = 50, message = "Ad soyad 3-50 karakter arasında olmalı")
    private String fullName;

    @NotBlank(message = "Email boş olamaz")
    @Email(message = "Geçersiz email formatı")
    private String email;

    @Pattern(regexp = "[0-9]{11}", message = "TCKN 11 haneli olmalı")
    private String nationalId;

    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 6, message = "Şifre en az 6 karakter olmalı")
    private String password;

    //@NotBlank(message = "Rol boş olamaz")
    private String role;

    // Constructor, getter ve setter'lar Lombok'dan.
}