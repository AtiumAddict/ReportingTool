package com.tyrellcorp.reportingtool.exception;

import lombok.Data;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ApiErrorDto {
    /**
     * field validation errors
     */
    private List<FieldError> fieldErrors;

    /**
     * Generic message
     */
    private String message;

    /**
     * Exception, if any
     */
    private String exception;

    /**
     * DraftStatus code of response
     */
    private HttpStatus status;

    public ApiErrorDto(BindingResult bindingResult) {
        this.fieldErrors = bindingResult.getFieldErrors();
        if (bindingResult.getAllErrors() != null) {
            this.message = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
        }
    }

    public ApiErrorDto(List<FieldError> fieldErrors, String message) {
        this(fieldErrors, message, null, null);
    }

    public ApiErrorDto(Exception ex, HttpStatus httpStatus) {
        this(null, null, ex, httpStatus);
    }

    public ApiErrorDto(List<FieldError> fieldErrors, String message, Exception exception, HttpStatus status) {
        this.fieldErrors = fieldErrors;
        this.message = message;
        if (exception != null) {
            StringWriter stringWriter = new StringWriter();
            exception.printStackTrace(new PrintWriter(stringWriter));
            this.exception = stringWriter.toString();
        }
        this.status = status;
    }

    public ApiErrorDto(String message, Exception ex) {
        this(null, message, ex, null);
    }


}
