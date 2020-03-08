package tsystems.rehab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tsystems.rehab.dao.blueprints.UserDAO;
import tsystems.rehab.entity.User;
import tsystems.rehab.config.security.CustomPrincipal;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserDAO userDAO;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.findByUsername(username);
		if (user==null) {
			throw new UsernameNotFoundException(username);
		}
		return new CustomPrincipal(user);
	}

}
