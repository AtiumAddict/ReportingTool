package uk.co.exus.reportingtool.service.service.report;


import org.springframework.data.domain.Page;
import uk.co.exus.reportingtool.service.dto.PageRequestParams;
import uk.co.exus.reportingtool.service.dto.report.CreateReportReqDto;
import uk.co.exus.reportingtool.service.dto.report.EditReportReqDto;
import uk.co.exus.reportingtool.service.dto.report.ReportResDto;
import uk.co.exus.reportingtool.service.dto.report.ReportSearchCriteriaDto;

import java.util.List;

public interface ReportService {
    ReportResDto findReportById(Long id);

    ReportResDto createReport(CreateReportReqDto createReportReqDto);

    ReportResDto editReport(Long reportId, EditReportReqDto editReportReqDto);

    void deleteReport(Long id);

    Page<ReportResDto> findReportsByCriteria(ReportSearchCriteriaDto criteria, PageRequestParams page);

    List<ReportResDto> findReportsByCriteria(ReportSearchCriteriaDto criteria);
}
