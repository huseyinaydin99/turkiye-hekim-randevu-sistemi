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

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "statu")
    private boolean statu;

    public Appointment(LocalDateTime appointmentDateTime, boolean attended, String noteToDoctor, AppUser user, Doctor doctor, Clinic clinic, Hospital hospital, District district, City city) {
        this.appointmentDateTime = appointmentDateTime;
        this.attended = attended;
        this.noteToDoctor = noteToDoctor;
        this.user = user;
        this.doctor = doctor;
        this.clinic = clinic;
        this.hospital = hospital;
        this.district = district;
        this.city = city;
    }
}