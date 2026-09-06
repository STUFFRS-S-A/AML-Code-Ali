package com.example.didit.app.controller.advice;

import com.example.didit.app.exception.BusinessException;
import com.example.didit.app.exception.CustomException;
import com.example.didit.app.exception.ErrorResponse;
import com.example.didit.app.model.ApiResponse;
import com.example.didit.app.util.ResponseCodes;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
@Hidden
public class AdviceControllerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AdviceControllerExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException ex) {
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage() != null ? ex.getMessage() : "An error occurred");
//        log.error(errorResponse.toString());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(
                ApiResponse.fail(HttpStatus.BAD_REQUEST.toString(), ex.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage() != null ? ex.getMessage() : "An error occurred");
        log.error(errorResponse.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException exception) {
        return ResponseEntity.badRequest().body(
                ApiResponse.fail(exception.getErrorCode(), exception.getArgs())
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.badRequest().body(
                ApiResponse.fail(ResponseCodes.LoginFailed, ex.getMessage())
        );
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ApiResponse<Void>> handleLockedException(LockedException ex) {
        return ResponseEntity.badRequest().body(
                ApiResponse.fail(ResponseCodes.UserIsLocked, ex.getMessage())
        );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiResponse<Void>> handleDisabledException(DisabledException ex) {
        return ResponseEntity.badRequest().body(
                ApiResponse.fail(ResponseCodes.UserIsDisabled, ex.getMessage())
        );
    }
}
