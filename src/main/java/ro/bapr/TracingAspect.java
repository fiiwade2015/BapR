package ro.bapr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author Spac Valentin - Marian
 * @version 1.0 21.11.2015.
 */
@Aspect
@Component
public class TracingAspect {
    private final Logger log = LogManager.getLogger();

    @Pointcut("execution(* ro.bapr..*(..))")
    protected void loggingOperation() {  }

    @Before("loggingOperation()")
    public void entering(JoinPoint joinPoint) {
        log.info(joinPoint.getSignature().toShortString() + ": Called with args: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "loggingOperation()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result)
    {
        StringBuilder stringBuilder = new StringBuilder(joinPoint.getSignature().toShortString());
        stringBuilder.append(": Exited.");

        if(result != null) {
            stringBuilder.append(" Return value: ").append(result);
        }
        log.info(stringBuilder.toString());
    }

    @AfterThrowing(pointcut = "loggingOperation()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e)
    {
        log.error("An exception has been thrown in " + joinPoint.getSignature().toShortString() + ". Exception: ", e);
    }
}
