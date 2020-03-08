package uk.co.exus.reportingtool.validation;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.exus.reportingtool.AbstractIntegrationTest;
import uk.co.exus.reportingtool.datahelper.EmployeeDataHelper;
import uk.co.exus.reportingtool.model.entity.Employee;
import uk.co.exus.reportingtool.service.dto.employee.EditEmployeeReqDto;
import uk.co.exus.reportingtool.service.service.employee.EmployeeService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Set;

public class EditEmployeeValidationIT extends AbstractIntegrationTest {
    @Autowired
    private Validator validator;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void validEditEmployeeTest() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        entityManager.persist(employee);
        EditEmployeeReqDto editEmployeeReqDto = EmployeeDataHelper.editEmployeeReqDto();

        //when
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(editEmployeeReqDto);

        //then
        Assert.assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidTitleValidationTest() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        entityManager.persist(employee);
        EditEmployeeReqDto editEmployeeReqDto = EmployeeDataHelper.editEmployeeReqDto();
        editEmployeeReqDto.setTitle("invalid title");

        //when
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(editEmployeeReqDto);

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
        entityManager.persist(employee);
        EditEmployeeReqDto editEmployeeReqDto = EmployeeDataHelper.editEmployeeReqDto();
        editEmployeeReqDto.setGender("invalid gender");

        //when
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(editEmployeeReqDto);

        //then
        Assert.assertEquals(1, constraintViolations.size());
        Assert.assertEquals("ValueOfEnum", new ArrayList<ConstraintViolation>(constraintViolations).get(0)
                .getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
        Assert.assertTrue(new ArrayList<ConstraintViolation>(constraintViolations).get(0).getMessageTemplate()
                .equals("{value.of.enum.violation}"));
    }
}
