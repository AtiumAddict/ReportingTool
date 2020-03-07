package uk.co.exus.reportingtool.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.exus.reportingtool.ReportingToolApplication;
import uk.co.exus.reportingtool.model.repository.EmployeeRepository;
import uk.co.exus.reportingtool.service.service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = ReportingToolApplication.class)
@ActiveProfiles("test")
public abstract class AbstractMvcTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected EmployeeRepository employeeRepository;

    @MockBean
    protected EmployeeService employeeService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Before
    public void init() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

}
