package tsystems.rehab.dto;

import java.util.List;

import lombok.Data;

@Data
public class EventGeneratorDto {

	private Long patientId;
	private Long treatmentId;
	private List<Integer> days;
	private List<String> treatTime;
	private String dosage;
	private Integer duration;
	private boolean startNextWeek;
}
