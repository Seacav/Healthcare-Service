package tsystems.rehab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import tsystems.rehab.dto.TreatmentDto;
import tsystems.rehab.service.blueprints.TreatmentService;

@Controller
public class TreatmentController {
	
	@Autowired
	TreatmentService treatmentService;

	/*
	 * @PostMapping("/doctor/addAppointment/searchTreatment") public ModelAndView
	 * searchTreatment(@ModelAttribute("treatmentType") String type,
	 * 
	 * @ModelAttribute("treatmentName") String name, Model model) {
	 * List<TreatmentDto> listOfTreatments =
	 * treatmentService.findByTypeAndName(type, name); boolean searchSuccess = true;
	 * if (listOfTreatments.isEmpty()) searchSuccess = false;
	 * model.addAttribute("treatments", listOfTreatments);
	 * model.addAttribute("searchSuccess", searchSuccess); return new
	 * ModelAndView("redirect:/doctor/addAppointment", model.asMap()); }
	 */
	
}
