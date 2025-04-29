package tr.com.huseyinaydin.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime appointmentDateTime;
    private boolean attended; // Gidildi mi gidilmedi mi
    private String noteToDoctor;

    @ManyToOne
    private AppUser user;

    @ManyToOne
    private Doctor doctor;
}