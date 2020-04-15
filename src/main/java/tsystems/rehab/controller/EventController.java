package tsystems.rehab.controller;

import java.math.BigInteger;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import tsystems.rehab.messaging.JmsProducer;
import tsystems.rehab.service.blueprints.EventService;

@Controller
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private JmsProducer producer;
	
	private static Logger logger = LogManager.getLogger(EventController.class.getName());

	@GetMapping("/nurse/")
	public String nurseMainPage() {
		logger.info("GET /nurse/");
		return "nurse/main";
	}
	
	@GetMapping(value="/listAllEvents", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HashMap<String, Object> listAll(@RequestParam("pageNumber") int pageNumber,
			@RequestParam("pageSize") int pageSize,
			@RequestParam(value="filterName", defaultValue="") String filterName,
			@RequestParam(value="patientName", defaultValue="") String patientName){
		logger.info("GET /listAllEvents?pageNumber={}&pageSize={}&filterName={}&patientName={}", 
				pageNumber, pageSize, filterName, patientName);
		return eventService.getAll(pageSize, pageNumber, filterName, patientName);
	}
	
	@GetMapping(value="/getCommentary", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HashMap<String, Object> listAll(@RequestParam("eventId") int eventId){
		logger.info("GET /getCommentary?eventId={}", eventId);
		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("commentary", eventService.getCommentary(eventId));
		return responseMap;
	}
	
	@PostMapping(value="/completeEvent")
	@ResponseBody
	public long completeEvent(@RequestParam("id") long id) {
		logger.info("GET /completeEvent?id={}", id);
		eventService.completeEvent(id);
		return id;
	}
	
	@PostMapping(value="/cancelEvent")
	@ResponseBody
	public long cancelEvent(@RequestParam("id") long id,
			@RequestParam(value="commentary", defaultValue="") String commentary) {
		logger.info("POST /cancelEvent?id={}&commentary={}", id, commentary);
		eventService.cancelEvent(id, commentary);
		return id;
	}
	
	@GetMapping(value="/activeEvents")
	@ResponseBody
	public BigInteger activeEvents(@RequestParam("id") long appointmentId) {
		logger.info("GET /activeEvents?id={}", appointmentId);
		return eventService.getNumberOfActiveEvents(appointmentId);
	}
	
}
