package tr.com.huseyinaydin.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.huseyinaydin.entities.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    private Long id;
    private String fullName;
    private String specialty;
    private Long clinicId;
    private Long hospitalId;
}