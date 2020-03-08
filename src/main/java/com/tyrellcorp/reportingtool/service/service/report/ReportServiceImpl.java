package com.tyrellcorp.reportingtool.service.service.report;

import com.tyrellcorp.reportingtool.exception.ResourceNotFoundException;
import com.tyrellcorp.reportingtool.model.entity.Employee;
import com.tyrellcorp.reportingtool.model.entity.Report;
import com.tyrellcorp.reportingtool.model.repository.EmployeeRepository;
import com.tyrellcorp.reportingtool.model.repository.ReportRepository;
import com.tyrellcorp.reportingtool.service.dto.PageRequestParams;
import com.tyrellcorp.reportingtool.service.dto.report.CreateReportReqDto;
import com.tyrellcorp.reportingtool.service.dto.report.EditReportReqDto;
import com.tyrellcorp.reportingtool.service.dto.report.ReportResDto;
import com.tyrellcorp.reportingtool.service.dto.report.ReportSearchCriteriaDto;
import com.tyrellcorp.reportingtool.service.mapper.ReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    public ReportResDto findReportById(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException(Report.class, reportId));

        return ReportMapper.INSTANCE.toReportResDto(report);
    }

    @Override
    public ReportResDto findReportOfEmployeeById(Long employeeId, Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException(Report.class, reportId));

        validateReportOfEmployee(employeeId, report);

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
    public ReportResDto editReportOfEmployee(Long employeeId, Long reportId, EditReportReqDto editReportReqDto) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException(Report.class, reportId));

        validateReportOfEmployee(employeeId, report);

        return editReport(reportId, editReportReqDto);
    }

    @Override
    public void deleteReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Report.class, id));

        reportRepository.delete(report);
    }

    @Override
    public void deleteReportOfEmployee(Long employeeId, Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Report.class, id));

        validateReportOfEmployee(employeeId, report);

        reportRepository.delete(report);
    }

    @Override
    public Page<ReportResDto> findReportsByCriteria(ReportSearchCriteriaDto criteria, PageRequestParams page) {
        return reportRepository.findAll(reportCriteriaResolver.resolveReportCriteria(criteria), reportCriteriaResolver.resolveReportPageParams(page))
                .map(ReportMapper.INSTANCE::toReportResDto);
    }

    private void validateReportOfEmployee(long employeeId, Report report) {
        if (report.getEmployee().getId() != employeeId) {
            throw new ResourceNotFoundException(Report.class);
        }
    }
}
