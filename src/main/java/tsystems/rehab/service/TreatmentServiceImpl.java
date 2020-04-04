package tsystems.rehab.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tsystems.rehab.dao.blueprints.TreatmentDAO;
import tsystems.rehab.dto.TreatmentDto;
import tsystems.rehab.mapper.TreatmentMapper;
import tsystems.rehab.service.blueprints.TreatmentService;

@Service
@Transactional
public class TreatmentServiceImpl implements TreatmentService{
	
	@Autowired
	private TreatmentDAO treatmentDAO;
	
	@Autowired
	private TreatmentMapper mapper;

	@Override
	public List<TreatmentDto> findByTypeAndName(String type, String name) {
		return treatmentDAO.findByTypeAndName(type, name).stream().map(treatment -> mapper.toDto(treatment))
				.collect(Collectors.toList());
	}

	@Override
	public TreatmentDto getById(Long id) {
		return mapper.toDto(treatmentDAO.getById(id));
	}

}
