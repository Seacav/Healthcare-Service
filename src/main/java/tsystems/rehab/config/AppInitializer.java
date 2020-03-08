package tsystems.rehab.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import tsystems.rehab.config.WebConfig;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		FilterRegistration.Dynamic filterRegistration = servletContext
				  .addFilter("characterEncodingFilter", new CharacterEncodingFilter("UTF-8", true, true));
		filterRegistration.addMappingForUrlPatterns(null, false, "/*");
		/*
		filterRegistration = servletContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter() );
		filterRegistration.addMappingForUrlPatterns(null, false, "/*");
		
		servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter("UTF-8", true, true));
		*/
	    super.onStartup(servletContext);
	}

}
