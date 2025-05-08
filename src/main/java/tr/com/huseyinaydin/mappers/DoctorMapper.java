package tr.com.huseyinaydin.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tr.com.huseyinaydin.dtos.DoctorDTO;
import tr.com.huseyinaydin.entities.Doctor;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorDTO toDto(Doctor doctor);
    List<DoctorDTO> toDtoList(List<Doctor> doctors);

    @Mapping(target = "id", ignore = true)
    Doctor toEntity(DoctorDTO doctorDto);
}