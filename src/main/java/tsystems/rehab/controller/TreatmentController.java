package tsystems.rehab.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import tsystems.rehab.dto.TreatmentDto;
import tsystems.rehab.service.blueprints.TreatmentService;

@Controller
public class TreatmentController {
	
	@Autowired
	TreatmentService treatmentService;
	
	@PostMapping("/doctor/addAppointment")
	public String searchTreatment(@ModelAttribute("treatmentType") String type,
			@ModelAttribute("treatmentName") String name, 
			@ModelAttribute("patientId") Long id, Model model) {
		List<TreatmentDto> listOfTreatments = treatmentService.findByTypeAndName(type, name);
		boolean searchSuccess = (listOfTreatments.isEmpty() || Objects.isNull(listOfTreatments)) ? false : true;
		model.addAttribute("treatments", listOfTreatments);
		model.addAttribute("searchSuccess", searchSuccess);
		model.addAttribute("patientId", id);
		return "appnt/add-appnt";
	}
	
}
