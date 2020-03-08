package uk.co.exus.reportingtool.service.dto.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Used as a response for reports.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportResDto {
    private String username;

    private Long id;

    private String title;

    private String description;

    private String priority;

    private LocalDateTime createdOn;

    private LocalDateTime editedOn;
}
