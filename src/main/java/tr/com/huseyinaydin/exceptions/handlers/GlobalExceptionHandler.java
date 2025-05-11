package tr.com.huseyinaydin.exceptions.handlers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tr.com.huseyinaydin.exceptions.AccountNotEnabledException;
import tr.com.huseyinaydin.exceptions.InvalidLoginException;
import tr.com.huseyinaydin.dtos.errors.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    /*@ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<String> handleInvalidLoginException(InvalidLoginException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }*/

    @ExceptionHandler(InvalidLoginException.class)
    public String handleInvalidLoginException(InvalidLoginException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        // Login sayfasına yönlendirme
        return "redirect:/errorPage?error=true";
    }

    @ExceptionHandler(AccountNotEnabledException.class)
    public String handleAccountNotEnabledException(AccountNotEnabledException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        // Login sayfasına yönlendirme
        return "redirect:/accountNotEnabled?error=true";
    }

    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthenticationException(AuthenticationException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Kullanıcı adı veya şifre hatalı!");
        return "redirect:/errorPage?error=true";
    }

    /*@ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(
                "NOT_FOUND",
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse response = new ErrorResponse(
                "BAD_REQUEST",
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse response = new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                "Beklenmeyen bir hata oluştu kardeş hayırlısı olsun :-)",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    // Diğer özel exception'lar için handler'lar ekleyebilirim
}