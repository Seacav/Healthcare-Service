package tsystems.rehab.service.blueprints;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import tsystems.rehab.dto.AppointmentDto;
import tsystems.rehab.dto.EventDto;

public interface EventService {

	void generateEvents(AppointmentDto appnt, int duration, 
			List<Integer> days, List<String> treatTime, boolean startsNextWeek);
	
	LocalDateTime getNearestDate(Long appointmentId);
	
	void deleteNearestElements(Long appointmentId);
	
	void changeTimesPattern(AppointmentDto appointment, List<String> newTreatTime);
	
	List<EventDto> getByAppointmentId(Long appointmentId);
	
	HashMap<String, Object> getAll(int pageSize, int pageNumber, String filterName, String patientName);
	
	void completeEvent(long id);
	
	void cancelEvent(long id, String commentary);
	
	String getCommentary(long id);
	
	BigInteger getNumberOfActiveEvents(long id);
}
