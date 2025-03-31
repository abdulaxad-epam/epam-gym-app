package epam.shared.util.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logging logger = new Logging(LoggingAspect.class);

    @Before("execution(* epam.shared.security.controller.*.*(..)) ||"+
            "execution(* epam.shared.trainee_trainer.controller.*.*(..)) ||"+
            "execution(* epam.trainee.controller.*.*(..)) ||"+
            "execution(* epam.trainer.controller.*.*(..)) ||"+
            "execution(* epam.training.controller.*.*(..)) ||"+
            "execution(* epam.training_type.controller.*.*(..))")
    public void logRequest(JoinPoint joinPoint) {

                logger.info("Controller Method: {0}.{1}(), Args: {2}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }


    @Before("execution(* epam.shared.security.service.*.*(..)) ||"+
            "execution(* epam.shared.trainee_trainer.service.*.*(..)) ||"+
            "execution(* epam.trainee.service.*.*(..)) ||"+
            "execution(* epam.trainer.service.*.*(..)) ||"+
            "execution(* epam.training.service.*.*(..)) ||"+
            "execution(* epam.training_type.service.*.*(..)) ||"+
            "execution(* epam.user.service.*.*(..))"
    )
    public void logServiceMethod(JoinPoint joinPoint) {
        logger.info("Service Method: {0}.{1}(), Args: {2}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }


    @Before("execution(* epam.shared.trainee_trainer.repository.*.*(..)) ||"+
            "execution(* epam.trainee.repository.*.*(..)) ||"+
            "execution(* epam.trainer.repository.*.*(..)) ||"+
            "execution(* epam.training.repository.*.*(..)) ||"+
            "execution(* epam.training_type.repository.*.*(..)) ||"+
            "execution(* epam.user.repository.*.*(..))")
    public void logRepositoryMethod(JoinPoint joinPoint) {
        logger.info("Repository Method: {0}.{1}(), Args: {2}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }


    @AfterReturning(value = "execution(* epam.shared.security.controller.*.*(..)) ||"+
            "execution(* epam.shared.trainee_trainer.controller.*.*(..)) ||"+
            "execution(* epam.trainee.controller.*.*(..)) ||"+
            "execution(* epam.trainer.controller.*.*(..)) ||"+
            "execution(* epam.training.controller.*.*(..)) ||"+
            "execution(* epam.training_type.controller.*.*(..))", returning = "result")
    public void logAfterSuccess(JoinPoint joinPoint, Object result) {
        logger.info("Executed REST: {0}.{1}(), Response: {2}, Status: 200",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result);
    }

    @AfterThrowing(value = "execution(* epam.*.*.*.*(..))", throwing = "exception")
    public void logExceptions(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in Method: {0}.{1}(), Message: {2}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                exception.getMessage());
    }

}
