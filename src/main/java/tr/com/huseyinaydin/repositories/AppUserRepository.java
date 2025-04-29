package tr.com.huseyinaydin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.huseyinaydin.entities.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
    boolean existsByEmail(String email);
}