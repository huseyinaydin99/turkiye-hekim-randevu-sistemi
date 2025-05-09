package tr.com.huseyinaydin.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tr.com.huseyinaydin.dtos.AvailableAppointmentDTO;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    //@PreAuthorize("hasRole('USER')")
    //@PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/confirm")
    @ResponseBody
    public ResponseEntity<String> confirmAppointment(@RequestBody AvailableAppointmentDTO dto) {
        System.out.println("Alınan veri: " + dto);
        // Şimdilik sadece logla, save yok
        return ResponseEntity.ok("Veri alındı.");
    }
}