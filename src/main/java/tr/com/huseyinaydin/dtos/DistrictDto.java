package tr.com.huseyinaydin.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.huseyinaydin.entities.City;
import tr.com.huseyinaydin.entities.Hospital;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDto {
    private Long id;
    private String name;
    //private CityDto city;
    //private List<HospitalDto> hospitals;
}