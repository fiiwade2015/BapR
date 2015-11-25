package ro.bapr.internal.springsecurity;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import ro.bapr.internal.exception.NoSuchUserException;
import ro.bapr.internal.model.Person;
import ro.bapr.internal.service.api.PersonService;
import ro.bapr.internal.utils.security.EncryptionUtil;

@Component(value = "customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private final Logger logger = LogManager.getLogger(CustomAuthenticationProvider.class);
	
	@Autowired
	private PersonService personService;

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();
		logger.info("if autenticated");
		Person person;
		try {
			person = verifyCredentials(email, password);
			logger.info("Authentication: " + "user.getName: "
					+ person.getName() + " user.getPassword(): "
					+ person.getPassword() + " user.getEmail: " + person.getEmail());

			List<GrantedAuthority> grantedAuths = new ArrayList<>();
			grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
					person.getEmail(), person.getPassword(), grantedAuths);
			auth.setDetails(person);
			return auth;
		} catch (NoSuchUserException e) {
			logger.error(e.getMessage());
			return null;
		}

	}

	public Person verifyCredentials(String email, String password)
			throws NoSuchUserException {

		try {
			password = EncryptionUtil.getSHA256Hash(password);
			Person user = personService.findByEmailAndPassword(email, password);

			if (user == null) {
				throw new NoSuchUserException("User not found!");
			}
			return user;
		} catch (NoResultException e) {
			throw new NoSuchUserException("User not found!");
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
			return null;
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
			return null;
		}

	}
}