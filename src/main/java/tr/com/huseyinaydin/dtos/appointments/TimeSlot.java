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
public class TimeSlot {
    private LocalTime startTime;
    private LocalTime endTime;
}