package tsystems.rehab.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import tsystems.rehab.config.DatabaseTestConfig;
import tsystems.rehab.config.JMSTestConfig;
import tsystems.rehab.config.WebConfig;
import tsystems.rehab.service.blueprints.AppointmentService;
import tsystems.rehab.service.blueprints.EventService;
import tsystems.rehab.service.blueprints.PatientService;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
class AppointmentControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private AppointmentService appointmentService;
	
	@Mock
	private PatientService patientService;
	
	@Mock
	private EventService eventService;
	
	@Mock
    private Principal principal;
	
	@BeforeEach
	void setBefore() {
		MockitoAnnotations.initMocks(this);
		
		Mockito.when(principal.getName()).thenReturn("fedorov_n");
		
		Mockito.doThrow(new ResponseStatusException(HttpStatus.FORBIDDEN))
				.when(appointmentService).getByPatientId(5L, "fedorov_n");
		
		mockMvc = MockMvcBuilders.standaloneSetup(
				new AppointmentController(patientService, appointmentService, eventService)).build();
	}

	@Test
	@WithMockUser(username = "fedorov_n", authorities = { "DOCTOR" })
	void testListAppointments() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
		        .get("/doctor/list-appointments?id=5")
		        .principal(principal);
		
		mockMvc.perform(requestBuilder)
			.andExpect(status().isForbidden());
	}

}
