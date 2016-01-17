package ro.bapr.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ro.bapr.internal.service.api.PersonService;

@Controller
@Deprecated
public class LoginController {
	private final Logger logger = LogManager.getLogger(LoginController.class);

	@Autowired
	private PersonService personService;
	
	@RequestMapping(value = Endpoint.LOGIN_PAGE)
	public String viewLoginPage(final Model uiModel,
			final HttpServletRequest request) {
		logger.info("Inside '/loginPage' controller.");

		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return Endpoint.LOGIN_PAGE;
		}

		return Endpoint.REDIRECT_PREFIX + Endpoint.TEST_URI;
	}
}
