package tr.com.huseyinaydin.dtos.register;

public record RegisterResponse(
        String fullName,
        String email,
        String nationalId,
        String password
) {
}