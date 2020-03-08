package tsystems.rehab.dao;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tsystems.rehab.entity.User;
import tsystems.rehab.dao.blueprints.UserDAO;

@Repository
public class UserDAOImpl implements UserDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User findByUsername(String username) {
		@SuppressWarnings("unchecked")
		TypedQuery<User> query = 
				sessionFactory.getCurrentSession()
				.createSQLQuery("select * from user where user.username='"+username+"'").addEntity(User.class);
		return query.getResultList().get(0);
	}

}
