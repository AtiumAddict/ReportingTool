package uk.co.exus.reportingtool.service.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.exus.reportingtool.exception.ResourceNotFoundException;
import uk.co.exus.reportingtool.model.entity.employee.Employee;
import uk.co.exus.reportingtool.model.entity.report.Report;
import uk.co.exus.reportingtool.model.repository.EmployeeRepository;
import uk.co.exus.reportingtool.model.repository.ReportRepository;
import uk.co.exus.reportingtool.service.dto.PageRequestParams;
import uk.co.exus.reportingtool.service.dto.report.CreateReportReqDto;
import uk.co.exus.reportingtool.service.dto.report.EditReportReqDto;
import uk.co.exus.reportingtool.service.dto.report.ReportResDto;
import uk.co.exus.reportingtool.service.dto.report.ReportSearchCriteriaDto;
import uk.co.exus.reportingtool.service.mapper.ReportMapper;
import uk.co.exus.reportingtool.service.service.ReportCriteriaResolver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ReportCriteriaResolver reportCriteriaResolver;

    @Override
    public ReportResDto findReportById(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Report.class, id));

        return ReportMapper.INSTANCE.toReportResDto(report);
    }

    @Override
    public ReportResDto createReport(CreateReportReqDto createReportReqDto) {
        Employee employee = employeeRepository.findById(createReportReqDto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException(Employee.class, createReportReqDto.getEmployeeId()));

        Report report = ReportMapper.INSTANCE.toReport(createReportReqDto);
        report.setEmployee(employee);

        report = reportRepository.save(report);

        return ReportMapper.INSTANCE.toReportResDto(report);
    }

    @Override
    public ReportResDto editReport(Long reportId, EditReportReqDto editReportReqDto) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException(Report.class, reportId));

        ReportMapper.INSTANCE.updateReport(editReportReqDto, report);
        report.setEdited(LocalDateTime.now());

        report = reportRepository.save(report);

        return ReportMapper.INSTANCE.toReportResDto(report);
    }

    @Override
    public void deleteReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Report.class, id));

        reportRepository.delete(report);
    }

    @Override
    public Page<ReportResDto> findReportsByCriteria(ReportSearchCriteriaDto criteria, PageRequestParams page) {
        return reportRepository.findAll(reportCriteriaResolver.resolveReportCriteria(criteria), reportCriteriaResolver.resolveReportPageParams(page))
                .map(ReportMapper.INSTANCE::toReportResDto);
    }

    @Override
    public List<ReportResDto> findReportsByCriteria(ReportSearchCriteriaDto criteria) {
        return reportRepository.findAll(reportCriteriaResolver.resolveReportCriteria(criteria)).stream()
                .map(ReportMapper.INSTANCE::toReportResDto).collect(Collectors.toList());
    }
}
