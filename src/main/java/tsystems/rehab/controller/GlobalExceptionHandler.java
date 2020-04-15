package tsystems.rehab.controller;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NoResultException.class)
	public String handleResourceNotFoundException(NoResultException nr) {
		return "error/500";
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ModelAndView handlerResponceStatusException(ResponseStatusException e, HttpServletRequest request, HttpServletResponse response) {
		ResponseStatusExceptionResolver responseStatusExceptionResolver = new ResponseStatusExceptionResolver();
		return responseStatusExceptionResolver.resolveException(request, response, null, e);
	}
}
