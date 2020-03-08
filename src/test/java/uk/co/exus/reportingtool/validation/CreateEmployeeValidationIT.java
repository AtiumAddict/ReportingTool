package uk.co.exus.reportingtool.validation;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.exus.reportingtool.AbstractIntegrationTest;
import uk.co.exus.reportingtool.datahelper.EmployeeDataHelper;
import uk.co.exus.reportingtool.model.entity.employee.Employee;
import uk.co.exus.reportingtool.service.dto.employee.CreateEmployeeReqDto;
import uk.co.exus.reportingtool.service.service.employee.EmployeeService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Set;

public class CreateEmployeeValidationIT extends AbstractIntegrationTest {
    @Autowired
    private Validator validator;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void validNewEmployeeTest() {
        //given
        CreateEmployeeReqDto createEmployeeReqDto = EmployeeDataHelper.createNewEmployeeReqDto();

        //when
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(createEmployeeReqDto);

        //then
        Assert.assertEquals(0, constraintViolations.size());
    }

    @Test
    public void uniqueUsernameValidationTest() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        entityManager.persistAndFlush(employee);
        entityManager.clear();
        CreateEmployeeReqDto duplicateUsernameCreateEmployeeReqDto = EmployeeDataHelper.duplicateUsernameCreateNewEmployeeReqDto();

        //when
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(duplicateUsernameCreateEmployeeReqDto);

        //then
        Assert.assertEquals(1, constraintViolations.size());
        Assert.assertEquals("Unique", new ArrayList<ConstraintViolation>(constraintViolations).get(0)
                .getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
        Assert.assertTrue(new ArrayList<ConstraintViolation>(constraintViolations).get(0).getMessageTemplate()
                .equals("{unique.value.violation}"));
    }

    @Test
    public void uniqueEmailValidationTest() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        entityManager.persistAndFlush(employee);
        entityManager.clear();
        CreateEmployeeReqDto duplicateEmailCreateEmployeeReqDto = EmployeeDataHelper.duplicateEmailCreateNewEmployeeReqDto();

        //when
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(duplicateEmailCreateEmployeeReqDto);

        //then
        Assert.assertEquals(1, constraintViolations.size());
        Assert.assertEquals("Unique", new ArrayList<ConstraintViolation>(constraintViolations).get(0)
                .getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
        Assert.assertTrue(new ArrayList<ConstraintViolation>(constraintViolations).get(0).getMessageTemplate()
                .equals("{unique.value.violation}"));
    }

    @Test
    public void emailFormatValidationTest() {
        //given
        CreateEmployeeReqDto createEmployeeReqDto = EmployeeDataHelper.createNewEmployeeReqDto();
        createEmployeeReqDto.setEmail("invalid email");

        //when
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(createEmployeeReqDto);

        //then
        Assert.assertEquals(1, constraintViolations.size());
        Assert.assertEquals("Email", new ArrayList<ConstraintViolation>(constraintViolations).get(0)
                .getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
        Assert.assertTrue(new ArrayList<ConstraintViolation>(constraintViolations).get(0).getMessageTemplate()
                .equals("{javax.validation.constraints.Email.message}"));
    }

    @Test
    public void invalidTitleValidationTest() {
        //given
        CreateEmployeeReqDto createEmployeeReqDto = EmployeeDataHelper.createNewEmployeeReqDto();
        createEmployeeReqDto.setTitle("invalid title");

        //when
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(createEmployeeReqDto);

        //then
        Assert.assertEquals(1, constraintViolations.size());
        Assert.assertEquals("ValueOfEnum", new ArrayList<ConstraintViolation>(constraintViolations).get(0)
                .getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
        Assert.assertTrue(new ArrayList<ConstraintViolation>(constraintViolations).get(0).getMessageTemplate()
                .equals("{value.of.enum.violation}"));
    }

    @Test
    public void invalidGenderValidationTest() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        entityManager.persistAndFlush(employee);
        entityManager.clear();
        CreateEmployeeReqDto createEmployeeReqDto = EmployeeDataHelper.createNewEmployeeReqDto();
        createEmployeeReqDto.setGender("invalid gender");

        //when
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(createEmployeeReqDto);

        //then
        Assert.assertEquals(1, constraintViolations.size());
        Assert.assertEquals("ValueOfEnum", new ArrayList<ConstraintViolation>(constraintViolations).get(0)
                .getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
        Assert.assertTrue(new ArrayList<ConstraintViolation>(constraintViolations).get(0).getMessageTemplate()
                .equals("{value.of.enum.violation}"));
    }
}
