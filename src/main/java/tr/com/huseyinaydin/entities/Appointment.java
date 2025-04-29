package tr.com.huseyinaydin.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
@Data
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @SequenceGenerator(
            name = "appointment_seq",
            sequenceName = "appointment_seq",
            allocationSize = 1  // Veritabanındaki sekans artış büyüklüğü ile uyumlu
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "appointment_date_time", nullable = false)
    private LocalDateTime appointmentDateTime;

    @Column(name = "attended", nullable = false)
    private boolean attended;

    @Column(name = "note_to_doctor")
    private String noteToDoctor;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}