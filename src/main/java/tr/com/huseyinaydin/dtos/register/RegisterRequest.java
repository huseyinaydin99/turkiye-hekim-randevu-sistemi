package tr.com.huseyinaydin.dtos.register;

public record RegisterRequest(
        String fullName,
        String email,
        String nationalId,
        String password
) {
}