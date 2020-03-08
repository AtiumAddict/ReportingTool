package com.company.reportingtool.service.dto.report;

import com.company.reportingtool.model.entity.Report;
import com.company.reportingtool.service.validation.constraint.ValueOfEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Used to create new reports.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateReportReqDto {
    @NotNull
    private Long employeeId;

    @NotNull
    private String title;

    private String description;

    @ValueOfEnum(enumClass = Report.Priority.class)
    private String priority;
}