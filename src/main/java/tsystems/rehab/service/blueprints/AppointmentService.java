package tsystems.rehab.service.blueprints;

import java.util.List;

import tsystems.rehab.dto.AppointmentDto;
import tsystems.rehab.dto.EventGeneratorDto;

public interface AppointmentService {
	
	List<AppointmentDto> getByPatientId(long id, String doctorName);
	
	void cancelByPatientId(long id);
	
	AppointmentDto getById(long id);
	
	void save(AppointmentDto appnt);
	
	void processAppointmentForm(EventGeneratorDto eGen);
	
	void changeTimePattern(long appointmentId, List<String> treatTime);
	
	void changeDosage(long appointmentId, String dosage);
	
	void cancelAppointment(long appointmentId);

}
