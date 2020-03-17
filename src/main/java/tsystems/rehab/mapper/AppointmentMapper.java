package tsystems.rehab.mapper;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tsystems.rehab.dto.AppointmentDto;
import tsystems.rehab.entity.Appointment;

@Component
public class AppointmentMapper {

	@Autowired
	private ModelMapper mapper;
	
	public Appointment toEntity(AppointmentDto dto) {
		return Objects.isNull(dto)? null : mapper.map(dto, Appointment.class);
	}
	
	public AppointmentDto toDto(Appointment entity) {
		return Objects.isNull(entity) ? null : mapper.map(entity, AppointmentDto.class);
	}
	
}
