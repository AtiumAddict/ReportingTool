package com.tyrellcorp.reportingtool.controller;

import com.tyrellcorp.reportingtool.service.dto.report.CreateReportReqDto;
import com.tyrellcorp.reportingtool.service.dto.report.EditReportReqDto;
import com.tyrellcorp.reportingtool.service.dto.report.ReportResDto;
import com.tyrellcorp.reportingtool.service.service.report.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Calls similar services with {@link ReportController}, but with an additional path variable, the employeeId
 */
@RestController
@RequestMapping("employees/{employeeId}/reports")
@Slf4j
public class EmployeeReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResDto> createReportOfEmployee(@PathVariable Long employeeId, @Valid @RequestBody CreateReportReqDto createReportReqDto) {
        createReportReqDto.setEmployeeId(employeeId);
        log.info("createReportOfEmployee {}: {}", employeeId, createReportReqDto);
        return ResponseEntity.ok(reportService.createReport(createReportReqDto));
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportResDto> findReportByIdOfEmployee(@PathVariable Long employeeId, @PathVariable Long reportId) {
        log.info("findReportByIdOfEmployee {} with id {}", employeeId, reportId);
        return ResponseEntity.ok(reportService.findReportOfEmployeeById(employeeId, reportId));
    }

    @PutMapping("/{reportId}")
    public ResponseEntity<ReportResDto> editReportOfEmployee(@PathVariable Long employeeId, @PathVariable Long reportId, @Valid @RequestBody EditReportReqDto editReportReqDto) {
        log.info("editReportOfEmployee {}: {}", employeeId, editReportReqDto);
        return ResponseEntity.ok(reportService.editReportOfEmployee(employeeId, reportId, editReportReqDto));
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deleteReportOfEmployee(@PathVariable Long employeeId, @PathVariable Long reportId) {
        log.info("deleteReportOfEmployee {} with id {}", employeeId, reportId);
        reportService.deleteReportOfEmployee(employeeId, reportId);
        return ResponseEntity.status(204).build();
    }
}
