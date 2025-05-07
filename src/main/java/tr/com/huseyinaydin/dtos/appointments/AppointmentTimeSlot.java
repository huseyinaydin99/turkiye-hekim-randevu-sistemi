package tr.com.huseyinaydin.dtos.appointments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentTimeSlot {
    private LocalTime startTime;
    private LocalTime endTime;
    private Long doctorId;
    private String doctorName;
    private String specialty;
    private Long clinicId;
    private Long hospitalId;
    private Long districtId;
    private Long cityId;
}