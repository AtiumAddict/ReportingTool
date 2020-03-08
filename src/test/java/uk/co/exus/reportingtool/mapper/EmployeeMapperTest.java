package uk.co.exus.reportingtool.mapper;

import org.junit.Assert;
import org.junit.Test;
import uk.co.exus.reportingtool.AbstractIntegrationTest;
import uk.co.exus.reportingtool.datahelper.EmployeeDataHelper;
import uk.co.exus.reportingtool.model.entity.employee.Employee;
import uk.co.exus.reportingtool.service.dto.employee.CreateEmployeeReqDto;
import uk.co.exus.reportingtool.service.dto.employee.EditEmployeeReqDto;
import uk.co.exus.reportingtool.service.dto.employee.EmployeeDetailsResDto;
import uk.co.exus.reportingtool.service.mapper.EmployeeMapper;

public class EmployeeMapperTest extends AbstractIntegrationTest {

    @Test
    public void employeeToEmployeeDetailsResDtoTest() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        employee.setId(1L);

        //when
        EmployeeDetailsResDto employeeDetailsResDto = EmployeeMapper.INSTANCE.toEmployeeDetailsResDto(employee);

        //then
        Assert.assertEquals(1, employeeDetailsResDto.getId());
        Assert.assertEquals("firstName", employeeDetailsResDto.getFirstName());
        Assert.assertEquals("lastName", employeeDetailsResDto.getLastName());
        Assert.assertEquals("username1", employeeDetailsResDto.getUsername());
        Assert.assertEquals("email1@exus.co.uk", employeeDetailsResDto.getEmail());
        Assert.assertEquals("FEMALE", employeeDetailsResDto.getGender());
        Assert.assertEquals("MS", employeeDetailsResDto.getTitle());
    }

    @Test
    public void createNewEmployeeReqDtoToEmployeeTest() {
        //given
        CreateEmployeeReqDto createEmployeeReqDto = EmployeeDataHelper.createNewEmployeeReqDto();

        //when
        Employee employee = EmployeeMapper.INSTANCE.toEmployee(createEmployeeReqDto);

        //then
        Assert.assertEquals("Karl", employee.getFirstName());
        Assert.assertEquals("Pilkington", employee.getLastName());
        Assert.assertEquals("kpilkington", employee.getUsername());
        Assert.assertEquals("k.pilkington@exus.co.uk", employee.getEmail());
        Assert.assertEquals(Employee.Gender.MALE, employee.getGender());
        Assert.assertEquals(Employee.Title.MR, employee.getTitle());
    }

    @Test
    public void updateEmployeeTest() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        EditEmployeeReqDto editEmployeeReqDto = EmployeeDataHelper.editEmployeeReqDto();

        //when
        EmployeeMapper.INSTANCE.updateEmployee(editEmployeeReqDto, employee);

        //then
        Assert.assertEquals("Karl", employee.getFirstName());
        Assert.assertEquals("Pilkington", employee.getLastName());
        Assert.assertEquals("username1", employee.getUsername()); // remains the same
        Assert.assertEquals("email1@exus.co.uk", employee.getEmail()); // remains the same
        Assert.assertEquals(Employee.Gender.MALE, employee.getGender());
        Assert.assertEquals(Employee.Title.MR, employee.getTitle());
    }
}
