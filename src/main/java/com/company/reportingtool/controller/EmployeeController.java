package com.company.reportingtool.controller;

import com.company.reportingtool.service.dto.employee.CreateEmployeeReqDto;
import com.company.reportingtool.service.dto.employee.EditEmployeeReqDto;
import com.company.reportingtool.service.dto.employee.EmployeeDetailsResDto;
import com.company.reportingtool.service.service.employee.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public EmployeeDetailsResDto findEmployeeByUsername(String username) {
        log.info("findEmployeeByUsername with username {}", username);
        return employeeService.findEmployeeByUsername(username);
    }
}
