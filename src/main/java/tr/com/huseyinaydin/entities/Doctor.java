package tr.com.huseyinaydin.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Doctor {
    @Id
    @GeneratedValue
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