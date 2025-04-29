package tr.com.huseyinaydin.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "clinic")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Clinic {
    @Id
    @SequenceGenerator(
            name = "clinic_seq",
            sequenceName = "clinic_seq",
            allocationSize = 1  // Veritabanındaki sekans artış büyüklüğü ile uyumlu
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clinic_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @OneToMany(mappedBy = "clinic")
    private List<Doctor> doctors;
}