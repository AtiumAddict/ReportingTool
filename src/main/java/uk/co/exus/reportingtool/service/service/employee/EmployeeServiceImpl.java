package uk.co.exus.reportingtool.service.service.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.exus.reportingtool.exception.ResourceNotFoundException;
import uk.co.exus.reportingtool.model.entity.Employee;
import uk.co.exus.reportingtool.model.repository.EmployeeRepository;
import uk.co.exus.reportingtool.service.dto.employee.CreateEmployeeReqDto;
import uk.co.exus.reportingtool.service.dto.employee.EditEmployeeReqDto;
import uk.co.exus.reportingtool.service.dto.employee.EmployeeDetailsResDto;
import uk.co.exus.reportingtool.service.mapper.EmployeeMapper;

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
