package tsystems.rehab.dao.blueprints;

import java.util.List;

import tsystems.rehab.entity.Patient;

public interface PatientDAO {
	List<Patient> list();
	
	void save(Patient patient);
	
	Patient get(long id);
	
	List<Patient> getByDoctorName(String doctorName);
	
	List<Patient> getByInsurance(String insNumber);
}
