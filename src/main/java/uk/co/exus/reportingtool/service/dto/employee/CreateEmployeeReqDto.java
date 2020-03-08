package uk.co.exus.reportingtool.service.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.exus.reportingtool.model.entity.Employee;
import uk.co.exus.reportingtool.service.service.employee.EmployeeService;
import uk.co.exus.reportingtool.service.validation.constraint.Unique;
import uk.co.exus.reportingtool.service.validation.constraint.ValueOfEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * Used to create new employees.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEmployeeReqDto {
    @ValueOfEnum(enumClass = Employee.Title.class)
    private String title;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Unique(service = EmployeeService.class, fieldName = "username")
    private String username;

    @NotNull
    @Email
    @Unique(service = EmployeeService.class, fieldName = "email")
    private String email;

    @ValueOfEnum(enumClass = Employee.Gender.class)
    private String gender;

    private String comments;
}
