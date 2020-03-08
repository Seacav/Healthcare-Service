package tsystems.rehab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tsystems.rehab.dao.blueprints.PatientDAO;
import tsystems.rehab.entity.Patient;
import tsystems.rehab.service.blueprints.PatientService;

@Service
@Transactional
public class PatientServiceImpl implements PatientService{

	@Autowired
	private PatientDAO patientDAO;
	
	@Override
	public List<Patient> list() {
		return patientDAO.list();
	}

	@Override
	public void save(Patient patient) {
		patientDAO.save(patient);
	}

	@Override
	public Patient get(long id) {
		return patientDAO.get(id);
	}

	@Override
	public Patient getByInsurance(String insNumber) {
		List<Patient> patients = patientDAO.getByInsurance(insNumber);
		if (patients.isEmpty()) {
			return null;
		}
		return patients.get(0);
	}

	@Override
	public List<Patient> listOfDoctor(String doctorName) {
		return patientDAO.getByDoctorName(doctorName);
	}

}
