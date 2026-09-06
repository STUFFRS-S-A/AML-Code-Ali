package com.appopay.visa.Exception;

import com.appopay.visa.controller.DeviceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorResponse errorResponse = new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), ex.getMessage() != null ? ex.getMessage() : "An error occurred");
        log.error(errorResponse.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleCustomException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), ex.getMessage() != null ? ex.getMessage() : "An error occurred");
        log.error(errorResponse.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
