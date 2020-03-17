package tsystems.rehab.dao.blueprints;

import java.util.List;

import tsystems.rehab.entity.Treatment;

public interface TreatmentDAO {

	public List<Treatment> findByTypeAndName(String type, String name);
	
}
