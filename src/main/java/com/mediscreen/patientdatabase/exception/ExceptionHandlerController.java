package com.mediscreen.patientdatabase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = PatientNotFoundException.class)
    public ResponseEntity<Object> handlePatientNotFoundException(PatientNotFoundException p){
        //1. Create payload containing exception details
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        PatientException patientException = new PatientException(p.getMessage(), notFound, ZonedDateTime.now()
        );
        //2. Return response entity
        return new ResponseEntity<>(patientException, notFound);
    }
}
