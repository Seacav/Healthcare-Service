package tsystems.rehab.dao.blueprints;

import tsystems.rehab.entity.User;

public interface UserDAO {
	User findByUsername(String username);

	void saveUser(User user);
}
