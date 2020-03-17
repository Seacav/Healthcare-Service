package tsystems.rehab.service.blueprints;

import java.util.List;

import tsystems.rehab.entity.Appointment;

public interface AppointmentService {
	
	List<Appointment> list();
	
	List<Appointment> getByPatientId(long id);
	
	void save(Appointment appnt, long patientId);

}
