package tsystems.rehab.service.blueprints;

import java.util.List;

import tsystems.rehab.dto.UserDto;
import tsystems.rehab.dto.UserRequestDto;

public interface UserService {

	void registerNewUser(UserDto user);
	
	List<UserRequestDto> getUsers();
}
