package com.company.reportingtool.datahelper;

import com.company.reportingtool.model.entity.Employee;
import com.company.reportingtool.model.entity.Report;
import com.company.reportingtool.service.dto.report.CreateReportReqDto;
import com.company.reportingtool.service.dto.report.EditReportReqDto;
import com.company.reportingtool.service.dto.report.ReportResDto;

public class ReportDataHelper {
    public static Report report(Employee employee) {
        Report report = new Report();
        report.setEmployee(employee);
        report.setTitle("Annual Report");
        report.setDescription("123");
        report.setPriority(Report.Priority.HIGH);

        return report;
    }

    public static CreateReportReqDto createReportReqDto(long employeeId) {
        CreateReportReqDto createReportReqDto = CreateReportReqDto.builder()
                .employeeId(employeeId)
                .title("Daily Report")
                .description("321")
                .priority("LOW")
                .build();

        return createReportReqDto;
    }

    public static CreateReportReqDto invalidPriorityCreateReportReqDto(long employeeId) {
        CreateReportReqDto createReportReqDto = CreateReportReqDto.builder()
                .employeeId(employeeId)
                .title("Daily Report")
                .description("321")
                .priority("INVALID PRIORITY")
                .build();

        return createReportReqDto;
    }

    public static EditReportReqDto editReportReqDto() {
        EditReportReqDto editReportReqDto = EditReportReqDto.builder()
                .title("Edited Title")
                .description("Edited description")
                .priority("HIGH")
                .build();

        return editReportReqDto;
    }

    public static ReportResDto reportResDto(long id) {
        ReportResDto reportResDto = ReportResDto.builder()
                .username("username")
                .id(id)
                .title("Monthly Report")
                .description("A monthly report")
                .priority("LOW")
                .build();

        return reportResDto;
    }
}
