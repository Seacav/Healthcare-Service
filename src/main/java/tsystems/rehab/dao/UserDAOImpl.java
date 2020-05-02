package tsystems.rehab.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tsystems.rehab.dao.blueprints.UserDAO;
import tsystems.rehab.entity.User;

@Repository
public class UserDAOImpl implements UserDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User findByUsername(String username) {
		@SuppressWarnings("unchecked")
		TypedQuery<User> query = 
				sessionFactory.getCurrentSession()
				.createSQLQuery("select * from user where user.username=:uname")
				.setParameter("uname", username)
				.addEntity(User.class);
		List<User> users = query.getResultList();
		if (users.isEmpty()) {
			return null;
		} else {
			return users.get(0);
		}
	}
	
	@Override
	public void saveUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
	}

	@Override
	public List<User> getUsers() {
		@SuppressWarnings("unchecked")
		NativeQuery<User> sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("select * from user as u where u.role='ROLE_DOCTOR' or u.role='ROLE_NURSE'");
		sqlQuery.addEntity(User.class);
		return sqlQuery.getResultList();
	}

}
