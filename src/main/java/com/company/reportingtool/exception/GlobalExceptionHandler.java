package com.company.reportingtool.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.Optional;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({ResourceNotFoundException.class, NoResultException.class})
    public final ResponseEntity<Object> handleNotFoundExceptions(Exception ex, WebRequest request) {
        log.error("handleNotFoundExceptions {} :  {}", ex.getClass(), ex.getMessage());
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(IllegalStateException.class)
    public final ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        log.error("handleIllegalStateException {} :  {}", ex.getClass(), ex.getMessage());
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({IllegalArgumentException.class, UnsupportedOperationException.class})
    public final ResponseEntity<Object> handleIllegalArgumentAndUnsupportedOperationException(Exception ex, WebRequest request) {
        log.error("handleIllegalArgumentAndUnsupportedOperationException {} :  {}", ex.getClass(), ex.getMessage());
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        String cause = Optional.ofNullable(ex.getCause()).map(Throwable::getMessage).orElse("");
        String extendedMessage = new StringBuilder(ex.getMessage()).append(" caused By : ").append(cause).toString();
        log.error("handleConstraintViolationException {} causedBy: {}", ex.getMessage(), cause);

        return handleExceptionInternal(new ConstraintViolationException(extendedMessage, ex.getConstraintViolations()), null,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleMethodArgumentNotValid {} :  {}", ex.getClass(), ex.getMessage());
        return this.handleExceptionInternal(ex, new ApiErrorDto(ex.getBindingResult()), headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        // Adapter needs to return simple text
        if (Objects.equals(request.getHeader(HttpHeaders.ACCEPT), MediaType.TEXT_PLAIN_VALUE)) {
            return new ResponseEntity<>(ex.getMessage(), status);
        }

        //FOR debug purposes. Remove when not needed
        if (body == null) {
            body = new ApiErrorDto(ex.getMessage(), ex);
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

}
