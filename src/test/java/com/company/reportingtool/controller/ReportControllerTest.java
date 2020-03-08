package com.company.reportingtool.controller;

import com.company.reportingtool.datahelper.ReportDataHelper;
import com.tyrellcorp.reportingtool.exception.ResourceNotFoundException;
import com.tyrellcorp.reportingtool.service.dto.report.CreateReportReqDto;
import com.tyrellcorp.reportingtool.service.dto.report.EditReportReqDto;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReportControllerTest extends AbstractMvcTest {
    @Test
    public void findReportByIdTest() throws Exception {
        //when
        Mockito.when(reportService.findReportById(1L)).thenReturn(ReportDataHelper.reportResDto(1));

        //then
        mockMvc.perform(get("/reports/1"))
                .andExpect(jsonPath("$.username", is("username")))
                .andExpect(jsonPath("$.title", is("Monthly Report")))
                .andExpect(jsonPath("$.description", is("A monthly report")))
                .andExpect(jsonPath("$.priority", is("LOW")))
                .andExpect(status().isOk());
    }

    @Test
    public void findNonExistentReportByIdTest() throws Exception {
        //when
        Mockito.when(reportService.findReportById(404L)).thenThrow(ResourceNotFoundException.class);

        //then
        mockMvc.perform(get("/reports/404"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createReportTest() throws Exception {
        //when
        CreateReportReqDto createReportReqDto = ReportDataHelper.createReportReqDto(1L);
        Mockito.when(reportService.createReport(createReportReqDto)).thenReturn(ReportDataHelper.reportResDto(1L));

        //then
        mockMvc.perform(post("/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createReportReqDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("username")))
                .andExpect(jsonPath("$.title", is("Monthly Report")))
                .andExpect(jsonPath("$.description", is("A monthly report")))
                .andExpect(jsonPath("$.priority", is("LOW")));
    }

    @Test
    public void editReportTest() throws Exception {
        //when
        EditReportReqDto editReportReqDto = ReportDataHelper.editReportReqDto();
        Mockito.when(reportService.editReport(1L, editReportReqDto)).thenReturn(ReportDataHelper.reportResDto(1L));

        //then
        mockMvc.perform(put("/reports/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editReportReqDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("username")))
                .andExpect(jsonPath("$.title", is("Monthly Report")))
                .andExpect(jsonPath("$.description", is("A monthly report")))
                .andExpect(jsonPath("$.priority", is("LOW")));
    }

    @Test
    public void editNonexistentReportTest() throws Exception {
        //when
        EditReportReqDto editReportReqDto = ReportDataHelper.editReportReqDto();
        Mockito.when(reportService.editReport(404L, editReportReqDto)).thenThrow(ResourceNotFoundException.class);

        //then
        mockMvc.perform(put("/reports/404")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editReportReqDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteReportTest() throws Exception {
        mockMvc.perform(delete("/reports/1"))
                .andExpect(status().is(204));
    }
}
