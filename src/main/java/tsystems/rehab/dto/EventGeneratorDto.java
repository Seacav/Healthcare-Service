package tsystems.rehab.dto;

import java.util.List;

import lombok.Data;

@Data
public class EventGeneratorDto {

	private List<String> days;
	private List<String> treatTime;
	private String duration;
	
}
