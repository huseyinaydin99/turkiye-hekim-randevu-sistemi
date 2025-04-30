package tr.com.huseyinaydin.dtos.login;

public record LoginRequest(
        String email,
        String password
) {
}