package uk.co.exus.reportingtool.service.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.exus.reportingtool.model.entity.Employee;
import uk.co.exus.reportingtool.service.validation.constraint.ValueOfEnum;

import javax.validation.constraints.NotNull;

/**
 * Used to edit existing employee details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditEmployeeReqDto {
    @ValueOfEnum(enumClass = Employee.Title.class)
    private String title;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @ValueOfEnum(enumClass = Employee.Gender.class)
    private String gender;

    private String comments;
}
