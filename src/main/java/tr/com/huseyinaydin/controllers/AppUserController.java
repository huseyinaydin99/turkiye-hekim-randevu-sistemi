package tr.com.huseyinaydin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tr.com.huseyinaydin.dtos.login.LoginRequest;
import tr.com.huseyinaydin.dtos.register.RegisterResponse;
import tr.com.huseyinaydin.dtos.register.RegisterRequest;
import tr.com.huseyinaydin.services.AppUserService;

@Controller
@RequestMapping
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    // Kayıt sayfasını döndürme
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest(null, "", "", "", "", ""));
        return "register"; // Thymeleaf template sayfası döner
    }

    // Kayıt işlemi
    @PostMapping("/register")
    public String registerUser(RegisterResponse registerRequest) {
        appUserService.register(registerRequest);  // Register işlemi
        return "redirect:/login";  // Kayıttan sonra login sayfasına yönlendir
    }

    // Giriş sayfasını döndürme (Custom Login)
    @GetMapping("/ulogin")
    public String showLoginPage(Model model) {
        model.addAttribute("registerRequest", new LoginRequest("", ""));
        return "ulogin"; // Custom login page/özel giriş sayfası tasarımı Thymeleaf template / şablonu
    }

    // Giriş işlemi
    // Özel Giriş işlemi - Spring Security ile çakışmayan endpoint
    @PostMapping("/clogin")
    public String customLoginUser(@RequestParam String email, @RequestParam String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        //System.out.println("customLoginUser: " + loginRequest.toString());

        // Kullanıcının zaten giriş yapıp yapmadığını kontrol et
        if (appUserService.isUserLoggedIn()) {
            // Eğer kullanıcı zaten giriş yapmışsa, ana sayfaya veya dashboard'a yönlendir
            return "login-success";  // Örneğin ana sayfa
        }

        // Eğer kullanıcı henüz giriş yapmamışsa, login işlemi yapılır
        appUserService.login(loginRequest);
        return appUserService.isUserLoggedIn() ? "login-success" : "login-error";
    }
}
