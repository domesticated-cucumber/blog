package com.leverx.shishlo.blog.controller;

import com.leverx.shishlo.blog.exception.BlogException;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BlogException.class)
    protected ResponseEntity<String> blogException(BlogException blogException){
        return new ResponseEntity<>(blogException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PropertyValueException.class)
    protected ResponseEntity<String> validateException(PropertyValueException propertyValueException){
        return new ResponseEntity<>(propertyValueException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
