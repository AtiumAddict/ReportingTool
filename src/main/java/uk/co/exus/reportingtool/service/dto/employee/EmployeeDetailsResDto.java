package uk.co.exus.reportingtool.service.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Used as a response for create, edit and find employee by id. Contains all of the details of an employee.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDetailsResDto {
    private long id;

    private String title;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String gender;

    private String comments;
}
