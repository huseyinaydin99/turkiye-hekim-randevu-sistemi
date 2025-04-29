package tr.com.huseyinaydin.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    private City city;

    @ManyToOne
    private District district;

    @OneToMany(mappedBy = "hospital")
    private List<Doctor> doctors;
}