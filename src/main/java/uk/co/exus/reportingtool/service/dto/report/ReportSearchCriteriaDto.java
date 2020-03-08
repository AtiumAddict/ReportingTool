package uk.co.exus.reportingtool.service.dto.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.exus.reportingtool.model.entity.report.Report;
import uk.co.exus.reportingtool.service.validation.constraint.ReportSearchCriteriaDtoConstraint;
import uk.co.exus.reportingtool.service.validation.constraint.ValueOfEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ReportSearchCriteriaDtoConstraint
public class ReportSearchCriteriaDto {
    private String username;

    @ValueOfEnum(enumClass = Report.Priority.class)
    private String priority;
}
