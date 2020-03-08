package com.tyrellcorp.reportingtool.service.validation.validator;

import com.tyrellcorp.reportingtool.service.service.FieldValueExists;
import com.tyrellcorp.reportingtool.service.validation.constraint.Unique;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class UniqueValidator implements ConstraintValidator<Unique, Object> {
    @Autowired
    private ApplicationContext applicationContext;

    private FieldValueExists service;
    private String fieldName;

    @Override
    public void initialize(Unique unique) {
        Class<? extends FieldValueExists> clazz = unique.service();
        this.fieldName = unique.fieldName();
        String serviceQualifier = unique.serviceQualifier();

        if (!serviceQualifier.equals("")) {
            this.service = this.applicationContext.getBean(serviceQualifier, clazz);
        } else {
            this.service = this.applicationContext.getBean(clazz);
        }
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (this.service.fieldValueExists(o, this.fieldName)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("{unique.value.violation}")
                    .addBeanNode()
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
