package tsystems.rehab.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import tsystems.rehab.config.DatabaseTestConfig;
import tsystems.rehab.config.JMSTestConfig;
import tsystems.rehab.dto.AppointmentDto;
import tsystems.rehab.dto.EventGeneratorDto;
import tsystems.rehab.service.blueprints.AppointmentService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class, JMSTestConfig.class})
@WebAppConfiguration
@TestMethodOrder(OrderAnnotation.class)
@Transactional
@Rollback(true)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class AppointmentServiceImplTest {
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static EventGeneratorDto eventGenerator;

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
	}

	@Test
	@Order(1)
	@Rollback(false)
	void testProcessAppointmentForm() {
		appointmentService.processAppointmentForm(eventGenerator);
		List<AppointmentDto> appointments = appointmentService.getByPatientId(1L,"ivanova_i");
		assertEquals("Acetaminophen", appointments.get(0).getTreatment().getName());
	}
	
	@Test
	@Order(2)
	void testGetByPatientId() {
		List<AppointmentDto> appointments = appointmentService.getByPatientId(1L, "ivanova_i");
		assertEquals("Acetaminophen", appointments.get(0).getTreatment().getName());
		assertThrows(ResponseStatusException.class,
				() -> appointmentService.getByPatientId(2L, "ivanova_i"));
	}
	
	@Test
	@Order(3)
	void testChangeDosage() {
		appointmentService.changeDosage(1L, "2 pills");
		AppointmentDto appointment = appointmentService.getById(1L);
		assertEquals("2 pills", appointment.getDosage());
	}
	
	@Test
	@Order(4)
	void testChangeTimePattern() {
		appointmentService.changeTimePattern(1L, Arrays.asList("16:00", "21:00"));
		AppointmentDto appointment = appointmentService.getById(1L);
		assertEquals("16:00 21:00", appointment.getReceiptTimes());
		
		assertThrows(ResponseStatusException.class,
				() -> appointmentService.changeTimePattern(1L, Arrays.asList("160", "21:")));
		
		assertThrows(ResponseStatusException.class,
				() -> appointmentService.changeTimePattern(1L, Arrays.asList()));
	}
	
	@Test
	@Order(5)
	void testCancelAppointment() {
		appointmentService.cancelAppointment(1L);
		assertEquals("INVALID", appointmentService.getById(1L).getStatus());
		sessionFactory.close();
	}
}
