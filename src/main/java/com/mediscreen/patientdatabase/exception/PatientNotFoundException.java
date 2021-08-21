package com.mediscreen.patientdatabase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public PatientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
