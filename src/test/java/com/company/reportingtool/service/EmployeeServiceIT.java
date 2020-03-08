package com.company.reportingtool.service;

import com.company.reportingtool.AbstractIntegrationTest;
import com.company.reportingtool.datahelper.EmployeeDataHelper;
import com.tyrellcorp.reportingtool.exception.ResourceNotFoundException;
import com.tyrellcorp.reportingtool.model.entity.Employee;
import com.tyrellcorp.reportingtool.service.dto.employee.CreateEmployeeReqDto;
import com.tyrellcorp.reportingtool.service.dto.employee.EditEmployeeReqDto;
import com.tyrellcorp.reportingtool.service.dto.employee.EmployeeDetailsResDto;
import com.tyrellcorp.reportingtool.service.service.employee.EmployeeService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        Assert.assertEquals("k.pilkington@company.co.uk", employeeDetailsResDto.getEmail());
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
        Assert.assertEquals("email1@company.co.uk", employeeDetailsResDto.getEmail());
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
        Assert.assertEquals("email1@company.co.uk", employeeDetailsResDto.getEmail());
        Assert.assertEquals("FEMALE", employeeDetailsResDto.getGender());
    }

    @Test
    public void findEmployeeByUsernameTest() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        long id = (Long) entityManager.persistAndGetId(employee);

        //when
        EmployeeDetailsResDto employeeDetailsResDto = employeeService.findEmployeeByUsername("username1");

        //then
        Assert.assertNotNull(employeeDetailsResDto);
        Assert.assertNotNull(employeeDetailsResDto.getId());
        Assert.assertEquals("MS", employeeDetailsResDto.getTitle());
        Assert.assertEquals("firstName", employeeDetailsResDto.getFirstName());
        Assert.assertEquals("lastName", employeeDetailsResDto.getLastName());
        Assert.assertEquals("username1", employeeDetailsResDto.getUsername());
        Assert.assertEquals("email1@company.co.uk", employeeDetailsResDto.getEmail());
        Assert.assertEquals("FEMALE", employeeDetailsResDto.getGender());
    }
}
