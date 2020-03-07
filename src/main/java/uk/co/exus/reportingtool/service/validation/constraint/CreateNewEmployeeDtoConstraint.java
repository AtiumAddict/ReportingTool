package uk.co.exus.reportingtool.service.validation.constraint;

import uk.co.exus.reportingtool.service.validation.validator.CreateNewEmployeeDtoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CreateNewEmployeeDtoValidator.class)
public @interface CreateNewEmployeeDtoConstraint {

    String message() default " ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
