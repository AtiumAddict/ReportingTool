package com.company.reportingtool.validation;

import com.company.reportingtool.AbstractIntegrationTest;
import com.company.reportingtool.datahelper.ReportDataHelper;
import com.tyrellcorp.reportingtool.service.dto.report.CreateReportReqDto;
import com.tyrellcorp.reportingtool.service.service.report.ReportService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Set;

public class CreateReportValidationIT extends AbstractIntegrationTest {
    @Autowired
    private Validator validator;

    @Autowired
    private ReportService reportService;

    @Test
    public void validReportTest() {
        //given
        CreateReportReqDto createReportReqDto = ReportDataHelper.createReportReqDto(1L);

        //when
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(createReportReqDto);

        //then
        Assert.assertEquals(0, constraintViolations.size());
    }

    @Test
    public void invalidPriorityValidationTest() {
        //given
        CreateReportReqDto createReportReqDto = ReportDataHelper.invalidPriorityCreateReportReqDto(1L);

        //when
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(createReportReqDto);

        //then
        Assert.assertEquals(1, constraintViolations.size());
        Assert.assertEquals("ValueOfEnum", new ArrayList<ConstraintViolation>(constraintViolations).get(0)
                .getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
        Assert.assertTrue(new ArrayList<ConstraintViolation>(constraintViolations).get(0).getMessageTemplate()
                .equals("{value.of.enum.violation}"));
    }
}
