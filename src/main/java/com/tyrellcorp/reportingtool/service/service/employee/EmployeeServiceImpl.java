package com.tyrellcorp.reportingtool.service.service.employee;

import com.tyrellcorp.reportingtool.exception.ResourceNotFoundException;
import com.tyrellcorp.reportingtool.model.entity.Employee;
import com.tyrellcorp.reportingtool.model.repository.EmployeeRepository;
import com.tyrellcorp.reportingtool.service.dto.employee.CreateEmployeeReqDto;
import com.tyrellcorp.reportingtool.service.dto.employee.EditEmployeeReqDto;
import com.tyrellcorp.reportingtool.service.dto.employee.EmployeeDetailsResDto;
import com.tyrellcorp.reportingtool.service.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDetailsResDto findEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Employee.class, id));

        return EmployeeMapper.INSTANCE.toEmployeeDetailsResDto(employee);
    }

    @Override
    public EmployeeDetailsResDto createEmployee(CreateEmployeeReqDto createEmployeeReqDto) {
        Employee employee = EmployeeMapper.INSTANCE.toEmployee(createEmployeeReqDto);
        employee = employeeRepository.save(employee);
        return EmployeeMapper.INSTANCE.toEmployeeDetailsResDto(employee);
    }

    @Override
    public EmployeeDetailsResDto editEmployee(Long employeeId, EditEmployeeReqDto editEmployeeReqDto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(Employee.class, employeeId));

        EmployeeMapper.INSTANCE.updateEmployee(editEmployeeReqDto, employee);

        employee = employeeRepository.save(employee);

        return EmployeeMapper.INSTANCE.toEmployeeDetailsResDto(employee);
    }

    @Override
    public EmployeeDetailsResDto findEmployeeByUsername(String username) {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(Employee.class, username));

        return EmployeeMapper.INSTANCE.toEmployeeDetailsResDto(employee);
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {
        if (value == null) {
            return false;
        }

        switch (fieldName) {
            case "username":
                return employeeRepository.existsByUsername((String) value);
            case "email":
                return employeeRepository.existsByEmail((String) value);
            default:
                throw new UnsupportedOperationException("Field name not supported");
        }
    }
}
