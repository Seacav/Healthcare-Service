package tsystems.rehab.dao.blueprints;

import java.util.List;

import tsystems.rehab.entity.User;

public interface UserDAO {
	List<User> getUsers();
	
	User findByUsername(String username);

	void saveUser(User user);
}
