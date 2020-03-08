package uk.co.exus.reportingtool.service.service.report;


import org.springframework.data.domain.Page;
import uk.co.exus.reportingtool.service.dto.PageRequestParams;
import uk.co.exus.reportingtool.service.dto.report.CreateReportReqDto;
import uk.co.exus.reportingtool.service.dto.report.EditReportReqDto;
import uk.co.exus.reportingtool.service.dto.report.ReportResDto;
import uk.co.exus.reportingtool.service.dto.report.ReportSearchCriteriaDto;

public interface ReportService {
    ReportResDto findReportById(Long reportId);

    ReportResDto findReportOfEmployeeById(Long employeeId, Long reportId);

    ReportResDto createReport(CreateReportReqDto createReportReqDto);

    ReportResDto editReport(Long reportId, EditReportReqDto editReportReqDto);

    ReportResDto editReportOfEmployee(Long employeeId, Long reportId, EditReportReqDto editReportReqDto);

    void deleteReport(Long id);

    void deleteReportOfEmployee(Long employeeId, Long id);

    Page<ReportResDto> findReportsByCriteria(ReportSearchCriteriaDto criteria, PageRequestParams page);
}
