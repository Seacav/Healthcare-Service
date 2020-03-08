package tsystems.rehab.dao.blueprints;

import java.util.List;

import tsystems.rehab.entity.Appointment;

public interface AppointmentDAO {
	List<Appointment> list();
	
	void save(Appointment appnt);
}
