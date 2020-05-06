package tsystems.rehab.dao.blueprints;

import java.util.List;

import tsystems.rehab.entity.Treatment;

public interface TreatmentDAO {

	/**
	 * @param type
	 * @param name
	 * @return
	 */
	List<Treatment> findByTypeAndName(String type, String name);
	
	/**
	 * @param id
	 * @return
	 */
	Treatment getById(Long id);
	
	/**
	 * @param treatment
	 */
	void saveTreatment(Treatment treatment);
}
