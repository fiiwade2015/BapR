package ro.bapr;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 23.11.2015.
 */
@Aspect
@Component
public class SPARQLQueryValidatorAspect {

    @Before("execution(* ro.bapr.external..*service..*ServiceImpl*.*(String))")
    public void entering(JoinPoint joinPoint) throws Exception {
        Object[] args = joinPoint.getArgs();
        for(Object arg : args) {
            if(arg == null || !(arg instanceof String) || ((String) arg).isEmpty()) {
                throw new Exception("Arg must be a non-empty/blank String");
            }
        }
    }
}
