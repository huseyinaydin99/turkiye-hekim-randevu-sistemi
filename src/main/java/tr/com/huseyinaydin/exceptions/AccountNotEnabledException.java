package tr.com.huseyinaydin.exceptions;

public class AccountNotEnabledException extends RuntimeException {
    private String message;

    public AccountNotEnabledException(String message) {
        super(message);
        this.message = message;
    }
}
