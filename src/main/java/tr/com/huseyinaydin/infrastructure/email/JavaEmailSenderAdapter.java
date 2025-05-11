package tr.com.huseyinaydin.infrastructure.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.domain.email.EmailSender;

@Service
@RequiredArgsConstructor
public class JavaEmailSenderAdapter implements EmailSender {
    private final JavaMailSender mailSender;

    @Override
    public void send(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setText(htmlContent, true);
            helper.setTo(to);
            helper.setSubject(subject);
            //helper.setFrom("noreply@demo.com");

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("E-posta gönderilemedi", e);
        }
    }
}