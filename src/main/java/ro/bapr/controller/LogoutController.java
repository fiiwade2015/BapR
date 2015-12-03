package ro.bapr.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LogoutController.class);
	
	@RequestMapping(value =  Endpoint.LOGOUT_PAGE)
	public String viewLoginPage(final Model uiModel,
			final HttpServletRequest request) {
		LOGGER.debug("Inside '/logout' controller.");

		return  Endpoint.LOGIN_PAGE;
	}
}
