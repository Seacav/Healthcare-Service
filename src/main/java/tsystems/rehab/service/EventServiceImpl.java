package tsystems.rehab.service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tsystems.rehab.dao.blueprints.EventDAO;
import tsystems.rehab.dto.AppointmentDto;
import tsystems.rehab.dto.EventDto;
import tsystems.rehab.dto.EventTableDto;
import tsystems.rehab.dto.InfotableDto;
import tsystems.rehab.entity.Event;
import tsystems.rehab.mapper.EventMapper;
import tsystems.rehab.messaging.Producer;
import tsystems.rehab.service.blueprints.EventService;

@Service
@Transactional
public class EventServiceImpl implements EventService{
	
	@Autowired
	private EventMapper mapper;
	
	@Autowired
	private EventDAO eventDAO;
	
	@Autowired
	private Producer producer;

	@Override
	public void generateEvents(AppointmentDto appnt, int duration, 
			List<Integer> days, List<String> treatTime, boolean startsNextWeek){
		
		List<EventDto> listOfEvents = new ArrayList<>();
		
		//Field that will help us to get first day of week
		TemporalField field = WeekFields.of(Locale.FRANCE).dayOfWeek();
		
		//Date to prevent generating events in past
		LocalDateTime today = LocalDateTime.now();
		
		//Equals Monday 00:00
		LocalDateTime startOfDefaultWeek = appnt.getCreated_at().toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate().with(field, 1).atStartOfDay();
		
		if (startsNextWeek) {
			startOfDefaultWeek = startOfDefaultWeek.plusWeeks(1);
		}
		
		for (int i = 0; i < duration; i++) {
			LocalDateTime startOfNextWeek = startOfDefaultWeek.plusWeeks(i);
			for (Integer d : days) {
				for (String time : treatTime) {
					LocalTime eventTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
					LocalDateTime eventDateTime = startOfNextWeek.plusDays(d).withHour(eventTime.getHour()).withMinute(eventTime.getMinute());
					if(today.compareTo(eventDateTime)<0) {
						EventDto event = EventDto.builder()
								.date(Timestamp.valueOf(eventDateTime))
								.appointment(appnt)
								.status("SCHEDULED").build();
						listOfEvents.add(event);
					}
				}
			}
		}
		List<Event> newEvents = listOfEvents.stream().map(event -> mapper.toEntity(event)).collect(Collectors.toList());
		eventDAO.saveEvents(newEvents);
		updateInfotable(newEvents, "CREATE");
	}

	@Override
	public LocalDateTime getNearestDate(Long appointmentId) {
		return eventDAO.getNearestDate(appointmentId).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	@Override
	public void deleteNearestElements(Long appointmentId) {
		List<Event> deletedEvents = eventDAO.getNearestEvents(appointmentId);
		eventDAO.deleteNearestEvents(appointmentId);
		updateInfotable(deletedEvents, "DELETE");
	}

	@Override
	public void changeTimesPattern(AppointmentDto appointment, List<String> newTreatTime) {
		LocalDateTime nearestDate = getNearestDate(appointment.getId());
		TemporalField field = WeekFields.of(Locale.FRANCE).dayOfWeek();
		LocalDateTime startOfDefaultWeek = nearestDate.toLocalDate().with(field, 1).atStartOfDay();
		LocalDateTime dueDate = appointment.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		deleteNearestElements(appointment.getId());
		
		List<EventDto> listOfEvents = new ArrayList<EventDto>();
		
		Pattern pattern = Pattern.compile(" ");
		List<Integer> days = pattern.splitAsStream(appointment.getPattern()).map(Integer::valueOf)
                .collect(Collectors.toList());
		while(startOfDefaultWeek.compareTo(dueDate)<0) {
			for (Integer d : days) {
				for (String time : newTreatTime) {
					LocalTime eventTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
					LocalDateTime eventDateTime = startOfDefaultWeek.plusDays(d).withHour(eventTime.getHour()).withMinute(eventTime.getMinute());
					if (eventDateTime.compareTo(LocalDateTime.now())>0) {
						EventDto event = EventDto.builder()
								.date(Timestamp.valueOf(eventDateTime))
								.appointment(appointment)
								.status("SCHEDULED").build();
						listOfEvents.add(event);
					}
				}
			}
			startOfDefaultWeek = startOfDefaultWeek.plusWeeks(1);
		}
		List<Event> newEvents = listOfEvents.stream().map(event -> mapper.toEntity(event)).collect(Collectors.toList());
		eventDAO.saveEvents(newEvents);
		updateInfotable(newEvents, "CREATE");
	}
	
	@Override
	public void changeDosage(long appointmentId) {
		List<Event> events = eventDAO.getByAppointmentId(appointmentId);
		updateInfotable(events, "UPDATE");
	}
	
	@Override
	public List<EventTableDto> getEventsForToday() {
		List<Event> events = eventDAO.getEventsForToday();
		ModelMapper modelMapper = new ModelMapper();
		List<EventTableDto> tableEvents = events.stream().map(event -> modelMapper.map(event, EventTableDto.class))
				.collect(Collectors.toList());
		return tableEvents;
	}

	@Override
	public List<EventDto> getByAppointmentId(Long appointmentId) {
		return eventDAO.getByAppointmentId(appointmentId).stream().map(event -> mapper.toDto(event)).collect(Collectors.toList());
	}
	
	@Override
	public void cancelByAppointmentId(Long appointmentId) {
		eventDAO.cancelByAppointmentId(appointmentId);
		List<Event> events = eventDAO.getByAppointmentId(appointmentId);
		updateInfotable(events, "UPDATE");
	}
	
	@Override
	public void cancelByPatientId(long patientId) {
		eventDAO.cancelByPatientId(patientId);
		List<Event> events = eventDAO.getByPatientId(patientId);
		updateInfotable(events, "UPDATE");
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
		Event event = eventDAO.getById(id);
		//EventDto event = mapper.toDto(eventDAO.getById(id));
		event.setStatus("COMPLETED");
		eventDAO.saveEvent(event);
		updateInfotable(new ArrayList<Event>(Arrays.asList(event)), "UPDATE");
	}

	@Override
	public void cancelEvent(long id, String commentary) {
		//EventDto event = mapper.toDto(eventDAO.getById(id));
		Event event = eventDAO.getById(id);
		event.setCommentary(commentary);
		event.setStatus("CANCELLED");
		eventDAO.saveEvent(event);
		updateInfotable(new ArrayList<Event>(Arrays.asList(event)), "UPDATE");
	}

	@Override
	public String getCommentary(long id) {
		return eventDAO.getById(id).getCommentary();
	}

	@Override
	public BigInteger getNumberOfActiveEvents(long appointmentId) {
		return eventDAO.getNumberOfActiveEvents(appointmentId);
	}
	
	public void updateInfotable(List<Event> newEvents, String flag) {
		ModelMapper modelMapper = new ModelMapper();
		List<EventTableDto> tableEvents = newEvents.stream().map(event -> modelMapper.map(event, EventTableDto.class))
				.collect(Collectors.toList());
		Date endOfDay = Timestamp.valueOf(LocalDate.now().atStartOfDay().plusHours(24));
		Date startOfDay = Timestamp.valueOf(LocalDate.now().atStartOfDay());
		tableEvents = tableEvents.stream()
				.filter(event -> event.getDate().compareTo(startOfDay)>0 && event.getDate().compareTo(endOfDay)<0)
				.collect(Collectors.toList());
		if (!tableEvents.isEmpty()) {
			try {
				InfotableDto infotable = InfotableDto.builder().flag(flag).events(tableEvents).build();
				producer.sendMessage("main-to-table.queue", new ObjectMapper().writeValueAsString(infotable));
			} catch (JsonProcessingException e) {
			}
		}
	}

}






