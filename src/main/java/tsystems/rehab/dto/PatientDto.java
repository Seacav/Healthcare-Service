package tsystems.rehab.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

	private Long id;
	
	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;	
	
	@NotBlank
	private String diagnosis;
	
	@NotBlank
	private String insNumber;
	private String doctorName;
	private String status;
		
}
