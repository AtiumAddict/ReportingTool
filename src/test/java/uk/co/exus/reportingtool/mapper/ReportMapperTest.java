package uk.co.exus.reportingtool.mapper;

import org.junit.Assert;
import org.junit.Test;
import uk.co.exus.reportingtool.AbstractIntegrationTest;
import uk.co.exus.reportingtool.datahelper.EmployeeDataHelper;
import uk.co.exus.reportingtool.datahelper.ReportDataHelper;
import uk.co.exus.reportingtool.model.entity.employee.Employee;
import uk.co.exus.reportingtool.model.entity.report.Report;
import uk.co.exus.reportingtool.service.dto.report.CreateReportReqDto;
import uk.co.exus.reportingtool.service.dto.report.EditReportReqDto;
import uk.co.exus.reportingtool.service.dto.report.ReportResDto;
import uk.co.exus.reportingtool.service.mapper.ReportMapper;

public class ReportMapperTest extends AbstractIntegrationTest {

    @Test
    public void reportToReportResDto() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        employee.setId(1L);
        Report report = ReportDataHelper.report(employee);

        //when
        ReportResDto reportResDto = ReportMapper.INSTANCE.toReportResDto(report);

        //then
        Assert.assertNotNull(reportResDto);
        Assert.assertEquals("username1", reportResDto.getUsername());
        Assert.assertEquals("Annual Report", reportResDto.getTitle());
        Assert.assertEquals("123", reportResDto.getDescription());
        Assert.assertEquals("HIGH", reportResDto.getPriority());
    }

    @Test
    public void createReportReqDtoToReport() {
        //given
        CreateReportReqDto createReportReqDto = ReportDataHelper.createReportReqDto(1L);

        //when
        Report report = ReportMapper.INSTANCE.toReport(createReportReqDto);

        //then
        Assert.assertNotNull(report);
        Assert.assertEquals(1L, report.getEmployee().getId().longValue());
        Assert.assertEquals("Daily Report", report.getTitle());
        Assert.assertEquals("321", report.getDescription());
        Assert.assertEquals(Report.Priority.LOW, report.getPriority());
    }

    @Test
    public void updateReport() {
        //given
        Employee employee = EmployeeDataHelper.employee(1L);
        employee.setId(1L);
        Report report = ReportDataHelper.report(employee);
        EditReportReqDto editReportReqDto = ReportDataHelper.editReportReqDto();

        //when
        ReportMapper.INSTANCE.updateReport(editReportReqDto, report);

        //then
        Assert.assertNotNull(report);
        Assert.assertEquals(1L, report.getEmployee().getId().longValue());
        Assert.assertEquals("Edited Title", report.getTitle());
        Assert.assertEquals("Edited description", report.getDescription());
        Assert.assertEquals(Report.Priority.HIGH, report.getPriority());
    }
}
