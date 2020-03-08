package com.company.reportingtool.service;

import com.company.reportingtool.AbstractIntegrationTest;
import com.company.reportingtool.datahelper.EmployeeDataHelper;
import com.company.reportingtool.datahelper.ReportDataHelper;
import com.tyrellcorp.reportingtool.exception.ResourceNotFoundException;
import com.tyrellcorp.reportingtool.model.entity.Employee;
import com.tyrellcorp.reportingtool.model.entity.Report;
import com.tyrellcorp.reportingtool.service.dto.PageRequestParams;
import com.tyrellcorp.reportingtool.service.dto.report.CreateReportReqDto;
import com.tyrellcorp.reportingtool.service.dto.report.EditReportReqDto;
import com.tyrellcorp.reportingtool.service.dto.report.ReportResDto;
import com.tyrellcorp.reportingtool.service.dto.report.ReportSearchCriteriaDto;
import com.tyrellcorp.reportingtool.service.service.report.ReportService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class ReportServiceIT extends AbstractIntegrationTest {
    @Autowired
    private ReportService reportService;

    @Test
    public void createReportTest() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        long employeeId = (long) entityManager.persistAndGetId(employee);
        CreateReportReqDto createReportReqDto = ReportDataHelper.createReportReqDto(employeeId);

        //when
        ReportResDto reportResDto = reportService.createReport(createReportReqDto);

        //then
        Assert.assertNotNull(reportResDto);
        Assert.assertNotNull(reportResDto.getId());
        Assert.assertEquals("Daily Report", reportResDto.getTitle());
        Assert.assertEquals("321", reportResDto.getDescription());
        Assert.assertEquals("LOW", reportResDto.getPriority());
        Assert.assertEquals("username1", reportResDto.getUsername());
    }

    @Test
    public void editReportTest() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        employee = entityManager.persistAndFlush(employee);
        Report report = ReportDataHelper.report(employee);
        long reportId = (long) entityManager.persistAndGetId(report);
        EditReportReqDto editReportReqDto = ReportDataHelper.editReportReqDto();

        //when
        ReportResDto reportResDto = reportService.editReport(reportId, editReportReqDto);

        //then
        Assert.assertNotNull(reportResDto);
        Assert.assertNotNull(reportResDto.getId());
        Assert.assertEquals("username1", reportResDto.getUsername());
        Assert.assertEquals("Edited Title", reportResDto.getTitle());
        Assert.assertEquals("Edited description", reportResDto.getDescription());
        Assert.assertEquals("HIGH", reportResDto.getPriority());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void editReportOfWrongEmployeeTest() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        employee = entityManager.persistAndFlush(employee);
        Report report = ReportDataHelper.report(employee);
        long reportId = (long) entityManager.persistAndGetId(report);
        EditReportReqDto editReportReqDto = ReportDataHelper.editReportReqDto();

        //when
        // adding 404 to the existing employee's id
        ReportResDto reportResDto = reportService.editReportOfEmployee(employee.getId() + 404, reportId, editReportReqDto);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void editNonexistentReportTest() {
        //given
        EditReportReqDto editReportReqDto = ReportDataHelper.editReportReqDto();

        //when
        reportService.editReport(404L, editReportReqDto);
    }

    @Test
    public void findReportByIdTest() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        employee = entityManager.persistAndFlush(employee);
        Report report = ReportDataHelper.report(employee);
        long reportId = (long) entityManager.persistAndGetId(report);

        //when
        ReportResDto reportResDto = reportService.findReportById(reportId);

        //then
        Assert.assertNotNull(reportResDto);
        Assert.assertNotNull(reportResDto.getId());
        Assert.assertEquals(reportId, reportResDto.getId().longValue());
        Assert.assertEquals("username1", reportResDto.getUsername());
        Assert.assertEquals("Annual Report", reportResDto.getTitle());
        Assert.assertEquals("123", reportResDto.getDescription());
        Assert.assertEquals("HIGH", reportResDto.getPriority());
    }

    @Test
    public void findReportOfEmployeeByIdTest() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        employee = entityManager.persistAndFlush(employee);
        Report report = ReportDataHelper.report(employee);
        long reportId = (long) entityManager.persistAndGetId(report);

        //when
        ReportResDto reportResDto = reportService.findReportOfEmployeeById(employee.getId(), reportId);

        //then
        Assert.assertNotNull(reportResDto);
        Assert.assertNotNull(reportResDto.getId());
        Assert.assertEquals(reportId, reportResDto.getId().longValue());
        Assert.assertEquals("username1", reportResDto.getUsername());
        Assert.assertEquals("Annual Report", reportResDto.getTitle());
        Assert.assertEquals("123", reportResDto.getDescription());
        Assert.assertEquals("HIGH", reportResDto.getPriority());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findNonexistentReportByIdTest() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        employee = entityManager.persistAndFlush(employee);
        Report report = ReportDataHelper.report(employee);
        long reportId = (long) entityManager.persistAndGetId(report);
        //when
        ReportResDto reportResDto = reportService.findReportOfEmployeeById(employee.getId(), reportId);

        //then
        Assert.assertNotNull(reportResDto);

        //when
        reportService.findReportOfEmployeeById(404L, reportId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findExistentReportOfWrongEmployeeByIdTest() {
        //when
        reportService.findReportById(404L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteReportTest() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        employee = entityManager.persistAndFlush(employee);
        Report report = ReportDataHelper.report(employee);
        long reportId = (long) entityManager.persistAndGetId(report);

        //when
        ReportResDto reportResDto = reportService.findReportById(reportId);

        //then 
        Assert.assertNotNull(reportResDto);

        //when 
        reportService.deleteReport(reportId);
        reportService.findReportById(reportId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteReportOfEmployeeTest() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        employee = entityManager.persistAndFlush(employee);
        Report report = ReportDataHelper.report(employee);
        long reportId = (long) entityManager.persistAndGetId(report);

        //when
        ReportResDto reportResDto = reportService.findReportOfEmployeeById(employee.getId(), reportId);

        //then 
        Assert.assertNotNull(reportResDto);

        //when 
        reportService.deleteReportOfEmployee(employee.getId(), reportId);
        reportService.findReportOfEmployeeById(employee.getId(), reportId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteNonexistentReportTest() {
        //when
        reportService.deleteReport(404L);
    }

    @Test
    public void searchReportsByUsernameTest() {
        //given
        Employee employee1 = EmployeeDataHelper.employee(1L);
        employee1 = entityManager.persistAndFlush(employee1);

        Employee employee2 = EmployeeDataHelper.employee(2L);
        employee2 = entityManager.persistAndFlush(employee2);

        List<Report> reports = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            reports.add(ReportDataHelper.report(employee1)); // username1 (50 reports)
            // Add a report that should not be returned from the search.
            reports.add(ReportDataHelper.report(employee2)); // username2 (50 reports)
        }
        reports.stream().forEach(report -> entityManager.persist(report)); // 100 reports

        // Search criteria: useraname ~ "name1"
        ReportSearchCriteriaDto usernameAndHighPriority = ReportSearchCriteriaDto.builder().username("name1").build();
        PageRequestParams pageRequestParams = PageRequestParams.builder().size(10).build();

        //when
        Page<ReportResDto> reportResDtos = reportService.findReportsByCriteria(usernameAndHighPriority, pageRequestParams);

        //then
        Assert.assertNotNull(reportResDtos);
        Assert.assertEquals(5, reportResDtos.getTotalPages());
        Assert.assertEquals(50, reportResDtos.getTotalElements());
        Assert.assertEquals("username1", reportResDtos.getContent().get(0).getUsername());
        Assert.assertEquals("HIGH", reportResDtos.getContent().get(0).getPriority());
        Assert.assertEquals("Annual Report", reportResDtos.getContent().get(0).getTitle());
    }

    @Test
    public void searchReportsByPriorityTest() {
        //given
        Employee employee1 = EmployeeDataHelper.employee(1L);
        employee1 = entityManager.persistAndFlush(employee1);

        Employee employee2 = EmployeeDataHelper.employee(2L);
        employee2 = entityManager.persistAndFlush(employee2);

        List<Report> reports = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            reports.add(ReportDataHelper.report(employee1)); // username1 (50 reports)
            // Add a report that should not be returned from the search.
            Report lowPriorityReport = ReportDataHelper.report(employee1);
            lowPriorityReport.setPriority(Report.Priority.LOW);
            reports.add(lowPriorityReport); // username1, LOW priority
        }
        reports.stream().forEach(report -> entityManager.persist(report)); // 100 reports

        // Search criteria: useraname ~ "name1"
        ReportSearchCriteriaDto usernameAndHighPriority = ReportSearchCriteriaDto.builder().priority("HIGH").build();
        PageRequestParams pageRequestParams = PageRequestParams.builder().size(10).direction("desc").build();

        //when
        Page<ReportResDto> reportResDtos = reportService.findReportsByCriteria(usernameAndHighPriority, pageRequestParams);

        //then
        Assert.assertNotNull(reportResDtos);
        Assert.assertEquals(5, reportResDtos.getTotalPages());
        Assert.assertEquals(50, reportResDtos.getTotalElements());
        Assert.assertEquals("username1", reportResDtos.getContent().get(0).getUsername());
        Assert.assertEquals("HIGH", reportResDtos.getContent().get(0).getPriority());
        Assert.assertEquals("Annual Report", reportResDtos.getContent().get(0).getTitle());
        Assert.assertTrue(reportResDtos.getContent().get(0).getId() > reportResDtos.getContent().get(9).getId()); // check page sorting direction
    }

    @Test
    public void searchReportsByUsernameAndPriorityTest() {
        //given
        Employee employee1 = EmployeeDataHelper.employee(1L);
        employee1 = entityManager.persistAndFlush(employee1);

        Employee employee2 = EmployeeDataHelper.employee(2L);
        employee2 = entityManager.persistAndFlush(employee2);

        List<Report> reports = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            reports.add(ReportDataHelper.report(employee1)); // username1, HIGH priority
            // Add a report that should not be returned from the search.
            Report employee2Report = ReportDataHelper.report(employee2);
            employee2Report.setPriority(Report.Priority.LOW);
            reports.add(employee2Report); // username2, LOW priority
        }
        reports.stream().forEach(report -> entityManager.persist(report));

        // Search criteria: useraname ~ "ernam", priority = "HIGH"
        ReportSearchCriteriaDto usernameAndHighPriority = ReportSearchCriteriaDto.builder().username("ernam").priority("HIGH").build();
        PageRequestParams pageRequestParams = PageRequestParams.builder().size(10).direction("asc").build();

        //when
        Page<ReportResDto> reportResDtos = reportService.findReportsByCriteria(usernameAndHighPriority, pageRequestParams);

        //then
        Assert.assertNotNull(reportResDtos);
        Assert.assertEquals(5, reportResDtos.getTotalPages());
        Assert.assertEquals(50, reportResDtos.getTotalElements());
        Assert.assertEquals("username1", reportResDtos.getContent().get(0).getUsername());
        Assert.assertEquals("HIGH", reportResDtos.getContent().get(0).getPriority());
        Assert.assertEquals("Annual Report", reportResDtos.getContent().get(0).getTitle());
        Assert.assertTrue(reportResDtos.getContent().get(9).getId() > reportResDtos.getContent().get(0).getId()); // check page sorting direction
    }
}
