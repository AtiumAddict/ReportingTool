package com.tyrellcorp.reportingtool.service.dto.report;

import com.tyrellcorp.reportingtool.model.entity.Report;
import com.tyrellcorp.reportingtool.service.validation.constraint.ValueOfEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Used to edit existing reports.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditReportReqDto {
    @NotNull
    private String title;

    private String description;

    @ValueOfEnum(enumClass = Report.Priority.class)
    private String priority;
}
