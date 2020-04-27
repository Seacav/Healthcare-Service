package tsystems.rehab.mapper;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tsystems.rehab.dto.UserDto;
import tsystems.rehab.entity.User;

@Component
public class UserMapper {
	@Autowired
	private ModelMapper mapper;
	
	public User toEntity(UserDto dto) {
		return Objects.isNull(dto) ? null : mapper.map(dto, User.class);
	}
	
	public UserDto toDto(User entity) {
		return Objects.isNull(entity) ? null : mapper.map(entity, UserDto.class);
	}
	
}
