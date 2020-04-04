package tsystems.rehab.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tsystems.rehab.service.blueprints.EventService;

@Controller
public class EventController {
	
	@Autowired
	private EventService eventService;

	@GetMapping("/nurse/")
	public String nurseMainPage() {
		return "nurse/main";
	}
	
	@GetMapping(value="/listAllEvents", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HashMap<String, Object> listAll(@RequestParam("pageNumber") int pageNumber,
			@RequestParam("pageSize") int pageSize,
			@RequestParam(value="filterName", defaultValue="") String filterName,
			@RequestParam(value="patientName", defaultValue="") String patientName){
		return eventService.getAll(pageSize, pageNumber, filterName, patientName);
	}
	
	@PostMapping(value="/completeEvent")
	public ResponseEntity<Object> completeEvent(@RequestParam("id") long id) {
		eventService.completeEvent(id);
		return ResponseEntity.status(HttpStatus.OK).body("Success");
	}
	
	@PostMapping(value="/cancelEvent")
	public ResponseEntity<Object> cancelEvent(@RequestParam("id") long id) {
		eventService.cancelEvent(id);
		return ResponseEntity.status(HttpStatus.OK).body("Success");
	}
	
}
