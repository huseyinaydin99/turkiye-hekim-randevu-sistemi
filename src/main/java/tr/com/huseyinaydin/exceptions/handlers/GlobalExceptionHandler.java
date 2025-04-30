package tr.com.huseyinaydin.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tr.com.huseyinaydin.exceptions.InvalidLoginException;

@ControllerAdvice
public class GlobalExceptionHandler {

    /*@ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<String> handleInvalidLoginException(InvalidLoginException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }*/

    @ExceptionHandler(InvalidLoginException.class)
    public String handleInvalidLoginException(InvalidLoginException ex, RedirectAttributes redirectAttributes) {
        // Hata mesajını yönlendirme ile birlikte ekleyebiliriz
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        // Login sayfasına yönlendirme
        return "redirect:/login?error=true";
    }

    // Diğer özel exception'lar için handler'lar ekleyebilirim
}