package com.company.reportingtool.mapper;

import com.company.reportingtool.AbstractIntegrationTest;
import com.company.reportingtool.datahelper.EmployeeDataHelper;
import com.company.reportingtool.datahelper.ReportDataHelper;
import com.tyrellcorp.reportingtool.model.entity.Employee;
import com.tyrellcorp.reportingtool.model.entity.Report;
import com.tyrellcorp.reportingtool.service.dto.report.CreateReportReqDto;
import com.tyrellcorp.reportingtool.service.dto.report.EditReportReqDto;
import com.tyrellcorp.reportingtool.service.dto.report.ReportResDto;
import com.tyrellcorp.reportingtool.service.mapper.ReportMapper;
import org.junit.Assert;
import org.junit.Test;

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
