package tsystems.rehab.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/loginPage")
	public String showLoginPage(Authentication auth) {
		if (auth != null) {
			String role = auth.getAuthorities().toString().toLowerCase();
			if (role.contains("doctor")) {
				return "redirect:/doctor/";
			} else if (role.contains("nurse")) {
				return "redirect:/nurse/";
			} else if (role.contains("admin")) {
				return "redirect:/admin/";
			}
		}
		return "login-page";
	}
	
}
