package tsystems.rehab.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tsystems.rehab.dao.blueprints.PatientDAO;
import tsystems.rehab.entity.Patient;

@Repository
public class PatientDAOImpl implements PatientDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Patient> list() {
		@SuppressWarnings("unchecked")
		TypedQuery<Patient> query = sessionFactory.getCurrentSession().createQuery("from Patient");
		return query.getResultList();
	}

	@Override
	public void save(Patient patient) {
		sessionFactory.getCurrentSession().save(patient);
	}

	@Override
	public Patient get(long id) {
		return sessionFactory.getCurrentSession().get(Patient.class, id);
	}

	@Override
	public List<Patient> getByInsurance(String insNumber) {
		@SuppressWarnings("unchecked")
		TypedQuery<Patient> query = 
				sessionFactory.getCurrentSession()
				.createSQLQuery("select * from Patient as p where p.ins_number='"+insNumber+"'").addEntity(Patient.class);
		return query.getResultList();
	}

	@Override
	public List<Patient> getByDoctorName(String doctorName) {
		@SuppressWarnings("unchecked")
		TypedQuery<Patient> query = 
				sessionFactory.getCurrentSession()
				.createSQLQuery("select * from Patient as p where p.doctor_name='"+doctorName+"'")
				.addEntity(Patient.class);
		return query.getResultList();
	}
	
}
