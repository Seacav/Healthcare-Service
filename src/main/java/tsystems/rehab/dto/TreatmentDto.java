package tsystems.rehab.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreatmentDto {

	private Long id;
	
	@NotBlank
	private String type;
	
	@NotBlank
	private String name;

	
}
