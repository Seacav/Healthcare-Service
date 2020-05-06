package tsystems.rehab.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tsystems.rehab.dto.PatientDto;
import tsystems.rehab.service.blueprints.PatientService;

@Controller
@RequestMapping("/doctor")
public class PatientController {
	
	private static Logger logger = LogManager.getLogger(PatientController.class.getName());
	
	private static final String REDIRECT_MAIN = "redirect:/doctor/";
	
	@Autowired
	private PatientService patientService;
	
	@GetMapping("/*")
	public String viewHome(Model model, Principal principal) {
		logger.info("GET /");
		model.addAttribute("patients", patientService.listOfDoctor(principal.getName()));
		return "patient/list-patients";
	}

	@GetMapping("/add-patient")
	public String addPatient() {
		logger.info("GET /add-patient");
		return "patient/add-patient";
	}
	
	@PostMapping("/add-patient")
	public String showPatientForm(@ModelAttribute("insNumber") String insNumber, Model model) {
		logger.info("POST /add-patient");
		PatientDto patient = patientService.getByInsurance(insNumber);
		if (patient != null) {
			model.addAttribute("patient", patient);
			return "patient/add-patient";
		}
		model.addAttribute("patient", new PatientDto());
		model.addAttribute("insNumber", insNumber);
		return "patient/patient-form";
	}
	
	@PostMapping("/addExistingPatient")
	public String addExistingPatient(@RequestParam("id") long id,
			@ModelAttribute("diagnosis") String diagnosis,
			Principal principal) {
		logger.info("POST /addExistingPatient?id={}", id);
		patientService.addExistingPatient(id, diagnosis, principal.getName());
		return REDIRECT_MAIN;
	}
	
	@PostMapping("/dischargePatient")
	public String dischargePatient(@RequestParam("patientId") long id) {
		patientService.dischargePatient(id);
		return REDIRECT_MAIN;
	}
	
	@PostMapping("/process-form")
	public String processPatientForm(@ModelAttribute("patient") @Valid PatientDto patient, 
			BindingResult br, Principal principal) {
		if (br.hasErrors()) {
			return "patient/patient-form";
		}
		patientService.processPatientForm(patient, principal.getName());
		return REDIRECT_MAIN;
	}
	
	@GetMapping(value="/getPatient", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public PatientDto getPatient(@RequestParam long id) {
		return patientService.get(id);
	}
}
