package tr.com.huseyinaydin.infrastructure.email;

import org.springframework.stereotype.Component;
import tr.com.huseyinaydin.domain.email.EmailContentBuilder;

@Component
public class VerificationEmailBuilder implements EmailContentBuilder {
    @Override
    public String build(String name, String link) {
        return "<h2>Merhaba " + name + "</h2>" +
                "<p>Hesabınızı doğrulamak için <a href='" + link + "'>buraya tıklayın</a>.</p>";
    }
}