package ro.bapr.internal.springsecurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component(value = "customLogoutHandler")
public class CustomLogoutHandler extends SimpleUrlLogoutSuccessHandler {
	private final Logger logger = LogManager.getLogger(CustomAuthenticationProvider.class);
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {
		
		try {
			super.onLogoutSuccess(request, response, authentication);
			logger.info("Logout succes");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (ServletException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
}
