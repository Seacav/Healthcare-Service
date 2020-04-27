package tsystems.rehab.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@NotBlank
	@Pattern(regexp="(DOCTOR)|(NURSE)")
	private String role;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
}
