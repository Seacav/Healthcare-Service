package tsystems.rehab.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tsystems.rehab.dao.blueprints.AppointmentDAO;
import tsystems.rehab.entity.Appointment;
import tsystems.rehab.entity.Patient;

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
	public List<Appointment> getByPatientId(long id) {
		NativeQuery sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("select * from Appointment as a where a.patient_id=:id");
		sqlQuery.setParameter("id", id);
		sqlQuery.addEntity(Appointment.class);
		return sqlQuery.getResultList();
	}

	@Override
	public void save(Appointment appnt) {
		sessionFactory.getCurrentSession().save(appnt);
	}

}
