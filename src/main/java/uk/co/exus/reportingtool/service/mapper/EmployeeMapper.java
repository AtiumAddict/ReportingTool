package uk.co.exus.reportingtool.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import uk.co.exus.reportingtool.model.entity.employee.Employee;
import uk.co.exus.reportingtool.service.dto.employee.CreateEmployeeReqDto;
import uk.co.exus.reportingtool.service.dto.employee.EditEmployeeReqDto;
import uk.co.exus.reportingtool.service.dto.employee.EmployeeDetailsResDto;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDetailsResDto toEmployeeDetailsResDto(Employee employee);

    Employee toEmployee(CreateEmployeeReqDto createEmployeeReqDto);

    void updateEmployee(EditEmployeeReqDto editEmployeeReqDto, @MappingTarget Employee employee);
}
