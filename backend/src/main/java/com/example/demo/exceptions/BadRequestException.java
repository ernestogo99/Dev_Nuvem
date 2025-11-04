package com.example.demo.exceptions;


import org.springframework.validation.BindingResult;

public class BadRequestException extends RuntimeException{
    private BindingResult bindingResult;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

    public BadRequestException(String message, BindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }

    public BadRequestException(String message, Throwable cause, BindingResult bindingResult) {
        super(message, cause);
        this.bindingResult = bindingResult;
    }
}
