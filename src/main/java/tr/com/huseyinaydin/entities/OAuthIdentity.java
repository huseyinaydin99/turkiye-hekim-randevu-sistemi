package tr.com.huseyinaydin.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "oauth_identity")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuthIdentity {
    @Id
    @SequenceGenerator(
            name = "oauth_identity_seq",
            sequenceName = "oauth_identity_seq",
            allocationSize = 1  // Veritabanındaki sekans artış büyüklüğü ile uyumlu
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "oauth_identity_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser user;
}