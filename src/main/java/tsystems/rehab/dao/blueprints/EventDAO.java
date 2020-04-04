package tsystems.rehab.dao.blueprints;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.query.NativeQuery;

import tsystems.rehab.entity.Event;

public interface EventDAO {

	void saveEvents(List<Event> event);
	
	void saveEvent(Event event);
	
	Date getNearestDate(long appointmentId);
	
	void deleteNearestEvents(long appointmentId);
	
	List<Event> getByAppointmentId(long appointmentId);
	
	Event getById(long id);
	
	List<Event> getAll(int pageSize, int pageNumber, String filterName);
	
	List<Event> getAllFiltered(int pageSize, int pageNumber, String filterName, String patientName);
	
	BigInteger getTotalNumberOfEvents(int pageSize, int pageNumber, String filterName, String patientName);
	
	NativeQuery constructQuery(int pageSize, int pageNumber, String filterName, String patientName, String startOfQuery, boolean count);
}
