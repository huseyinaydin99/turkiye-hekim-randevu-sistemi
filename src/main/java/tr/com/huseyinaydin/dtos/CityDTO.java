package tr.com.huseyinaydin.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {
    private Long id;
    private String name;
    //private List<DistrictDto> districts;
}