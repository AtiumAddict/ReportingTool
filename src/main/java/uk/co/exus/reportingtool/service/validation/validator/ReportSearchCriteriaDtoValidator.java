package uk.co.exus.reportingtool.service.validation.validator;

import uk.co.exus.reportingtool.service.dto.report.ReportSearchCriteriaDto;
import uk.co.exus.reportingtool.service.validation.constraint.ReportSearchCriteriaDtoConstraint;

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
