package tr.com.huseyinaydin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.huseyinaydin.entities.AccountConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<AccountConfirmationToken, Long> {
    Optional<AccountConfirmationToken> findByToken(String token);
}