package tr.com.huseyinaydin.services;

import org.springframework.security.core.userdetails.UserDetails;
import tr.com.huseyinaydin.dtos.AppUserDTO;
import tr.com.huseyinaydin.dtos.login.AllFieldLoginResponse;
import tr.com.huseyinaydin.dtos.login.LoginRequest;
import tr.com.huseyinaydin.dtos.register.RegisterRequest;
import tr.com.huseyinaydin.dtos.register.RegisterResponse;
import tr.com.huseyinaydin.entities.AppUser;

public interface AppUserService {
    RegisterResponse register(RegisterRequest dto);
    AllFieldLoginResponse login(LoginRequest request);
    boolean isUserLoggedIn();
    UserDetails getCurrentUserDetails();
    AppUserDTO getUserById(Long id);
    AppUserDTO getUserByEmail(String email);
    AppUser findUserByEmail(String email);

    AppUser findByEmail(String email);
    void createPasswordResetTokenForUser(AppUser user, String token);
    void changeUserPassword(AppUser user, String password);
}