package tsystems.rehab.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tsystems.rehab.dto.EventGeneratorDto;
import tsystems.rehab.dto.TreatmentDto;
import tsystems.rehab.service.blueprints.AppointmentService;
import tsystems.rehab.service.blueprints.PatientService;
import tsystems.rehab.service.blueprints.TreatmentService;

@Controller
public class AppointmentController {

	private PatientService patientService;
	private AppointmentService appointmentService;
	private TreatmentService treatmentService;
	
	@Autowired
	public AppointmentController(PatientService patientService,
				AppointmentService appointmentService,
				TreatmentService treatmentService) {
		this.patientService = patientService;
		this.appointmentService = appointmentService;
		this.treatmentService = treatmentService;
	}
	
	@GetMapping("/doctor/list-appointments")
	public String listAppointments(@RequestParam("id") long id, Model model) {
		model.addAttribute("patient", patientService.get(id));
		model.addAttribute("appnt", appointmentService.getByPatientId(id));
		return "appnt/list-appnt";
	}
	
	@GetMapping("/doctor/addAppointment")
	public String addAppointment(@RequestParam("patientId") Long id, Model model) {
		model.addAttribute("patientId", id);
		return "appnt/add-appnt";
	}
	
	@PostMapping("/doctor/addAppointment")
	public String searchTreatment(@ModelAttribute("treatmentType") String type,
			@ModelAttribute("treatmentName") String name, 
			@ModelAttribute("patientId") Long id, Model model) {
		List<TreatmentDto> listOfTreatments = treatmentService.findByTypeAndName(type, name);
		boolean searchSuccess = true;
		if (listOfTreatments.isEmpty() || Objects.isNull(listOfTreatments))
			searchSuccess = false;
		model.addAttribute("treatments", listOfTreatments);
		model.addAttribute("searchSuccess", searchSuccess);
		model.addAttribute("patientId", id);
		return "appnt/add-appnt";
	}
	
	@GetMapping("/doctor/addAppointment/showForm")
	public String showAppointmentForm(@RequestParam("patientId") Long id, 
			@RequestParam("treatmentId") Long treatmentId, Model model) {
		EventGeneratorDto eventGenerator = new EventGeneratorDto();
		model.addAttribute("eventGenerator", eventGenerator);
		return "appnt/appnt-form";
	}
	
	@PostMapping("/doctor/addAppointment/processForm")
	public String processAppointmentForm(@ModelAttribute("eventGenerator") EventGeneratorDto eGen) {
		eGen.getDays().forEach(day -> System.out.println(day));
		eGen.getTreatTime().forEach(time->System.out.println(time));
		System.out.println(eGen.getDuration());
		return null;
	}
	
}









