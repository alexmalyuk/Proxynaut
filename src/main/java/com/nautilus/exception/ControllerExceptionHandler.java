package com.nautilus.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleInvalidTopUpTypeException(Exception ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ResponseStatusException.class})
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex) {
        return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
    }

}
