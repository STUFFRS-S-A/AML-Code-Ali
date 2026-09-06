package com.example.didit.app.exception;


import lombok.Data;

@Data
public class ErrorResponse {
    private final int status;
    private final String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
