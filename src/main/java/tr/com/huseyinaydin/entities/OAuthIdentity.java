package tr.com.huseyinaydin.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class OAuthIdentity {
    @Id
    @GeneratedValue
    private Long id;

    private String provider; // E-Devlet
    private String providerId;

    @OneToOne
    private User user;
}