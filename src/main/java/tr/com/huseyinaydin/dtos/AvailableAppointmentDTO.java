package tr.com.huseyinaydin.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailableAppointmentDTO {
    private Long id;
    private LocalDateTime appointmentDateTimeStart;
    private LocalDateTime appointmentDateTimeEnd;
    private Long clinicId;
    private Long hospitalId;
    private Long districtId;
    private Long cityId;
    private Long doctorId;

    private String doctorNote;
    private String cityName;
    private String districtName;
    private String hospitalName;
    private String clinicName;
    private String doctorName;
}