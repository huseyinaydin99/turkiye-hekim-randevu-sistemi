package tr.com.huseyinaydin.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Hospital {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne
    private District district;

    @OneToMany(mappedBy = "hospital")
    private List<Doctor> doctors;
}