package uk.co.exus.reportingtool.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import uk.co.exus.reportingtool.AbstractIntegrationTest;
import uk.co.exus.reportingtool.datahelper.EmployeeDataHelper;
import uk.co.exus.reportingtool.datahelper.ReportDataHelper;
import uk.co.exus.reportingtool.exception.ResourceNotFoundException;
import uk.co.exus.reportingtool.model.entity.employee.Employee;
import uk.co.exus.reportingtool.model.entity.report.Report;
import uk.co.exus.reportingtool.service.dto.PageRequestParams;
import uk.co.exus.reportingtool.service.dto.report.CreateReportReqDto;
import uk.co.exus.reportingtool.service.dto.report.EditReportReqDto;
import uk.co.exus.reportingtool.service.dto.report.ReportResDto;
import uk.co.exus.reportingtool.service.dto.report.ReportSearchCriteriaDto;
import uk.co.exus.reportingtool.service.service.report.ReportService;

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

    @Test(expected = ResourceNotFoundException.class)
    public void findNonexistentReportByIdTest() {
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
