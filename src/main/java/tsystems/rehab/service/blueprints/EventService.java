package tsystems.rehab.service.blueprints;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import tsystems.rehab.dto.AppointmentDto;
import tsystems.rehab.dto.EventDto;
import tsystems.rehab.dto.EventGeneratorDto;

public interface EventService {

	void generateEvents(AppointmentDto appointment, EventGeneratorDto eGen);
	
	LocalDateTime getNearestDate(Long appointmentId);
	
	void deleteNearestElements(Long appointmentId);
	
	void changeTimesPattern(AppointmentDto appointment, List<String> newTreatTime, LocalDateTime nearestDate);
	
	List<EventDto> getByAppointmentId(Long appointmentId);
	
	HashMap<String, Object> getAll(int pageSize, int pageNumber, String filterName, String patientName);
	
	void completeEvent(long id);
	
	void cancelEvent(long id);
}
