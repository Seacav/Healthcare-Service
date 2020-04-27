package tsystems.rehab.controller;

import javax.jms.JMSException;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

import com.fasterxml.jackson.core.JsonProcessingException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static Logger logger = LogManager.getLogger(GlobalExceptionHandler.class.getName());

	@ExceptionHandler(NoResultException.class)
	public String handleResourceNotFoundException(NoResultException nr) {
		return "error/500";
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ModelAndView handlerResponceStatusException(ResponseStatusException e, HttpServletRequest request, HttpServletResponse response) {
		ResponseStatusExceptionResolver responseStatusExceptionResolver = new ResponseStatusExceptionResolver();
		return responseStatusExceptionResolver.resolveException(request, response, null, e);
	}
	
	@ExceptionHandler(JMSException.class)
	public void handleJMSException(JMSException nr) {
		logger.error(nr.getErrorCode() + " Error occurred with message broker");
	}
	
	@ExceptionHandler(JsonProcessingException.class)
	public void handleJsonProcessingException(JsonProcessingException nr) {
		logger.error("Error processing JSON");
	}
}
