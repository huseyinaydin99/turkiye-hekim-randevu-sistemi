package tr.com.huseyinaydin.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "city")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class City {
    @Id
    @SequenceGenerator(
            name = "city_seq",
            sequenceName = "city_seq",
            allocationSize = 1  // Veritabanındaki sekans artış büyüklüğü ile uyumlu
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "city")
    private List<District> districts;
}