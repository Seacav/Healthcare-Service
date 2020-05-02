package tsystems.rehab.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import tsystems.rehab.config.DatabaseTestConfig;
import tsystems.rehab.config.JMSTestConfig;
import tsystems.rehab.dao.blueprints.EventDAO;
import tsystems.rehab.dto.AppointmentDto;
import tsystems.rehab.dto.EventDto;
import tsystems.rehab.dto.EventGeneratorDto;
import tsystems.rehab.service.blueprints.AppointmentService;
import tsystems.rehab.service.blueprints.EventService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class, JMSTestConfig.class})
@WebAppConfiguration
@TestMethodOrder(OrderAnnotation.class)
@Transactional
@Rollback(true)
class EventServiceImplTest {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private EventDAO eventDAO;
	
	private static EventGeneratorDto eventGenerator;
	private static EventGeneratorDto eventGeneratorSecond;
	
	private AppointmentDto firstAppointment;
	private AppointmentDto secondAppointment;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		eventGenerator = EventGeneratorDto.builder()
				.patientId(1L)
				.treatmentId(1L)
				.days(Arrays.asList(2, 3))
				.treatTime(Arrays.asList("13:00", "20:00"))
				.dosage("1 pill")
				.duration(1)
				.startNextWeek(true)
				.build();
		
		eventGeneratorSecond = EventGeneratorDto.builder()
				.patientId(1L)
				.treatmentId(2L)
				.days(Arrays.asList(2, 3))
				.treatTime(Arrays.asList("10:00", "17:30"))
				.dosage("2 pills")
				.duration(2)
				.startNextWeek(true)
				.build();
		
	}
	
	@Test
	@Rollback(false)
	@Order(1)
	void testEventService() {
		//Generates 4 events and appointment with id=1
		appointmentService.processAppointmentForm(eventGenerator);
		firstAppointment = appointmentService.getById(1L);
		assertNotNull(firstAppointment);
		
		//Generates 8 events and appointment with id=2
		appointmentService.processAppointmentForm(eventGeneratorSecond);
		secondAppointment = appointmentService.getById(2L);
		assertNotNull(secondAppointment);
	}
	

	@Test
	@Order(2)
	void testGetByAppointmentId() {
		List<EventDto> events = eventService.getByAppointmentId(1L);
		assertEquals(4, events.size());
	}

	@Test
	@Order(3)
	void testGetAll() {
		HashMap<String, Object> events = eventService.getAll(10, 1, "", "Smith");
		assertEquals(BigInteger.valueOf(12L), events.get("length"));
		List<EventDto> eventsList = (List<EventDto>) events.get("items");
		assertEquals(10, eventsList.size());
	}
	
	@Test
	@Order(4)
	void testGetNumberOfActiveEvents() {
		//Get count of active events for the first appointment
		assertEquals(BigInteger.valueOf(4L), eventService.getNumberOfActiveEvents(1L));
		//Get count for the second
		assertEquals(BigInteger.valueOf(8L), eventService.getNumberOfActiveEvents(2L));
	}
	
	@Test
	@Order(5)
	@Rollback(false)
	void testGetEventsForToday() {
		EventDto eventOne = EventDto.builder()
				.appointment(appointmentService.getById(1L))
				.status("SCHEDULED")
				.date(Timestamp.valueOf(LocalDateTime.now().withMinute(0).plusMinutes(10)))
				.build();
		//Save event with id=13
		eventService.saveEvent(eventOne);
		assertEquals(1, eventService.getEventsForToday().size());
	}

	@Test
	@Order(6)
	void testCompleteEvent() {
		eventService.completeEvent(13L);
		assertEquals("COMPLETED", eventService.getById(13L).getStatus());
	}

	@Test
	@Order(7)
	@Rollback(false)
	void testCancelEvent() {
		eventService.cancelEvent(13L, "Patient got discharged");
		assertEquals("CANCELLED", eventService.getById(13L).getStatus());
	}

	@Test
	@Order(8)
	void testGetCommentary() {
		assertEquals("Patient got discharged", eventService.getById(13L).getCommentary());
	}

	@Test
	void testFilters() {
		HashMap<String, Object> events = eventService.getAll(10, 1, "hour", "Smith");
		List<EventDto> eventsList = (List<EventDto>) events.get("items");
		assertEquals(1, eventsList.size());
		
		EventDto eventOne = EventDto.builder()
				.appointment(appointmentService.getById(1L))
				.status("SCHEDULED")
				.date(Timestamp.valueOf(LocalDateTime.now().withHour(13).withMinute(0)))
				.build();
		//Save event with id=14
		eventService.saveEvent(eventOne);
		events = eventService.getAll(10, 1, "today", "Smith");
		eventsList = (List<EventDto>) events.get("items");
		assertEquals(2, eventsList.size());
	}

}
