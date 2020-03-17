package tsystems.rehab.dao.blueprints;

import java.util.List;

import tsystems.rehab.entity.Appointment;

public interface AppointmentDAO {
	List<Appointment> list();
	
	List<Appointment> getByPatientId(long id);
	
	void save(Appointment appnt);
}
