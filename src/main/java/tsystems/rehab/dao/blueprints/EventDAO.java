package tsystems.rehab.dao.blueprints;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.query.NativeQuery;

import tsystems.rehab.entity.Event;

public interface EventDAO {

	NativeQuery<?> constructQuery(int pageSize, int pageNumber, String filterName, String patientName, boolean count);
	
	void deleteNearestEvents(long appointmentId);
	
	List<Event> getAllFiltered(int pageSize, int pageNumber, String filterName, String patientName);
	
	List<Event> getByAppointmentId(long appointmentId);
	
	void cancelByAppointmentId(long appointmentId);
	
	List<Event> getByPatientId(long patientId);
	
	void cancelByPatientId(long patientId);
	
	Event getById(long id);
	
	Date getNearestDate(long appointmentId);
	
	List<Event> getNearestEvents(long appointmentId);
	
	BigInteger getTotalNumberOfEvents(int pageSize, int pageNumber, String filterName, String patientName);
	
	BigInteger getNumberOfActiveEvents(long appointmentId);
	
	void saveEvent(Event event);
	
	void saveEvents(List<Event> event);

	List<Event> getEventsForToday();
	
}
