package tr.com.huseyinaydin.exceptions;

public class AppointmentNotAvailableException extends RuntimeException {
    public AppointmentNotAvailableException(String message) {
        super(message);
    }
}