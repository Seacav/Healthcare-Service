package tsystems.rehab.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tsystems.rehab.dao.blueprints.AppointmentDAO;
import tsystems.rehab.entity.Appointment;

@Repository
public class AppointmentDAOImpl implements AppointmentDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Appointment> list() {
		@SuppressWarnings("unchecked")
		TypedQuery<Appointment> query = sessionFactory.getCurrentSession().createQuery("from Appointment");
		return query.getResultList();
	}

	@Override
	public void save(Appointment appnt) {
		sessionFactory.getCurrentSession().save(appnt);
	}

}
