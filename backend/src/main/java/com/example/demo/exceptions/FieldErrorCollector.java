package com.example.demo.exceptions;


import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Component
class FieldErrorCollector {
    public List<FieldError> collect(BindingResult bindingResult){
        if(bindingResult == null || bindingResult.getFieldErrors().isEmpty()){
            return null;
        }
        return bindingResult.getFieldErrors()
                .stream()
                .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}