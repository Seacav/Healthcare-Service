package tsystems.rehab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

	private Long id;
	private String firstName;
	private String lastName;
	private String diagnosis;
	private String insNumber;
	private String doctorName;
	private String status;
		
}
