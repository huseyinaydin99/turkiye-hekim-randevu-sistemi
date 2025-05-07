package tr.com.huseyinaydin.dtos.appointments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest {
    private Long doctorId;
    private Long doctorVersion; // Optimistic lock için
    private Long clinicId;
    private Long hospitalId;
    private Long userId;
    private LocalDateTime appointmentDateTime;
    private String noteToDoctor;
}