package tr.com.huseyinaydin;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ThrsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThrsApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		/*modelMapper.typeMap(AvailableAppointment.class, AvailableAppointmentDto.class).addMappings(mapper -> {
			mapper.map(src -> src.getDoctor() != null ? src.getDoctor().getId() : null, AvailableAppointmentDto::setDoctorId);
			mapper.map(src -> src.getClinic() != null ? src.getClinic().getId() : null, AvailableAppointmentDto::setClinicId);
			mapper.map(src -> src.getHospital() != null ? src.getHospital().getId() : null, AvailableAppointmentDto::setHospitalId);
			mapper.map(src -> src.getDistrict() != null ? src.getDistrict().getId() : null, AvailableAppointmentDto::setDistrictId);
			mapper.map(src -> src.getCity() != null ? src.getCity().getId() : null, AvailableAppointmentDto::setCityId);
		});*/
		return modelMapper;
	}
}