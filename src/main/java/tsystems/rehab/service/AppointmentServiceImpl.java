package tsystems.rehab.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tsystems.rehab.dao.blueprints.AppointmentDAO;
import tsystems.rehab.dto.AppointmentDto;
import tsystems.rehab.dto.EventGeneratorDto;
import tsystems.rehab.mapper.AppointmentMapper;
import tsystems.rehab.service.blueprints.AppointmentService;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService{
	
	@Autowired
	private AppointmentDAO appointmentDAO;
	
	@Autowired
	private AppointmentMapper mapper;

	@Override
	public void save(AppointmentDto appnt) {
		appointmentDAO.save(mapper.toEntity(appnt));
	}
	
	@Override
	public AppointmentDto saveAndReturn(AppointmentDto appnt) {
		return mapper.toDto(appointmentDAO.saveAndReturn(mapper.toEntity(appnt)));
	}
	
	@Override
	public List<AppointmentDto> getByPatientId(long id) {
		return appointmentDAO.getByPatientId(id).stream().map(appnt -> mapper.toDto(appnt)).collect(Collectors.toList());
	}
	
	@Override
	public AppointmentDto getById(long id) {
		return mapper.toDto(appointmentDAO.getById(id));
	}

	//Sets all fields except 'patient' and 'treatment'
	@Override
	public AppointmentDto generateAppointmentDto(EventGeneratorDto eGen) {
		AppointmentDto appnt = new AppointmentDto();
		
		TemporalField field = WeekFields.of(Locale.FRANCE).dayOfWeek();

		String pattern = eGen.getDays().stream().map(day -> day.toString()).collect(Collectors.joining(" "));
		appnt.setPattern(pattern);
		
		appnt.setReceiptTimes(eGen.getTreatTime().stream().collect(Collectors.joining(" ")));
		
		appnt.setDosage(eGen.getDosage());
		
		appnt.setStatus("VALID");
		
		appnt.setCreated_at(new Date());
		
		LocalDateTime dueDate = LocalDate.now().with(field, 1).atStartOfDay().plusWeeks(eGen.getDuration());
		
		if (eGen.isStartNextWeek())
			dueDate = dueDate.plusWeeks(1);
		
		appnt.setDueDate(Timestamp.valueOf(dueDate));
		return appnt;
	}
	
}
