package tr.com.huseyinaydin.services.impls;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.domain.email.EmailContentBuilder;
import tr.com.huseyinaydin.domain.email.EmailSender;
import tr.com.huseyinaydin.dtos.AppUserDTO;
import tr.com.huseyinaydin.dtos.login.AllFieldLoginResponse;
import tr.com.huseyinaydin.dtos.login.LoginRequest;
import tr.com.huseyinaydin.dtos.register.RegisterRequest;
import tr.com.huseyinaydin.dtos.register.RegisterResponse;
import tr.com.huseyinaydin.entities.AccountConfirmationToken;
import tr.com.huseyinaydin.entities.AppUser;
import tr.com.huseyinaydin.entities.PasswordResetToken;
import tr.com.huseyinaydin.exceptions.InvalidLoginException;
import tr.com.huseyinaydin.exceptions.ResourceNotFoundException;
import tr.com.huseyinaydin.repositories.AppUserRepository;
import tr.com.huseyinaydin.repositories.ConfirmationTokenRepository;
import tr.com.huseyinaydin.repositories.PasswordResetTokenRepository;
import tr.com.huseyinaydin.services.AppUserService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailSender emailSender;
    private final EmailContentBuilder emailContentBuilder;

    @Override
    public AppUserDTO getUserById(Long id) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı. ID: " + id));

        return modelMapper.map(user, AppUserDTO.class);
    }

    @Override
    public AppUser findUserByEmail(String email) {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı. Email: " + email));

        return user;
    }

    @Override
    public AppUserDTO getUserByEmail(String email) {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı. Email: " + email));

        return modelMapper.map(user, AppUserDTO.class);
    }

    @Override
    public RegisterResponse register(RegisterRequest dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Bu e-posta zaten kayıtlı!");
        }

        AppUser user = AppUser.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .nationalId(dto.getNationalId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role("USER")
                .build();

        AppUser savedUser = userRepository.save(user);

        // 1. Token oluştur
        String token = UUID.randomUUID().toString();
        AccountConfirmationToken confirmationToken = new AccountConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                savedUser
        );
        confirmationTokenRepository.save(confirmationToken);

        // 2. Mail gönder
        String link = "http://localhost:8080/account-confirm/confirm?token=" + token;
        emailSender.send(
                savedUser.getEmail(),
                "Türkiye Hekim Randevu Sistemi hesap onay e-postası.",
                emailContentBuilder.build(savedUser.getFullName(), link)
        );

        return modelMapper.map(savedUser, RegisterResponse.class);
    }

    @Override
    public AllFieldLoginResponse login(LoginRequest request) {
        // Kullanıcı adı ve şifreyi doğrulamak için AuthenticationManager kullanılıyor kardeş
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        // AuthenticationManager ile kimlik doğrulama yapılır
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 🔥 Bu satır olmazsa isUserLoggedIn() daima false olur

        // Eğer doğrulama başarılıysa, başarılı yanıt döndürülür (Örneğin bir JWT token verilebilir)
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            AppUser user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new InvalidLoginException("Bu e-posta ile kayıtlı kullanıcı bulunamadı!"));

            return modelMapper.map(user, AllFieldLoginResponse.class);
        }

        throw new InvalidLoginException("Bu e-posta ile kayıtlı kullanıcı bulunamadı!");  // Hatalı giriş
    }

    /**
     * Mevcut oturum açmış kullanıcı bilgilerini döndürür.
     * Kullanıcı oturum açmamışsa null döner.
     */
    public UserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null &&
                authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {

            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * Mevcut oturum açmış kullanıcının entity nesnesini döndürür.
     * Kullanıcı oturum açmamışsa null döner.
     */
    public AppUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null &&
                authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {

            String email = authentication.getName(); // Kullanıcı adı veya e-posta
            return userRepository.findByEmail(email).orElse(null);
        }
        return null;
    }

    /**
     * Mevcut oturum açmış kullanıcının DTO nesnesini döndürür.
     * Kullanıcı oturum açmamışsa null döner.
     */
    public AllFieldLoginResponse getCurrentUserResponse() {
        AppUser user = getCurrentUser();
        if (user != null) {
            return modelMapper.map(user, AllFieldLoginResponse.class);
        }
        return null;
    }

    /**
     * Kullanıcının giriş yapmış olup olmadığını kontrol eder.
     */
    public boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
                authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken);
    }

    @Override
    public AppUser findByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public void createPasswordResetTokenForUser(AppUser user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

    @Override
    public void changeUserPassword(AppUser user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}