package tsystems.rehab.service.blueprints;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import tsystems.rehab.dto.AppointmentDto;
import tsystems.rehab.dto.EventDto;
import tsystems.rehab.dto.EventTableDto;

public interface EventService {

	void generateEvents(AppointmentDto appnt, int duration, 
			List<Integer> days, List<String> treatTime, boolean startsNextWeek);
	
	LocalDateTime getNearestDate(Long appointmentId);
	
	void deleteNearestElements(Long appointmentId);
	
	void changeTimesPattern(AppointmentDto appointment, List<String> newTreatTime);
	
	void changeDosage(long appointmentId);
	
	List<EventTableDto> getEventsForToday();
	
	List<EventDto> getByAppointmentId(Long appointmentId);
	
	void cancelByAppointmentId(Long appointmentId);
	
	void cancelByPatientId(long patientId);
	
	HashMap<String, Object> getAll(int pageSize, int pageNumber, String filterName, String patientName);
	
	void completeEvent(long id);
	
	void cancelEvent(long id, String commentary);
	
	String getCommentary(long id);
	
	BigInteger getNumberOfActiveEvents(long id);
	
}
