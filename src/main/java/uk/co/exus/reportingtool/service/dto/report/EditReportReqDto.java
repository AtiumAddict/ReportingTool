package uk.co.exus.reportingtool.service.dto.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.exus.reportingtool.model.entity.report.Report;
import uk.co.exus.reportingtool.service.validation.constraint.ValueOfEnum;

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
