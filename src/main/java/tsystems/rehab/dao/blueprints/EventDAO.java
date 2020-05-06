package tsystems.rehab.dao.blueprints;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.query.NativeQuery;

import tsystems.rehab.entity.Event;

public interface EventDAO {

	/**
	 * Constructs query with pagination and filter either to get number of events or event data.
	 * 
	 * @param pageSize number of displayed events on screen
	 * @param pageNumber page number displayed on screen
	 * @param filterName date filter to sort events
	 * @param patientName patient name to sort by patient
	 * @param count if 'true' return count query otherwise event data query 
	 * @return constructed query
	 */
	NativeQuery constructQuery(int pageSize, int pageNumber, String filterName, String patientName, boolean count);
	
	/**
	 * Deletes the remaining scheduled events before replacing them with newly generated events.
	 * Required for changing time pattern of appointment.
	 * 
	 * @param appointmentId appointment's id
	 */
	void deleteNearestEvents(long appointmentId);
	
	/**
	 * Get list of filtered events
	 * 
	 * @param pageSize number of displayed events on screen
	 * @param pageNumber page number displayed on screen
	 * @param filterName date filter to sort events
	 * @param patientName patient name to sort by patient
	 * @return list of filtered events
	 */
	List<Event> getAllFiltered(int pageSize, int pageNumber, String filterName, String patientName);
	
	/**
	 * Get all events of appointment
	 * 
	 * @param appointmentId appointment's id
	 * @return
	 */
	List<Event> getByAppointmentId(long appointmentId);
	
	/**
	 * Cancel appointment
	 * 
	 * @param appointmentId
	 */
	void cancelByAppointmentId(long appointmentId);
	
	/**
	 * @param patientId
	 * @return
	 */
	List<Event> getByPatientId(long patientId);
	
	/**
	 * @param patientId
	 */
	void cancelByPatientId(long patientId);
	
	/**
	 * @param id
	 * @return
	 */
	Event getById(long id);
	
	/**
	 * @param appointmentId
	 * @return
	 */
	Date getNearestDate(long appointmentId);
	
	/**
	 * @param appointmentId
	 * @return
	 */
	List<Event> getNearestEvents(long appointmentId);
	
	/**
	 * @param pageSize
	 * @param pageNumber
	 * @param filterName
	 * @param patientName
	 * @return
	 */
	BigInteger getTotalNumberOfEvents(int pageSize, int pageNumber, String filterName, String patientName);
	
	/**
	 * @param appointmentId
	 * @return
	 */
	BigInteger getNumberOfActiveEvents(long appointmentId);
	
	/**
	 * @param event
	 */
	void saveEvent(Event event);
	
	/**
	 * @param event
	 */
	void saveEvents(List<Event> event);

	/**
	 * @return
	 */
	List<Event> getEventsForToday();
	
}
