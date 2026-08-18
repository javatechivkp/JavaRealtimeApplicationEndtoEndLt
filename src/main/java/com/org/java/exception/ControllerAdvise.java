package com.org.java.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvise extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoIdFoundException.class)
    public ResponseEntity<String> NoIdemptyInputExceptionHandller(NoIdFoundException emptyInputException){
        return new ResponseEntity<String>("id is not found please look it once",HttpStatus.BAD_REQUEST);

    }
}