package com.tyrellcorp.reportingtool.service.dto.report;

import com.tyrellcorp.reportingtool.model.entity.Report;
import com.tyrellcorp.reportingtool.service.validation.constraint.ReportSearchCriteriaDtoConstraint;
import com.tyrellcorp.reportingtool.service.validation.constraint.ValueOfEnum;
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
