package tsystems.rehab.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tsystems.rehab.dto.PatientDto;
import tsystems.rehab.entity.Patient;
import tsystems.rehab.service.blueprints.AppointmentService;
import tsystems.rehab.service.blueprints.PatientService;

@Controller
@RequestMapping("/doctor")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private AppointmentService appointmentService;

	@GetMapping("/*")
	public String viewHome(Model model, Principal principal) {
		model.addAttribute("patients", patientService.listOfDoctor(principal.getName()));
		return "patient/list-patients";
	}

	@GetMapping("/add-patient")
	public String addPatient() {
		return "patient/add-patient";
	}
	
	@PostMapping("/add-patient")
	public String showPatientForm(@ModelAttribute("insNumber") String insNumber, Model model) {
		try {
			PatientDto patient = patientService.getByInsurance(insNumber);
			if (patient != null) {
				model.addAttribute("patient", patient);
				return "patient/add-patient";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		PatientDto patient = new PatientDto();
		model.addAttribute("patient", patient);
		model.addAttribute("insNumber", insNumber);
		return "patient/patient-form";
	}
	
	@PostMapping("/process-form")
	public ModelAndView processPatientForm(@ModelAttribute("patient") PatientDto patient, Principal principal) {
		patient.setStatus("TREATED");
		patient.setDoctorName(principal.getName());
		patientService.save(patient);
		return new ModelAndView("redirect:/doctor/");
	}
}
