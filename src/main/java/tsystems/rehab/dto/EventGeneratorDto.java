package tsystems.rehab.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class EventGeneratorDto {

	@NotNull
	private Long patientId;
	
	@NotNull
	private Long treatmentId;
	
	@NotEmpty
	private List<Integer> days;
	
	@NotEmpty
	private List<@Pattern(regexp="\\A\\d{2}[:]\\d{2}\\z") String> treatTime;
	
	@NotBlank
	private String dosage;
	
	@NotNull
	private Integer duration;
	private boolean startNextWeek;
}
