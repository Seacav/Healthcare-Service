package tsystems.rehab.dao.blueprints;

import java.util.List;

import tsystems.rehab.entity.Appointment;

public interface AppointmentDAO {
	List<Appointment> getByPatientId(long id);
	
	void cancelByPatientId(long id);
	
	void save(Appointment appnt);
	
	Appointment saveAndReturn(Appointment appnt);
	
	Appointment getById(long id);
}
