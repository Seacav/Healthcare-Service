package tsystems.rehab.service.blueprints;

import java.util.List;

import tsystems.rehab.dto.TreatmentDto;

public interface TreatmentService {

	List<TreatmentDto> findByTypeAndName(String type, String name);
	
	TreatmentDto getById(Long id);
	
	void addNewTreatment(TreatmentDto treatment);
}
