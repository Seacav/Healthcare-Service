package tsystems.rehab.service.blueprints;

import java.util.List;

import tsystems.rehab.dto.PatientDto;

public interface PatientService {
	List<PatientDto> listOfDoctor(String doctorName);
	
	void save(PatientDto patient);
	
	PatientDto get(long id);
	
	PatientDto getByInsurance(String insNumber);
	
	void addExistingPatient(long patientId, String diagnosis, String doctorName);
	
	void dischargePatient(long patientId);
	
	void processPatientForm(PatientDto patient, String doctorName);
}
