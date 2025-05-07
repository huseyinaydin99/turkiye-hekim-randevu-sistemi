package tr.com.huseyinaydin.services;

import org.springframework.security.core.userdetails.UserDetails;
import tr.com.huseyinaydin.dtos.AppUserDto;
import tr.com.huseyinaydin.dtos.login.AllFieldLoginResponse;
import tr.com.huseyinaydin.dtos.login.LoginRequest;
import tr.com.huseyinaydin.dtos.register.RegisterRequest;
import tr.com.huseyinaydin.dtos.register.RegisterResponse;

public interface AppUserService {
    RegisterResponse register(RegisterRequest dto);
    AllFieldLoginResponse login(LoginRequest request);
    boolean isUserLoggedIn();
    UserDetails getCurrentUserDetails();
    AppUserDto getUserById(Long id);
    AppUserDto getUserByEmail(String email);
}