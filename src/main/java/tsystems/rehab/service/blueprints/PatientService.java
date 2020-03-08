package tsystems.rehab.service.blueprints;

import java.util.List;

import tsystems.rehab.entity.Patient;

public interface PatientService {
	List<Patient> list();
	
	List<Patient> listOfDoctor(String doctorName);
	
	void save(Patient patient);
	
	Patient get(long id);
	
	Patient getByInsurance(String insNumber);
}
