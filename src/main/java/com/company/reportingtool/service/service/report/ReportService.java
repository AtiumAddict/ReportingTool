package com.company.reportingtool.service.service.report;


import com.company.reportingtool.service.dto.PageRequestParams;
import com.company.reportingtool.service.dto.report.CreateReportReqDto;
import com.company.reportingtool.service.dto.report.EditReportReqDto;
import com.company.reportingtool.service.dto.report.ReportResDto;
import com.company.reportingtool.service.dto.report.ReportSearchCriteriaDto;
import org.springframework.data.domain.Page;

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
