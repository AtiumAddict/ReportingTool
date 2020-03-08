package com.company.reportingtool.service.mapper;

import com.company.reportingtool.model.entity.Report;
import com.company.reportingtool.service.dto.report.CreateReportReqDto;
import com.company.reportingtool.service.dto.report.EditReportReqDto;
import com.company.reportingtool.service.dto.report.ReportResDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReportMapper {
    ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);

    @Mapping(source = "employeeId", target = "employee.id")
    Report toReport(CreateReportReqDto createReportReqDto);

    @Mapping(source = "employee.username", target = "username")
    ReportResDto toReportResDto(Report report);

    void updateReport(EditReportReqDto editReportReqDto, @MappingTarget Report report);
}
