package ro.bapr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 21.11.2015.
 */
@Aspect
@Component
public class TracingAspect {
    private final Logger log = LogManager.getLogger();

    @Before("execution(org.springframework.http.ResponseEntity<ro.bapr.internal.model.Person> getPerson(..))")
    public void entering(JoinPoint joinPoint) {
        log.trace("Entering " + joinPoint.getSignature());
    }
}
