package uk.co.exus.reportingtool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.exus.reportingtool.service.dto.PageRequestParams;
import uk.co.exus.reportingtool.service.dto.report.CreateReportReqDto;
import uk.co.exus.reportingtool.service.dto.report.EditReportReqDto;
import uk.co.exus.reportingtool.service.dto.report.ReportResDto;
import uk.co.exus.reportingtool.service.dto.report.ReportSearchCriteriaDto;
import uk.co.exus.reportingtool.service.service.report.ReportService;

import javax.validation.Valid;

@RestController
@RequestMapping("/reports")
@Slf4j
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResDto> createReport(@Valid @RequestBody CreateReportReqDto createReportReqDto) {
        log.info("createReport: {}", createReportReqDto);
        return ResponseEntity.ok(reportService.createReport(createReportReqDto));
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportResDto> findReportById(@PathVariable Long reportId) {
        log.info("findReportById: {}", reportId);
        return ResponseEntity.ok(reportService.findReportById(reportId));
    }

    @PutMapping("/{reportId}")
    public ResponseEntity<ReportResDto> editReport(@PathVariable Long reportId, @Valid @RequestBody EditReportReqDto editReportReqDto) {
        log.info("editReport with id {}: {}", reportId, editReportReqDto);
        return ResponseEntity.ok(reportService.editReport(reportId, editReportReqDto));
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long reportId) {
        log.info("deleteReport with id {}", reportId);
        reportService.deleteReport(reportId);
        return ResponseEntity.status(204).build();
    }

    @GetMapping
    public ResponseEntity<Page<ReportResDto>> findReportsByCriteria(@Valid ReportSearchCriteriaDto reportSearchCriteriaDto, PageRequestParams pageRequestParams) {
        log.info("findReportsByCriteria: {}", reportSearchCriteriaDto);
        return ResponseEntity.ok(reportService.findReportsByCriteria(reportSearchCriteriaDto, pageRequestParams));
    }
}
