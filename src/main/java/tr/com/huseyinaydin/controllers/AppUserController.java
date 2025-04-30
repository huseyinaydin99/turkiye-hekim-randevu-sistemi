package tr.com.huseyinaydin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tr.com.huseyinaydin.dtos.login.LoginRequest;
import tr.com.huseyinaydin.dtos.register.RegisterResponse;
import tr.com.huseyinaydin.dtos.register.RegisterRequest;
import tr.com.huseyinaydin.services.AppUserService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

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
    public ModelAndView customLoginUser(@RequestParam String email, @RequestParam String password) throws UnsupportedEncodingException {
        LoginRequest loginRequest = new LoginRequest(email, password);
        // Eğer kullanıcı henüz giriş yapmamışsa, login işlemi yapılır
        var user = appUserService.login(loginRequest);
        ModelAndView modelAndView = new ModelAndView("login-success");

        if(user != null){
            modelAndView.addObject("nameSurname", user.getFullName());
            return modelAndView;
        }
        else {
            ModelAndView error = new ModelAndView("login-error");
            error.addObject("errorMessage", "Giriş başarısız oldu.");
            return error;
        }
    }
}