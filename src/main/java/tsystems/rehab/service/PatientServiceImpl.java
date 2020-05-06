package tsystems.rehab.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import tsystems.rehab.dao.blueprints.PatientDAO;
import tsystems.rehab.dto.PatientDto;
import tsystems.rehab.entity.Patient;
import tsystems.rehab.mapper.PatientMapper;
import tsystems.rehab.service.blueprints.AppointmentService;
import tsystems.rehab.service.blueprints.PatientService;

@Service
@Transactional
public class PatientServiceImpl implements PatientService{

	private PatientDAO patientDAO;
	private PatientMapper mapper;
	private AppointmentService appointmentService;
	
	private static final String STATUS_TREATED = "TREATED";
	private static final String STATUS_DISCHARGED = "DISCHARGED";
	
	@Autowired
	public PatientServiceImpl(PatientDAO patientDAO,
			PatientMapper mapper,
			@Lazy AppointmentService appointmentService) {
		this.patientDAO = patientDAO;
		this.mapper = mapper;
		this.appointmentService = appointmentService;
	}

	@Override
	public void save(PatientDto patient) {
		patientDAO.save(mapper.toEntity(patient));
	}

	@Override
	public PatientDto get(long id) {
		return mapper.toDto(patientDAO.get(id));
	}

	@Override
	public PatientDto getByInsurance(String insNumber) {
		List<Patient> patients = patientDAO.getByInsurance(insNumber);
		if (patients.isEmpty()) {
			return null;
		}
		return mapper.toDto(patients.get(0));
	}

	@Override
	public List<PatientDto> listOfDoctor(String doctorName) {
		return patientDAO.getByDoctorName(doctorName).stream().map(patient -> mapper.toDto(patient))
				.collect(Collectors.toList());
	}

	@Override
	public void addExistingPatient(long patientId, String diagnosis, String doctorName) {
		PatientDto patient = get(patientId);
		if (patient.getStatus().equals(STATUS_TREATED)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient already assigned");
		}
		patient.setDoctorName(doctorName);
		patient.setDiagnosis(diagnosis);
		patient.setStatus(STATUS_TREATED);
		save(patient);
	}

	@Override
	public void dischargePatient(long patientId) {
		PatientDto patient = get(patientId);
		patient.setStatus(STATUS_DISCHARGED);
		save(patient);
		appointmentService.cancelByPatientId(patientId);
	}

	@Override
	public void processPatientForm(PatientDto patient, String doctorName) {
		patient.setStatus(STATUS_TREATED);
		patient.setDoctorName(doctorName);
		save(patient);
	}

}
