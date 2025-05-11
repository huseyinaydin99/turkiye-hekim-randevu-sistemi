package tr.com.huseyinaydin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tr.com.huseyinaydin.entities.AccountConfirmationToken;
import tr.com.huseyinaydin.entities.AppUser;
import tr.com.huseyinaydin.repositories.AppUserRepository;
import tr.com.huseyinaydin.repositories.ConfirmationTokenRepository;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account-confirm")
public class AccountConfirmController {

    private final ConfirmationTokenRepository tokenRepository;
    private final AppUserRepository userRepository;

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        AccountConfirmationToken confirmationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Geçersiz token"));

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token süresi dolmuş");
        }

        AppUser user = confirmationToken.getUser();
        user.setAccountEnabled(true);
        userRepository.save(user);

        tokenRepository.delete(confirmationToken);

        return "redirect:/login";
    }
}