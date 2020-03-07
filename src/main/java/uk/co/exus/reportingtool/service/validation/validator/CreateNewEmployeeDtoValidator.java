package uk.co.exus.reportingtool.service.validation.validator;

import org.springframework.beans.factory.annotation.Autowired;
import uk.co.exus.reportingtool.model.repository.EmployeeRepository;
import uk.co.exus.reportingtool.service.dto.employee.CreateEmployeeReqDto;
import uk.co.exus.reportingtool.service.validation.constraint.CreateNewEmployeeDtoConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreateNewEmployeeDtoValidator implements ConstraintValidator<CreateNewEmployeeDtoConstraint, CreateEmployeeReqDto> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public boolean isValid(CreateEmployeeReqDto createEmployeeReqDto, ConstraintValidatorContext constraintValidatorContext) {


        return true;
    }
}