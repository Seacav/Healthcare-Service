package tsystems.rehab.dao.blueprints;

import java.util.List;

import tsystems.rehab.entity.Appointment;

public interface AppointmentDAO {
	
	/**
	 * @param id
	 * @return
	 */
	List<Appointment> getByPatientId(long id);
	
	/**
	 * @param id
	 */
	void cancelByPatientId(long id);
	
	/**
	 * @param appnt
	 */
	void save(Appointment appnt);
	
	/**
	 * @param appnt
	 * @return
	 */
	Appointment saveAndReturn(Appointment appnt);
	
	/**
	 * @param id
	 * @return
	 */
	Appointment getById(long id);
}
