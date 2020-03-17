package tsystems.rehab.mapper;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tsystems.rehab.dto.PatientDto;
import tsystems.rehab.entity.Patient;

@Component
public class PatientMapper{

	@Autowired
	private ModelMapper mapper;
	
	public Patient toEntity(PatientDto dto) {
		return Objects.isNull(dto) ? null : mapper.map(dto, Patient.class);
	}
	
	public PatientDto toDto(Patient entity) {
		return Objects.isNull(entity) ? null : mapper.map(entity, PatientDto.class);
	}
	
}
