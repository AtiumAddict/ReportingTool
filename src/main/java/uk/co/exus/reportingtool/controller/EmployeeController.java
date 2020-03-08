package uk.co.exus.reportingtool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.exus.reportingtool.service.dto.employee.CreateEmployeeReqDto;
import uk.co.exus.reportingtool.service.dto.employee.EditEmployeeReqDto;
import uk.co.exus.reportingtool.service.dto.employee.EmployeeDetailsResDto;
import uk.co.exus.reportingtool.service.service.employee.EmployeeService;

import javax.validation.Valid;

@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDetailsResDto> createEmployee(@Valid @RequestBody CreateEmployeeReqDto createEmployeeReqDto) {
        log.info("createNewEmployee: {}", createEmployeeReqDto);
        return ResponseEntity.ok(employeeService.createEmployee(createEmployeeReqDto));
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDetailsResDto> editEmployee(@PathVariable Long employeeId, @Valid @RequestBody EditEmployeeReqDto editEmployeeReqDto) {
        log.info("editEmployee: {}", editEmployeeReqDto);
        return ResponseEntity.ok(employeeService.editEmployee(employeeId, editEmployeeReqDto));
    }

    @GetMapping("/{employeeId}")
    public EmployeeDetailsResDto findEmployeeById(@PathVariable Long employeeId) {
        log.info("findEmployeeById with id {}", employeeId);
        return employeeService.findEmployeeById(employeeId);
    }
}
