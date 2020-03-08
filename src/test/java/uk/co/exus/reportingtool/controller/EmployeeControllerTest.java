package com.company.reportingtool.controller;

import com.company.reportingtool.datahelper.EmployeeDataHelper;
import com.company.reportingtool.exception.ResourceNotFoundException;
import com.company.reportingtool.service.dto.employee.CreateEmployeeReqDto;
import com.company.reportingtool.service.dto.employee.EditEmployeeReqDto;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeControllerTest extends AbstractMvcTest {
    @Test
    public void findEmployeeByIdTest() throws Exception {
        //when
        Mockito.when(employeeService.findEmployeeById(1L)).thenReturn(EmployeeDataHelper.employeeDetailsResDto(1));

        //then
        mockMvc.perform(get("/employees/1"))
                .andExpect(jsonPath("$.firstName", is("Karl")))
                .andExpect(jsonPath("$.lastName", is("Pilkington")))
                .andExpect(jsonPath("$.email", is("k.pilkington@company.co.uk")))
                .andExpect(status().isOk());
    }

    @Test
    public void findNonExistentEmployeeByIdTest() throws Exception {
        //when
        Mockito.when(employeeService.findEmployeeById(404L)).thenThrow(ResourceNotFoundException.class);

        //then
        mockMvc.perform(get("/employees/404"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createNewEmployeeTest() throws Exception {
        //when
        CreateEmployeeReqDto createEmployeeReqDto = EmployeeDataHelper.createNewEmployeeReqDto();
        Mockito.when(employeeService.createEmployee(createEmployeeReqDto)).thenReturn(EmployeeDataHelper.employeeDetailsResDto(1L));

        //then
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createEmployeeReqDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments", is("Karl is the best")))
                .andExpect(jsonPath("$.email", is("k.pilkington@company.co.uk")))
                .andExpect(jsonPath("$.firstName", is("Karl")));
    }

    @Test
    public void editEmployeeTest() throws Exception {
        //when
        EditEmployeeReqDto editEmployeeReqDto = EmployeeDataHelper.editEmployeeReqDto();
        Mockito.when(employeeService.editEmployee(1L, editEmployeeReqDto)).thenReturn(EmployeeDataHelper.employeeDetailsResDto(1L));

        //then
        mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editEmployeeReqDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments", is("Karl is the best")))
                .andExpect(jsonPath("$.email", is("k.pilkington@company.co.uk")))
                .andExpect(jsonPath("$.firstName", is("Karl")));
    }

    @Test
    public void findEmployeeByUsernameTest() throws Exception {
        //when
        Mockito.when(employeeService.findEmployeeByUsername("kpilkington")).thenReturn(EmployeeDataHelper.employeeDetailsResDto(1));

        //then
        mockMvc.perform(get("/employees")
                .param("username", "kpilkington"))
                .andExpect(jsonPath("$.firstName", is("Karl")))
                .andExpect(jsonPath("$.lastName", is("Pilkington")))
                .andExpect(jsonPath("$.email", is("k.pilkington@company.co.uk")))
                .andExpect(status().isOk());
    }
}
