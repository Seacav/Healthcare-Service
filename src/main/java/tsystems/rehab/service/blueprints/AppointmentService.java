package tsystems.rehab.service.blueprints;

import java.util.List;

import tsystems.rehab.dto.AppointmentDto;
import tsystems.rehab.dto.EventGeneratorDto;
import tsystems.rehab.entity.Appointment;

public interface AppointmentService {
	
	List<AppointmentDto> getByPatientId(long id);
	
	AppointmentDto getById(long id);
	
	void save(AppointmentDto appnt);
	
	AppointmentDto saveAndReturn(AppointmentDto appnt);
	
	AppointmentDto generateAppointmentDto(EventGeneratorDto eGen);

}
