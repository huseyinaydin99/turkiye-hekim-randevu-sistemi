package tr.com.huseyinaydin.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account_confirmation_token")
public class AccountConfirmationToken {

    @Id
    @SequenceGenerator(
            name = "account_confirmation_token_seq",
            sequenceName = "account_confirmation_token_seq",
            allocationSize = 1  // Veritabanındaki sekans artış büyüklüğü ile uyumlu
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_confirmation_token_seq")
    @Column(name = "id")
    private Long id;

    private String token;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private AppUser user;

    public AccountConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, AppUser user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }

    // getters, setters, constructor
}