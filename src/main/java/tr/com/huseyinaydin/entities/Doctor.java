package tr.com.huseyinaydin.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String title;

    @ManyToOne
    private Clinic clinic;

    @ManyToOne
    private Hospital hospital;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;
}