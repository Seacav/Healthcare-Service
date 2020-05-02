package tsystems.rehab.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tsystems.rehab.dto.TreatmentDto;
import tsystems.rehab.dto.UserDto;
import tsystems.rehab.service.blueprints.TreatmentService;
import tsystems.rehab.service.blueprints.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private UserService userService;
	private TreatmentService treatmentService;
	
	private static Logger logger = LogManager.getLogger(AdminController.class.getName());

	@Autowired
	public AdminController(UserService userService,
			TreatmentService treatmentService) {
		this.userService = userService;
		this.treatmentService = treatmentService;
	}
	
	@GetMapping("/")
	public String mainPage() {
		return "admin/admin-main";
	}
	
	@GetMapping("/registrationForm")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new UserDto());
		return "admin/registration-form";
	}
	
	@GetMapping("/treatmentForm")
	public String showTreatmentForm(Model model) {
		model.addAttribute("treatment", new TreatmentDto());
		return "admin/treatment-form";
	}
	
	@GetMapping("/listUsers")
	public String listUsers(Model model) {
		model.addAttribute("users", userService.getUsers());
		return "admin/user-list";
	}
	
	@PostMapping("/register")
	public String registerNewUser(@ModelAttribute("user") UserDto user) {
		logger.info("Registering "+user.getUsername());
		userService.registerNewUser(user);
		return "redirect:/admin/";
	}
	
	@PostMapping("/addTreatment")
	public String addNewTreatment(@ModelAttribute("treatment") TreatmentDto treatment) {
		logger.info("Adding new treatment "+treatment.toString());
		treatmentService.addNewTreatment(treatment);
		return "redirect:/admin/";
	}

}
