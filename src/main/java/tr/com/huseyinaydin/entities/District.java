package tr.com.huseyinaydin.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "district")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class District {
    @Id
    @SequenceGenerator(
            name = "district_seq",
            sequenceName = "district_seq",
            allocationSize = 1  // Veritabanındaki sekans artış büyüklüğü ile uyumlu
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "district_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "district")
    private List<Hospital> hospitals;
}