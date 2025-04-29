package tr.com.huseyinaydin.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Appointment {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime dateTime;
    private boolean attended; // Gidildi mi gidilmedi mi
    private String noteToDoctor;

    @ManyToOne
    private User user;

    @ManyToOne
    private Doctor doctor;
}