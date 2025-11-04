package com.example.demo.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;

@RestController
@ControllerAdvice
class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private final FieldErrorCollector fieldErrorCollector;
    private Logger logger = LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);

    public CustomizedResponseEntityExceptionHandler(FieldErrorCollector fieldErrorCollector) {
        this.fieldErrorCollector = fieldErrorCollector;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(WebRequest webRequest, Exception e) {
        return buildResponse(webRequest, e, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleUsernameNotFoundException(WebRequest webRequest, UsernameNotFoundException e) {
        return buildResponse(webRequest, e, null, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionDto> handleBadCredentialsException(WebRequest webRequest, BadCredentialsException e) {
        return buildResponse(webRequest, e, null, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDto> handleBadRequestException(WebRequest webRequest, BadRequestException e) {
        return buildResponse(webRequest, e, e.getBindingResult(), HttpStatus.BAD_REQUEST);
    }

   
    private ResponseEntity<ExceptionDto> buildResponse(WebRequest webRequest, Exception e, BindingResult bindingResult, HttpStatus httpStatus) {
        List<FieldError> errors = fieldErrorCollector.collect(bindingResult);
        ExceptionDto body =
                new ExceptionDto(
                        errors,
                        Instant.now(),
                        httpStatus.getReasonPhrase(),
                        httpStatus.value(),
                        e.getMessage(),
                        webRequest.getDescription(false));
        return ResponseEntity
                .status(httpStatus)
                .body(body);
    }
}
