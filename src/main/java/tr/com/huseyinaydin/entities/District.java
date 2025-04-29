package tr.com.huseyinaydin.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class District {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne
    private City city;

    @OneToMany(mappedBy = "district")
    private List<Hospital> hospitals;
}