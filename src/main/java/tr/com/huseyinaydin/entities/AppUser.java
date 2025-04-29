package tr.com.huseyinaydin.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String nationalId;
    private String password; // E-devlet OAuth2 ile gerekirse null olabilir
    private String role; // PATIENT, ADMIN, DOCTOR

    @OneToMany(mappedBy = "user")
    private List<Appointment> appointments;
}