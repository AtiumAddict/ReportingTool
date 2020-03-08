package uk.co.exus.reportingtool.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import uk.co.exus.reportingtool.model.entity.report.Report;
import uk.co.exus.reportingtool.service.dto.report.CreateReportReqDto;
import uk.co.exus.reportingtool.service.dto.report.EditReportReqDto;
import uk.co.exus.reportingtool.service.dto.report.ReportResDto;

@Mapper
public interface ReportMapper {
    ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);

    @Mapping(source = "employeeId", target = "employee.id")
    Report toReport(CreateReportReqDto createReportReqDto);

    @Mapping(source = "employee.username", target = "username")
    ReportResDto toReportResDto(Report report);

    void updateReport(EditReportReqDto editReportReqDto, @MappingTarget Report report);
}
