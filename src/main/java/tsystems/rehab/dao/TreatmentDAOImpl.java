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
		NativeQuery sqlQuery = sessionFactory.getCurrentSession()
				.createSQLQuery("select * from treatment as t where t.type=:type and t.name like :name");
		sqlQuery.setParameter("type", type);
		sqlQuery.setParameter("name", "%"+name+"%");
		sqlQuery.addEntity(Treatment.class);
		return sqlQuery.getResultList();
	}

}
