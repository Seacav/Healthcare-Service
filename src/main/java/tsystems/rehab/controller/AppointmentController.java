package tsystems.rehab.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tsystems.rehab.dto.AppointmentDto;
import tsystems.rehab.dto.EventGeneratorDto;
import tsystems.rehab.service.blueprints.AppointmentService;
import tsystems.rehab.service.blueprints.EventService;
import tsystems.rehab.service.blueprints.PatientService;
import tsystems.rehab.service.blueprints.TreatmentService;

@Controller
public class AppointmentController {

	private PatientService patientService;
	private AppointmentService appointmentService;
	private TreatmentService treatmentService;
	private EventService eventService;
	
	@Autowired
	public AppointmentController(PatientService patientService,
				AppointmentService appointmentService,
				EventService eventService,
				TreatmentService treatmentService) {
		this.patientService = patientService;
		this.appointmentService = appointmentService;
		this.treatmentService = treatmentService;
		this.eventService = eventService;
	}
	
	@GetMapping("/doctor/list-appointments")
	public String listAppointments(@RequestParam("id") long id, Model model) {
		model.addAttribute("patient", patientService.get(id));
		model.addAttribute("appnt", appointmentService.getByPatientId(id));
		return "appnt/list-appnt";
	}
	
	@GetMapping("/doctor/list-appointments/show")
	public String showAppointment(@RequestParam("appointmentId") long id, Model model) {
		model.addAttribute("appnt", appointmentService.getById(id));
		model.addAttribute("events", eventService.getByAppointmentId(id));
		return "appnt/show-appnt";
	}
	
	@PostMapping("/doctor/list-appointments/cancel")
	public String cancelAppointment(@RequestParam("id") long id,
			@RequestParam("patientId") long patientId) {
		AppointmentDto appnt = appointmentService.getById(id);
		appnt.setStatus("INVALID");
		appointmentService.save(appnt);
		return "redirect:/doctor/list-appointments?id="+patientId;
	}
	
	@PostMapping("doctor/list-appointments/changeDosage")
	public String changeDosage(@RequestParam("id") long id,
			@ModelAttribute("dosage") String dosage) {
		AppointmentDto appnt = appointmentService.getById(id);
		appnt.setDosage(dosage);
		appointmentService.save(appnt);
		return "redirect:/doctor/list-appointments/show?appointmentId="+id;
	}
	
	@PostMapping("/doctor/list-appointments/changePattern")
	public String changePattern(@RequestParam("id") long id,
			@RequestParam("treatTime[]") List<String> treatTime) {
		LocalDateTime nearestDate = eventService.getNearestDate(id);
		eventService.deleteNearestElements(id);
		AppointmentDto appnt = appointmentService.getById(id);
		appnt.setReceiptTimes(treatTime.stream().collect(Collectors.joining(" ")));
		appointmentService.save(appnt);
		eventService.changeTimesPattern(appnt, treatTime, nearestDate);
		return "redirect:/doctor/list-appointments/show?appointmentId="+id;
	}
	
	@GetMapping("/doctor/addAppointment")
	public String addAppointment(@RequestParam("patientId") Long id, Model model) {
		model.addAttribute("patientId", id);
		return "appnt/add-appnt";
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
	
	@PostMapping("/doctor/addAppointment/processForm")
	public String processAppointmentForm(@ModelAttribute("eventGenerator") EventGeneratorDto eGen) {		
		AppointmentDto appntDto = appointmentService.generateAppointmentDto(eGen);
		
		appntDto.setPatient(patientService.get(eGen.getPatientId()));
		
		appntDto.setTreatment(treatmentService.getById(eGen.getTreatmentId()));
		
		appntDto = appointmentService.saveAndReturn(appntDto);
		
		eventService.generateEvents(appntDto, eGen);
		
		return "redirect:/doctor/list-appointments?id="+appntDto.getPatient().getId();
	}
	
}









