package uk.co.exus.reportingtool.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.exus.reportingtool.AbstractIntegrationTest;
import uk.co.exus.reportingtool.datahelper.EmployeeDataHelper;
import uk.co.exus.reportingtool.exception.ResourceNotFoundException;
import uk.co.exus.reportingtool.model.entity.employee.Employee;
import uk.co.exus.reportingtool.service.dto.employee.CreateEmployeeReqDto;
import uk.co.exus.reportingtool.service.dto.employee.EditEmployeeReqDto;
import uk.co.exus.reportingtool.service.dto.employee.EmployeeDetailsResDto;
import uk.co.exus.reportingtool.service.service.employee.EmployeeService;

public class EmployeeServiceIT extends AbstractIntegrationTest {
    @Autowired
    private EmployeeService employeeService;

    @Test
    public void createNewEmployeeTest() {
        //given
        CreateEmployeeReqDto createEmployeeReqDto = EmployeeDataHelper.createNewEmployeeReqDto();

        //when
        EmployeeDetailsResDto employeeDetailsResDto = employeeService.createEmployee(createEmployeeReqDto);

        //then
        Assert.assertNotNull(employeeDetailsResDto);
        Assert.assertNotNull(employeeDetailsResDto.getId());
        Assert.assertEquals("Karl", employeeDetailsResDto.getFirstName());
        Assert.assertEquals("Pilkington", employeeDetailsResDto.getLastName());
        Assert.assertEquals("kpilkington", employeeDetailsResDto.getUsername());
        Assert.assertEquals("k.pilkington@exus.co.uk", employeeDetailsResDto.getEmail());
        Assert.assertEquals("MALE", employeeDetailsResDto.getGender());
    }

    @Test
    public void editEmployeeTest() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        long id = (Long) entityManager.persistAndGetId(employee);
        EditEmployeeReqDto editEmployeeReqDto = EmployeeDataHelper.editEmployeeReqDto();

        //when
        EmployeeDetailsResDto employeeDetailsResDto = employeeService.editEmployee(id, editEmployeeReqDto);

        //then
        Assert.assertNotNull(employeeDetailsResDto);
        Assert.assertNotNull(employeeDetailsResDto.getId());
        Assert.assertEquals("Karl", employeeDetailsResDto.getFirstName());
        Assert.assertEquals("Pilkington", employeeDetailsResDto.getLastName());
        Assert.assertEquals("username1", employeeDetailsResDto.getUsername());
        Assert.assertEquals("email1@exus.co.uk", employeeDetailsResDto.getEmail());
        Assert.assertEquals("MALE", employeeDetailsResDto.getGender());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void editNonexistentEmployeeTest() {
        //given
        EditEmployeeReqDto editEmployeeReqDto = EmployeeDataHelper.editEmployeeReqDto();

        //when
        EmployeeDetailsResDto employeeDetailsResDto = employeeService.editEmployee(404L, editEmployeeReqDto);
    }

    @Test
    public void findEmployeeByIdTest() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        long id = (Long) entityManager.persistAndGetId(employee);

        //when
        EmployeeDetailsResDto employeeDetailsResDto = employeeService.findEmployeeById(id);

        //then
        Assert.assertNotNull(employeeDetailsResDto);
        Assert.assertNotNull(employeeDetailsResDto.getId());
        Assert.assertEquals("MS", employeeDetailsResDto.getTitle());
        Assert.assertEquals("firstName", employeeDetailsResDto.getFirstName());
        Assert.assertEquals("lastName", employeeDetailsResDto.getLastName());
        Assert.assertEquals("username1", employeeDetailsResDto.getUsername());
        Assert.assertEquals("email1@exus.co.uk", employeeDetailsResDto.getEmail());
        Assert.assertEquals("FEMALE", employeeDetailsResDto.getGender());
    }
}
