package uk.co.exus.reportingtool.datahelper;

import uk.co.exus.reportingtool.model.entity.employee.Employee;
import uk.co.exus.reportingtool.service.dto.employee.CreateEmployeeReqDto;
import uk.co.exus.reportingtool.service.dto.employee.EditEmployeeReqDto;
import uk.co.exus.reportingtool.service.dto.employee.EmployeeDetailsResDto;

public class EmployeeDataHelper {
    public static Employee employee(long id) {
        Employee employee = new Employee();
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setUsername("username" + id);
        employee.setEmail("email" + id + "@exus.co.uk");
        employee.setTitle(Employee.Title.MS);
        employee.setGender(Employee.Gender.FEMALE);
        employee.setComments("comments");

        return employee;
    }

    public static CreateEmployeeReqDto createNewEmployeeReqDto() {
        CreateEmployeeReqDto newEmployeeReqDto = CreateEmployeeReqDto.builder()
                .title("MR")
                .firstName("Karl")
                .lastName("Pilkington")
                .username("kpilkington")
                .email("k.pilkington@exus.co.uk")
                .gender("MALE")
                .comments("Karl is the best")
                .build();

        return newEmployeeReqDto;
    }

    public static CreateEmployeeReqDto duplicateUsernameCreateNewEmployeeReqDto() {
        CreateEmployeeReqDto newEmployeeReqDto = createNewEmployeeReqDto();
        newEmployeeReqDto.setUsername("username1");

        return newEmployeeReqDto;
    }

    public static CreateEmployeeReqDto duplicateEmailCreateNewEmployeeReqDto() {
        CreateEmployeeReqDto newEmployeeReqDto = createNewEmployeeReqDto();
        newEmployeeReqDto.setEmail("email1@exus.co.uk");

        return newEmployeeReqDto;
    }

    public static EditEmployeeReqDto editEmployeeReqDto() {
        EditEmployeeReqDto editEmployeeReqDto = EditEmployeeReqDto.builder()
                .title("MR")
                .firstName("Karl")
                .lastName("Pilkington")
                .gender("MALE")
                .comments("Karl is the best")
                .build();

        return editEmployeeReqDto;
    }

    public static EmployeeDetailsResDto employeeDetailsResDto(long id) {
        EmployeeDetailsResDto employeeDetailsResDto = EmployeeDetailsResDto.builder()
                .id(id)
                .title("MR")
                .firstName("Karl")
                .lastName("Pilkington")
                .username("kpilkington")
                .email("k.pilkington@exus.co.uk")
                .gender("MALE")
                .comments("Karl is the best")
                .build();

        return employeeDetailsResDto;
    }
}
