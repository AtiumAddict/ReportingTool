package com.tyrellcorp.reportingtool.service.service.employee;


import com.tyrellcorp.reportingtool.service.dto.employee.CreateEmployeeReqDto;
import com.tyrellcorp.reportingtool.service.dto.employee.EditEmployeeReqDto;
import com.tyrellcorp.reportingtool.service.dto.employee.EmployeeDetailsResDto;
import com.tyrellcorp.reportingtool.service.service.FieldValueExists;

public interface EmployeeService extends FieldValueExists {
    EmployeeDetailsResDto findEmployeeById(Long id);

    EmployeeDetailsResDto createEmployee(CreateEmployeeReqDto createEmployeeReqDto);

    EmployeeDetailsResDto editEmployee(Long employeeId, EditEmployeeReqDto createNewEmployeeReqDto);

    EmployeeDetailsResDto findEmployeeByUsername(String username);
}
