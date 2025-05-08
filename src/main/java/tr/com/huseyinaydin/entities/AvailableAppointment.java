package tr.com.huseyinaydin.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "available_appointment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailableAppointment {
    @Id
    @SequenceGenerator(
            name = "available_appointment_seq",
            sequenceName = "available_appointment_seq",
            allocationSize = 1  // Veritabanındaki sekans artış büyüklüğü ile uyumlu
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "available_appointment_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "available_appointment_date_time_start", nullable = false)
    private LocalDateTime appointmentDateTimeStart;

    @Column(name = "available_appointment_date_time_end", nullable = false)
    private LocalDateTime appointmentDateTimeEnd;

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
}