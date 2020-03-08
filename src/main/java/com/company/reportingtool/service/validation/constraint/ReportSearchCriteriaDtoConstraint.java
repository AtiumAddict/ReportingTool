package com.company.reportingtool.service.validation.constraint;

import com.company.reportingtool.service.validation.validator.ReportSearchCriteriaDtoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReportSearchCriteriaDtoValidator.class)
public @interface ReportSearchCriteriaDtoConstraint {
    String message() default " ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
