package tsystems.rehab.dao.blueprints;

import java.util.List;

import tsystems.rehab.entity.User;

public interface UserDAO {
	
	/**
	 * @return
	 */
	List<User> getUsers();
	
	/**
	 * @param username
	 * @return
	 */
	User findByUsername(String username);

	/**
	 * @param user
	 */
	void saveUser(User user);
}
