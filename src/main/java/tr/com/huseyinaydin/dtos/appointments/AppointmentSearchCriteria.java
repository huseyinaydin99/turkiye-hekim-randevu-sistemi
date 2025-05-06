package tr.com.huseyinaydin.dtos.appointments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSearchCriteria {
    private Long doctorId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public boolean hasDateRange() {
        return startDate != null && endDate != null;
    }
}