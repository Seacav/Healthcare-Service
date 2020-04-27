package tsystems.rehab.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tsystems.rehab.controller.EventController;
import tsystems.rehab.dao.blueprints.UserDAO;
import tsystems.rehab.dto.UserDto;
import tsystems.rehab.entity.User;
import tsystems.rehab.mapper.UserMapper;
import tsystems.rehab.service.blueprints.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	private UserDAO userDAO;
	private UserMapper userMapper;
	private PasswordEncoder passwordEncoder;
	
	private static Logger logger = LogManager.getLogger(UserService.class.getName());
	
	@Autowired
	public UserServiceImpl(UserDAO userDAO,
			UserMapper userMapper,
			PasswordEncoder passwordEncoder) {
		this.userDAO = userDAO;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void registerNewUser(UserDto user) {
		if (!checkIfExists(user.getUsername())) {
			User newUser = userMapper.toEntity(user);
			newUser.setRole("ROLE_"+user.getRole().toUpperCase());
			newUser.setPassword(passwordEncoder.encode(user.getPassword()));
			userDAO.saveUser(newUser);
			logger.info("New user was added to the database");
		} else {
			logger.info("Looks like user with this username already exists");
		}
	}

	public boolean checkIfExists(String username) {
		User user = userDAO.findByUsername(username);
		if (user != null) {
			return true;
		}
		return false;
	}

}
