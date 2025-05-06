package tr.com.huseyinaydin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.huseyinaydin.entities.OAuthIdentity;

import java.util.Optional;

public interface OAuthIdentityRepository extends JpaRepository<OAuthIdentity, Long> {

    // Provider ve providerId'ye göre OAuthIdentity'yi bulmak için bir metot
    Optional<OAuthIdentity> findByProviderAndProviderId(String provider, String providerId);

    // AppUser'a göre OAuthIdentity'yi bulmak için bir metot
    Optional<OAuthIdentity> findByUser_Id(Long userId);
}