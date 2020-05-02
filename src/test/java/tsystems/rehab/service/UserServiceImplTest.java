package tsystems.rehab.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import tsystems.rehab.config.DatabaseTestConfig;
import tsystems.rehab.config.JMSTestConfig;
import tsystems.rehab.dto.UserDto;
import tsystems.rehab.dto.UserRequestDto;
import tsystems.rehab.service.blueprints.UserService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DatabaseTestConfig.class, JMSTestConfig.class})
@WebAppConfiguration
@TestMethodOrder(OrderAnnotation.class)
@Transactional
class UserServiceImplTest {
	
	@Autowired
	private UserService userService;

	@Test
	void testRegisterNewUser() {
		//Current number of users
		int current = userService.getUsers().size();
		UserDto user = UserDto.builder()
				.firstName("John")
				.lastName("Malkovich")
				.role("NURSE")
				.username("malkovich_j")
				.password("malkovich_j").build();
		userService.registerNewUser(user);
		assertEquals(current+1, userService.getUsers().size());
	}

}
