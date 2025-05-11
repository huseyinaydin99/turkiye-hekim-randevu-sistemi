package tr.com.huseyinaydin.domain.email;

public interface EmailSender {
    void send(String to, String subject, String htmlContent);
}