package tsystems.rehab.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tsystems.rehab.dao.blueprints.TreatmentDAO;
import tsystems.rehab.entity.Treatment;

@Repository
public class TreatmentDAOImpl implements TreatmentDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Treatment> findByTypeAndName(String type, String name) {
		@SuppressWarnings("unchecked")
		NativeQuery<Treatment> sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("select * from treatment as t where t.type=:type and t.name like :name order by t.name asc")
				.addEntity(Treatment.class)
				.setParameter("type", type)
				.setParameter("name", "%"+name+"%");
		return sqlQuery.getResultList();
	}

	@Override
	public Treatment getById(Long id) {
		@SuppressWarnings("unchecked")
		NativeQuery<Treatment> sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("select * from treatment as t where t.id=:id")
				.addEntity(Treatment.class)
				.setParameter("id", id);
		return (Treatment) sqlQuery.getSingleResult();
	}

}
