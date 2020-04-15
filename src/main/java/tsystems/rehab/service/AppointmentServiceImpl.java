package tsystems.rehab.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import tsystems.rehab.dao.blueprints.AppointmentDAO;
import tsystems.rehab.dto.AppointmentDto;
import tsystems.rehab.dto.EventGeneratorDto;
import tsystems.rehab.mapper.AppointmentMapper;
import tsystems.rehab.service.blueprints.AppointmentService;
import tsystems.rehab.service.blueprints.EventService;
import tsystems.rehab.service.blueprints.PatientService;
import tsystems.rehab.service.blueprints.TreatmentService;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService{
	
	private AppointmentDAO appointmentDAO;
	private AppointmentMapper mapper;
	private EventService eventService;
	private PatientService patientService;
	private TreatmentService treatmentService;
	
	@Autowired
	public AppointmentServiceImpl(AppointmentDAO appointmentDAO,
			AppointmentMapper mapper,
			EventService eventService,
			PatientService patientService,
			TreatmentService treatmentService) {
		this.appointmentDAO = appointmentDAO;
		this.mapper = mapper;
		this.eventService = eventService;
		this.patientService = patientService;
		this.treatmentService = treatmentService;
	}

	@Override
	public void save(AppointmentDto appnt) {
		appointmentDAO.save(mapper.toEntity(appnt));
	}

	@Override
	public List<AppointmentDto> getByPatientId(long id) {
		return appointmentDAO.getByPatientId(id).stream().map(appnt -> mapper.toDto(appnt)).collect(Collectors.toList());
	}
	
	@Override
	public AppointmentDto getById(long id) {
		return mapper.toDto(appointmentDAO.getById(id));
	}

	//Creates appointment and generates events
	@Override
	public void processAppointmentForm(EventGeneratorDto eGen) {
		TemporalField field = WeekFields.of(Locale.FRANCE).dayOfWeek();
		LocalDateTime dueDate = LocalDate.now().with(field, 1).atStartOfDay().plusWeeks(eGen.getDuration());
		if (eGen.isStartNextWeek()) {
			dueDate = dueDate.plusWeeks(1);
		}
		
		AppointmentDto appnt = AppointmentDto.builder()
				.patient(patientService.get(eGen.getPatientId()))
				.treatment(treatmentService.getById(eGen.getTreatmentId()))
				.pattern(eGen.getDays().stream().map(day -> day.toString()).collect(Collectors.joining(" ")))
				.receiptTimes(eGen.getTreatTime().stream().collect(Collectors.joining(" ")))
				.dosage(eGen.getDosage())
				.status("VALID")
				.created_at(new Date())
				.dueDate(Timestamp.valueOf(dueDate))
				.build();
		
		appnt = mapper.toDto(appointmentDAO.saveAndReturn(mapper.toEntity(appnt)));
		eventService.generateEvents(appnt, eGen.getDuration(), eGen.getDays(), eGen.getTreatTime(), eGen.isStartNextWeek());
	}

	@Override
	public void changeTimePattern(long appointmentId, List<String> treatTime) {
		if (treatTime.isEmpty()) {
			throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, "No treat time found");
		}
		String pattern = "\\A\\d{2}[:]\\d{2}\\z";
		treatTime.forEach(time -> {
			if(!Pattern.matches(pattern, time)) {
				throw new ResponseStatusException(
				          HttpStatus.BAD_REQUEST, "Invalid data");
			}
		});
		AppointmentDto appnt = getById(appointmentId);
		appnt.setReceiptTimes(treatTime.stream().collect(Collectors.joining(" ")));
		save(appnt);
		eventService.changeTimesPattern(appnt, treatTime);
	}

	@Override
	public void changeDosage(long appointmentId, String dosage) {
		AppointmentDto appnt = getById(appointmentId);
		appnt.setDosage(dosage);
		save(appnt);
	}

	@Override
	public void cancelAppointment(long appointmentId) {
		AppointmentDto appnt = getById(appointmentId);
		appnt.setStatus("INVALID");
		save(appnt);
	}
	
	
}
