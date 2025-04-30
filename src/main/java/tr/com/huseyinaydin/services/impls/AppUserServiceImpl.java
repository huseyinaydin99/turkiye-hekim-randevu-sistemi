package tr.com.huseyinaydin.services.impls;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.dtos.login.LoginRequest;
import tr.com.huseyinaydin.dtos.login.LoginResponse;
import tr.com.huseyinaydin.dtos.register.RegisterRequest;
import tr.com.huseyinaydin.entities.AppUser;
import tr.com.huseyinaydin.exceptions.InvalidLoginException;
import tr.com.huseyinaydin.repositories.AppUserRepository;
import tr.com.huseyinaydin.services.AppUserService;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    public AppUserServiceImpl(AppUserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public LoginResponse register(RegisterRequest dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Bu e-posta zaten kayıtlı!");
        }

        AppUser user = AppUser.builder()
                .fullName(dto.fullName())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role("ROLE_USER")
                .build();

        return modelMapper.map(userRepository.save(user), LoginResponse.class);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // Kullanıcı adı ve şifreyi doğrulamak için AuthenticationManager kullanılıyor kardeş
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.email(), request.password());

        // AuthenticationManager ile kimlik doğrulama yapılır
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // Eğer doğrulama başarılıysa, başarılı yanıt döndürülür (Örneğin bir JWT token verilebilir)
        if (authentication.isAuthenticated()) {
            AppUser user = userRepository.findByEmail(request.email())
                    .orElseThrow(() -> new IllegalArgumentException("Bu e-posta ile kayıtlı kullanıcı bulunamadı!"));

            return modelMapper.map(user, LoginResponse.class);
        }

        throw new InvalidLoginException("Bu e-posta ile kayıtlı kullanıcı bulunamadı!");  // Hatalı giriş
    }
}