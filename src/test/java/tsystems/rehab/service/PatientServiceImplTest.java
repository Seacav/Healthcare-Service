package tsystems.rehab.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import tsystems.rehab.config.DatabaseTestConfig;
import tsystems.rehab.config.JMSTestConfig;
import tsystems.rehab.dto.PatientDto;
import tsystems.rehab.service.blueprints.PatientService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class, JMSTestConfig.class})
@WebAppConfiguration
@Transactional
@Rollback(true)
class PatientServiceImplTest {
	
	@Autowired
	private PatientService patientService;
	
	private static PatientDto patient;
	private static PatientDto patientSecond;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		patient = PatientDto.builder()
				.firstName("Michael").lastName("Mason")
				.diagnosis("Ischemia").insNumber("197385")
				.doctorName("ivanova_i").status("TREATED")
				.build();
		
		patientSecond = PatientDto.builder()
				.firstName("Anna").lastName("Nickson")
				.diagnosis("Angina").insNumber("198407")
				.build();
		
	}

	@Test
	void testGet() {
		assertNotNull(patientService.get(1L));
	}

	@Test
	void testGetByInsurance() {
		assertNotNull(patientService.getByInsurance("115348"));
		assertNull(patientService.getByInsurance("0000"));
	}

	@Test
	void testListOfDoctor() {
		patientService.save(patient);
		assertEquals(2, patientService.listOfDoctor("ivanova_i").size());
	}
	
	@Test
	void testProcessPatientForm() {
		patientService.processPatientForm(patientSecond, "ivanova_i");
		assertEquals("ivanova_i", patientService.getByInsurance("198407").getDoctorName());
		assertEquals("TREATED", patientService.getByInsurance("198407").getStatus());
	}

	@Test
	void testAddExistingPatient() {
		assertThrows(ResponseStatusException.class,
				() -> patientService.addExistingPatient(1L, "Pneumonia", "fedorov_n"));
		
		patientService.dischargePatient(1L);
		patientService.addExistingPatient(1L, "Pneumonia", "fedorov_n");
		assertEquals("fedorov_n", patientService.get(1L).getDoctorName());
	}
	
	@Test
	void testDischargePatient() {
		String oldStatus = patientService.get(1L).getStatus();
		patientService.dischargePatient(1L);
		String newStatus = patientService.get(1L).getStatus();
		assertNotEquals(oldStatus, newStatus);
	}

}
