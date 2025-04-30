package tr.com.huseyinaydin.exceptions;

public class InvalidLoginException extends RuntimeException {

    private final String message;

    // Parametresiz constructor
    public InvalidLoginException() {
        super("Geçersiz giriş!");
        this.message = "Geçersiz giriş!";
    }

    // Mesaj parametresi alan constructor
    public InvalidLoginException(String message) {
        super(message);
        this.message = message;
    }

    // Mesaj ve cause parametreleri alan constructor
    public InvalidLoginException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}