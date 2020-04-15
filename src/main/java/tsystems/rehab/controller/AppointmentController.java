package tsystems.rehab.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tsystems.rehab.dto.EventGeneratorDto;
import tsystems.rehab.service.blueprints.AppointmentService;
import tsystems.rehab.service.blueprints.EventService;
import tsystems.rehab.service.blueprints.PatientService;

@Controller
public class AppointmentController {

	private PatientService patientService;
	private AppointmentService appointmentService;
	private EventService eventService;
	
	private static Logger logger = LogManager.getLogger(AppointmentController.class.getName());
	
	@Autowired
	public AppointmentController(PatientService patientService,
				AppointmentService appointmentService,
				EventService eventService) {
		this.patientService = patientService;
		this.appointmentService = appointmentService;
		this.eventService = eventService;
	}
	
	@GetMapping("/doctor/addAppointment")
	public String addAppointment(@RequestParam("patientId") Long id, Model model) {
		logger.info("GET /doctor/addAppointment?patientId={}", id);
		model.addAttribute("patientId", id);
		return "appnt/add-appnt";
	}
	
	@PostMapping("/doctor/list-appointments/cancel")
	public String cancelAppointment(@RequestParam("id") long id,
			@RequestParam("patientId") long patientId) {
		logger.info("POST /doctor/list-appointments/cancel?id={}&patientId={}", id, patientId);
		appointmentService.cancelAppointment(id);
		return "redirect:/doctor/list-appointments?id="+patientId;
	}
	
	@PostMapping("doctor/list-appointments/changeDosage")
	public String changeDosage(@RequestParam("id") long id,
			@ModelAttribute("dosage") String dosage) {
		appointmentService.changeDosage(id, dosage);
		return "redirect:/doctor/list-appointments/show?appointmentId="+id;
	}
	
	@PostMapping("/doctor/list-appointments/changePattern")
	public String changePattern(@RequestParam("id") long id,
			@RequestParam("treatTime[]") List<String> treatTime) {
		appointmentService.changeTimePattern(id, treatTime);
		return "redirect:/doctor/list-appointments/show?appointmentId="+id;
	}
	
	@GetMapping("/doctor/list-appointments")
	public String listAppointments(@RequestParam("id") long id, Model model) {
		model.addAttribute("patient", patientService.get(id));
		model.addAttribute("appnt", appointmentService.getByPatientId(id));
		return "appnt/list-appnt";
	}

	@PostMapping("/doctor/addAppointment/processForm")
	public String processAppointmentForm(@ModelAttribute("eventGenerator") @Valid EventGeneratorDto eGen,
			BindingResult br, HttpServletRequest request) {
		if (br.hasErrors()) {
			return "redirect:"+request.getHeader("referer");
		}
		appointmentService.processAppointmentForm(eGen);
		return "redirect:/doctor/list-appointments?id="+eGen.getPatientId();
	}
	
	@GetMapping("/doctor/list-appointments/show")
	public String showAppointment(@RequestParam("appointmentId") long id, Model model) {
		model.addAttribute("appnt", appointmentService.getById(id));
		model.addAttribute("events", eventService.getByAppointmentId(id));
		return "appnt/show-appnt";
	}
	
	@GetMapping("/doctor/addAppointment/showForm")
	public String showAppointmentForm(@RequestParam("patientId") Long id,
			@RequestParam("treatmentId") Long treatmentId, 
			@RequestParam("isDrug") boolean isDrug, Model model) {
		EventGeneratorDto eventGenerator = new EventGeneratorDto();
		eventGenerator.setPatientId(id);
		eventGenerator.setTreatmentId(treatmentId);
		model.addAttribute("eventGenerator", eventGenerator);
		model.addAttribute("isDrug", isDrug);
		return "appnt/appnt-form";
	}
}









