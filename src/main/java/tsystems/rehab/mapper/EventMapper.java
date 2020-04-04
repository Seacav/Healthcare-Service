package tsystems.rehab.mapper;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tsystems.rehab.dto.EventDto;
import tsystems.rehab.entity.Event;

@Component
public class EventMapper {
	
	@Autowired
	private ModelMapper mapper;
	
	public Event toEntity(EventDto dto) {
		return Objects.isNull(dto)? null : mapper.map(dto, Event.class);
	}
	
	public EventDto toDto(Event entity) {
		return Objects.isNull(entity) ? null : mapper.map(entity, EventDto.class);
	}
}
