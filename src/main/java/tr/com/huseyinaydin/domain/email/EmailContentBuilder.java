package tr.com.huseyinaydin.domain.email;

public interface EmailContentBuilder {
    String build(String name, String link);
}