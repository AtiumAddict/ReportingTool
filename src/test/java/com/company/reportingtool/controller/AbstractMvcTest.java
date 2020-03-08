package com.company.reportingtool.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyrellcorp.reportingtool.ReportingToolApplication;
import com.tyrellcorp.reportingtool.service.service.employee.EmployeeService;
import com.tyrellcorp.reportingtool.service.service.report.ReportService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = ReportingToolApplication.class)
@ActiveProfiles("test")
public abstract class AbstractMvcTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected EmployeeService employeeService;

    @MockBean
    protected ReportService reportService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Before
    public void init() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

}
