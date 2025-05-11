package tr.com.huseyinaydin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.entities.AppUser;
import tr.com.huseyinaydin.exceptions.AccountNotEnabledException;
import tr.com.huseyinaydin.exceptions.InvalidLoginException;
import tr.com.huseyinaydin.repositories.AppUserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;

    public AppUserDetailsService() {
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Bu e-posta ile kayıtlı kullanıcı bulunamadı!"));

        if (!user.isAccountEnabled()) {
            throw new AccountNotEnabledException("Hesabınız henüz doğrulanmamış. Lütfen e-posta adresinizi kontrol edin.");
        }

        return new AppUserDetails(user);
    }
}