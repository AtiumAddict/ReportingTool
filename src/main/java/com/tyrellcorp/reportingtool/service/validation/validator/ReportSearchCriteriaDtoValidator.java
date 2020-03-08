package com.tyrellcorp.reportingtool.service.validation.validator;

import com.tyrellcorp.reportingtool.service.dto.report.ReportSearchCriteriaDto;
import com.tyrellcorp.reportingtool.service.validation.constraint.ReportSearchCriteriaDtoConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReportSearchCriteriaDtoValidator implements ConstraintValidator<ReportSearchCriteriaDtoConstraint, ReportSearchCriteriaDto> {

    @Override
    public boolean isValid(ReportSearchCriteriaDto reportSearchCriteriaDto, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (reportSearchCriteriaDto.getPriority() == null && reportSearchCriteriaDto.getUsername() == null) {
            context.buildConstraintViolationWithTemplate("{criteria.empty}")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
