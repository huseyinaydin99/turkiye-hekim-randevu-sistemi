package tr.com.huseyinaydin.services;

import tr.com.huseyinaydin.dtos.login.LoginRequest;
import tr.com.huseyinaydin.dtos.login.LoginResponse;
import tr.com.huseyinaydin.dtos.register.RegisterRequest;

public interface AppUserService {
    LoginResponse register(RegisterRequest dto);
    LoginResponse login(LoginRequest request);
}