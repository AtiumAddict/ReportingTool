package com.tyrellcorp.reportingtool.service.mapper;

import com.tyrellcorp.reportingtool.model.entity.Employee;
import com.tyrellcorp.reportingtool.service.dto.employee.CreateEmployeeReqDto;
import com.tyrellcorp.reportingtool.service.dto.employee.EditEmployeeReqDto;
import com.tyrellcorp.reportingtool.service.dto.employee.EmployeeDetailsResDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDetailsResDto toEmployeeDetailsResDto(Employee employee);

    Employee toEmployee(CreateEmployeeReqDto createEmployeeReqDto);

    void updateEmployee(EditEmployeeReqDto editEmployeeReqDto, @MappingTarget Employee employee);
}
