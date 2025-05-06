package tr.com.huseyinaydin.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tr.com.huseyinaydin.dtos.appointments.AppointmentSearchForm;
import tr.com.huseyinaydin.dtos.login.LoginRequest;
import tr.com.huseyinaydin.dtos.register.RegisterResponse;
import tr.com.huseyinaydin.dtos.register.RegisterRequest;
import tr.com.huseyinaydin.services.AppUserService;
import tr.com.huseyinaydin.session.UserSession;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private UserSession userSession;
    private final ModelMapper modelMapper;

    // Kayıt sayfasını döndürme
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register"; // Thymeleaf template sayfası döner
    }

    // Kayıt işlemi
    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("registerRequest") RegisterRequest registerRequest,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        appUserService.register(registerRequest);
        return "redirect:/login";
    }

    // Giriş sayfasını döndürme (Custom Login)
    @GetMapping("/clogin")
    public ModelAndView showLoginPage(Model model) {
        ModelAndView page = new ModelAndView("ulogin");
        page.addObject("loginRequest", new LoginRequest());
        return page; // Custom login page/özel giriş sayfası tasarımı Thymeleaf template / şablonu
    }

    // Giriş işlemi
    // Özel Giriş işlemi - Spring Security ile çakışmayan endpoint
    @PostMapping("/clogin")
    public ModelAndView customLoginUser(
            @Valid @ModelAttribute("loginRequest") LoginRequest loginRequest,
            BindingResult bindingResult,
            HttpServletRequest request) {

        // Validasyon hataları
        if (bindingResult.hasErrors()) {
            ModelAndView errorView = new ModelAndView("ulogin");
            errorView.addObject("loginRequest", loginRequest);
            return errorView;
        }

        userSession = modelMapper.map(appUserService.login(loginRequest), UserSession.class);

        ModelAndView modelAndView = new ModelAndView("login-success");

        if(userSession != null){
            modelAndView.addObject("searchForm", new AppointmentSearchForm()); // form nesnesini ekliyoruz
            modelAndView.addObject("nameSurname", userSession.getFullName());
            return modelAndView;
        }
        else {
            ModelAndView errorView = new ModelAndView("login-error");
            errorView.addObject("loginRequest", loginRequest);
            errorView.addObject("errorMessage", "Email veya şifre hatalı!");
            return errorView;
        }
    }
}