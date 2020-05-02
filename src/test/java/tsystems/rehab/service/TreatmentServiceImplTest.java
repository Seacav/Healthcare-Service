package tsystems.rehab.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import tsystems.rehab.config.DatabaseTestConfig;
import tsystems.rehab.dto.TreatmentDto;
import tsystems.rehab.service.blueprints.TreatmentService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class})
@WebAppConfiguration
class TreatmentServiceImplTest {
	
	@Autowired
	private TreatmentService treatmentService;

	@Test
	void testFindByTypeAndName() {
		List<TreatmentDto> treatment = treatmentService.findByTypeAndName("DRUG", "Adderall");
		assertEquals("Adderall", treatment.get(0).getName());
	}
	
	@Test
	void testGetById() {
		TreatmentDto treatment = treatmentService.getById(1L);
		assertNotNull(treatment);
	}
	
	@Test
	@Rollback(true)
	void testAddNewTreatment() {
		TreatmentDto treatment = TreatmentDto.builder()
				.type("DRUG").name("Tramadol").build();
		treatmentService.addNewTreatment(treatment);
		assertNotNull(treatmentService.findByTypeAndName("DRUG", "Tramadol"));
	}
}
