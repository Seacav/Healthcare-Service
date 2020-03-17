package tsystems.rehab.mapper;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tsystems.rehab.dto.TreatmentDto;
import tsystems.rehab.entity.Treatment;

@Component
public class TreatmentMapper {

	@Autowired
	private ModelMapper mapper;
	
	public Treatment toEntity(TreatmentDto dto) {
		return Objects.isNull(dto) ? null : mapper.map(dto, Treatment.class);
	}
	
	public TreatmentDto toDto(Treatment entity) {
		return Objects.isNull(entity) ? null : mapper.map(entity, TreatmentDto.class);
	}
}
