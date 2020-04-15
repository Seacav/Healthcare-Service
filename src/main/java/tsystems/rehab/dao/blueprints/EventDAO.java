package tsystems.rehab.dao.blueprints;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.query.NativeQuery;

import tsystems.rehab.entity.Event;

public interface EventDAO {

	NativeQuery constructQuery(int pageSize, int pageNumber, String filterName, String patientName, String startOfQuery, boolean count);
	
	void deleteNearestEvents(long appointmentId);
	
	List<Event> getAll(int pageSize, int pageNumber, String filterName);
	
	List<Event> getAllFiltered(int pageSize, int pageNumber, String filterName, String patientName);
	
	List<Event> getByAppointmentId(long appointmentId);
	
	Event getById(long id);
	
	Date getNearestDate(long appointmentId);
	
	BigInteger getTotalNumberOfEvents(int pageSize, int pageNumber, String filterName, String patientName);
	
	BigInteger getNumberOfActiveEvents(long appointmentId);
	
	void saveEvent(Event event);
	
	void saveEvents(List<Event> event);
}
