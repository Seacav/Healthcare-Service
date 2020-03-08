package tsystems.rehab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tsystems.rehab.dao.blueprints.AppointmentDAO;
import tsystems.rehab.dao.blueprints.PatientDAO;
import tsystems.rehab.entity.Appointment;
import tsystems.rehab.entity.Patient;

import tsystems.rehab.service.blueprints.AppointmentService;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService{
	
	@Autowired
	private AppointmentDAO appointmentDAO;

	@Autowired
	private PatientDAO patientDAO;
	
	@Override
	public List<Appointment> list() {
		return appointmentDAO.list();
	}

	@Override
	public void save(Appointment appnt, long patientId) {
		Patient patient = patientDAO.get(patientId);
		appnt.setPatient(patient);
		patient.add(appnt);
		appointmentDAO.save(appnt);
	}

}
