package tr.com.huseyinaydin.dtos.appointments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSearchCriteria {
    private Long cityId;
    private Long districtId;
    private Long hospitalId;
    private Long clinicId;
    private Long doctorId;
    private LocalDateTime startDate;
    //private LocalDateTime endDate;
}