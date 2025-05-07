package tr.com.huseyinaydin.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tr.com.huseyinaydin.dtos.DoctorDto;
import tr.com.huseyinaydin.entities.Doctor;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorDto toDto(Doctor doctor);
    List<DoctorDto> toDtoList(List<Doctor> doctors);

    @Mapping(target = "id", ignore = true)
    Doctor toEntity(DoctorDto doctorDto);
}