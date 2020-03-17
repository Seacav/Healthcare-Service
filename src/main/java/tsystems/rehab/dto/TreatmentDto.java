package tsystems.rehab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentDto {

	private Long id;
	private String type;
	private String name;

	
}
