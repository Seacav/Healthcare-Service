package tsystems.rehab.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InfotableDto {

	private String flag;
	private List<EventTableDto> events;
	
}
