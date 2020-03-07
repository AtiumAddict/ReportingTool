package uk.co.exus.reportingtool.service.service;


import uk.co.exus.reportingtool.service.dto.employee.CreateEmployeeReqDto;
import uk.co.exus.reportingtool.service.dto.employee.EditEmployeeReqDto;
import uk.co.exus.reportingtool.service.dto.employee.EmployeeDetailsResDto;

public interface EmployeeService extends FieldValueExists {
    EmployeeDetailsResDto findEmployeeById(Long id);

    EmployeeDetailsResDto createEmployee(CreateEmployeeReqDto createEmployeeReqDto);

    EmployeeDetailsResDto editEmployee(Long employeeId, EditEmployeeReqDto createNewEmployeeReqDto);
}
