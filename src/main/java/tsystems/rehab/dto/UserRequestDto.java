package tsystems.rehab.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

	private Long id;
	private String username;
	private String role;
	private String firstName;
	private String lastName;
	
}
