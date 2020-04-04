package tsystems.rehab.service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tsystems.rehab.dao.blueprints.EventDAO;
import tsystems.rehab.dto.AppointmentDto;
import tsystems.rehab.dto.EventDto;
import tsystems.rehab.dto.EventGeneratorDto;
import tsystems.rehab.mapper.EventMapper;
import tsystems.rehab.service.blueprints.EventService;

@Service
@Transactional
public class EventServiceImpl implements EventService{
	
	@Autowired
	private EventMapper mapper;
	
	@Autowired
	private EventDAO eventDAO;

	@Override
	public void generateEvents(AppointmentDto appointment, EventGeneratorDto eGen) {
		List<EventDto> listOfEvents = new ArrayList<>();
		
		//Field that will help us to get first day of week
		TemporalField field = WeekFields.of(Locale.FRANCE).dayOfWeek();
		
		LocalDateTime today = LocalDateTime.now();
		
		//Equals Monday 00:00
		LocalDateTime startOfDefaultWeek = appointment.getCreated_at().toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate().with(field, 1).atStartOfDay();
		
		if (eGen.isStartNextWeek())
			startOfDefaultWeek = startOfDefaultWeek.plusWeeks(1);
		
		for (int i = 0; i < eGen.getDuration(); i++) {
			LocalDateTime startOfNextWeek = startOfDefaultWeek.plusWeeks(i);
			for (Integer d : eGen.getDays()) {
				for (String time : eGen.getTreatTime()) {
					LocalTime eventTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
					LocalDateTime eventDateTime = startOfNextWeek.plusDays(d).withHour(eventTime.getHour()).withMinute(eventTime.getMinute());
					if(today.compareTo(eventDateTime)<0) {
						EventDto event = EventDto.builder()
								.date(Timestamp.valueOf(eventDateTime))
								.appointment(appointment)
								.status("SCHEDULED").build();
						listOfEvents.add(event);
					}
				}
			}
		}		
		eventDAO.saveEvents(listOfEvents.stream().map(event -> mapper.toEntity(event)).collect(Collectors.toList()));
	}

	@Override
	public LocalDateTime getNearestDate(Long appointmentId) {
		return eventDAO.getNearestDate(appointmentId).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	@Override
	public void deleteNearestElements(Long appointmentId) {
		eventDAO.deleteNearestEvents(appointmentId);
	}

	@Override
	public void changeTimesPattern(AppointmentDto appointment, List<String> newTreatTime, LocalDateTime nearestDate) {
		TemporalField field = WeekFields.of(Locale.FRANCE).dayOfWeek();
		LocalDateTime startOfDefaultWeek = nearestDate.toLocalDate().with(field, 1).atStartOfDay();
		LocalDateTime dueDate = appointment.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		List<EventDto> listOfEvents = new ArrayList<EventDto>();
		
		Pattern pattern = Pattern.compile(" ");
		List<Integer> days = pattern.splitAsStream(appointment.getPattern()).map(Integer::valueOf)
                .collect(Collectors.toList());
		while(startOfDefaultWeek.compareTo(dueDate)<0) {
			for (Integer d : days) {
				for (String time : newTreatTime) {
					LocalTime eventTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
					LocalDateTime eventDateTime = startOfDefaultWeek.plusDays(d).withHour(eventTime.getHour()).withMinute(eventTime.getMinute());
					EventDto event = EventDto.builder()
							.date(Timestamp.valueOf(eventDateTime))
							.appointment(appointment)
							.status("SCHEDULED").build();
					/*
					event.setDate(Timestamp.valueOf(eventDateTime));
					event.setAppointment(appointment);
					event.setStatus("SCHEDULED");
					*/
					listOfEvents.add(event);
				}
			}
			startOfDefaultWeek = startOfDefaultWeek.plusWeeks(1);
		}
		eventDAO.saveEvents(listOfEvents.stream().map(event -> mapper.toEntity(event)).collect(Collectors.toList()));
	}

	@Override
	public List<EventDto> getByAppointmentId(Long appointmentId) {
		return eventDAO.getByAppointmentId(appointmentId).stream().map(event -> mapper.toDto(event)).collect(Collectors.toList());
	}

	@Override
	public HashMap<String, Object> getAll(int pageSize, int pageNumber, String filterName, String patientName) {
		BigInteger totalCount = eventDAO.getTotalNumberOfEvents(pageSize, pageNumber, filterName, patientName);
		List<EventDto> listOfEvents = 
				eventDAO.getAllFiltered(pageSize, pageNumber, filterName, patientName)
					.stream().map(event -> mapper.toDto(event)).collect(Collectors.toList());
		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("length", totalCount);
		responseMap.put("items", listOfEvents);
		return responseMap;
	}

	@Override
	public void completeEvent(long id) {
		EventDto event = mapper.toDto(eventDAO.getById(id));
		event.setStatus("COMPLETED");
		eventDAO.saveEvent(mapper.toEntity(event));
	}

	@Override
	public void cancelEvent(long id) {
		EventDto event = mapper.toDto(eventDAO.getById(id));
		event.setStatus("CANCELLED");
		eventDAO.saveEvent(mapper.toEntity(event));
	}
	
}






