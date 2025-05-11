package tr.com.huseyinaydin.controllers;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import tr.com.huseyinaydin.entities.AppUser;
import tr.com.huseyinaydin.entities.PasswordResetToken;
import tr.com.huseyinaydin.repositories.PasswordResetTokenRepository;
import tr.com.huseyinaydin.services.AppUserService;

import java.util.UUID;

@Controller
@RequestMapping("/reset-password")
@RequiredArgsConstructor
public class PasswordResetController {

    private final AppUserService userService;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @GetMapping
    public String showResetPasswordForm(@RequestParam(required = false) String token, Model model) {
        if (token == null) {
            return "forgot-password";
        }

        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
        if (resetToken == null || resetToken.isExpired()) {
            model.addAttribute("error", "Geçersiz veya süresi dolmuş token");
            return "reset-password-error";
        }

        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping
    public String handlePasswordReset(@RequestParam String email, RedirectAttributes redirectAttributes) {
        AppUser user = userService.findByEmail(email);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Bu email ile kayıtlı kullanıcı bulunamadı");
            return "redirect:/reset-password";
        }

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String resetLink = "http://localhost:8080/reset-password?token=" + token;
        sendPasswordResetEmail(user.getEmail(), resetLink);

        redirectAttributes.addFlashAttribute("message", "Şifre sıfırlama linki email adresinize gönderildi");
        return "redirect:/reset-password";
    }

    @PostMapping("/change")
    public String changePassword(@RequestParam String token,
                                 @RequestParam String password,
                                 RedirectAttributes redirectAttributes) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
        if (resetToken == null || resetToken.isExpired()) {
            redirectAttributes.addFlashAttribute("error", "Geçersiz veya süresi dolmuş token");
            return "redirect:/reset-password";
        }

        AppUser user = resetToken.getUser();
        userService.changeUserPassword(user, password);
        passwordResetTokenRepository.delete(resetToken);

        redirectAttributes.addFlashAttribute("message", "Şifreniz başarıyla değiştirildi");
        return "redirect:/login";
    }

    private void sendPasswordResetEmail(String email, String resetLink) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        Context context = new Context();
        context.setVariable("resetLink", resetLink);
        String htmlContent = templateEngine.process("password-reset-email", context);

        try {
            helper.setTo(email);
            helper.setSubject("Şifre Sıfırlama Talebi");
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            // Handle exception
        }
    }
}