package tsystems.rehab.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tsystems.rehab.dao.blueprints.AppointmentDAO;
import tsystems.rehab.entity.Appointment;

@Repository
public class AppointmentDAOImpl implements AppointmentDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Appointment> getByPatientId(long id) {
		@SuppressWarnings("unchecked")
		NativeQuery<Appointment> sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("select * from Appointment as a where a.patient_id=:id and a.status='VALID'");
		sqlQuery.setParameter("id", id);
		sqlQuery.addEntity(Appointment.class);
		return sqlQuery.getResultList();
	}

	@Override
	public void save(Appointment appnt) {
		sessionFactory.getCurrentSession().merge(appnt);
	}

	@Override
	public Appointment saveAndReturn(Appointment appnt) {
		sessionFactory.getCurrentSession().saveOrUpdate(appnt);
		return appnt;
	}

	@Override
	public Appointment getById(long id) {
		@SuppressWarnings("unchecked")
		NativeQuery<Appointment> sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("select * from appointment as a where a.id=:id")
				.setParameter("id", id)
				.addEntity(Appointment.class);
		return sqlQuery.getSingleResult();
	}

}
