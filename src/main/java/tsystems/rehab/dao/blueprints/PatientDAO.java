package tsystems.rehab.dao.blueprints;

import java.util.List;

import tsystems.rehab.entity.Patient;

public interface PatientDAO {
	
	/**
	 * @param patient
	 */
	void save(Patient patient);
	
	/**
	 * @param id
	 * @return
	 */
	Patient get(long id);
	
	/**
	 * @param doctorName
	 * @return
	 */
	List<Patient> getByDoctorName(String doctorName);
	
	/**
	 * @param insNumber
	 * @return
	 */
	List<Patient> getByInsurance(String insNumber);
}
