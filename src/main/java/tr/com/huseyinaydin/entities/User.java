package tr.com.huseyinaydin.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String fullName;
    private String email;
    private String nationalId;
    private String password; // E-devlet OAuth2 ile gerekirse null olabilir
    private String role; // PATIENT, ADMIN, DOCTOR

    @OneToMany(mappedBy = "user")
    private List<Appointment> appointments;
}