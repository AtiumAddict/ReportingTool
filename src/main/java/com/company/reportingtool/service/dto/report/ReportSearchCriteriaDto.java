package com.company.reportingtool.service.dto.report;

import com.company.reportingtool.model.entity.Report;
import com.company.reportingtool.service.validation.constraint.ReportSearchCriteriaDtoConstraint;
import com.company.reportingtool.service.validation.constraint.ValueOfEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
